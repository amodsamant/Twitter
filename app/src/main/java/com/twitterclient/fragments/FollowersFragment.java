package com.twitterclient.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.twitterclient.network.NetworkUtils;
import com.twitterclient.network.TwitterClientApplication;

public class FollowersFragment extends FollowListFragment {

    public static FollowersFragment newInstance(String screenName) {
        FollowersFragment fragment = new FollowersFragment();
        Bundle args = new Bundle();
        args.putString("screen_name", screenName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(NetworkUtils.isNetworkAvailable(getActivity()) && NetworkUtils.isOnline()) {
            populateFollowList();
        }
    }

    private void populateFollowList() {
        String screenName = getArguments().getString("screen_name");
        TwitterClientApplication.getTwitterClient().getFollowers(screenName, getHandler());
    }
}
