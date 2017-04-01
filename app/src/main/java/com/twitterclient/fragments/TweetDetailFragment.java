package com.twitterclient.fragments;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.twitterclient.R;
import com.twitterclient.activities.ProfileActivity;
import com.twitterclient.models.Tweet;
import com.twitterclient.utils.DateGenericUtils;
import com.twitterclient.utils.PatternEditableBuilder;

import org.parceler.Parcels;

import java.util.regex.Pattern;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class TweetDetailFragment extends Fragment {

    public static TweetDetailFragment newInstance(Tweet tweet) {

        TweetDetailFragment tweetDetailFragment = new TweetDetailFragment();

        Bundle args = new Bundle();
        args.putParcelable("tweet", Parcels.wrap(tweet));
        tweetDetailFragment.setArguments(args);
        return tweetDetailFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_tweet_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView tvUsername = (TextView) view.findViewById(R.id.tvUsername);

        TextView tvScreenName = (TextView) view.findViewById(R.id.tvScreenName);

        TextView tvText = (TextView) view.findViewById(R.id.tvText);
        tvText.setTypeface(Typeface.createFromAsset(getContext().getAssets(),
                "fonts/HelveticaNeueLight.ttf"));

        TextView tvRetweetCount = (TextView) view.findViewById(R.id.tvRetweetCount);
        tvRetweetCount.setTypeface(Typeface.createFromAsset(getContext().getAssets(),
                "fonts/HelveticaNeueLight.ttf"));
        TextView tvLikeCount = (TextView) view.findViewById(R.id.tvLikeCount);
        tvLikeCount.setTypeface(Typeface.createFromAsset(getContext().getAssets(),
                "fonts/HelveticaNeueLight.ttf"));

        ImageView ivUser = (ImageView) view.findViewById(R.id.ivUser);
        ImageView ivTweet = (ImageView) view.findViewById(R.id.ivTweet);
        ImageView ivVerified = (ImageView) view.findViewById(R.id.ivVerified);

        TextView tvDate = (TextView) view.findViewById(R.id.tvDate);
        tvDate.setTypeface(Typeface.createFromAsset(getContext().getAssets(),
                "fonts/HelveticaNeueLight.ttf"));

        TextView tvTime = (TextView) view.findViewById(R.id.tvTime);
        tvTime.setTypeface(Typeface.createFromAsset(getContext().getAssets(),
                "fonts/HelveticaNeueLight.ttf"));

        final Tweet tweet = Parcels.unwrap(getArguments().getParcelable("tweet"));

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
                .bitmapTransform( new RoundedCornersTransformation(getContext(),5,0))
                .diskCacheStrategy( DiskCacheStrategy.SOURCE )
                .into(ivUser);

        ivTweet.setImageResource(0);
        if(tweet.getEntities()!=null && tweet.getEntities().getMedia()!=null &&
                !tweet.getEntities().getMedia().isEmpty()  &&
                tweet.getEntities().getMedia().get(0).getMediaUrlHttps()!=null) {
            ivTweet.setVisibility(View.VISIBLE);

            /**
             * Using large image url with RoundedCornersTransformation
             */
            String imageUrl = tweet.getEntities().getMedia().get(0).getMediaUrlHttps()+":large";
            Glide.with(this).load(imageUrl)
                    .fitCenter()
                    .bitmapTransform( new RoundedCornersTransformation(getContext(),20,5))
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


        Button btnReply = (Button) view.findViewById(R.id.btnReply);
        btnReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                ComposeTweetFragment fragment = ComposeTweetFragment
                        .getInstance("@ "+ tweet.getUser().getScreenName()+ " ");
                fragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_FullScreen);
                fragment.show(fm,"compose_frag");
            }
        });


        final Button btnLike = (Button) view.findViewById(R.id.btnLike);
        if(tweet.isFavorited()) {
            Drawable img = getResources().getDrawable(R.drawable.ic_favorite_set);
            btnLike.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
        }
        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!tweet.isFavorited()) {
                    Drawable img = getResources().getDrawable(R.drawable.ic_favorite_set);
                    btnLike.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
                    tweet.setFavouritesCount(tweet.getFavouritesCount() + 1);
                    tweet.setFavorited(true);

                } else {
                    Drawable img = getResources().getDrawable(R.drawable.ic_favorite);
                    btnLike.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
                    tweet.setFavouritesCount(tweet.getFavouritesCount() - 1);
                    tweet.setFavorited(false);

                }
            }
        });



        final Button btnRetweet = (Button) view.findViewById(R.id.btnRetweet);
        if(tweet.isRetweeted()) {
            Drawable img = getResources().getDrawable(R.drawable.ic_retweet_set);
            btnRetweet.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
        }
        btnRetweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!tweet.isRetweeted()) {
                    Drawable img = getResources().getDrawable(R.drawable.ic_retweet_set);
                    btnRetweet.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
                    tweet.setRetweetCount(tweet.getRetweetCount() + 1);
                    tweet.setRetweeted(true);

                } else {
                    Drawable img = getResources().getDrawable(R.drawable.ic_retweet);
                    btnRetweet.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
                    tweet.setRetweetCount(tweet.getRetweetCount() - 1);
                    tweet.setRetweeted(false);
                }
            }
        });

        new PatternEditableBuilder().
                addPattern(Pattern.compile("\\@(\\w+)"),
                        this.getResources().getColor(R.color.twitterBlue),
                        new PatternEditableBuilder.SpannableClickedListener() {
                            @Override
                            public void onSpanClicked(String text) {
                                Intent intent = new Intent(getContext(),
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
}
