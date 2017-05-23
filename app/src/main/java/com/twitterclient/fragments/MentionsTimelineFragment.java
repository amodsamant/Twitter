package com.twitterclient.fragments;

import static com.twitterclient.utils.Constants.DEFAULT_RESET_SINCE_ID;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.twitterclient.network.NetworkUtils;
import com.twitterclient.network.TwitterClientApplication;

public class MentionsTimelineFragment extends TweetsListFragment {

    public static MentionsTimelineFragment newInstance() {
        return new MentionsTimelineFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(NetworkUtils.isNetworkAvailable(getActivity()) && NetworkUtils.isOnline()) {
            populateTimeline(-1, -1);
        }
    }

    void populateTimeline(long maxId, long sinceId) {

        TwitterClientApplication.getTwitterClient()
                .getMentionsTimeline(maxId, sinceId, getHandler(sinceId==DEFAULT_RESET_SINCE_ID));
    }
}
