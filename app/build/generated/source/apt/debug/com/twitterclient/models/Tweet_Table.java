package com.twitterclient.models;

import android.content.ContentValues;
import android.database.Cursor;
import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.DatabaseHolder;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.QueryBuilder;
import com.raizlabs.android.dbflow.sql.language.ConditionGroup;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.property.BaseProperty;
import com.raizlabs.android.dbflow.sql.language.property.IProperty;
import com.raizlabs.android.dbflow.sql.language.property.IntProperty;
import com.raizlabs.android.dbflow.sql.language.property.LongProperty;
import com.raizlabs.android.dbflow.sql.language.property.Property;
import com.raizlabs.android.dbflow.structure.ModelAdapter;
import com.raizlabs.android.dbflow.structure.database.DatabaseStatement;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import java.lang.Boolean;
import java.lang.Class;
import java.lang.IllegalArgumentException;
import java.lang.Override;
import java.lang.String;

/**
 * This is generated code. Please do not modify */
public final class Tweet_Table extends ModelAdapter<Tweet> {
  /**
   * Primary Key */
  public static final LongProperty id = new LongProperty(Tweet.class, "id");

  public static final Property<String> body = new Property<String>(Tweet.class, "body");

  public static final Property<String> createdAt = new Property<String>(Tweet.class, "createdAt");

  /**
   * Foreign Key */
  public static final LongProperty user_id = new LongProperty(Tweet.class, "user_id");

  public static final Property<Boolean> favorited = new Property<Boolean>(Tweet.class, "favorited");

  public static final Property<Boolean> retweeted = new Property<Boolean>(Tweet.class, "retweeted");

  public static final IntProperty retweetCount = new IntProperty(Tweet.class, "retweetCount");

  public static final IntProperty favouritesCount = new IntProperty(Tweet.class, "favouritesCount");

  public static final Property<String> imageUrl = new Property<String>(Tweet.class, "imageUrl");

  public static final IProperty[] ALL_COLUMN_PROPERTIES = new IProperty[]{id,body,createdAt,user_id,favorited,retweeted,retweetCount,favouritesCount,imageUrl};

  public Tweet_Table(DatabaseHolder holder, DatabaseDefinition databaseDefinition) {
    super(databaseDefinition);
  }

  @Override
  public final Class<Tweet> getModelClass() {
    return Tweet.class;
  }

  public final String getTableName() {
    return "`Tweet`";
  }

  @Override
  public final BaseProperty getProperty(String columnName) {
    columnName = QueryBuilder.quoteIfNeeded(columnName);
    switch (columnName)  {
      case "`id`":  {
        return id;
      }
      case "`body`":  {
        return body;
      }
      case "`createdAt`":  {
        return createdAt;
      }
      case "`user_id`":  {
        return user_id;
      }
      case "`favorited`":  {
        return favorited;
      }
      case "`retweeted`":  {
        return retweeted;
      }
      case "`retweetCount`":  {
        return retweetCount;
      }
      case "`favouritesCount`":  {
        return favouritesCount;
      }
      case "`imageUrl`":  {
        return imageUrl;
      }
      default:  {
        throw new IllegalArgumentException("Invalid column name passed. Ensure you are calling the correct table's column");
      }
    }
  }

  @Override
  public final void saveForeignKeys(Tweet model, DatabaseWrapper wrapper) {
    if (model.user != null) {
      model.user.save(wrapper);
    }
  }

  @Override
  public final IProperty[] getAllColumnProperties() {
    return ALL_COLUMN_PROPERTIES;
  }

  @Override
  public final void bindToInsertValues(ContentValues values, Tweet model) {
    values.put("`id`", model.id);
    values.put("`body`", model.body != null ? model.body : null);
    values.put("`createdAt`", model.createdAt != null ? model.createdAt : null);
    if (model.user != null) {
      values.put("`user_id`", model.user.id);
    } else {
      values.putNull("user_id");
    }
    values.put("`favorited`", model.favorited ? 1 : 0);
    values.put("`retweeted`", model.retweeted ? 1 : 0);
    values.put("`retweetCount`", model.retweetCount);
    values.put("`favouritesCount`", model.favouritesCount);
    values.put("`imageUrl`", model.imageUrl != null ? model.imageUrl : null);
  }

  @Override
  public final void bindToInsertStatement(DatabaseStatement statement, Tweet model, int start) {
    statement.bindLong(1 + start, model.id);
    if (model.body != null)  {
      statement.bindString(2 + start, model.body);
    } else {
      statement.bindNull(2 + start);
    }
    if (model.createdAt != null)  {
      statement.bindString(3 + start, model.createdAt);
    } else {
      statement.bindNull(3 + start);
    }
    if (model.user != null) {
      statement.bindLong(4 + start, model.user.id);
    } else {
      statement.bindNull(4 + start);
    }
    statement.bindLong(5 + start, model.favorited ? 1 : 0);
    statement.bindLong(6 + start, model.retweeted ? 1 : 0);
    statement.bindLong(7 + start, model.retweetCount);
    statement.bindLong(8 + start, model.favouritesCount);
    if (model.imageUrl != null)  {
      statement.bindString(9 + start, model.imageUrl);
    } else {
      statement.bindNull(9 + start);
    }
  }

