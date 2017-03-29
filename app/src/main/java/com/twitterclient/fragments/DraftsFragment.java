package com.twitterclient.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import com.twitterclient.R;
import com.twitterclient.adapters.DraftsAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DraftsFragment extends DialogFragment {

    public interface DraftsFragmentListener {
        void onFinishDraft(String tweet);
    }

    ListView lvDrafts;
    DraftsAdapter adapter;
    List<String> drafts;

    public DraftsFragment() {
    }

    public static DraftsFragment getInstance() {
        DraftsFragment draftsFragment = new DraftsFragment();
        Bundle args = new Bundle();
        draftsFragment.setArguments(args);
        return draftsFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.drafts_frag, container);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbarDrafts);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setTitle("Drafts");
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        drafts = getDrafts();
        lvDrafts = (ListView) view.findViewById(R.id.lvDrafts);
        adapter = new DraftsAdapter(getContext(), drafts);
        lvDrafts.setAdapter(adapter);

        lvDrafts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                SharedPreferences sharedPreferences = getActivity()
                        .getSharedPreferences("Drafts", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(adapter.getItem(i));
                editor.apply();

                sendBackResult(adapter.getItem(i));
            }
        });

    }

    /**
     * Function gets a list of drafts from the saved shared preferences
     * @return
     */
    private List<String> getDrafts() {

       SharedPreferences sharedPreferences = getActivity()
                .getSharedPreferences("Drafts", Context.MODE_PRIVATE);

        Map<String,String> draftsMap = (Map<String, String>) sharedPreferences.getAll();
        List<String> drafts = new ArrayList<>(draftsMap.values());
        if(drafts!=null && !drafts.isEmpty()) {
            Log.d("DEBUG", drafts.get(0));
        }

        return drafts;
    }

    /**
     * Sets the windows to the correct size for this fragment
     */
    @Override
    public void onResume() {

        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        super.onResume();
    }

    /**
     * Sends the tweet back to the compose fragment
     * @param tweetDraft
     */
    public void sendBackResult(String tweetDraft) {
        DraftsFragmentListener listener = (DraftsFragmentListener) getTargetFragment();
        listener.onFinishDraft(tweetDraft);
        dismiss();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
