package com.twitterclient.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.twitterclient.R;
import com.twitterclient.fragments.ComposeTweetFragment;
import com.twitterclient.models.Tweet;
import com.twitterclient.utils.DateGenericUtils;
import com.twitterclient.utils.PatternEditableBuilder;

import org.parceler.Parcels;

import java.util.regex.Pattern;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;


public class TweetDetailActivity extends AppCompatActivity {

    DisplayMetrics displayMetrics = new DisplayMetrics();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarDetail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Tweet");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView tvUsername = (TextView) findViewById(R.id.tvUsername);

        TextView tvScreenName = (TextView) findViewById(R.id.tvScreenName);

        TextView tvText = (TextView) findViewById(R.id.tvText);
        tvText.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/HelveticaNeueLight.ttf"));

        TextView tvRetweetCount = (TextView) findViewById(R.id.tvRetweetCount);
        tvRetweetCount.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/HelveticaNeueLight.ttf"));
        TextView tvLikeCount = (TextView) findViewById(R.id.tvLikeCount);
        tvLikeCount.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/HelveticaNeueLight.ttf"));

        ImageView ivUser = (ImageView) findViewById(R.id.ivUser);
        ImageView ivTweet = (ImageView) findViewById(R.id.ivTweet);
        ImageView ivVerified = (ImageView) findViewById(R.id.ivVerified);

        TextView tvDate = (TextView) findViewById(R.id.tvDate);
        tvDate.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/HelveticaNeueLight.ttf"));

        TextView tvTime = (TextView) findViewById(R.id.tvTime);
        tvTime.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/HelveticaNeueLight.ttf"));

        final Tweet tweet = Parcels.unwrap(getIntent().getParcelableExtra("tweet"));

        tvUsername.setText(tweet.getUser().getName());
        tvScreenName.setText("@"+tweet.getUser().getScreenName());
        tvText.setText(tweet.getBody());

        /**
         * Using a Span here to have a different style inside of each text view
         */
        ForegroundColorSpan blackSpan = new ForegroundColorSpan(
                getResources().getColor(R.color.twitterDarkerGrey));
        SpannableStringBuilder ssb = new SpannableStringBuilder(String.valueOf(tweet.getRetweetCount()));
        ssb.setSpan(
                blackSpan,
                0,
                ssb.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.append(" RETWEETS");
        tvRetweetCount.setText(ssb, TextView.BufferType.EDITABLE);

        SpannableStringBuilder ssbFav = new SpannableStringBuilder(String.valueOf(tweet.getFavouritesCount()));
        ssbFav.setSpan(
                blackSpan,
                0,
                ssbFav.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssbFav.append(" LIKES");
        tvLikeCount.setText(ssbFav, TextView.BufferType.EDITABLE);

        ivUser.setImageResource(0);
        Glide.with(this).load(tweet.getUser().getProfileImageUrl())
                .fitCenter()
                .bitmapTransform( new RoundedCornersTransformation(this,5,0))
                .diskCacheStrategy( DiskCacheStrategy.SOURCE )
                .into(ivUser);

        ivTweet.setImageResource(0);
        if(tweet.getEntities()!=null && tweet.getEntities().getMedia()!=null &&
                !tweet.getEntities().getMedia().isEmpty()  &&
                tweet.getEntities().getMedia().get(0).getMediaUrlHttps()!=null) {
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int height = displayMetrics.heightPixels;
            int width = displayMetrics.widthPixels;
            ivTweet.setVisibility(View.VISIBLE);

            /**
             * Using large image url with RoundedCornersTransformation
             */
            String imageUrl = tweet.getEntities().getMedia().get(0).getMediaUrlHttps()+":large";
            Glide.with(this).load(imageUrl)
                    .override(width,height)
                    .fitCenter()
                    .bitmapTransform( new RoundedCornersTransformation(this,20,5))
                    .diskCacheStrategy( DiskCacheStrategy.SOURCE )
                    .into(ivTweet);
        } else {
            ivTweet.setVisibility(View.GONE);
        }

        if(!tweet.getUser().isVerified()) {
            ivVerified.setVisibility(View.GONE);
        }

        tvDate.setText(DateGenericUtils.getDate(tweet.getCreatedAt()));
        tvTime.setText(DateGenericUtils.getTime(tweet.getCreatedAt()));


        Button btnReply = (Button) findViewById(R.id.btnReply);
        btnReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getSupportFragmentManager();
                ComposeTweetFragment fragment = ComposeTweetFragment
                        .getInstance("@ "+ tweet.getUser().getScreenName()+ " ");
                fragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_FullScreen);
                fragment.show(fm,"compose_frag");
            }
        });


        Button btnLike = (Button) findViewById(R.id.btnLike);
        if(tweet.isFavorited()) {
            Drawable img = getResources().getDrawable(R.drawable.ic_favorite_set);
            btnLike.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
        }

        final Button btnRetweet = (Button) findViewById(R.id.btnRetweet);
        if(tweet.isRetweeted()) {
            Drawable img = getResources().getDrawable(R.drawable.ic_retweet_set);
            btnRetweet.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
        }

        new PatternEditableBuilder().
                addPattern(Pattern.compile("\\@(\\w+)"),
                        this.getResources().getColor(R.color.twitterBlue),
                        new PatternEditableBuilder.SpannableClickedListener() {
                            @Override
                            public void onSpanClicked(String text) {
                                Intent intent = new Intent(TweetDetailActivity.this,
                                        ProfileActivity.class);
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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
