package com.twitterclient.models;

import android.content.ContentValues;
import android.database.Cursor;
import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.DatabaseHolder;
import com.raizlabs.android.dbflow.sql.QueryBuilder;
import com.raizlabs.android.dbflow.sql.language.ConditionGroup;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.property.BaseProperty;
import com.raizlabs.android.dbflow.sql.language.property.IProperty;
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
public final class User_Table extends ModelAdapter<User> {
  /**
   * Primary Key */
  public static final LongProperty id = new LongProperty(User.class, "id");

  public static final Property<String> name = new Property<String>(User.class, "name");

  public static final Property<String> profileImageUrl = new Property<String>(User.class, "profileImageUrl");

  public static final Property<String> screenName = new Property<String>(User.class, "screenName");

  public static final Property<Boolean> verified = new Property<Boolean>(User.class, "verified");

  public static final Property<String> description = new Property<String>(User.class, "description");

  public static final LongProperty followersCnt = new LongProperty(User.class, "followersCnt");

  public static final LongProperty followingCnt = new LongProperty(User.class, "followingCnt");

  public static final Property<String> profileBackground = new Property<String>(User.class, "profileBackground");

  public static final Property<Boolean> following = new Property<Boolean>(User.class, "following");

  public static final IProperty[] ALL_COLUMN_PROPERTIES = new IProperty[]{id,name,profileImageUrl,screenName,verified,description,followersCnt,followingCnt,profileBackground,following};

  public User_Table(DatabaseHolder holder, DatabaseDefinition databaseDefinition) {
    super(databaseDefinition);
  }

  @Override
  public final Class<User> getModelClass() {
    return User.class;
  }

  public final String getTableName() {
    return "`User`";
  }

  @Override
  public final BaseProperty getProperty(String columnName) {
    columnName = QueryBuilder.quoteIfNeeded(columnName);
    switch (columnName)  {
      case "`id`":  {
        return id;
      }
      case "`name`":  {
        return name;
      }
      case "`profileImageUrl`":  {
        return profileImageUrl;
      }
      case "`screenName`":  {
        return screenName;
      }
      case "`verified`":  {
        return verified;
      }
      case "`description`":  {
        return description;
      }
      case "`followersCnt`":  {
        return followersCnt;
      }
      case "`followingCnt`":  {
        return followingCnt;
      }
      case "`profileBackground`":  {
        return profileBackground;
      }
      case "`following`":  {
        return following;
      }
      default:  {
        throw new IllegalArgumentException("Invalid column name passed. Ensure you are calling the correct table's column");
      }
    }
  }

  @Override
  public final IProperty[] getAllColumnProperties() {
    return ALL_COLUMN_PROPERTIES;
  }

  @Override
  public final void bindToInsertValues(ContentValues values, User model) {
    values.put("`id`", model.id);
    values.put("`name`", model.name != null ? model.name : null);
    values.put("`profileImageUrl`", model.profileImageUrl != null ? model.profileImageUrl : null);
    values.put("`screenName`", model.screenName != null ? model.screenName : null);
    values.put("`verified`", model.verified ? 1 : 0);
    values.put("`description`", model.description != null ? model.description : null);
    values.put("`followersCnt`", model.followersCnt);
    values.put("`followingCnt`", model.followingCnt);
    values.put("`profileBackground`", model.profileBackground != null ? model.profileBackground : null);
    values.put("`following`", model.following ? 1 : 0);
  }

  @Override
  public final void bindToInsertStatement(DatabaseStatement statement, User model, int start) {
    statement.bindLong(1 + start, model.id);
    if (model.name != null)  {
      statement.bindString(2 + start, model.name);
    } else {
      statement.bindNull(2 + start);
    }
    if (model.profileImageUrl != null)  {
      statement.bindString(3 + start, model.profileImageUrl);
    } else {
      statement.bindNull(3 + start);
    }
    if (model.screenName != null)  {
      statement.bindString(4 + start, model.screenName);
    } else {
      statement.bindNull(4 + start);
    }
    statement.bindLong(5 + start, model.verified ? 1 : 0);
    if (model.description != null)  {
      statement.bindString(6 + start, model.description);
    } else {
      statement.bindNull(6 + start);
    }
    statement.bindLong(7 + start, model.followersCnt);
    statement.bindLong(8 + start, model.followingCnt);
    if (model.profileBackground != null)  {
      statement.bindString(9 + start, model.profileBackground);
    } else {
      statement.bindNull(9 + start);
    }
    statement.bindLong(10 + start, model.following ? 1 : 0);
  }

