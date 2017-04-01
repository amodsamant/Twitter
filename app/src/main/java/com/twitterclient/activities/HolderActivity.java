package com.twitterclient.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.twitterclient.R;
import com.twitterclient.fragments.TweetDetailFragment;
import com.twitterclient.models.Tweet;

import org.parceler.Parcels;

public class HolderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holder);

        String fragType = getIntent().getStringExtra("frag_type");
        Tweet tweet = Parcels.unwrap(getIntent().getParcelableExtra("tweet"));

        switch (fragType) {

            case "detail":
                FragmentManager fm = getSupportFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.holder, TweetDetailFragment.newInstance(tweet))
                        .commit();
            default:
        }

    }
}
