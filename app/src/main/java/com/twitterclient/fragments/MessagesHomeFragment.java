package com.twitterclient.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.twitterclient.models.Message;
import com.twitterclient.network.NetworkUtils;
import com.twitterclient.network.TwitterClient;
import com.twitterclient.network.TwitterClientApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MessagesHomeFragment extends MessagesFragment {

    TwitterClient twitterClient;

    public static MessagesHomeFragment newInstance() {
        MessagesHomeFragment fragment = new MessagesHomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        twitterClient = TwitterClientApplication.getTwitterClient();
        /**
         * Calling populate time line with since id of 1 for loading the initial tweets
         */
        if(NetworkUtils.isNetworkAvailable(getActivity()) && NetworkUtils.isOnline()) {
            populateMessages();
        }


    }

    private void populateMessages() {

        twitterClient.getDirectMessages(new JsonHttpResponseHandler() {
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

                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);

                addAllMessages(recycleMessages(messages));

                int curSize = adapter.getItemCount();
                adapter.notifyItemRangeInserted(curSize, messages.size()-1);



            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONObject errorResponse) {

                Snackbar.make(getView(), "Error fetching Tweets! Try Again",
                        Snackbar.LENGTH_LONG).show();
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
