package com.twitterclient.fragments;

import static com.twitterclient.network.TwitterClient.SCREEN_NAME;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.twitterclient.network.TwitterClientApplication;
import com.twitterclient.utils.Constants;

public class ProfileTweetsTimelineFragment extends TweetsListFragment {

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
        populateTimeline();
    }

    /**
     * Function populate the profile tweets timeline
     * @param maxId
     * @param sinceId
     */
    void populateTimeline(long maxId, long sinceId) {

        String screenName = getArguments().getString(SCREEN_NAME);
        TwitterClientApplication.getTwitterClient().getUserTimeline(screenName, maxId, sinceId,
                        getHandler(sinceId==Constants.DEFAULT_RESET_SINCE_ID));
    }
}
