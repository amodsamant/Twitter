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
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.twitterclient.R;
import com.twitterclient.adapters.TimelineRecyclerAdapter;
import com.twitterclient.helpers.EndlessRecyclerViewScrollListener;
import com.twitterclient.models.Tweet;
import com.twitterclient.network.NetworkUtils;
import com.twitterclient.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public abstract class TweetsListFragment extends Fragment {

    protected List<Tweet> mTweets;
    protected RecyclerView mRecyclerView;
    protected TimelineRecyclerAdapter mAdapter;
    protected ProgressBar mProgressBar;

    protected DividerItemDecoration mDividerItemDecoration;
    protected LinearLayoutManager mLayoutManager;
    protected EndlessRecyclerViewScrollListener mScrollListener;
    protected SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTweets = new ArrayList<>();
        mAdapter = new TimelineRecyclerAdapter(getActivity(),mTweets);

        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
        mDividerItemDecoration = new DividerItemDecoration(getActivity(),
                mLayoutManager.getOrientation());

        mScrollListener = new EndlessRecyclerViewScrollListener(mLayoutManager) {
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

        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.VISIBLE);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rvMessages);
        mRecyclerView.addItemDecoration(mDividerItemDecoration);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setVisibility(View.GONE);

        mRecyclerView.addOnScrollListener(mScrollListener);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                populateTimeline(-1, 1);
            }
        });

        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light);

        return view;
    }

    public void addAllTweets(List<Tweet> tweets) {
        this.mTweets.addAll(tweets);
    }

    public void clearAllTweets() {
        this.mTweets.clear();
    }

    /**
     * Function loads data for next set
     */
    void loadNextDataFromApi() {
        long maxTweetId = -1;
        if(mTweets.size() > 0) {
            maxTweetId = mTweets.get(mTweets.size()-1).getId();
        }
        populateTimeline(maxTweetId,-1);
    }

    /**
     * Handler functino to process the received tweets
     * @param reset if reset true then clear and populate
     * @return
     */
    protected JsonHttpResponseHandler getHandler(final boolean reset) {
        return new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                if(reset) {
                    clearAllTweets();
                    mAdapter.notifyDataSetChanged();
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

                mProgressBar.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);

                addAllTweets(respTweets);

                int curSize = mAdapter.getItemCount();
                mAdapter.notifyItemRangeInserted(curSize, mTweets.size()-1);

                mSwipeRefreshLayout.setRefreshing(false);
                if(reset) {
                    mScrollListener.resetState();
                    mLayoutManager.scrollToPosition(0);
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONObject errorResponse) {
                mSwipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getActivity(), "Error fetching Tweets! Try Again",
                        Toast.LENGTH_LONG).show();
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        };

    }

    protected void populateTimeline() {
        if (NetworkUtils.isNetworkAvailable(getActivity()) && NetworkUtils.isOnline()) {
            populateTimeline(Constants.DEFAULT_MAX_ID, Constants.DEFAULT_SINCE_ID);
        }
    }

    abstract void populateTimeline(long maxId, long sinceId);
}
