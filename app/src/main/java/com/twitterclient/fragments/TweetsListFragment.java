package com.twitterclient.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.twitterclient.R;
import com.twitterclient.adapters.TimelineRecyclerAdapter;
import com.twitterclient.helpers.EndlessRecyclerViewScrollListener;
import com.twitterclient.models.Tweet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public abstract class TweetsListFragment extends Fragment {

    List<Tweet> tweets;
    RecyclerView recyclerView;
    TimelineRecyclerAdapter adapter;
    ProgressBar progressBar;

    DividerItemDecoration dividerItemDecoration;
    LinearLayoutManager layoutManager;
    EndlessRecyclerViewScrollListener scrollListener;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tweets = new ArrayList<>();
        adapter = new TimelineRecyclerAdapter(getActivity(),tweets);

        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
        dividerItemDecoration = new DividerItemDecoration(getActivity(),
                layoutManager.getOrientation());

        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                loadNextDataFromApi();
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_tweets_list, container, false);

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        recyclerView = (RecyclerView) view.findViewById(R.id.rvMessages);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        recyclerView.setVisibility(View.GONE);

        recyclerView.addOnScrollListener(scrollListener);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                populateTimeline(-1, 1);
            }
        });

        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light);

        return view;
    }

    public void addAllTweets(List<Tweet> tweets) {
        this.tweets.addAll(tweets);
    }

    public void clearAllTweets() {
        this.tweets.clear();
    }

    void loadNextDataFromApi() {
        long maxTweetId = -1;
        if(tweets.size() > 0) {
            maxTweetId = tweets.get(tweets.size()-1).getId();
        }
        populateTimeline(maxTweetId,-1);
    }

    protected  JsonHttpResponseHandler getHandler(final boolean reset) {
        return new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                if(reset) {
                    clearAllTweets();
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

                    } catch (JSONException e) {
                        Snackbar.make(getView(), "Try Again",
                                Snackbar.LENGTH_LONG).show();
                    }
                }

                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);

                addAllTweets(respTweets);

                int curSize = adapter.getItemCount();
                adapter.notifyItemRangeInserted(curSize, tweets.size()-1);

                swipeRefreshLayout.setRefreshing(false);
                if(reset) {
                    scrollListener.resetState();
                    layoutManager.scrollToPosition(0);
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONObject errorResponse) {
                swipeRefreshLayout.setRefreshing(false);
                Snackbar.make(getView(), "Error fetching Tweets! Try Again",
                        Snackbar.LENGTH_LONG).show();
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        };

    }

    abstract void populateTimeline(long maxId, long sinceId);
}
