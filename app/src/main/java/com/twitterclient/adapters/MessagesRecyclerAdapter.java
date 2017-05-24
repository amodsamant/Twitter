package com.twitterclient.adapters;

import static com.twitterclient.utils.Constants.SCREEN_NAME;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.twitterclient.R;
import com.twitterclient.activities.HolderActivity;
import com.twitterclient.activities.ProfileActivity;
import com.twitterclient.models.Message;
import com.twitterclient.utils.DateGenericUtils;
import com.twitterclient.utils.GenericUtils;

import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;


public class MessagesRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<Message> mMessages;

    public MessagesRecyclerAdapter(Context context, List<Message> messages) {
        this.mContext = context;
        this.mMessages = messages;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            default:
                View view = inflater.inflate(R.layout.message_row, parent, false);
                viewHolder = new ViewHolderMR(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final Message message = mMessages.get(position);
        final ViewHolderMR viewHolder = (ViewHolderMR) holder;

        viewHolder.tvUser.setText(message.getSender().getName());
        if(message.getSender()!=null &&  message.getSender().isVerified()) {
            viewHolder.ivVerified.setVisibility(View.VISIBLE);
        } else {
            viewHolder.ivVerified.setVisibility(View.GONE);
        }

        viewHolder.tvScreenName.setText("@"+ message.getSender().getScreenName());
        viewHolder.tvRelTime.setText(DateGenericUtils.
                getRelativeTimeAgo( message.getCreatedAt()));
        viewHolder.ivUser.setImageResource(0);
        String profileImageUrl = GenericUtils.modifyProfileImageUrl(
                message.getSender().getProfileImageUrl());
        Glide.with(mContext).load(profileImageUrl)
                .fitCenter()
                .bitmapTransform(new CropCircleTransformation(mContext))
                .diskCacheStrategy( DiskCacheStrategy.SOURCE )
                .into(viewHolder.ivUser);

        viewHolder.ivUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ProfileActivity.class);
                intent.putExtra(SCREEN_NAME, message.getSender().getScreenName());
                mContext.startActivity(intent);
            }
        });

        viewHolder.tvBody.setText(message.getText());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, HolderActivity.class);
                intent.putExtra("frag_type", "message");
                intent.putExtra("message", Parcels.wrap(message));
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

}
