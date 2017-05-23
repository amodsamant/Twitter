package com.twitterclient.activities;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.twitterclient.R;
import com.twitterclient.adapters.SmartFragmentStatePagerAdapter;
import com.twitterclient.databinding.ActivityProfileBinding;
import com.twitterclient.fragments.ProfileLikesTimelineFragment;
import com.twitterclient.fragments.ProfileTweetsTimelineFragment;
import com.twitterclient.models.User;
import com.twitterclient.network.TwitterClientApplication;
import com.twitterclient.utils.GenericUtils;
import com.twitterclient.utils.PatternEditableBuilder;

import org.json.JSONObject;

import java.util.regex.Pattern;

import cz.msebera.android.httpclient.Header;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;


public class ProfileActivity extends AppCompatActivity {

    ActivityProfileBinding binding;
    String screenName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_profile);

        setSupportActionBar(binding.profileToolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.palatte);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @SuppressWarnings("ResourceType")
            @Override
            public void onGenerated(Palette palette) {
                int vibrantColor = palette.getVibrantColor(R.color.twitterBlue);
                binding.profileToolbarLayout.setContentScrimColor(vibrantColor);
                binding.profileToolbarLayout.setStatusBarScrimColor(R.color.twitterBlue);
            }
        });

        binding.appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset) > 800) {
                    binding.profileToolbarLayout.setTitle(binding.tvName.getText().toString());
                } else {
                    binding.profileToolbarLayout.setTitle("");
                }
            }
        });

        screenName = getIntent().getStringExtra("screen_name");

        setupViews();

        ViewPager viewPager = (ViewPager) findViewById(R.id.profile_viewpager);
        viewPager.setAdapter(new ProfileViewPagerAdapter(getSupportFragmentManager(),
                ProfileActivity.this));

        TabLayout tabLayout = (TabLayout) findViewById(R.id.profile_tabs);
        tabLayout.setupWithViewPager(viewPager);

    }


    public class ProfileViewPagerAdapter extends SmartFragmentStatePagerAdapter {

        private String[] tabTitles = {"TWEETS", "LIKES"};
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

        binding.tvName.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/HelveticaNeueBd.ttf"));
        binding.tvTagline.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/HelveticaNeueLight.ttf"));

        binding.tvFollowers.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/HelveticaNeueLight.ttf"));

        binding.tvFollowers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, HolderActivity.class);
                intent.putExtra("frag_type","followers");
                intent.putExtra("screen_name",screenName);
                startActivity(intent);
            }
        });

        binding.tvFollowing.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/HelveticaNeueLight.ttf"));
        binding.tvFollowing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, HolderActivity.class);
                intent.putExtra("frag_type","following");
                intent.putExtra("screen_name",screenName);
                startActivity(intent);
            }
        });

       TwitterClientApplication.getTwitterClient().getUserInfo(screenName, new JsonHttpResponseHandler() {
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

        binding.tvName.setText(user.getName());

        binding.tvProfileScreenName.setText("@"+ user.getScreenName());
        if(user.getDescription()==null || user.getDescription()=="" ) {
            binding.tvTagline.setVisibility(View.GONE);
        } else {
            binding.tvTagline.setVisibility(View.VISIBLE);
            binding.tvTagline.setText(user.getDescription());
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
                        }).into(binding.tvTagline);

        /**
         * Using a Span here to have a different style inside of each text view
         */
        ForegroundColorSpan blackSpan = new ForegroundColorSpan(
                getResources().getColor(R.color.twitterDarkerGrey));
        SpannableStringBuilder ssb = new SpannableStringBuilder(
                String.valueOf(GenericUtils.format(user.getFollowing())));
        ssb.setSpan(
                blackSpan,
                0,
                ssb.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.append(" FOLLOWING");
        binding.tvFollowing.setText(ssb, TextView.BufferType.EDITABLE);

        SpannableStringBuilder ssbFollowers = new SpannableStringBuilder(
                String.valueOf(GenericUtils.format(user.getFollowers())));
        ssbFollowers.setSpan(
                blackSpan,
                0,
                ssbFollowers.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssbFollowers.append(" FOLLOWERS");
        binding.tvFollowers.setText(ssbFollowers, TextView.BufferType.EDITABLE);

        binding.ivProfileUser.setImageResource(0);
        Glide.with(this).load(GenericUtils.modifyProfileImageUrl(user.getProfileImageUrl()))
                .fitCenter()
                .bitmapTransform( new RoundedCornersTransformation(this,5,0))
                .into(binding.ivProfileUser);

        binding.ivBackdrop.setImageResource(0);

        /**
         * Using the banner url
         */
        String imageUrl = user.getProfileBackground() + "/1500x500";
        Glide.with(this).load(imageUrl)
                 .diskCacheStrategy( DiskCacheStrategy.SOURCE )
                .into(binding.ivBackdrop);

        if(!user.isVerified()) {
            binding.ivProfileVerified.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
