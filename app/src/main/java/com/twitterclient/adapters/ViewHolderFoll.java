package com.twitterclient.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.twitterclient.R;

public class ViewHolderFoll extends RecyclerView.ViewHolder  {

    ImageView ivUser;
    TextView tvUser;
    ImageView ivVerified;
    TextView tvScreenName;
    TextView tvBody;
    Button btnFriend;

    public ViewHolderFoll(View itemView) {
        super(itemView);

        ivUser = (ImageView) itemView.findViewById(R.id.ivFollUser);
        tvUser = (TextView) itemView.findViewById(R.id.tvFollUsername);
        ivVerified = (ImageView) itemView.findViewById(R.id.ivFollVerified);
        tvScreenName = (TextView) itemView.findViewById(R.id.tvFollScreenName);
        tvBody = (TextView) itemView.findViewById(R.id.tvFollTagline);
        btnFriend = (Button) itemView.findViewById(R.id.btnFoll);

    }


}