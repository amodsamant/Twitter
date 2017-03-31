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
	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, BASE_API_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}


	public void getHomeTimeline(long maxId, long sinceId, AsyncHttpResponseHandler handler) {

		String apiUrl = getApiUrl("/statuses/home_timeline.json");

		RequestParams params = new RequestParams();
		params.put("count", RETRIEVE_COUNT);
		if(maxId>0) {
            params.put("max_id", maxId-1);
        }
        if(sinceId>0) {
            params.put("since_id",sinceId);
        }
        params.put("include_entities",true);

        getClient().get(apiUrl,params,handler);

	}

	public void postTweet(String tweet, AsyncHttpResponseHandler handler) {

        String apiUrl = getApiUrl("/statuses/update.json");

        RequestParams params = new RequestParams();
        params.put("status",tweet);

        getClient().post(apiUrl, params, handler);
    }



	public void getPersonalUserInfo(AsyncHttpResponseHandler handler) {

		String apiUrl = getApiUrl("/users/show.json");

		RequestParams params = new RequestParams();
		params.put("screen_name","amod_samant");

		getClient().get(apiUrl, params, handler);
	}

    public void getMentionsTimeline(long maxId, long sinceId, JsonHttpResponseHandler handler) {

		String apiUrl = getApiUrl("/statuses/mentions_timeline.json");

		RequestParams params = new RequestParams();
		params.put("count", RETRIEVE_COUNT);
		params.put("include_entities",true);
		if(maxId>0) {
			params.put("max_id", maxId-1);
		}
		if(sinceId>0) {
			params.put("since_id",sinceId);
		}

		getClient().get(apiUrl,params,handler);
    }


//    public void getTweet(long id, AsyncHttpResponseHandler handler) {
//
//        String apiUrl = getApiUrl("/statuses/show.json");
//        RequestParams params = new RequestParams();
//        params.put("id",id);
//
//        getClient().get(apiUrl, params, handler);
//
//    }
}
