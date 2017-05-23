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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.twitterclient.R;
import com.twitterclient.adapters.SmartFragmentStatePagerAdapter;
import com.twitterclient.fragments.ComposeTweetFragment;
import com.twitterclient.fragments.HomeTimelineFragment;
import com.twitterclient.fragments.MentionsTimelineFragment;
import com.twitterclient.fragments.MessagesHomeFragment;
import com.twitterclient.models.Tweet;
import com.twitterclient.models.User;
import com.twitterclient.network.TwitterClientApplication;
import com.twitterclient.utils.GenericUtils;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class HomeActivity extends AppCompatActivity implements
        ComposeTweetFragment.ComposeTweetListener {

    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private HomeTimelineFragment homeTimelineFragment;
    private String screenName;

    ImageView ivHeaderProfile;
    ImageView ivHeader;
    TextView tvHeaderName;
    TextView tvHeaderScreenName;

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
        onPageChangeListener();

        tabLayout = (TabLayout) findViewById(R.id.twitter_tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTab(0);

        screenName = getIntent().getStringExtra("screen_name");

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
            }
        });

    }

    public class HomeViewPagerAdapter extends SmartFragmentStatePagerAdapter {

        private final int NUM_ITEMS = 3;
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
                    return MentionsTimelineFragment.newInstance();
                case 2:
                    return MessagesHomeFragment.newInstance();
            }
            return null;
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

    }

    private void setupDrawerContent(NavigationView navigationView) {

        View navHeaderView = navigationView.inflateHeaderView(R.layout.nav_header);
        ivHeaderProfile = (ImageView)navHeaderView.findViewById(R.id.ivHeaderProfile);
        ivHeader = (ImageView)navHeaderView.findViewById(R.id.ivHeader);
        tvHeaderName = (TextView)navHeaderView.findViewById(R.id.tvHeaderName);
        tvHeaderScreenName = (TextView)navHeaderView.findViewById(R.id.tvHeaderScreenName);

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });

        TwitterClientApplication.getTwitterClient()
                .getPersonalUserInfo(new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    Gson gson = new Gson();
                    User user = gson.fromJson(response.toString(),
                            User.class);

                    tvHeaderName.setText(user.getName());
                    tvHeaderScreenName.setText("@"+user.getScreenName());

                    ivHeaderProfile.setImageResource(0);
                    String profileImageUrl = GenericUtils
                            .modifyProfileImageUrl(user.getProfileImageUrl());
                    Glide.with(HomeActivity.this).load(profileImageUrl)
                            .fitCenter()
                            .bitmapTransform(new CropCircleTransformation(HomeActivity.this))
                            .into(ivHeaderProfile);

                    ivHeader.setImageResource(0);
                    String url = user.getProfileBackground();
                    Glide.with(HomeActivity.this).load(url)
                            .fitCenter()
                            .into(ivHeader);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers,
                        Throwable throwable, JSONObject errorResponse) {
                    /**
                     * Need not be handled as this would not cause any user issue
                     */
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                }
        });

    }

    public void selectDrawerItem(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.profile_menu_item:
                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                intent.putExtra("screen_name", screenName);
                startActivity(intent);
                break;
            case R.id.log_off_item:
                TwitterClientApplication.getTwitterClient().clearAccessToken();
                Intent logoutIntent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(logoutIntent);
                break;

            default:
        }
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
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

    /**
     * Function used to set the correct icons on the tab with title
     */
    void setupTab(int position) {

        int[] tabIcons =
                {R.drawable.ic_home, R.drawable.ic_mentions, R.drawable.ic_message};
        int[] tabIconsSelected =
                {R.drawable.ic_home_selected, R.drawable.ic_mentions_selected,
                        R.drawable.ic_message_selected};
        String[] tabTitles = {"Home", "Mentions", "Messages"};

        for(int tab=0; tab<tabIcons.length;tab++) {
            if(tab != position) {
                tabLayout.getTabAt(tab).setIcon(tabIcons[tab]);
            } else {
                tabLayout.getTabAt(tab).setIcon(tabIconsSelected[tab]);
                getSupportActionBar().setTitle(tabTitles[position]);
            }
        }

    }

    /**
     * Function used to set the correct icons on the tab
     */
    void onPageChangeListener() {

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            // This method will be invoked when a new page becomes selected.
            @Override
            public void onPageSelected(int position) {
                setupTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
            @Override
            public void onPageScrolled(int position,
                                       float positionOffset, int positionOffsetPixels) {}
        });

    }
}