package com.twitterclient.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.twitterclient.R;
import com.twitterclient.adapters.SmartFragmentStatePagerAdapter;
import com.twitterclient.fragments.ProfileTweetsTimelineFragment;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ViewPager viewPager = (ViewPager) findViewById(R.id.profile_viewpager);
        viewPager.setAdapter(new ProfileViewPagerAdapter(getSupportFragmentManager(),
                ProfileActivity.this));

        TabLayout tabLayout = (TabLayout) findViewById(R.id.profile_tabs);
        tabLayout.setupWithViewPager(viewPager);

    }


    public class ProfileViewPagerAdapter extends SmartFragmentStatePagerAdapter {

        private String[] tabTitles = {"TWEETS", "MEDIA", "LIKES"};
        Context mContext;

        public ProfileViewPagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            mContext = context;
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {

                case 0:
                    return ProfileTweetsTimelineFragment.newInstance("amod_samant");
                case 1:
                    return ProfileTweetsTimelineFragment.newInstance("amod_samant");
                case 2:
                    return ProfileTweetsTimelineFragment.newInstance("amod_samant");
                default:
                    return null;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }
    }
}
