package com.twitterclient.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.twitterclient.R;

import java.util.List;

public class DraftsAdapter extends ArrayAdapter<String> {

    public DraftsAdapter(Context context, List<String> drafts) {
        super(context, 0, drafts);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String draftText = getItem(position);

        if(convertView==null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.draft_item, parent, false);
        }
        TextView tvDraft = (TextView) convertView.findViewById(R.id.tvDraft);
        tvDraft.setText(draftText);

        return convertView;
    }


}
