package com.twitterclient.utils;

import android.text.format.DateUtils;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class DateGenericUtils {

    final static String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
    static SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);

    final static String timeFormat = "h:mm a";
    static SimpleDateFormat sfTimeFormat = new SimpleDateFormat(timeFormat, Locale.ENGLISH);

    final static String dateFormat = "dd MMM yy";
    static SimpleDateFormat sfDateFormat = new SimpleDateFormat(dateFormat, Locale.ENGLISH);

    /**
     * Function converts the twitter format date "Mon Apr 01 21:16:23 +0000 2014"
     * intp relative time span and truncates to show only one letter s,m,h,d
     * @param rawJsonDate
     * @return
     */
    public static String getRelativeTimeAgo(String rawJsonDate) {

        sf.setLenient(true);
        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            long systemTime = System.currentTimeMillis();

            if(dateMillis>=systemTime) {
                return "0s";
            }

            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    systemTime, DateUtils.SECOND_IN_MILLIS).toString();

            int space = relativeDate.indexOf(" ");
            return relativeDate.substring(0,space) + relativeDate.charAt(space+1);
        } catch (ParseException e) {
            Log.e("Error", e.getMessage());
        }

        return relativeDate;
    }

    /**
     * Function retrieves the time in "h:mm a" format
     * @param rawJsonDate
     * @return
     */
    public static String getTime(String rawJsonDate) {

        sf.setLenient(true);
        String time = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            time = sfTimeFormat.format(dateMillis);
        } catch (ParseException e) {
            Log.e("Error", e.getMessage());
        }
        return time;
    }

    /**
     * Function retrieves the date in "dd MMM yy" format
     * @param rawJsonDate
     * @return
     */
    public static String getDate(String rawJsonDate) {

        sf.setLenient(true);
        String date = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            date = sfDateFormat.format(dateMillis);
        } catch(ParseException e) {
            Log.e("Error", e.getMessage());
        }
        return date;
    }



}
