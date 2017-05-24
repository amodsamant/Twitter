package com.twitterclient.helpers;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SharedPrefHelper {

    /**
     * Function to get all the saved preferences
     * @param context
     * @return
     */
    public List<String> getAll(Context context) {

        SharedPreferences sharedPreferences = getSharedPreferences(context);
        Map<String,String> draftsMap = (Map<String, String>) sharedPreferences.getAll();
        List<String> drafts = new ArrayList<>(draftsMap.values());
        if(drafts!=null && !drafts.isEmpty()) {
            Log.d("DEBUG", drafts.get(0));
        }

        return drafts;
    }

    /**
     * Function returns the SharedPreferences
     * @return
     */
    public static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences("Drafts", Context.MODE_PRIVATE);
    }



}
