package com.twitterclient.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.twitterclient.models.Follow;
import com.twitterclient.network.NetworkUtils;
import com.twitterclient.network.TwitterClient;
import com.twitterclient.network.TwitterClientApplication;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class FollowingFragment extends FollowListFragment {

    TwitterClient twitterClient;

    public static FollowingFragment newInstance(String screenName) {
        FollowingFragment fragment = new FollowingFragment();
        Bundle args = new Bundle();
        args.putString("screen_name", screenName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        twitterClient = TwitterClientApplication.getTwitterClient();
        /**
         * Calling populate time line with since id of 1 for loading the initial tweets
         */
        if(NetworkUtils.isNetworkAvailable(getActivity()) && NetworkUtils.isOnline()) {
            populateFollowList();
        }


    }

    private void populateFollowList() {

        String screenName = getArguments().getString("screen_name");
        twitterClient.getFollowers(screenName, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                clearAllUsers();
                adapter.notifyDataSetChanged();

                Log.d("DEBUG", response.toString());
                Gson gson = new Gson();

                Follow following = gson.fromJson(response.toString(), Follow.class);

                addAllUsers(following.getUsers());

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }
}
