package com.twitterclient.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.twitterclient.network.TwitterClientApplication;
import com.twitterclient.utils.Constants;

public class ProfileLikesTimelineFragment extends TweetsListFragment {

    private static final String SCREEN_NAME = "screen_name";

    public static ProfileLikesTimelineFragment newInstance(String screenName) {
        ProfileLikesTimelineFragment fragment = new ProfileLikesTimelineFragment();
        Bundle args = new Bundle();
        args.putString(SCREEN_NAME, screenName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        populateTimeline();
    }

    void populateTimeline(long maxId, long sinceId) {
        String screenName = getArguments().getString(SCREEN_NAME);
        TwitterClientApplication.getTwitterClient().getFavorites(screenName, maxId, sinceId,
                        getHandler(sinceId==Constants.DEFAULT_RESET_SINCE_ID));
    }
}
