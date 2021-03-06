package com.twitterclient.adapters;

import static com.twitterclient.utils.Constants.SCREEN_NAME;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.twitterclient.R;
import com.twitterclient.activities.HolderActivity;
import com.twitterclient.activities.ProfileActivity;
import com.twitterclient.fragments.ComposeTweetFragment;
import com.twitterclient.models.Tweet;
import com.twitterclient.utils.Constants;
import com.twitterclient.utils.DateGenericUtils;
import com.twitterclient.utils.GenericUtils;
import com.twitterclient.utils.PatternEditableBuilder;

import org.parceler.Parcels;

import java.util.List;
import java.util.regex.Pattern;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;


public class TimelineRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context mContext;
    List<Tweet> mTweets;

    public TimelineRecyclerAdapter(Context context, List<Tweet> tweets) {
        this.mContext = context;
        this.mTweets = tweets;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder;

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {

            default:
                View view = inflater.inflate(R.layout.basic_tweet_row, parent, false);
                viewHolder = new ViewHolderBR(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final Tweet tweet = mTweets.get(position);
        final ViewHolderBR viewHolder = (ViewHolderBR) holder;

        viewHolder.tvUser.setText(tweet.getUser().getName());
        if(tweet.getUser()!=null && tweet.getUser().isVerified()) {
            viewHolder.ivVerified.setVisibility(View.VISIBLE);
        } else {
            viewHolder.ivVerified.setVisibility(View.GONE);
        }

        viewHolder.tvScreenName.setText("@"+tweet.getUser().getScreenName());
        viewHolder.tvRelTime.setText(DateGenericUtils.
                getRelativeTimeAgo(tweet.getCreatedAt()));
        viewHolder.ivUser.setImageResource(0);

        /**
         * Code to set profile image
         */
        String profileImageUrl = GenericUtils.modifyProfileImageUrl(tweet.getUser().getProfileImageUrl());
        Glide.with(mContext).load(profileImageUrl)
                .fitCenter()
                .bitmapTransform(new RoundedCornersTransformation(mContext,5,0))
                .diskCacheStrategy( DiskCacheStrategy.SOURCE )
                .into(viewHolder.ivUser);


        viewHolder.ivUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ProfileActivity.class);
                intent.putExtra(SCREEN_NAME, tweet.getUser().getScreenName());
                mContext.startActivity(intent);
            }
        });


        /**
         * Code to set tweet image
         */
        viewHolder.ivTweet.setImageResource(0);
        if(tweet.getEntities()!=null && tweet.getEntities().getMedia()!=null &&
                !tweet.getEntities().getMedia().isEmpty()  &&
                tweet.getEntities().getMedia().get(0).getMediaUrlHttps()!=null) {

            DisplayMetrics displayMetrics = new DisplayMetrics();
            WindowManager window = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            window.getDefaultDisplay().getMetrics(displayMetrics);
            int height = displayMetrics.heightPixels;
            int width = displayMetrics.widthPixels;

            String imageUrl = tweet.getEntities().getMedia().get(0).getMediaUrlHttps()+":large";

            Glide.with(mContext).load(imageUrl)
                    .override(width/2,height/2)
                    .fitCenter()
                    .bitmapTransform( new RoundedCornersTransformation(mContext,10,0))
                    .diskCacheStrategy( DiskCacheStrategy.SOURCE )
                    .into(viewHolder.ivTweet);
        }

        /**
         * TODO: Handle video url
         */
        if(tweet.getExtendedEntities()!=null && tweet.getExtendedEntities().getMedia()!=null &&
                !tweet.getExtendedEntities().getMedia().isEmpty()  &&
                tweet.getExtendedEntities().getMedia().get(0).getType().equalsIgnoreCase("video")) {

            String videoUrl = tweet.getExtendedEntities()
                    .getMedia().get(0).getVideoInfo().getVariants().get(0).getUrl();

        }

        /* Custom font HelveticaNeueLight */
        Typeface fontLight = Typeface
                .createFromAsset(mContext.getAssets(), Constants.FONT_LIGHT);
        viewHolder.tvBody.setTypeface(fontLight);
        viewHolder.tvBody.setText(tweet.getBody());
        new PatternEditableBuilder().
                addPattern(Pattern.compile("\\@(\\w+)"),
                        mContext.getResources().getColor(R.color.twitterBlue),
                        new PatternEditableBuilder.SpannableClickedListener() {
                            @Override
                            public void onSpanClicked(String text) {
                                Intent intent = new Intent(mContext, ProfileActivity.class);
                                intent.putExtra(SCREEN_NAME, text.substring(1));
                                mContext.startActivity(intent);
                            }
                        }).into(viewHolder.tvBody);

        new PatternEditableBuilder().
                addPattern(Pattern.compile("\\#(\\w+)"),
                        mContext.getResources().getColor(R.color.twitterBlue),null)
                .into(viewHolder.tvBody);

        viewHolder.btnRetweet.setText(String.valueOf(tweet.getRetweetCount()));
        if(!tweet.isRetweeted()) {
            Drawable img = mContext.getResources().getDrawable(R.drawable.ic_retweet);
            viewHolder.btnRetweet.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
        } else {
            Drawable img = mContext.getResources().getDrawable(R.drawable.ic_retweet_set);
            viewHolder.btnRetweet.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
        }
        viewHolder.btnRetweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!tweet.isRetweeted()) {
                    Drawable img = mContext.getResources().getDrawable(R.drawable.ic_retweet_set);
                    viewHolder.btnRetweet.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
                    tweet.setRetweetCount(tweet.getRetweetCount() + 1);
                    tweet.setRetweeted(true);
                    viewHolder.btnRetweet.setText(String.valueOf(tweet.getRetweetCount()));
                } else {
                    Drawable img = mContext.getResources().getDrawable(R.drawable.ic_retweet);
                    viewHolder.btnRetweet.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
                    tweet.setRetweetCount(tweet.getRetweetCount() - 1);
                    tweet.setRetweeted(false);
                    viewHolder.btnRetweet.setText(String.valueOf(tweet.getRetweetCount()));
                }
            }
        });

        viewHolder.btnLike.setText(String.valueOf(tweet.getFavouritesCount()));
        if(!tweet.isFavorited()) {
            Drawable img = mContext.getResources().getDrawable(R.drawable.ic_favorite);
            viewHolder.btnLike.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
        } else {
            Drawable img = mContext.getResources().getDrawable(R.drawable.ic_favorite_set);
            viewHolder.btnLike.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
        }
        viewHolder.btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!tweet.isFavorited()) {
                    Drawable img = mContext.getResources().getDrawable(R.drawable.ic_favorite_set);
                    viewHolder.btnLike.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
                    tweet.setFavouritesCount(tweet.getFavouritesCount() + 1);
                    tweet.setFavorited(true);
                    viewHolder.btnLike.setText(String.valueOf(tweet.getFavouritesCount()));
                } else {
                    Drawable img = mContext.getResources().getDrawable(R.drawable.ic_favorite);
                    viewHolder.btnLike.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
                    tweet.setFavouritesCount(tweet.getFavouritesCount() - 1);
                    tweet.setFavorited(false);
                    viewHolder.btnLike.setText(String.valueOf(tweet.getFavouritesCount()));
                }
            }
        });

        viewHolder.btnReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = ((AppCompatActivity)mContext).getSupportFragmentManager();
                ComposeTweetFragment fragment = ComposeTweetFragment
                        .getInstance("@"+tweet.getUser().getScreenName());
                fragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_FullScreen);
                fragment.show(fm,"compose_frag");
            }
        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, HolderActivity.class);
                intent.putExtra("frag_type", "detail");
                intent.putExtra("tweet", Parcels.wrap(tweet));
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mTweets.size();
    }
}
