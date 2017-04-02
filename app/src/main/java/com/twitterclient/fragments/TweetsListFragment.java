package com.twitterclient.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.twitterclient.R;
import com.twitterclient.adapters.TimelineRecyclerAdapter;
import com.twitterclient.helpers.EndlessRecyclerViewScrollListener;
import com.twitterclient.models.Tweet;

import java.util.ArrayList;
import java.util.List;

public abstract class TweetsListFragment extends Fragment {

    List<Tweet> tweets;
    RecyclerView recyclerView;
    TimelineRecyclerAdapter adapter;

    DividerItemDecoration dividerItemDecoration;
    LinearLayoutManager layoutManager;
    EndlessRecyclerViewScrollListener scrollListener;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tweets = new ArrayList<>();
        adapter = new TimelineRecyclerAdapter(getActivity(),tweets);

        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
        dividerItemDecoration = new DividerItemDecoration(getActivity(),
                layoutManager.getOrientation());

        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                loadNextDataFromApi();
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_tweets_list, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycleView);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(scrollListener);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                populateTimeline(-1, 1);
            }
        });

        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light);

        return view;
    }

    public void addAllTweets(List<Tweet> tweets) {
        this.tweets.addAll(tweets);
    }

    public void clearAllTweets() {
        this.tweets.clear();
    }


    abstract void loadNextDataFromApi();

    abstract void populateTimeline(long maxId, long sinceId);
}
