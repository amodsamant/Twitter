package com.twitterclient.fragments;

import static com.twitterclient.utils.Constants.DEFAULT_RESET_SINCE_ID;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.twitterclient.models.Tweet;
import com.twitterclient.network.TwitterClientApplication;

public class HomeTimelineFragment extends TweetsListFragment {

   public static HomeTimelineFragment newInstance() {
        return new HomeTimelineFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        populateTimeline();
    }

    void populateTimeline(long maxId, long sinceId) {
        TwitterClientApplication.getTwitterClient()
                .getHomeTimeline(maxId, sinceId, getHandler(sinceId==DEFAULT_RESET_SINCE_ID));
    }

    public void onFinishTweet(Tweet tweet) {
        tweets.add(0,tweet);
        adapter.notifyItemInserted(0);
        layoutManager.scrollToPosition(0);
    }

}
