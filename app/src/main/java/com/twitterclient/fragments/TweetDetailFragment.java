package com.twitterclient.fragments;

import android.content.Intent;
import android.databinding.DataBindingUtil;
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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.twitterclient.R;
import com.twitterclient.activities.ProfileActivity;
import com.twitterclient.databinding.FragTweetDetailBinding;
import com.twitterclient.models.Tweet;
import com.twitterclient.utils.DateGenericUtils;
import com.twitterclient.utils.GenericUtils;
import com.twitterclient.utils.PatternEditableBuilder;

import org.parceler.Parcels;

import java.util.regex.Pattern;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class TweetDetailFragment extends Fragment {

    FragTweetDetailBinding binding;

    ForegroundColorSpan twitterGreySpan;

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
        binding = DataBindingUtil.inflate(inflater,R.layout.frag_tweet_detail,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        twitterGreySpan = new ForegroundColorSpan(
                getResources().getColor(R.color.twitterDarkerGrey));

        binding.tvText.setTypeface(Typeface.createFromAsset(getContext().getAssets(),
                GenericUtils.LIGHT_FONT));

        binding.tvRetweetCount.setTypeface(Typeface.createFromAsset(getContext().getAssets(),
                GenericUtils.LIGHT_FONT));
        binding.tvLikeCount.setTypeface(Typeface.createFromAsset(getContext().getAssets(),
                GenericUtils.LIGHT_FONT));

        binding.tvDate.setTypeface(Typeface.createFromAsset(getContext().getAssets(),
                GenericUtils.LIGHT_FONT));

        binding.tvTime.setTypeface(Typeface.createFromAsset(getContext().getAssets(),
                GenericUtils.LIGHT_FONT));

        final Tweet tweet = Parcels.unwrap(getArguments().getParcelable("tweet"));

        binding.tvUsername.setText(tweet.getUser().getName());
        binding.tvScreenName.setText("@"+tweet.getUser().getScreenName());
        binding.tvText.setText(tweet.getBody());

        updateRetweets(tweet);
        updateLikes(tweet);

        binding.ivUser.setImageResource(0);
        Glide.with(this).load(tweet.getUser().getProfileImageUrl())
                .fitCenter()
                .bitmapTransform( new RoundedCornersTransformation(getContext(),5,0))
                .diskCacheStrategy( DiskCacheStrategy.SOURCE )
                .into(binding.ivUser);

        binding.ivTweet.setImageResource(0);
        if(tweet.getEntities()!=null && tweet.getEntities().getMedia()!=null &&
                !tweet.getEntities().getMedia().isEmpty()  &&
                tweet.getEntities().getMedia().get(0).getMediaUrlHttps()!=null) {
            binding.ivTweet.setVisibility(View.VISIBLE);

            /**
             * Using large image url with RoundedCornersTransformation
             */
            String imageUrl = tweet.getEntities().getMedia().get(0).getMediaUrlHttps()+":large";
            Glide.with(this).load(imageUrl)
                    .fitCenter()
                    .bitmapTransform( new RoundedCornersTransformation(getContext(),20,5))
                    .diskCacheStrategy( DiskCacheStrategy.SOURCE )
                    .into(binding.ivTweet);
        } else {
            binding.ivTweet.setVisibility(View.GONE);
        }

        if(!tweet.getUser().isVerified()) {
            binding.ivVerified.setVisibility(View.GONE);
        }

        binding.tvDate.setText(DateGenericUtils.getDate(tweet.getCreatedAt()));
        binding.tvTime.setText(DateGenericUtils.getTime(tweet.getCreatedAt()));


        binding.btnReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                ComposeTweetFragment fragment = ComposeTweetFragment
                        .getInstance("@ "+ tweet.getUser().getScreenName()+ " ");
                fragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_FullScreen);
                fragment.show(fm,"compose_frag");
            }
        });

        if(tweet.isFavorited()) {
            Drawable img = getResources().getDrawable(R.drawable.ic_favorite_set);
            binding.btnLike.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
        }
        binding.btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!tweet.isFavorited()) {
                    Drawable img = getResources().getDrawable(R.drawable.ic_favorite_set);
                    binding.btnLike.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
                    tweet.setFavouritesCount(tweet.getFavouritesCount() + 1);
                    tweet.setFavouritesCount(tweet.getFavouritesCount());
                    tweet.setFavorited(true);
                } else {
                    Drawable img = getResources().getDrawable(R.drawable.ic_favorite);
                    binding.btnLike.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
                    tweet.setFavouritesCount(tweet.getFavouritesCount() - 1);
                    tweet.setFavouritesCount(tweet.getFavouritesCount());
                    tweet.setFavorited(false);
                }
                updateLikes(tweet);
            }
        });


        if(tweet.isRetweeted()) {
            Drawable img = getResources().getDrawable(R.drawable.ic_retweet_set);
            binding.btnRetweet.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
        }
        binding.btnRetweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!tweet.isRetweeted()) {
                    Drawable img = getResources().getDrawable(R.drawable.ic_retweet_set);
                    binding.btnRetweet
                            .setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
                    tweet.setRetweetCount(tweet.getRetweetCount() + 1);
                    tweet.setRetweeted(true);

                } else {
                    Drawable img = getResources().getDrawable(R.drawable.ic_retweet);
                    binding.btnRetweet
                            .setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
                    tweet.setRetweetCount(tweet.getRetweetCount() - 1);
                    tweet.setRetweeted(false);
                }
                updateRetweets(tweet);
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
                        }).into(binding.tvText);

        new PatternEditableBuilder().
                addPattern(Pattern.compile("\\#(\\w+)"),
                        this.getResources().getColor(R.color.twitterBlue),null).into(binding.tvText);
    }

    /**
     * Function udpates the tweets to the desired span styles
     * @param tweet
     */
    void updateRetweets(Tweet tweet) {

        SpannableStringBuilder ssb = new SpannableStringBuilder(
                String.valueOf(tweet.getRetweetCount()));
        ssb.setSpan(
                twitterGreySpan,
                0,
                ssb.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.append(" RETWEETS");
        binding.tvRetweetCount.setText(ssb, TextView.BufferType.EDITABLE);
    }

    /**
     * Function udpates the tweets to the desired span styles
     * @param tweet
     */
    void updateLikes(Tweet tweet) {

        SpannableStringBuilder ssbFav = new SpannableStringBuilder(
                String.valueOf(tweet.getFavouritesCount()));
        ssbFav.setSpan(
                twitterGreySpan,
                0,
                ssbFav.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssbFav.append(" LIKES");
        binding.tvLikeCount.setText(ssbFav, TextView.BufferType.EDITABLE);

    }
}
