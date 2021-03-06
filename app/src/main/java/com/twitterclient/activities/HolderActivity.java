package com.twitterclient.activities;

import static com.twitterclient.utils.Constants.SCREEN_NAME;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.twitterclient.R;
import com.twitterclient.fragments.FollowersFragment;
import com.twitterclient.fragments.FollowingFragment;
import com.twitterclient.fragments.SearchFragment;
import com.twitterclient.fragments.TweetDetailFragment;
import com.twitterclient.models.Tweet;

import org.parceler.Parcels;

public class HolderActivity extends AppCompatActivity {

    private SearchView mSearchView;
    private boolean mIsSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holder);

        Toolbar toolbar = (Toolbar) findViewById(R.id.holder_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String fragType = getIntent().getStringExtra("frag_type");
        Tweet tweet = Parcels.unwrap(getIntent().getParcelableExtra("tweet"));
        String screenName = getIntent().getStringExtra(SCREEN_NAME);

        FragmentManager fm = getSupportFragmentManager();
        switch (fragType) {

            case "detail":
                fm.beginTransaction()
                        .replace(R.id.holder, TweetDetailFragment.newInstance(tweet))
                        .commit();
                getSupportActionBar().setTitle("Tweet");
                break;
            case "following":
                fm.beginTransaction()
                        .replace(R.id.holder, FollowingFragment.newInstance(screenName))
                        .commit();
                getSupportActionBar().setTitle("Following");
                break;
            case "followers":
                fm.beginTransaction()
                        .replace(R.id.holder, FollowersFragment.newInstance(screenName))
                        .commit();
                getSupportActionBar().setTitle("Followers");
                break;
            case "search":
                mIsSearch = true;
            default:
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.holder_menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.holder_search);
        mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        EditText etSearchView = (EditText) mSearchView.
                findViewById(android.support.v7.appcompat.R.id.search_src_text);
        etSearchView.setTextColor(Color.GRAY);
        etSearchView.setHintTextColor(Color.GRAY);
        etSearchView.setFocusable(true);
        etSearchView.requestFocusFromTouch();
        if(mIsSearch) {
            MenuItemCompat.expandActionView(searchItem);
            mSearchView.requestFocus();
        }

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                SearchFragment searchFragment = SearchFragment.newInstance(query);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.holder, searchFragment)
                        .commit();

                mSearchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) { return false; }
        });
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
