package com.twitterclient.network;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

public class TwitterClient extends OAuthBaseClient {
    public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class;
    public static final String BASE_API_URL = "https://api.twitter.com/1.1";
    public static final String REST_CONSUMER_KEY = "dgQwWB99bxJzoaIvGi6RPzIT8";
    public static final String REST_CONSUMER_SECRET = "asPiAAshcipGDa2f7KY0XDaFHDmK9tgYTzuI0aAvfTMO4obZDu";
    public static final String REST_CALLBACK_URL = "oauth://amodtwitterclient";

    public static final int RETRIEVE_COUNT = 30;

    public static final String COUNT = "count";
    public static final String MAX_ID = "max_id";
    public static final String SINCE_ID = "since_id";
    public static final String INCLUDE_ENTITIES = "include_entities";
    public static final String SCREEN_NAME = "screen_name";
    public static final String INCLUDE_USER_ENTITIES = "include_user_entities";
    public static final String STATUS = "status";


    public TwitterClient(Context context) {
        super(context, REST_API_CLASS, BASE_API_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
    }

    /**
     * Function to call home timeline
     * @param maxId
     * @param sinceId
     * @param handler
     */
    public void getHomeTimeline(long maxId, long sinceId, AsyncHttpResponseHandler handler) {

        String apiUrl = getApiUrl("/statuses/home_timeline.json");

        RequestParams params = new RequestParams();
        params.put(COUNT, RETRIEVE_COUNT);
        if(maxId>0) {
            params.put(MAX_ID, maxId-1);
        }
        if(sinceId>0) {
            params.put(SINCE_ID,sinceId);
        }
        params.put(INCLUDE_ENTITIES,true);

        getClient().get(apiUrl,params,handler);

    }

    /**
     * Function to call user timeline
     * @param screenName
     * @param maxId
     * @param sinceId
     * @param handler
     */
    public void getUserTimeline(String screenName, long maxId, long sinceId,
                                AsyncHttpResponseHandler handler) {

        String apiUrl = getApiUrl("/statuses/user_timeline.json");

        RequestParams params = new RequestParams();
        params.put(COUNT, RETRIEVE_COUNT);
        if(maxId>0) {
            params.put(MAX_ID, maxId-1);
        }
        if(sinceId>0) {
            params.put(SINCE_ID,sinceId);
        }
        params.put(INCLUDE_ENTITIES,true);
        params.put(SCREEN_NAME, screenName);

        getClient().get(apiUrl,params,handler);

    }

    /**
     * Function to post a tweet
     * @param tweet
     * @param handler
     */
    public void postTweet(String tweet, AsyncHttpResponseHandler handler) {

        String apiUrl = getApiUrl("/statuses/update.json");

        RequestParams params = new RequestParams();
        params.put(STATUS,tweet);

        getClient().post(apiUrl, params, handler);
    }

    /**
     * Function to get user info
     * @param screenName
     * @param handler
     */
    public void getUserInfo(String screenName, AsyncHttpResponseHandler handler) {

        String apiUrl = getApiUrl("/users/show.json");

        RequestParams params = new RequestParams();
        params.put(SCREEN_NAME,screenName);

        getClient().get(apiUrl, params, handler);
    }

    /**
     * Function to get personal info
     * @param handler
     */
    public void getPersonalUserInfo(AsyncHttpResponseHandler handler) {

        String apiUrl = getApiUrl("/users/show.json");

        RequestParams params = new RequestParams();
        params.put(SCREEN_NAME,"amod_samant");

        getClient().get(apiUrl, params, handler);
    }

    /**
     * Function to get Mentions timeline
     * @param maxId
     * @param sinceId
     * @param handler
     */
    public void getMentionsTimeline(long maxId, long sinceId, JsonHttpResponseHandler handler) {

        String apiUrl = getApiUrl("/statuses/mentions_timeline.json");

        RequestParams params = new RequestParams();
        params.put(COUNT, RETRIEVE_COUNT);
        params.put(INCLUDE_ENTITIES,true);
        if(maxId>0) {
            params.put(MAX_ID, maxId-1);
        }
        if(sinceId>0) {
            params.put(SINCE_ID,sinceId);
        }

        getClient().get(apiUrl,params,handler);
    }


    /**
     * Function to get followers
     * @param screenName
     * @param handler
     */
    public void getFollowers(String screenName, JsonHttpResponseHandler handler) {

        String apiUrl = getApiUrl("/followers/list.json");

        RequestParams params = new RequestParams();
        params.put(SCREEN_NAME, screenName);
        params.put(COUNT, RETRIEVE_COUNT);
        params.put(INCLUDE_USER_ENTITIES,true);

        getClient().get(apiUrl,params,handler);
    }

    /**
     * Function to get following
     * @param screenName
     * @param handler
     */
    public void getFollowing(String screenName, JsonHttpResponseHandler handler) {

        String apiUrl = getApiUrl("/friends/list.json");

        RequestParams params = new RequestParams();
        params.put(SCREEN_NAME, screenName);
        params.put(COUNT, RETRIEVE_COUNT);
        params.put(INCLUDE_USER_ENTITIES,true);

        getClient().get(apiUrl,params,handler);
    }

    /**
     * Function to get favorites
     * @param screenName
     * @param maxId
     * @param sinceId
     * @param handler
     */
    public void getFavorites(String screenName, long maxId, long sinceId,
                             JsonHttpResponseHandler handler) {

        String apiUrl = getApiUrl("/favorites/list.json");

        RequestParams params = new RequestParams();
        params.put(SCREEN_NAME, screenName);
        params.put(COUNT, RETRIEVE_COUNT);
        params.put(INCLUDE_ENTITIES,true);

        if(maxId>0) {
            params.put(MAX_ID, maxId-1);
        }
        if(sinceId>0) {
            params.put(SINCE_ID,sinceId);
        }

        getClient().get(apiUrl,params,handler);

    }

    /**
     * Function to get search results
     * @param query
     * @param maxId
     * @param sinceId
     * @param handler
     */
    public void getSearchResults(String query, long maxId, long sinceId,
                                 JsonHttpResponseHandler handler) {

        String apiUrl = getApiUrl("/search/tweets.json");

        RequestParams params = new RequestParams();
        params.put("q", query);
        params.put(COUNT, RETRIEVE_COUNT);
        params.put(INCLUDE_ENTITIES,true);

        if(maxId>0) {
            params.put(MAX_ID, maxId-1);
        }
        if(sinceId>0) {
            params.put(SINCE_ID,sinceId);
        }

        getClient().get(apiUrl,params,handler);
    }

    /**
     * Function to get direct messages
     * @param handler
     */
    public void getDirectMessages(JsonHttpResponseHandler handler) {

        String apiUrl = getApiUrl("/direct_messages.json");

        RequestParams params = new RequestParams();

        getClient().get(apiUrl,params,handler);
    }

    public void getUser(JsonHttpResponseHandler handler) {

        String apiUrl = getApiUrl("account/verify_credentials.json");
        getClient().get(apiUrl, new RequestParams(), handler);
    }

}
