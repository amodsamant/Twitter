package com.twitterclient.fragments;

import static com.twitterclient.R.id.progressBar;

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
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.twitterclient.R;
import com.twitterclient.adapters.MessagesRecyclerAdapter;
import com.twitterclient.helpers.EndlessRecyclerViewScrollListener;
import com.twitterclient.models.Message;
import com.twitterclient.network.TwitterClientApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MessagesFragment extends Fragment {

    List<Message> mMessages;
    RecyclerView mRecyclerView;
    MessagesRecyclerAdapter mAdapter;
    ProgressBar mProgressBar;

    DividerItemDecoration mDividerItemDecoration;
    LinearLayoutManager mLayoutManager;
    EndlessRecyclerViewScrollListener mScrollListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMessages = new ArrayList<>();
        mAdapter = new MessagesRecyclerAdapter(getActivity(),mMessages);

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

        mRecyclerView.addOnScrollListener(mScrollListener);
        mRecyclerView.setVisibility(View.GONE);
        mProgressBar = (ProgressBar) view.findViewById(progressBar);
        mProgressBar.setVisibility(View.VISIBLE);

        return view;
    }

    public void addAllMessages(List<Message> messages) {
        this.mMessages.addAll(messages);
    }

    protected void populateMessages() {

        TwitterClientApplication.getTwitterClient().getDirectMessages(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                Log.d("DEBUG", response.toString());
                List<Message> messages = new ArrayList<>();
                Gson gson = new Gson();
                for(int x = 0; x < response.length();x++) {
                    try {
                        Message message = gson.fromJson(response.getJSONObject(x).toString(),
                                Message.class);
                        messages.add(message);

                    } catch (JSONException e) {
                        Snackbar.make(getView(), "Try Again",
                                Snackbar.LENGTH_LONG).show();
                    }
                }

                mProgressBar.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);

                addAllMessages(recycleMessages(messages));

                int curSize = mAdapter.getItemCount();
                mAdapter.notifyItemRangeInserted(curSize, messages.size()-1);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                    Throwable throwable, JSONObject errorResponse) {

                Toast.makeText(getActivity(), "Error fetching Tweets! Try Again",
                        Toast.LENGTH_LONG).show();
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    /**
     * Function only keeps the latest message to display in the Message timeline
     * @param messages
     * @return
     */
    public List<Message> recycleMessages(List<Message> messages) {

        List<Message> result = new ArrayList<>();
        HashSet<Long> senderIds = new HashSet<>();

        for(Message message: messages) {
            if (!senderIds.contains(message.getSender().getId())) {
                senderIds.add(message.getSender().getId());
                result.add(message);
            }
        }
        return result;
    }
}
