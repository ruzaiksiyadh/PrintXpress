package lk.printxpress.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
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
import lk.printxpress.data.local.entity.Address;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AddressDao_Impl implements AddressDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Address> __insertionAdapterOfAddress;

  private final EntityDeletionOrUpdateAdapter<Address> __deletionAdapterOfAddress;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAddressById;

  public AddressDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfAddress = new EntityInsertionAdapter<Address>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `addresses` (`address_id`,`user_id`,`address_line`,`city`,`postal_code`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Address entity) {
        statement.bindLong(1, entity.getAddressId());
        statement.bindLong(2, entity.getUserId());
        statement.bindString(3, entity.getAddressLine());
        statement.bindString(4, entity.getCity());
        statement.bindString(5, entity.getPostalCode());
      }
    };
    this.__deletionAdapterOfAddress = new EntityDeletionOrUpdateAdapter<Address>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `addresses` WHERE `address_id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Address entity) {
        statement.bindLong(1, entity.getAddressId());
      }
    };
    this.__preparedStmtOfDeleteAddressById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM addresses WHERE address_id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertAddress(final Address address, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfAddress.insertAndReturnId(address);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteAddress(final Address address, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfAddress.handle(address);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteAddressById(final long addressId,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAddressById.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, addressId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteAddressById.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Address>> getAddressesForUser(final long userId) {
    final String _sql = "SELECT * FROM addresses WHERE user_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"addresses"}, new Callable<List<Address>>() {
      @Override
      @NonNull
      public List<Address> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfAddressId = CursorUtil.getColumnIndexOrThrow(_cursor, "address_id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "user_id");
          final int _cursorIndexOfAddressLine = CursorUtil.getColumnIndexOrThrow(_cursor, "address_line");
          final int _cursorIndexOfCity = CursorUtil.getColumnIndexOrThrow(_cursor, "city");
          final int _cursorIndexOfPostalCode = CursorUtil.getColumnIndexOrThrow(_cursor, "postal_code");
          final List<Address> _result = new ArrayList<Address>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Address _item;
            final long _tmpAddressId;
            _tmpAddressId = _cursor.getLong(_cursorIndexOfAddressId);
            final long _tmpUserId;
            _tmpUserId = _cursor.getLong(_cursorIndexOfUserId);
            final String _tmpAddressLine;
            _tmpAddressLine = _cursor.getString(_cursorIndexOfAddressLine);
            final String _tmpCity;
            _tmpCity = _cursor.getString(_cursorIndexOfCity);
            final String _tmpPostalCode;
            _tmpPostalCode = _cursor.getString(_cursorIndexOfPostalCode);
            _item = new Address(_tmpAddressId,_tmpUserId,_tmpAddressLine,_tmpCity,_tmpPostalCode);
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

  @Override
  public Object getAddressById(final long addressId,
      final Continuation<? super Address> $completion) {
    final String _sql = "SELECT * FROM addresses WHERE address_id = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, addressId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Address>() {
      @Override
      @Nullable
      public Address call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfAddressId = CursorUtil.getColumnIndexOrThrow(_cursor, "address_id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "user_id");
          final int _cursorIndexOfAddressLine = CursorUtil.getColumnIndexOrThrow(_cursor, "address_line");
          final int _cursorIndexOfCity = CursorUtil.getColumnIndexOrThrow(_cursor, "city");
          final int _cursorIndexOfPostalCode = CursorUtil.getColumnIndexOrThrow(_cursor, "postal_code");
          final Address _result;
          if (_cursor.moveToFirst()) {
            final long _tmpAddressId;
            _tmpAddressId = _cursor.getLong(_cursorIndexOfAddressId);
            final long _tmpUserId;
            _tmpUserId = _cursor.getLong(_cursorIndexOfUserId);
            final String _tmpAddressLine;
            _tmpAddressLine = _cursor.getString(_cursorIndexOfAddressLine);
            final String _tmpCity;
            _tmpCity = _cursor.getString(_cursorIndexOfCity);
            final String _tmpPostalCode;
            _tmpPostalCode = _cursor.getString(_cursorIndexOfPostalCode);
            _result = new Address(_tmpAddressId,_tmpUserId,_tmpAddressLine,_tmpCity,_tmpPostalCode);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
