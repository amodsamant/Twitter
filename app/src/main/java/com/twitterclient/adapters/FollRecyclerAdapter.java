package com.twitterclient.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.twitterclient.R;
import com.twitterclient.models.User;
import com.twitterclient.utils.Constants;
import com.twitterclient.utils.GenericUtils;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class FollRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<User> mUsers;

    public FollRecyclerAdapter(Context context, List<User> users) {
        this.mContext = context;
        this.mUsers = users;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            default:
                View view = inflater.inflate(R.layout.basic_foll_row, parent, false);
                viewHolder = new ViewHolderFoll(view);

        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        User user = mUsers.get(position);
        ViewHolderFoll viewHolder = (ViewHolderFoll) holder;

        viewHolder.tvUser.setText(user.getName());
        if(user.isVerified()) {
            viewHolder.ivVerified.setVisibility(View.VISIBLE);
        } else {
            viewHolder.ivVerified.setVisibility(View.GONE);
        }

        viewHolder.tvScreenName.setText("@"+user.getScreenName());

        viewHolder.ivUser.setImageResource(0);

        /* Code to set profile image */
        String profileImageUrl = GenericUtils.modifyProfileImageUrl(user.getProfileImageUrl());
        Glide.with(mContext).load(profileImageUrl)
                .fitCenter()
                .bitmapTransform(new RoundedCornersTransformation(mContext,5,0))
                .diskCacheStrategy( DiskCacheStrategy.SOURCE )
                .into(viewHolder.ivUser);

        if(!user.isFollowing()) {
            Drawable img = mContext.getResources().getDrawable(R.drawable.ic_friend_add);

            viewHolder.btnFriend
                    .setBackground(mContext.getResources().getDrawable(R.drawable.border_button_add));
            viewHolder.btnFriend.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
        } else {
            Drawable img = mContext.getResources().getDrawable(R.drawable.ic_friend);
            viewHolder.btnFriend.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
        }

        viewHolder.tvBody.setText(user.getDescription());
        Typeface fontLight = Typeface
                .createFromAsset(mContext.getAssets(), Constants.FONT_LIGHT);
        viewHolder.tvBody.setTypeface(fontLight);

    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }
}
