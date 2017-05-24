package com.twitterclient.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.twitterclient.R;
import com.twitterclient.adapters.FollRecyclerAdapter;
import com.twitterclient.helpers.EndlessRecyclerViewScrollListener;
import com.twitterclient.models.Follow;
import com.twitterclient.models.User;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public abstract class FollowListFragment extends Fragment {

    private List<User> mUsers;
    private RecyclerView mRecyclerView;
    private FollRecyclerAdapter mAdapter;
    private ProgressBar mProgressBar;

    private DividerItemDecoration mDividerItemDecoration;
    private LinearLayoutManager mLayoutManager;
    private EndlessRecyclerViewScrollListener mScrollListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUsers = new ArrayList<>();
        mAdapter = new FollRecyclerAdapter(getActivity(),mUsers);

        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
        mDividerItemDecoration = new DividerItemDecoration(getActivity(),
                mLayoutManager.getOrientation());
        mScrollListener = new EndlessRecyclerViewScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {}
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_tweets_list, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rvMessages);
        mRecyclerView.addItemDecoration(mDividerItemDecoration);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        mRecyclerView.setVisibility(View.GONE);

        mRecyclerView.addOnScrollListener(mScrollListener);

        return view;
    }

    /**
     * Function adds all the users passed in the params
     * @param users
     */
    public void addAllUsers(List<User> users) {
        this.mUsers.addAll(users);
    }

    /**
     * Function clears all the users
     */
    public void clearAllUsers() {
        this.mUsers.clear();
    }

    /**
     * Handler function to respond to follow apis
     * @return
     */
    protected JsonHttpResponseHandler getHandler() {
        return new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                clearAllUsers();
                mAdapter.notifyDataSetChanged();

                Log.d("DEBUG", response.toString());
                Gson gson = new Gson();
                Follow followers = gson.fromJson(response.toString(), Follow.class);
                mRecyclerView.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.GONE);
                addAllUsers(followers.getUsers());
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONObject errorResponse) {
                Snackbar.make(getView(), "Error fetching Tweets! Try Again",
                        Snackbar.LENGTH_LONG).show();
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        };
    }
}
