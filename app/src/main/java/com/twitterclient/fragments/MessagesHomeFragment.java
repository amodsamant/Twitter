package com.twitterclient.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
                        Message messgae = gson.fromJson(response.getJSONObject(x).toString(),
                                Message.class);
                        messages.add(messgae);

                    } catch (JSONException e) {
                        //TODO:
                    }
                }

                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);

                addAllMessages(messages);

                int curSize = adapter.getItemCount();
                adapter.notifyItemRangeInserted(curSize, messages.size()-1);

                swipeRefreshLayout.setRefreshing(false);


            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONObject errorResponse) {
                swipeRefreshLayout.setRefreshing(false);
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    @Override
    void loadNextDataFromApi() {
    }

}
