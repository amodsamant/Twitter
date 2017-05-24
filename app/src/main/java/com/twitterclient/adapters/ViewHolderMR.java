package com.twitterclient.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.twitterclient.R;

public class ViewHolderMR extends RecyclerView.ViewHolder  {

    ImageView ivUser;
    TextView tvUser;
    ImageView ivVerified;
    TextView tvScreenName;
    TextView tvBody;
    TextView tvRelTime;

    public ViewHolderMR(View itemView) {
        super(itemView);

        ivUser = (ImageView) itemView.findViewById(R.id.ivUserMR);
        tvUser = (TextView) itemView.findViewById(R.id.tvUsernameMR);
        ivVerified = (ImageView) itemView.findViewById(R.id.ivVerifiedBR);
        tvScreenName = (TextView) itemView.findViewById(R.id.tvScreenNameMR);
        tvBody = (TextView) itemView.findViewById(R.id.tvTextMR);
        tvRelTime = (TextView) itemView.findViewById(R.id.tvSinceTimeMR);
    }


}