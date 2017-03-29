package com.twitterclient.models;

import com.raizlabs.android.dbflow.annotation.Database;

@Database(name = TwitterDatabase.NAME, version = TwitterDatabase.VERSION)
public class TwitterDatabase {
    public static final String NAME = "TimelineDb";

    public static final int VERSION = 1;
}