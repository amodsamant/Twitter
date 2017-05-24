package com.twitterclient.fragments;


import static com.twitterclient.R.id.progressBar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.twitterclient.models.Statuses;
import com.twitterclient.network.TwitterClientApplication;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class SearchFragment extends TweetsListFragment {

    public static SearchFragment newInstance(String query) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString("query", query);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        populateTimeline();
    }

    void populateTimeline(long maxId, final long sinceId) {

        String query = getArguments().getString("query");
        TwitterClientApplication.getTwitterClient()
                .getSearchResults(query, maxId, sinceId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                if(sinceId==1) {
                    clearAllTweets();
                    mAdapter.notifyDataSetChanged();
                }
                Log.d("DEBUG", response.toString());

                Gson gson = new Gson();

                Statuses statuses = gson.fromJson(response.toString(), Statuses.class);

                mProgressBar.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);

                addAllTweets(statuses.getTweets());

                int curSize = mAdapter.getItemCount();
                mAdapter.notifyItemRangeInserted(curSize, mTweets.size()-1);

                mSwipeRefreshLayout.setRefreshing(false);
                if(sinceId==1) {
                    mScrollListener.resetState();
                    mLayoutManager.scrollToPosition(0);
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONObject errorResponse) {
                mSwipeRefreshLayout.setRefreshing(false);
                Snackbar.make(getView(), "Error fetching Tweets! Try Again",
                        Snackbar.LENGTH_LONG).show();
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }


}
