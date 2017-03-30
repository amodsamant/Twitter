package com.twitterclient.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.twitterclient.R;
import com.twitterclient.fragments.ComposeTweetFragment;
import com.twitterclient.models.Tweet;

public class TimelineActivity extends AppCompatActivity
        implements ComposeTweetFragment.ComposeTweetListener {


    static long maxTweetId = -1;
    static long sinceId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");






//        if(!NetworkUtils.isNetworkAvailable(this) || !NetworkUtils.isOnline()) {
//            populateTimelineFromDb();
//        }









    }




//    /**
//     * Code for infinite scroll
//     */
//    public void loadNextDataFromApi() {
//        populateTimeline(maxTweetId,-1);
//    }



    @Override
    public void onFinishTweet(Tweet tweet) {
//        tweets.add(0,tweet);
//        adapter.notifyItemInserted(0);
//        layoutManager.scrollToPosition(0);
    }

//    public TwitterClient getTwitterClient() {
//        return twitterCLient;
//    }

//    /**
//     * Code to save to db
//     */
//    @Override
//    protected void onStop() {
//
//        FlowManager.getDatabase(TwitterDatabase.class)
//                .beginTransactionAsync(new ProcessModelTransaction.Builder<>(
//                        new ProcessModelTransaction.ProcessModel<Tweet>() {
//                            @Override
//                            public void processModel(Tweet tweet, DatabaseWrapper wrapper) {
//                                tweet.setImageUrl(tweet.getEntities().getMedia().get(0).getMediaUrlHttps()+":medium");
//                                tweet.save();
//                            }
//                        }).addAll(tweets).build())
//                .error(new Transaction.Error() {
//                    @Override
//                    public void onError(Transaction transaction, Throwable error) {
//                        Log.e("ERROR", error.getMessage());
//                    }
//                })
//                .success(new Transaction.Success() {
//                    @Override
//                    public void onSuccess(Transaction transaction) {
//                        Log.d("DEBUG", transaction.toString());
//                    }
//                }).build().execute();
//
//        super.onStop();
//    }

//    /**
//     * Code to populate from database when no internet connection
//     */
//    public void populateTimelineFromDb() {
//        List<Tweet> tweetsFromDb = SQLite.select().from(Tweet.class).limit(50).queryList();
//        tweets.addAll(tweetsFromDb);
//
//    }





}
