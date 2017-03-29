package com.twitterclient.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ProcessModelTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;
import com.twitterclient.R;
import com.twitterclient.adapters.TimelineRecyclerAdapter;
import com.twitterclient.fragments.ComposeTweetFragment;
import com.twitterclient.helpers.EndlessRecyclerViewScrollListener;
import com.twitterclient.models.Tweet;
import com.twitterclient.models.TwitterDatabase;
import com.twitterclient.network.NetworkUtils;
import com.twitterclient.network.TwitterClient;
import com.twitterclient.network.TwitterClientApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class TimelineActivity extends AppCompatActivity
        implements ComposeTweetFragment.ComposeTweetListener {

    List<Tweet> tweets;
    RecyclerView recyclerView;
    TimelineRecyclerAdapter adapter;

    TwitterClient twitterCLient;

    DividerItemDecoration dividerItemDecoration;
    LinearLayoutManager layoutManager;
    EndlessRecyclerViewScrollListener scrollListener;
    SwipeRefreshLayout swipeRefreshLayout;

    static long maxTweetId = -1;
    static long sinceId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        /**
         * Code to handle implicit intents
         */
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                String urlOfPage = intent.getStringExtra(Intent.EXTRA_TEXT);
                openComposeFrag(urlOfPage);
            }
        }

        /**
         * Compose button
         */
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openComposeFrag(null);
            }
        });

        tweets = new ArrayList<>();
        if(!NetworkUtils.isNetworkAvailable(this) || !NetworkUtils.isOnline()) {
            populateTimelineFromDb();
        }

        adapter = new TimelineRecyclerAdapter(this,tweets);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);

        recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());

        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                loadNextDataFromApi();
            }
        };
        recyclerView.addOnScrollListener(scrollListener);
        twitterCLient = TwitterClientApplication.getTwitterClient();

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                populateTimeline(-1, 1);
            }
        });

        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light);

        /**
         * Calling populate time line with since id of 1 for loading the initial tweets
         */
        if(NetworkUtils.isNetworkAvailable(this) && NetworkUtils.isOnline()) {
            populateTimeline(maxTweetId, sinceId);
        }
    }

    /**
     * Override this function to close the app from the timeline activity
     */
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    private void populateTimeline(long maxId, final long sinceId) {

        twitterCLient.getTimeline(maxId, sinceId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                if(sinceId==1) {
                    tweets.clear();
                    adapter.notifyDataSetChanged();
                }
                Log.d("DEBUG", response.toString());
                List<Tweet> respTweets = new ArrayList<>();
                Gson gson = new Gson();
                for(int x = 0; x < response.length();x++) {
                    try {
                        Tweet tweet = gson.fromJson(response.getJSONObject(x).toString(),
                                Tweet.class);
                        respTweets.add(tweet);

                        maxTweetId = tweet.getId();

                    } catch (JSONException e) {
                        //TODO:
                    }
                }

                tweets.addAll(respTweets);
                int curSize = adapter.getItemCount();
                adapter.notifyItemRangeInserted(curSize, tweets.size()-1);

                swipeRefreshLayout.setRefreshing(false);
                if(sinceId==1) {
                    scrollListener.resetState();
                    layoutManager.scrollToPosition(0);
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONObject errorResponse) {
                swipeRefreshLayout.setRefreshing(false);
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    /**
     * Code for infinite scroll
     */
    public void loadNextDataFromApi() {
        populateTimeline(maxTweetId,-1);
    }

    public void openComposeFrag(String tweet) {
        FragmentManager fm = getSupportFragmentManager();
        ComposeTweetFragment fragment = ComposeTweetFragment.getInstance(tweet);
        fragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_FullScreen);
        fragment.show(fm,"compose_frag");
    }

    @Override
    public void onFinishTweet(Tweet tweet) {
        tweets.add(0,tweet);
        adapter.notifyItemInserted(0);
        layoutManager.scrollToPosition(0);
    }

    public TwitterClient getTwitterClient() {
        return twitterCLient;
    }

    /**
     * Code to save to db
     */
    @Override
    protected void onStop() {

        FlowManager.getDatabase(TwitterDatabase.class)
                .beginTransactionAsync(new ProcessModelTransaction.Builder<>(
                        new ProcessModelTransaction.ProcessModel<Tweet>() {
                            @Override
                            public void processModel(Tweet tweet, DatabaseWrapper wrapper) {
                                tweet.setImageUrl(tweet.getEntities().getMedia().get(0).getMediaUrlHttps()+":medium");
                                tweet.save();
                            }
                        }).addAll(tweets).build())
                .error(new Transaction.Error() {
                    @Override
                    public void onError(Transaction transaction, Throwable error) {
                        Log.e("ERROR", error.getMessage());
                    }
                })
                .success(new Transaction.Success() {
                    @Override
                    public void onSuccess(Transaction transaction) {
                        Log.d("DEBUG", transaction.toString());
                    }
                }).build().execute();

        super.onStop();
    }

    /**
     * Code to populate from database when no internet connection
     */
    public void populateTimelineFromDb() {
        List<Tweet> tweetsFromDb = SQLite.select().from(Tweet.class).limit(50).queryList();
        tweets.addAll(tweetsFromDb);

    }

}
