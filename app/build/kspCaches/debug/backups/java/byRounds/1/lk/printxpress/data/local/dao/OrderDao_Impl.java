package lk.printxpress.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
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
import lk.printxpress.data.local.entity.Order;
import lk.printxpress.data.local.entity.OrderItem;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class OrderDao_Impl implements OrderDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Order> __insertionAdapterOfOrder;

  private final EntityInsertionAdapter<OrderItem> __insertionAdapterOfOrderItem;

  private final SharedSQLiteStatement __preparedStmtOfUpdateOrderStatus;

  private final SharedSQLiteStatement __preparedStmtOfUpdateDeliveryDate;

  public OrderDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfOrder = new EntityInsertionAdapter<Order>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `orders` (`order_id`,`user_id`,`address_id`,`order_date`,`status`,`delivery_type`,`delivery_date`,`delivery_fee`,`total_amount`,`instructions`,`artwork_filename`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Order entity) {
        statement.bindLong(1, entity.getOrderId());
        statement.bindLong(2, entity.getUserId());
        if (entity.getAddressId() == null) {
          statement.bindNull(3);
        } else {
          statement.bindLong(3, entity.getAddressId());
        }
        statement.bindLong(4, entity.getOrderDate());
        statement.bindString(5, entity.getStatus());
        statement.bindString(6, entity.getDeliveryType());
        statement.bindString(7, entity.getDeliveryDate());
        statement.bindDouble(8, entity.getDeliveryFee());
        statement.bindDouble(9, entity.getTotalAmount());
        statement.bindString(10, entity.getInstructions());
        statement.bindString(11, entity.getArtworkFilename());
      }
    };
    this.__insertionAdapterOfOrderItem = new EntityInsertionAdapter<OrderItem>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `order_items` (`order_item_id`,`order_id`,`product_id`,`quantity`,`unit_price`,`specifications`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final OrderItem entity) {
        statement.bindLong(1, entity.getOrderItemId());
        statement.bindLong(2, entity.getOrderId());
        statement.bindLong(3, entity.getProductId());
        statement.bindLong(4, entity.getQuantity());
        statement.bindDouble(5, entity.getUnitPrice());
        statement.bindString(6, entity.getSpecifications());
      }
    };
    this.__preparedStmtOfUpdateOrderStatus = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE orders SET status = ? WHERE order_id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateDeliveryDate = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE orders SET delivery_date = ? WHERE order_id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertOrder(final Order order, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfOrder.insertAndReturnId(order);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertOrderItem(final OrderItem item,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfOrderItem.insertAndReturnId(item);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertOrderItems(final List<OrderItem> items,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfOrderItem.insert(items);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateOrderStatus(final long orderId, final String status,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateOrderStatus.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, status);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, orderId);
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
          __preparedStmtOfUpdateOrderStatus.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateDeliveryDate(final long orderId, final String deliveryDate,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateDeliveryDate.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, deliveryDate);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, orderId);
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
          __preparedStmtOfUpdateDeliveryDate.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Order>> getOrdersForUser(final long userId) {
    final String _sql = "SELECT * FROM orders WHERE user_id = ? ORDER BY order_date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"orders"}, new Callable<List<Order>>() {
      @Override
      @NonNull
      public List<Order> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfOrderId = CursorUtil.getColumnIndexOrThrow(_cursor, "order_id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "user_id");
          final int _cursorIndexOfAddressId = CursorUtil.getColumnIndexOrThrow(_cursor, "address_id");
          final int _cursorIndexOfOrderDate = CursorUtil.getColumnIndexOrThrow(_cursor, "order_date");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfDeliveryType = CursorUtil.getColumnIndexOrThrow(_cursor, "delivery_type");
          final int _cursorIndexOfDeliveryDate = CursorUtil.getColumnIndexOrThrow(_cursor, "delivery_date");
          final int _cursorIndexOfDeliveryFee = CursorUtil.getColumnIndexOrThrow(_cursor, "delivery_fee");
          final int _cursorIndexOfTotalAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "total_amount");
          final int _cursorIndexOfInstructions = CursorUtil.getColumnIndexOrThrow(_cursor, "instructions");
          final int _cursorIndexOfArtworkFilename = CursorUtil.getColumnIndexOrThrow(_cursor, "artwork_filename");
          final List<Order> _result = new ArrayList<Order>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Order _item;
            final long _tmpOrderId;
            _tmpOrderId = _cursor.getLong(_cursorIndexOfOrderId);
            final long _tmpUserId;
            _tmpUserId = _cursor.getLong(_cursorIndexOfUserId);
            final Long _tmpAddressId;
            if (_cursor.isNull(_cursorIndexOfAddressId)) {
              _tmpAddressId = null;
            } else {
              _tmpAddressId = _cursor.getLong(_cursorIndexOfAddressId);
            }
            final long _tmpOrderDate;
            _tmpOrderDate = _cursor.getLong(_cursorIndexOfOrderDate);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final String _tmpDeliveryType;
            _tmpDeliveryType = _cursor.getString(_cursorIndexOfDeliveryType);
            final String _tmpDeliveryDate;
            _tmpDeliveryDate = _cursor.getString(_cursorIndexOfDeliveryDate);
            final double _tmpDeliveryFee;
            _tmpDeliveryFee = _cursor.getDouble(_cursorIndexOfDeliveryFee);
            final double _tmpTotalAmount;
            _tmpTotalAmount = _cursor.getDouble(_cursorIndexOfTotalAmount);
            final String _tmpInstructions;
            _tmpInstructions = _cursor.getString(_cursorIndexOfInstructions);
            final String _tmpArtworkFilename;
            _tmpArtworkFilename = _cursor.getString(_cursorIndexOfArtworkFilename);
            _item = new Order(_tmpOrderId,_tmpUserId,_tmpAddressId,_tmpOrderDate,_tmpStatus,_tmpDeliveryType,_tmpDeliveryDate,_tmpDeliveryFee,_tmpTotalAmount,_tmpInstructions,_tmpArtworkFilename);
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
  public Object getOrderById(final long orderId, final Continuation<? super Order> $completion) {
    final String _sql = "SELECT * FROM orders WHERE order_id = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, orderId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Order>() {
      @Override
      @Nullable
      public Order call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfOrderId = CursorUtil.getColumnIndexOrThrow(_cursor, "order_id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "user_id");
          final int _cursorIndexOfAddressId = CursorUtil.getColumnIndexOrThrow(_cursor, "address_id");
          final int _cursorIndexOfOrderDate = CursorUtil.getColumnIndexOrThrow(_cursor, "order_date");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfDeliveryType = CursorUtil.getColumnIndexOrThrow(_cursor, "delivery_type");
          final int _cursorIndexOfDeliveryDate = CursorUtil.getColumnIndexOrThrow(_cursor, "delivery_date");
          final int _cursorIndexOfDeliveryFee = CursorUtil.getColumnIndexOrThrow(_cursor, "delivery_fee");
          final int _cursorIndexOfTotalAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "total_amount");
          final int _cursorIndexOfInstructions = CursorUtil.getColumnIndexOrThrow(_cursor, "instructions");
          final int _cursorIndexOfArtworkFilename = CursorUtil.getColumnIndexOrThrow(_cursor, "artwork_filename");
          final Order _result;
          if (_cursor.moveToFirst()) {
            final long _tmpOrderId;
            _tmpOrderId = _cursor.getLong(_cursorIndexOfOrderId);
            final long _tmpUserId;
            _tmpUserId = _cursor.getLong(_cursorIndexOfUserId);
            final Long _tmpAddressId;
            if (_cursor.isNull(_cursorIndexOfAddressId)) {
              _tmpAddressId = null;
            } else {
              _tmpAddressId = _cursor.getLong(_cursorIndexOfAddressId);
            }
            final long _tmpOrderDate;
            _tmpOrderDate = _cursor.getLong(_cursorIndexOfOrderDate);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final String _tmpDeliveryType;
            _tmpDeliveryType = _cursor.getString(_cursorIndexOfDeliveryType);
            final String _tmpDeliveryDate;
            _tmpDeliveryDate = _cursor.getString(_cursorIndexOfDeliveryDate);
            final double _tmpDeliveryFee;
            _tmpDeliveryFee = _cursor.getDouble(_cursorIndexOfDeliveryFee);
            final double _tmpTotalAmount;
            _tmpTotalAmount = _cursor.getDouble(_cursorIndexOfTotalAmount);
            final String _tmpInstructions;
            _tmpInstructions = _cursor.getString(_cursorIndexOfInstructions);
            final String _tmpArtworkFilename;
            _tmpArtworkFilename = _cursor.getString(_cursorIndexOfArtworkFilename);
            _result = new Order(_tmpOrderId,_tmpUserId,_tmpAddressId,_tmpOrderDate,_tmpStatus,_tmpDeliveryType,_tmpDeliveryDate,_tmpDeliveryFee,_tmpTotalAmount,_tmpInstructions,_tmpArtworkFilename);
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

  @Override
  public Flow<Order> observeOrderById(final long orderId) {
    final String _sql = "SELECT * FROM orders WHERE order_id = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, orderId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"orders"}, new Callable<Order>() {
      @Override
      @Nullable
      public Order call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfOrderId = CursorUtil.getColumnIndexOrThrow(_cursor, "order_id");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "user_id");
          final int _cursorIndexOfAddressId = CursorUtil.getColumnIndexOrThrow(_cursor, "address_id");
          final int _cursorIndexOfOrderDate = CursorUtil.getColumnIndexOrThrow(_cursor, "order_date");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfDeliveryType = CursorUtil.getColumnIndexOrThrow(_cursor, "delivery_type");
          final int _cursorIndexOfDeliveryDate = CursorUtil.getColumnIndexOrThrow(_cursor, "delivery_date");
          final int _cursorIndexOfDeliveryFee = CursorUtil.getColumnIndexOrThrow(_cursor, "delivery_fee");
          final int _cursorIndexOfTotalAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "total_amount");
          final int _cursorIndexOfInstructions = CursorUtil.getColumnIndexOrThrow(_cursor, "instructions");
          final int _cursorIndexOfArtworkFilename = CursorUtil.getColumnIndexOrThrow(_cursor, "artwork_filename");
          final Order _result;
          if (_cursor.moveToFirst()) {
            final long _tmpOrderId;
            _tmpOrderId = _cursor.getLong(_cursorIndexOfOrderId);
            final long _tmpUserId;
            _tmpUserId = _cursor.getLong(_cursorIndexOfUserId);
            final Long _tmpAddressId;
            if (_cursor.isNull(_cursorIndexOfAddressId)) {
              _tmpAddressId = null;
            } else {
              _tmpAddressId = _cursor.getLong(_cursorIndexOfAddressId);
            }
            final long _tmpOrderDate;
            _tmpOrderDate = _cursor.getLong(_cursorIndexOfOrderDate);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final String _tmpDeliveryType;
            _tmpDeliveryType = _cursor.getString(_cursorIndexOfDeliveryType);
            final String _tmpDeliveryDate;
            _tmpDeliveryDate = _cursor.getString(_cursorIndexOfDeliveryDate);
            final double _tmpDeliveryFee;
            _tmpDeliveryFee = _cursor.getDouble(_cursorIndexOfDeliveryFee);
            final double _tmpTotalAmount;
            _tmpTotalAmount = _cursor.getDouble(_cursorIndexOfTotalAmount);
            final String _tmpInstructions;
            _tmpInstructions = _cursor.getString(_cursorIndexOfInstructions);
            final String _tmpArtworkFilename;
            _tmpArtworkFilename = _cursor.getString(_cursorIndexOfArtworkFilename);
            _result = new Order(_tmpOrderId,_tmpUserId,_tmpAddressId,_tmpOrderDate,_tmpStatus,_tmpDeliveryType,_tmpDeliveryDate,_tmpDeliveryFee,_tmpTotalAmount,_tmpInstructions,_tmpArtworkFilename);
          } else {
            _result = null;
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
  public Object getOrderItems(final long orderId,
      final Continuation<? super List<OrderItem>> $completion) {
    final String _sql = "SELECT * FROM order_items WHERE order_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, orderId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<OrderItem>>() {
      @Override
      @NonNull
      public List<OrderItem> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfOrderItemId = CursorUtil.getColumnIndexOrThrow(_cursor, "order_item_id");
          final int _cursorIndexOfOrderId = CursorUtil.getColumnIndexOrThrow(_cursor, "order_id");
          final int _cursorIndexOfProductId = CursorUtil.getColumnIndexOrThrow(_cursor, "product_id");
          final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
          final int _cursorIndexOfUnitPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "unit_price");
          final int _cursorIndexOfSpecifications = CursorUtil.getColumnIndexOrThrow(_cursor, "specifications");
          final List<OrderItem> _result = new ArrayList<OrderItem>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final OrderItem _item;
            final long _tmpOrderItemId;
            _tmpOrderItemId = _cursor.getLong(_cursorIndexOfOrderItemId);
            final long _tmpOrderId;
            _tmpOrderId = _cursor.getLong(_cursorIndexOfOrderId);
            final long _tmpProductId;
            _tmpProductId = _cursor.getLong(_cursorIndexOfProductId);
            final int _tmpQuantity;
            _tmpQuantity = _cursor.getInt(_cursorIndexOfQuantity);
            final double _tmpUnitPrice;
            _tmpUnitPrice = _cursor.getDouble(_cursorIndexOfUnitPrice);
            final String _tmpSpecifications;
            _tmpSpecifications = _cursor.getString(_cursorIndexOfSpecifications);
            _item = new OrderItem(_tmpOrderItemId,_tmpOrderId,_tmpProductId,_tmpQuantity,_tmpUnitPrice,_tmpSpecifications);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<OrderItem>> observeOrderItems(final long orderId) {
    final String _sql = "SELECT * FROM order_items WHERE order_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, orderId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"order_items"}, new Callable<List<OrderItem>>() {
      @Override
      @NonNull
      public List<OrderItem> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfOrderItemId = CursorUtil.getColumnIndexOrThrow(_cursor, "order_item_id");
          final int _cursorIndexOfOrderId = CursorUtil.getColumnIndexOrThrow(_cursor, "order_id");
          final int _cursorIndexOfProductId = CursorUtil.getColumnIndexOrThrow(_cursor, "product_id");
          final int _cursorIndexOfQuantity = CursorUtil.getColumnIndexOrThrow(_cursor, "quantity");
          final int _cursorIndexOfUnitPrice = CursorUtil.getColumnIndexOrThrow(_cursor, "unit_price");
          final int _cursorIndexOfSpecifications = CursorUtil.getColumnIndexOrThrow(_cursor, "specifications");
          final List<OrderItem> _result = new ArrayList<OrderItem>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final OrderItem _item;
            final long _tmpOrderItemId;
            _tmpOrderItemId = _cursor.getLong(_cursorIndexOfOrderItemId);
            final long _tmpOrderId;
            _tmpOrderId = _cursor.getLong(_cursorIndexOfOrderId);
            final long _tmpProductId;
            _tmpProductId = _cursor.getLong(_cursorIndexOfProductId);
            final int _tmpQuantity;
            _tmpQuantity = _cursor.getInt(_cursorIndexOfQuantity);
            final double _tmpUnitPrice;
            _tmpUnitPrice = _cursor.getDouble(_cursorIndexOfUnitPrice);
            final String _tmpSpecifications;
            _tmpSpecifications = _cursor.getString(_cursorIndexOfSpecifications);
            _item = new OrderItem(_tmpOrderItemId,_tmpOrderId,_tmpProductId,_tmpQuantity,_tmpUnitPrice,_tmpSpecifications);
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