  @Override
  public final void bindToStatement(DatabaseStatement statement, User model) {
    bindToInsertStatement(statement, model, 0);
  }

  @Override
  public final String getInsertStatementQuery() {
    return "INSERT INTO `User`(`id`,`name`,`profileImageUrl`,`screenName`,`verified`,`description`,`followersCnt`,`followingCnt`,`profileBackground`,`following`) VALUES (?,?,?,?,?,?,?,?,?,?)";
  }

  @Override
  public final String getCompiledStatementQuery() {
    return "INSERT INTO `User`(`id`,`name`,`profileImageUrl`,`screenName`,`verified`,`description`,`followersCnt`,`followingCnt`,`profileBackground`,`following`) VALUES (?,?,?,?,?,?,?,?,?,?)";
  }

  @Override
  public final String getCreationQuery() {
    return "CREATE TABLE IF NOT EXISTS `User`(`id` INTEGER,`name` TEXT,`profileImageUrl` TEXT,`screenName` TEXT,`verified` INTEGER,`description` TEXT,`followersCnt` INTEGER,`followingCnt` INTEGER,`profileBackground` TEXT,`following` INTEGER, PRIMARY KEY(`id`)" + ");";
  }

  @Override
  public final void loadFromCursor(Cursor cursor, User model) {
    int index_id = cursor.getColumnIndex("id");
    if (index_id != -1 && !cursor.isNull(index_id)) {
      model.id = cursor.getLong(index_id);
    } else {
      model.id = (long) 0;
    }
    int index_name = cursor.getColumnIndex("name");
    if (index_name != -1 && !cursor.isNull(index_name)) {
      model.name = cursor.getString(index_name);
    } else {
      model.name = null;
    }
    int index_profileImageUrl = cursor.getColumnIndex("profileImageUrl");
    if (index_profileImageUrl != -1 && !cursor.isNull(index_profileImageUrl)) {
      model.profileImageUrl = cursor.getString(index_profileImageUrl);
    } else {
      model.profileImageUrl = null;
    }
    int index_screenName = cursor.getColumnIndex("screenName");
    if (index_screenName != -1 && !cursor.isNull(index_screenName)) {
      model.screenName = cursor.getString(index_screenName);
    } else {
      model.screenName = null;
    }
    int index_verified = cursor.getColumnIndex("verified");
    if (index_verified != -1 && !cursor.isNull(index_verified)) {
      model.verified = cursor.getInt(index_verified) == 1 ? true : false;
    } else {
      model.verified = false;
    }
    int index_description = cursor.getColumnIndex("description");
    if (index_description != -1 && !cursor.isNull(index_description)) {
      model.description = cursor.getString(index_description);
    } else {
      model.description = null;
    }
    int index_followersCnt = cursor.getColumnIndex("followersCnt");
    if (index_followersCnt != -1 && !cursor.isNull(index_followersCnt)) {
      model.followersCnt = cursor.getLong(index_followersCnt);
    } else {
      model.followersCnt = (long) 0;
    }
    int index_followingCnt = cursor.getColumnIndex("followingCnt");
    if (index_followingCnt != -1 && !cursor.isNull(index_followingCnt)) {
      model.followingCnt = cursor.getLong(index_followingCnt);
    } else {
      model.followingCnt = (long) 0;
    }
    int index_profileBackground = cursor.getColumnIndex("profileBackground");
    if (index_profileBackground != -1 && !cursor.isNull(index_profileBackground)) {
      model.profileBackground = cursor.getString(index_profileBackground);
    } else {
      model.profileBackground = null;
    }
    int index_following = cursor.getColumnIndex("following");
    if (index_following != -1 && !cursor.isNull(index_following)) {
      model.following = cursor.getInt(index_following) == 1 ? true : false;
    } else {
      model.following = false;
    }
  }

  @Override
  public final boolean exists(User model, DatabaseWrapper wrapper) {
    return SQLite.selectCountOf()
    .from(User.class)
    .where(getPrimaryConditionClause(model))
    .hasData(wrapper);
  }

  @Override
  public final ConditionGroup getPrimaryConditionClause(User model) {
    ConditionGroup clause = ConditionGroup.clause();
    clause.and(id.eq(model.id));
    return clause;
  }

  @Override
  public final User newInstance() {
    return new User();
  }
}
