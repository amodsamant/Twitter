package com.twitterclient.utils;

/**
 * Created by Amod on 3/26/17.
 */

public class GenericUtils {

    /**
     * Modifies the default url for profile image from _normal to _bigger
     * @param defaultUrl
     * @return
     */
    public static String modifyProfileImageUrl(String defaultUrl) {
        return defaultUrl.replace("_normal.", "_bigger.");

    }

}
