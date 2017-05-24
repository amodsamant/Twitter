package com.twitterclient.fragments;

import static com.twitterclient.utils.Constants.SCREEN_NAME;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.twitterclient.network.NetworkUtils;
import com.twitterclient.network.TwitterClientApplication;

public class FollowersFragment extends FollowListFragment {

    public static FollowersFragment newInstance(String screenName) {
        FollowersFragment fragment = new FollowersFragment();
        Bundle args = new Bundle();
        args.putString(SCREEN_NAME, screenName);
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

    /**
     * Function populates the followers list
     */
    private void populateFollowList() {
        String screenName = getArguments().getString(SCREEN_NAME);
        TwitterClientApplication.getTwitterClient().getFollowers(screenName, getHandler());
    }
}
