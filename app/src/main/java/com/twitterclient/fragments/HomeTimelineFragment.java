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

    /**
     * Function to populate the main home timeline
     * @param maxId
     * @param sinceId
     */
    void populateTimeline(long maxId, long sinceId) {
        TwitterClientApplication.getTwitterClient()
                .getHomeTimeline(maxId, sinceId, getHandler(sinceId==DEFAULT_RESET_SINCE_ID));
    }

    /**
     * Function to notify on finishing a tweet
     * @param tweet
     */
    public void onFinishTweet(Tweet tweet) {
        mTweets.add(0,tweet);
        mAdapter.notifyItemInserted(0);
        mLayoutManager.scrollToPosition(0);
    }

}
