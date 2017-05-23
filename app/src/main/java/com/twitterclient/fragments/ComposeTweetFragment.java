package com.twitterclient.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.twitterclient.R;
import com.twitterclient.databinding.ComposeFragBinding;
import com.twitterclient.helpers.SharedPrefHelper;
import com.twitterclient.models.Tweet;
import com.twitterclient.models.User;
import com.twitterclient.network.TwitterClient;
import com.twitterclient.network.TwitterClientApplication;
import com.twitterclient.utils.GenericUtils;

import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class ComposeTweetFragment extends DialogFragment
        implements DraftsFragment.DraftsFragmentListener{

    ComposeFragBinding binding;

    private ComposeTweetListener listener;
    public interface ComposeTweetListener {
        void onFinishTweet(Tweet tweet);
    }

    static final String TAG = ComposeTweetFragment.class.getSimpleName();
    static final String TWEET = "tweet";

    public ComposeTweetFragment() {
    }

    public static ComposeTweetFragment getInstance(String tweetData) {
        ComposeTweetFragment frag = new ComposeTweetFragment();
        Bundle args = new Bundle();
        args.putString(TWEET,tweetData);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ComposeTweetListener) {
            listener = (ComposeTweetListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement ComposeTweetFragment.ComposeTweetListener");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater,R.layout.compose_frag,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        if(new SharedPrefHelper().getAll(getActivity()).isEmpty()) {
            binding.btnDraft.setVisibility(View.GONE);
        }

        /**
         * OnClick for draft button
         */
        binding.btnDraft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                DraftsFragment fragment = DraftsFragment.getInstance();
                fragment.setTargetFragment(ComposeTweetFragment.this, 0);
                fragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_FullScreen);
                fragment.show(fm,"drafts_frag");
            }
        });

        /**
         * Code to set the count and style the compose tweet body
         */
        binding.etTweet.setHorizontallyScrolling(false);
        binding.etTweet.setHintTextColor(Color.GRAY);
        binding.etTweet.setTextColor(Color.GRAY);
        binding.etTweet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int count = 140 - charSequence.length();
                binding.tvLetterCount.setText(String.valueOf(count));
                if(count<0) {
                    binding.tvLetterCount.setTextColor(Color.RED);
                    binding.btnTweet.setClickable(false);
                    binding.btnTweet.setBackgroundColor(ContextCompat
                            .getColor(getContext(),R.color.fadedBlue));
                } else {
                    binding.btnTweet.setBackgroundColor(ContextCompat
                            .getColor(getContext(),R.color.twitterBlue));
                    binding.btnTweet.setClickable(true);
                    binding.tvLetterCount.setTextColor(ContextCompat
                            .getColor(getContext(),R.color.grey));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        /**
         * Add the text only if its passed this fragment
         */
        String screenName = getArguments().getString(TWEET);
        if(screenName!=null) {
            binding.etTweet.setText(screenName);
            binding.etTweet.setSelection(screenName.length());
        }

        /**
         * Listener for closing the compose fragment
         */
        binding.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.etTweet.getText().length()>0) {
                    MaterialDialog dialog = new MaterialDialog.Builder(getContext())
                            .content("Save draft?")
                            .positiveText("SAVE")
                            .negativeText("DELETE")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    onSaveDraft(binding.etTweet.getText().toString());
                                    binding.btnDraft.setVisibility(View.VISIBLE);
                                    dismiss();
                                }
                            })
                            .onNegative(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    dismiss();
                                }
                            }).build();

                    dialog.show();
                } else {
                    dismiss();
                }
            }
        });

        /**
         * Listener for tweeting
         */
        binding.btnTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tweet = binding.etTweet.getText().toString();
                if(!tweet.isEmpty()){
                    postTweet(tweet);
                }
                dismiss();
            }
        });

        /**
         * Here personal info is requested to get the profile url
         */
        TwitterClient twitterCLient = TwitterClientApplication.getTwitterClient();
        twitterCLient.getPersonalUserInfo(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Gson gson = new Gson();
                User user = gson.fromJson(response.toString(),
                        User.class);

                binding.ivUser.setImageResource(0);
                String profileImageUrl = GenericUtils.modifyProfileImageUrl(user.getProfileImageUrl());
                Glide.with(getContext()).load(profileImageUrl)
                        .fitCenter()
                        .bitmapTransform(new RoundedCornersTransformation(getContext(),5,0))
                        .into(binding.ivUser);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  Throwable throwable, JSONObject errorResponse) {
                /**
                 * Need not be handled as this would not cause any user issue
                 */
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });


    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    /**
     * Set the keyboard soft input
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow()
                .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    /**
     * Sets the windows to the correct size for this fragment
     */
    @Override
    public void onResume() {

        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        super.onResume();
    }

    /**
     * Function handles the tweet post
     * @param tweetStr
     */
    public void postTweet(String tweetStr) {

        TwitterClientApplication.getTwitterClient().postTweet(tweetStr,
                new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                        Gson gson = new Gson();
                        Log.d(TAG, response.toString());
                        try {
                            Tweet tweet = gson.fromJson(response.toString(), Tweet.class);
                            listener.onFinishTweet(tweet);
                        } catch (Exception e) {
                            Snackbar snackbar = Snackbar
                                    .make(getView(), "Error in tweeting",
                                            Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable,
                                          JSONObject errorResponse) {
                        Snackbar snackbar = Snackbar
                                .make(getView(), "Error in posting the tweet",
                                        Snackbar.LENGTH_LONG);
                        snackbar.show();
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                    }
                });
    }


    /**
     * Function used to save the draft to preferences
     * @param draftTweet
     */
    private void onSaveDraft(String draftTweet) {

        SharedPreferences.Editor editor = SharedPrefHelper.getSharedPreferences(getActivity()).edit();
        editor.putString(draftTweet, draftTweet);
        editor.apply();
        binding.btnDraft.setVisibility(View.VISIBLE);

    }


    @Override
    public void onFinishDraft(String tweet) {

        binding.etTweet.setText(tweet);
        binding.etTweet.setSelection(tweet.length());

        List<String> drafts = new SharedPrefHelper().getAll(getActivity());
        if(drafts==null || drafts.isEmpty()) {
            binding.btnDraft.setVisibility(View.GONE);
        }
    }
}