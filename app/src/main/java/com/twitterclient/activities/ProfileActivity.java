package com.twitterclient.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.twitterclient.R;
import com.twitterclient.adapters.SmartFragmentStatePagerAdapter;
import com.twitterclient.fragments.ProfileLikesTimelineFragment;
import com.twitterclient.fragments.ProfileTweetsTimelineFragment;
import com.twitterclient.models.User;
import com.twitterclient.network.TwitterClient;
import com.twitterclient.network.TwitterClientApplication;
import com.twitterclient.utils.GenericUtils;
import com.twitterclient.utils.PatternEditableBuilder;

import org.json.JSONObject;

import java.util.regex.Pattern;

import cz.msebera.android.httpclient.Header;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;


public class ProfileActivity extends AppCompatActivity {

    String screenName;
    TextView tvUsername;
    TextView tvScreenName;
    TextView tvText;
    TextView tvFollowers;
    TextView tvFollowing;
    ImageView ivUser;
    ImageView ivBackdrop;
    ImageView ivVerified;

    TwitterClient twitterClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.profile_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        twitterClient = TwitterClientApplication.getTwitterClient();

        screenName = getIntent().getStringExtra("screen_name");

        setupViews();

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
                    return ProfileTweetsTimelineFragment.newInstance(screenName);
                case 1:
                    return ProfileTweetsTimelineFragment.newInstance(screenName);
                case 2:
                    return ProfileLikesTimelineFragment.newInstance(screenName);
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

    public void setupViews() {

        tvUsername = (TextView) findViewById(R.id.tvName);
        tvUsername.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/HelveticaNeueBd.ttf"));

        tvScreenName = (TextView) findViewById(R.id.tvProfileScreenName);

        tvText = (TextView) findViewById(R.id.tvTagline);
        tvText.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/HelveticaNeueLight.ttf"));

        tvFollowers = (TextView) findViewById(R.id.tvFollowers);
        tvFollowers.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/HelveticaNeueLight.ttf"));

        tvFollowers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, HolderActivity.class);
                intent.putExtra("frag_type","followers");
                intent.putExtra("screen_name",screenName);
                startActivity(intent);

            }
        });

        tvFollowing = (TextView) findViewById(R.id.tvFollowing);
        tvFollowing.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/HelveticaNeueLight.ttf"));
        tvFollowing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, HolderActivity.class);
                intent.putExtra("frag_type","following");
                intent.putExtra("screen_name",screenName);
                startActivity(intent);
            }
        });

        ivUser = (ImageView) findViewById(R.id.ivProfileUser);
        ivBackdrop = (ImageView) findViewById(R.id.ivBackdrop);
        ivVerified = (ImageView) findViewById(R.id.ivProfileVerified);

        twitterClient.getUserInfo(screenName, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                Log.d("DEBUG", response.toString());
                Gson gson = new Gson();
                User user = gson.fromJson(response.toString(),
                        User.class);

                populateUserInfo(user);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });


    }

    void populateUserInfo(User user) {

        tvUsername.setText(user.getName());
        tvScreenName.setText("@"+ user.getScreenName());
        if(user.getDescription()==null || user.getDescription()=="" ) {
            tvText.setVisibility(View.GONE);
        } else {
            tvText.setVisibility(View.VISIBLE);
            tvText.setText(user.getDescription());
        }
        new PatternEditableBuilder().
                addPattern(Pattern.compile("\\@(\\w+)"),
                        this.getResources().getColor(R.color.twitterBlue),
                        new PatternEditableBuilder.SpannableClickedListener() {
                            @Override
                            public void onSpanClicked(String text) {
                                Intent intent = new Intent(ProfileActivity.this, ProfileActivity.class);
                                intent.putExtra("screen_name", text.substring(1));
                                startActivity(intent);
                            }
                        }).into(tvText);

        new PatternEditableBuilder().
                addPattern(Pattern.compile("\\#(\\w+)"),
                        this.getResources().getColor(R.color.twitterBlue),
                        new PatternEditableBuilder.SpannableClickedListener() {
                            @Override
                            public void onSpanClicked(String text) {
//                                Intent intent = new Intent(ProfileActivity.this, ProfileActivity.class);
//                                intent.putExtra("screen_name", text.substring(1));
//                                startActivity(intent);
                            }
                        }).into(tvText);

        /**
         * Using a Span here to have a different style inside of each text view
         */
        ForegroundColorSpan blackSpan = new ForegroundColorSpan(
                getResources().getColor(R.color.twitterDarkerGrey));
        SpannableStringBuilder ssb = new SpannableStringBuilder(
                String.valueOf(user.getFollowing()));
        ssb.setSpan(
                blackSpan,
                0,
                ssb.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.append(" FOLLOWING");
        tvFollowing.setText(ssb, TextView.BufferType.EDITABLE);

        SpannableStringBuilder ssbFollowers = new SpannableStringBuilder(
                String.valueOf(user.getFollowers()));
        ssbFollowers.setSpan(
                blackSpan,
                0,
                ssbFollowers.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssbFollowers.append(" FOLLOWERS");
        tvFollowers.setText(ssbFollowers, TextView.BufferType.EDITABLE);

        ivUser.setImageResource(0);
        Glide.with(this).load(GenericUtils.modifyProfileImageUrl(user.getProfileImageUrl()))
                .fitCenter()
                .bitmapTransform( new RoundedCornersTransformation(this,5,0))
                .into(ivUser);

        ivBackdrop.setImageResource(0);
        /**
         * Using large image url with RoundedCornersTransformation
         */
        String imageUrl = user.getProfileBackground() + "/1500x500";
        Glide.with(this).load(imageUrl)
                //.bitmapTransform( new RoundedCornersTransformation(this,20,5))
                .diskCacheStrategy( DiskCacheStrategy.SOURCE )
                .into(ivBackdrop);


        if(!user.isVerified()) {
            ivVerified.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
