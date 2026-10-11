package lk.printxpress.data.local.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;
import lk.printxpress.data.local.entity.Design;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class DesignDao_Impl implements DesignDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Design> __insertionAdapterOfDesign;

  private final EntityDeletionOrUpdateAdapter<Design> __deletionAdapterOfDesign;

  public DesignDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfDesign = new EntityInsertionAdapter<Design>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `designs` (`design_id`,`user_id`,`file_name`,`file_path`,`description`,`created_date`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Design entity) {
        statement.bindLong(1, entity.getDesignId());
        statement.bindLong(2, entity.getUserId());
        statement.bindString(3, entity.getFileName());
        statement.bindString(4, entity.getFilePath());
        statement.bindString(5, entity.getDescription());
        statement.bindLong(6, entity.getCreatedDate());
      }
    };
    this.__deletionAdapterOfDesign = new EntityDeletionOrUpdateAdapter<Design>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `designs` WHERE `design_id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Design entity) {
        statement.bindLong(1, entity.getDesignId());
      }
    };
  }

  @Override
  public Object insertDesign(final Design design, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfDesign.insertAndReturnId(design);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteDesign(final Design design, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfDesign.handle(design);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Design>> getDesignsForUser(final long userId) {
    final String _sql = "SELECT * FROM designs WHERE user_id = ? ORDER BY created_date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"designs"}, new Callable<List<Design>>() {
      @Override
      @NonNull
      public List<Design> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfDesignId = CursorUtil.getColumnIndexOrThrow(_cursor, "design_id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "user_id");
          final int _cursorIndexOfFileName = CursorUtil.getColumnIndexOrThrow(_cursor, "file_name");
          final int _cursorIndexOfFilePath = CursorUtil.getColumnIndexOrThrow(_cursor, "file_path");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfCreatedDate = CursorUtil.getColumnIndexOrThrow(_cursor, "created_date");
          final List<Design> _result = new ArrayList<Design>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Design _item;
            final long _tmpDesignId;
            _tmpDesignId = _cursor.getLong(_cursorIndexOfDesignId);
            final long _tmpUserId;
            _tmpUserId = _cursor.getLong(_cursorIndexOfUserId);
            final String _tmpFileName;
            _tmpFileName = _cursor.getString(_cursorIndexOfFileName);
            final String _tmpFilePath;
            _tmpFilePath = _cursor.getString(_cursorIndexOfFilePath);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final long _tmpCreatedDate;
            _tmpCreatedDate = _cursor.getLong(_cursorIndexOfCreatedDate);
            _item = new Design(_tmpDesignId,_tmpUserId,_tmpFileName,_tmpFilePath,_tmpDescription,_tmpCreatedDate);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