  @Override
  public final void bindToStatement(DatabaseStatement statement, Tweet model) {
    bindToInsertStatement(statement, model, 0);
  }

  @Override
  public final String getInsertStatementQuery() {
    return "INSERT INTO `Tweet`(`id`,`body`,`createdAt`,`user_id`,`favorited`,`retweeted`,`retweetCount`,`favouritesCount`,`imageUrl`) VALUES (?,?,?,?,?,?,?,?,?)";
  }

  @Override
  public final String getCompiledStatementQuery() {
    return "INSERT INTO `Tweet`(`id`,`body`,`createdAt`,`user_id`,`favorited`,`retweeted`,`retweetCount`,`favouritesCount`,`imageUrl`) VALUES (?,?,?,?,?,?,?,?,?)";
  }

  @Override
  public final String getCreationQuery() {
    return "CREATE TABLE IF NOT EXISTS `Tweet`(`id` INTEGER,`body` TEXT,`createdAt` TEXT,`user_id` INTEGER,`favorited` INTEGER,`retweeted` INTEGER,`retweetCount` INTEGER,`favouritesCount` INTEGER,`imageUrl` TEXT, PRIMARY KEY(`id`)"+ ", FOREIGN KEY(`user_id`) REFERENCES " + FlowManager.getTableName(User.class) + "(`id`) ON UPDATE NO ACTION ON DELETE NO ACTION" + ");";
  }

  @Override
  public final void loadFromCursor(Cursor cursor, Tweet model) {
    int index_id = cursor.getColumnIndex("id");
    if (index_id != -1 && !cursor.isNull(index_id)) {
      model.id = cursor.getLong(index_id);
    } else {
      model.id = (long) 0;
    }
    int index_body = cursor.getColumnIndex("body");
    if (index_body != -1 && !cursor.isNull(index_body)) {
      model.body = cursor.getString(index_body);
    } else {
      model.body = null;
    }
    int index_createdAt = cursor.getColumnIndex("createdAt");
    if (index_createdAt != -1 && !cursor.isNull(index_createdAt)) {
      model.createdAt = cursor.getString(index_createdAt);
    } else {
      model.createdAt = null;
    }
    int index_user_id = cursor.getColumnIndex("user_id");
    if (index_user_id != -1 && !cursor.isNull(index_user_id)) {
      model.user = SQLite.select().from(User.class).where()
          .and(User_Table.id.eq(cursor.getLong(index_user_id)))
          .querySingle();
    } else {
      model.user = null;
    }
    int index_favorited = cursor.getColumnIndex("favorited");
    if (index_favorited != -1 && !cursor.isNull(index_favorited)) {
      model.favorited = cursor.getInt(index_favorited) == 1 ? true : false;
    } else {
      model.favorited = false;
    }
    int index_retweeted = cursor.getColumnIndex("retweeted");
    if (index_retweeted != -1 && !cursor.isNull(index_retweeted)) {
      model.retweeted = cursor.getInt(index_retweeted) == 1 ? true : false;
    } else {
      model.retweeted = false;
    }
    int index_retweetCount = cursor.getColumnIndex("retweetCount");
    if (index_retweetCount != -1 && !cursor.isNull(index_retweetCount)) {
      model.retweetCount = cursor.getInt(index_retweetCount);
    } else {
      model.retweetCount = (int) 0;
    }
    int index_favouritesCount = cursor.getColumnIndex("favouritesCount");
    if (index_favouritesCount != -1 && !cursor.isNull(index_favouritesCount)) {
      model.favouritesCount = cursor.getInt(index_favouritesCount);
    } else {
      model.favouritesCount = (int) 0;
    }
    int index_imageUrl = cursor.getColumnIndex("imageUrl");
    if (index_imageUrl != -1 && !cursor.isNull(index_imageUrl)) {
      model.imageUrl = cursor.getString(index_imageUrl);
    } else {
      model.imageUrl = null;
    }
  }

  @Override
  public final boolean exists(Tweet model, DatabaseWrapper wrapper) {
    return SQLite.selectCountOf()
    .from(Tweet.class)
    .where(getPrimaryConditionClause(model))
    .hasData(wrapper);
  }

  @Override
  public final ConditionGroup getPrimaryConditionClause(Tweet model) {
    ConditionGroup clause = ConditionGroup.clause();
    clause.and(id.eq(model.id));
    return clause;
  }

  @Override
  public final Tweet newInstance() {
    return new Tweet();
  }
}
