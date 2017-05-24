package com.twitterclient.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.twitterclient.network.NetworkUtils;

public class MessagesHomeFragment extends MessagesFragment {

    public static MessagesHomeFragment newInstance() {
        return new MessagesHomeFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(NetworkUtils.isNetworkAvailable(getActivity()) && NetworkUtils.isOnline()) {
            populateMessages();
        }
    }
}
