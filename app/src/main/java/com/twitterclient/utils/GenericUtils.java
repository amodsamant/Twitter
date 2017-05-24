package com.twitterclient.utils;

public class GenericUtils {

    /**
     * Modifies the default url for profile image from _normal to _bigger
     * @param defaultUrl
     * @return
     */
    public static String modifyProfileImageUrl(String defaultUrl) {
        return defaultUrl.replace("_normal.", "_bigger.");
    }

    /**
     * Function to convert number into correct format
     * @param count
     * @return
     */
    public static String format(long count) {
        if (count < 1000) return "" + count;
        int exp = (int) (Math.log(count) / Math.log(1000));
        return String.format("%.1f%c",
                count / Math.pow(1000, exp),
                "KMGTPE".charAt(exp-1));
    }

}
