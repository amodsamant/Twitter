package com.twitterclient.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.twitterclient.R;
import com.twitterclient.adapters.SmartFragmentStatePagerAdapter;
import com.twitterclient.fragments.ComposeTweetFragment;
import com.twitterclient.fragments.HomeTimelineFragment;
import com.twitterclient.fragments.MentionsTimelineFragment;
import com.twitterclient.fragments.MessagesFragment;
import com.twitterclient.fragments.MessagesHomeFragment;
import com.twitterclient.models.Tweet;

public class HomeActivity extends AppCompatActivity implements
        ComposeTweetFragment.ComposeTweetListener {

    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private HomeTimelineFragment homeTimelineFragment;
    private MentionsTimelineFragment mentionsTimelineFragment;
    private MessagesFragment messagesFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();

        mDrawer.addDrawerListener(drawerToggle);

        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        setupDrawerContent(nvDrawer);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new HomeViewPagerAdapter(getSupportFragmentManager(),
                HomeActivity.this));

        tabLayout = (TabLayout) findViewById(R.id.twitter_tabs);
        tabLayout.setupWithViewPager(viewPager);

        /**
         * Code to handle implicit intents
         */
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                String urlOfPage = intent.getStringExtra(Intent.EXTRA_TEXT);
                openComposeFrag(urlOfPage);
            }
        }

        /**
         * Compose button
         */

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openComposeFrag(null);
                Toast.makeText(HomeActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public class HomeViewPagerAdapter extends SmartFragmentStatePagerAdapter {

        private String[] tabTitles = {"Home", "Mentions","Messages"};
        private Context context;

        public HomeViewPagerAdapter(FragmentManager fragmentManager, Context context) {
            super(fragmentManager);
            this.context = context;
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    homeTimelineFragment = HomeTimelineFragment.newInstance();
                    return homeTimelineFragment;
                case 1:
                    mentionsTimelineFragment = MentionsTimelineFragment.newInstance();
                    return mentionsTimelineFragment;
                case 2:
                    messagesFragment = MessagesHomeFragment.newInstance();
                    return messagesFragment;

            }
            return null;
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.profile_menu_item:
                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                intent.putExtra("screen_name", "amod_samant");
                startActivity(intent);
                break;
            case R.id.settings_menu_item:
                //TODO: Call settings activity
                break;

            default:
        }

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }


    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, mDrawer, toolbar,
                R.string.drawer_open, R.string.drawer_close);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_search) {
            Intent intent = new Intent(HomeActivity.this, HolderActivity.class);
            intent.putExtra("frag_type", "search");
            startActivity(intent);
            return true;
        }
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Override this function to close the app from the timeline activity
     */
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void openComposeFrag(String tweet) {
        FragmentManager fm = getSupportFragmentManager();
        ComposeTweetFragment fragment = ComposeTweetFragment.getInstance(tweet);
        fragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_FullScreen);
        fragment.show(fm, "compose_frag");
    }

    @Override
    public void onFinishTweet(Tweet tweet) {

        homeTimelineFragment.onFinishTweet(tweet);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }
}