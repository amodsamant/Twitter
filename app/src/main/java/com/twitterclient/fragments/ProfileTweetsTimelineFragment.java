package com.twitterclient.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.twitterclient.models.Tweet;
import com.twitterclient.network.NetworkUtils;
import com.twitterclient.network.TwitterClient;
import com.twitterclient.network.TwitterClientApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class ProfileTweetsTimelineFragment extends TweetsListFragment {

    private static final String SCREEN_NAME = "screen_name";
    TwitterClient twitterCLient;

    public static ProfileTweetsTimelineFragment newInstance(String screenName) {
        ProfileTweetsTimelineFragment fragment = new ProfileTweetsTimelineFragment();
        Bundle args = new Bundle();
        args.putString(SCREEN_NAME, screenName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        twitterCLient = TwitterClientApplication.getTwitterClient();

        /**
         * Calling populate time line with since id of 1 for loading the initial tweets
         */
        if(NetworkUtils.isNetworkAvailable(getActivity()) && NetworkUtils.isOnline()) {
            populateTimeline(-1, -1);
        }


    }

    void populateTimeline(long maxId, final long sinceId) {

        String screenName = getArguments().getString(SCREEN_NAME);

        twitterCLient.getUserTimeline(screenName, maxId, sinceId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                if(sinceId==1) {
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
                        //TODO:
                    }
                }

                addAllTweets(respTweets);

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

    @Override
    void loadNextDataFromApi() {
        long maxTweetId = -1;
        if(tweets.size() > 0) {
            maxTweetId = tweets.get(tweets.size()-1).getId();
        }

        populateTimeline(maxTweetId,-1);
    }
}
