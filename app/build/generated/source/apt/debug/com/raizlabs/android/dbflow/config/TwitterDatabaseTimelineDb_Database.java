package com.raizlabs.android.dbflow.config;

import com.twitterclient.models.Tweet;
import com.twitterclient.models.Tweet_Table;
import com.twitterclient.models.TwitterDatabase;
import com.twitterclient.models.User;
import com.twitterclient.models.User_Table;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;

/**
 * This is generated code. Please do not modify */
public final class TwitterDatabaseTimelineDb_Database extends DatabaseDefinition {
  public TwitterDatabaseTimelineDb_Database(DatabaseHolder holder) {
    holder.putDatabaseForTable(User.class, this);
    holder.putDatabaseForTable(Tweet.class, this);
    models.add(User.class);
    modelTableNames.put("User", User.class);
    modelAdapters.put(User.class, new User_Table(holder, this));
    models.add(Tweet.class);
    modelTableNames.put("Tweet", Tweet.class);
    modelAdapters.put(Tweet.class, new Tweet_Table(holder, this));
  }

  @Override
  public final Class<?> getAssociatedDatabaseClassFile() {
    return TwitterDatabase.class;
  }

  @Override
  public final boolean isForeignKeysSupported() {
    return false;
  }

  @Override
  public final boolean isInMemory() {
    return false;
  }

  @Override
  public final boolean backupEnabled() {
    return false;
  }

  @Override
  public final boolean areConsistencyChecksEnabled() {
    return false;
  }

  @Override
  public final int getDatabaseVersion() {
    return 1;
  }

  @Override
  public final String getDatabaseName() {
    return "TimelineDb";
  }
}
