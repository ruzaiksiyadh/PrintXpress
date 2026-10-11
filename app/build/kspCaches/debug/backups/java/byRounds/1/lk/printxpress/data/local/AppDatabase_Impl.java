package lk.printxpress.data.local;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;
import lk.printxpress.data.local.dao.AddressDao;
import lk.printxpress.data.local.dao.AddressDao_Impl;
import lk.printxpress.data.local.dao.DesignDao;
import lk.printxpress.data.local.dao.DesignDao_Impl;
import lk.printxpress.data.local.dao.NotificationDao;
import lk.printxpress.data.local.dao.NotificationDao_Impl;
import lk.printxpress.data.local.dao.OrderDao;
import lk.printxpress.data.local.dao.OrderDao_Impl;
import lk.printxpress.data.local.dao.ProductDao;
import lk.printxpress.data.local.dao.ProductDao_Impl;
import lk.printxpress.data.local.dao.UserDao;
import lk.printxpress.data.local.dao.UserDao_Impl;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile UserDao _userDao;

  private volatile AddressDao _addressDao;

  private volatile ProductDao _productDao;

  private volatile OrderDao _orderDao;

  private volatile DesignDao _designDao;

  private volatile NotificationDao _notificationDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `users` (`user_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `email` TEXT NOT NULL, `phone` TEXT NOT NULL, `password_hash` TEXT NOT NULL)");
        db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_users_email` ON `users` (`email`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `addresses` (`address_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `user_id` INTEGER NOT NULL, `address_line` TEXT NOT NULL, `city` TEXT NOT NULL, `postal_code` TEXT NOT NULL, FOREIGN KEY(`user_id`) REFERENCES `users`(`user_id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_addresses_user_id` ON `addresses` (`user_id`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `categories` (`category_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `category_name` TEXT NOT NULL, `icon_name` TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `products` (`product_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `category_id` INTEGER NOT NULL, `name` TEXT NOT NULL, `description` TEXT NOT NULL, `material` TEXT NOT NULL, `size` TEXT NOT NULL, `price` REAL NOT NULL, `image_path` TEXT NOT NULL, FOREIGN KEY(`category_id`) REFERENCES `categories`(`category_id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_products_category_id` ON `products` (`category_id`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `designs` (`design_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `user_id` INTEGER NOT NULL, `file_name` TEXT NOT NULL, `file_path` TEXT NOT NULL, `description` TEXT NOT NULL, `created_date` INTEGER NOT NULL, FOREIGN KEY(`user_id`) REFERENCES `users`(`user_id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_designs_user_id` ON `designs` (`user_id`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `orders` (`order_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `user_id` INTEGER NOT NULL, `address_id` INTEGER, `order_date` INTEGER NOT NULL, `status` TEXT NOT NULL, `delivery_type` TEXT NOT NULL, `delivery_date` TEXT NOT NULL, `delivery_fee` REAL NOT NULL, `total_amount` REAL NOT NULL, `instructions` TEXT NOT NULL, `artwork_filename` TEXT NOT NULL, FOREIGN KEY(`user_id`) REFERENCES `users`(`user_id`) ON UPDATE NO ACTION ON DELETE CASCADE , FOREIGN KEY(`address_id`) REFERENCES `addresses`(`address_id`) ON UPDATE NO ACTION ON DELETE SET NULL )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_orders_user_id` ON `orders` (`user_id`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_orders_address_id` ON `orders` (`address_id`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `order_items` (`order_item_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `order_id` INTEGER NOT NULL, `product_id` INTEGER NOT NULL, `quantity` INTEGER NOT NULL, `unit_price` REAL NOT NULL, `specifications` TEXT NOT NULL, FOREIGN KEY(`order_id`) REFERENCES `orders`(`order_id`) ON UPDATE NO ACTION ON DELETE CASCADE , FOREIGN KEY(`product_id`) REFERENCES `products`(`product_id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_order_items_order_id` ON `order_items` (`order_id`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_order_items_product_id` ON `order_items` (`product_id`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `notifications` (`notification_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `user_id` INTEGER NOT NULL, `title` TEXT NOT NULL, `message` TEXT NOT NULL, `created_date` INTEGER NOT NULL, `is_read` INTEGER NOT NULL, FOREIGN KEY(`user_id`) REFERENCES `users`(`user_id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_notifications_user_id` ON `notifications` (`user_id`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'faf3c99010e85df0002c533a458b6be2')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `users`");
        db.execSQL("DROP TABLE IF EXISTS `addresses`");
        db.execSQL("DROP TABLE IF EXISTS `categories`");
        db.execSQL("DROP TABLE IF EXISTS `products`");
        db.execSQL("DROP TABLE IF EXISTS `designs`");
        db.execSQL("DROP TABLE IF EXISTS `orders`");
        db.execSQL("DROP TABLE IF EXISTS `order_items`");
        db.execSQL("DROP TABLE IF EXISTS `notifications`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsUsers = new HashMap<String, TableInfo.Column>(5);
        _columnsUsers.put("user_id", new TableInfo.Column("user_id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("email", new TableInfo.Column("email", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("phone", new TableInfo.Column("phone", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("password_hash", new TableInfo.Column("password_hash", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUsers = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUsers = new HashSet<TableInfo.Index>(1);
        _indicesUsers.add(new TableInfo.Index("index_users_email", true, Arrays.asList("email"), Arrays.asList("ASC")));
        final TableInfo _infoUsers = new TableInfo("users", _columnsUsers, _foreignKeysUsers, _indicesUsers);
        final TableInfo _existingUsers = TableInfo.read(db, "users");
        if (!_infoUsers.equals(_existingUsers)) {
          return new RoomOpenHelper.ValidationResult(false, "users(lk.printxpress.data.local.entity.User).\n"
                  + " Expected:\n" + _infoUsers + "\n"
                  + " Found:\n" + _existingUsers);
        }
        final HashMap<String, TableInfo.Column> _columnsAddresses = new HashMap<String, TableInfo.Column>(5);
        _columnsAddresses.put("address_id", new TableInfo.Column("address_id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAddresses.put("user_id", new TableInfo.Column("user_id", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAddresses.put("address_line", new TableInfo.Column("address_line", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAddresses.put("city", new TableInfo.Column("city", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAddresses.put("postal_code", new TableInfo.Column("postal_code", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysAddresses = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysAddresses.add(new TableInfo.ForeignKey("users", "CASCADE", "NO ACTION", Arrays.asList("user_id"), Arrays.asList("user_id")));
        final HashSet<TableInfo.Index> _indicesAddresses = new HashSet<TableInfo.Index>(1);
        _indicesAddresses.add(new TableInfo.Index("index_addresses_user_id", false, Arrays.asList("user_id"), Arrays.asList("ASC")));
        final TableInfo _infoAddresses = new TableInfo("addresses", _columnsAddresses, _foreignKeysAddresses, _indicesAddresses);
        final TableInfo _existingAddresses = TableInfo.read(db, "addresses");
        if (!_infoAddresses.equals(_existingAddresses)) {
          return new RoomOpenHelper.ValidationResult(false, "addresses(lk.printxpress.data.local.entity.Address).\n"
                  + " Expected:\n" + _infoAddresses + "\n"
                  + " Found:\n" + _existingAddresses);
        }
        final HashMap<String, TableInfo.Column> _columnsCategories = new HashMap<String, TableInfo.Column>(3);
        _columnsCategories.put("category_id", new TableInfo.Column("category_id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCategories.put("category_name", new TableInfo.Column("category_name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCategories.put("icon_name", new TableInfo.Column("icon_name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCategories = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCategories = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCategories = new TableInfo("categories", _columnsCategories, _foreignKeysCategories, _indicesCategories);
        final TableInfo _existingCategories = TableInfo.read(db, "categories");
        if (!_infoCategories.equals(_existingCategories)) {
          return new RoomOpenHelper.ValidationResult(false, "categories(lk.printxpress.data.local.entity.Category).\n"
                  + " Expected:\n" + _infoCategories + "\n"
                  + " Found:\n" + _existingCategories);
        }
        final HashMap<String, TableInfo.Column> _columnsProducts = new HashMap<String, TableInfo.Column>(8);
        _columnsProducts.put("product_id", new TableInfo.Column("product_id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("category_id", new TableInfo.Column("category_id", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("material", new TableInfo.Column("material", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("size", new TableInfo.Column("size", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("price", new TableInfo.Column("price", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducts.put("image_path", new TableInfo.Column("image_path", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysProducts = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysProducts.add(new TableInfo.ForeignKey("categories", "CASCADE", "NO ACTION", Arrays.asList("category_id"), Arrays.asList("category_id")));
        final HashSet<TableInfo.Index> _indicesProducts = new HashSet<TableInfo.Index>(1);
        _indicesProducts.add(new TableInfo.Index("index_products_category_id", false, Arrays.asList("category_id"), Arrays.asList("ASC")));
        final TableInfo _infoProducts = new TableInfo("products", _columnsProducts, _foreignKeysProducts, _indicesProducts);
        final TableInfo _existingProducts = TableInfo.read(db, "products");
        if (!_infoProducts.equals(_existingProducts)) {
          return new RoomOpenHelper.ValidationResult(false, "products(lk.printxpress.data.local.entity.Product).\n"
                  + " Expected:\n" + _infoProducts + "\n"
                  + " Found:\n" + _existingProducts);
        }
        final HashMap<String, TableInfo.Column> _columnsDesigns = new HashMap<String, TableInfo.Column>(6);
        _columnsDesigns.put("design_id", new TableInfo.Column("design_id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDesigns.put("user_id", new TableInfo.Column("user_id", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDesigns.put("file_name", new TableInfo.Column("file_name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDesigns.put("file_path", new TableInfo.Column("file_path", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDesigns.put("description", new TableInfo.Column("description", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsDesigns.put("created_date", new TableInfo.Column("created_date", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysDesigns = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysDesigns.add(new TableInfo.ForeignKey("users", "CASCADE", "NO ACTION", Arrays.asList("user_id"), Arrays.asList("user_id")));
        final HashSet<TableInfo.Index> _indicesDesigns = new HashSet<TableInfo.Index>(1);
        _indicesDesigns.add(new TableInfo.Index("index_designs_user_id", false, Arrays.asList("user_id"), Arrays.asList("ASC")));
        final TableInfo _infoDesigns = new TableInfo("designs", _columnsDesigns, _foreignKeysDesigns, _indicesDesigns);
        final TableInfo _existingDesigns = TableInfo.read(db, "designs");
        if (!_infoDesigns.equals(_existingDesigns)) {
          return new RoomOpenHelper.ValidationResult(false, "designs(lk.printxpress.data.local.entity.Design).\n"
                  + " Expected:\n" + _infoDesigns + "\n"
                  + " Found:\n" + _existingDesigns);
        }
        final HashMap<String, TableInfo.Column> _columnsOrders = new HashMap<String, TableInfo.Column>(11);
        _columnsOrders.put("order_id", new TableInfo.Column("order_id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOrders.put("user_id", new TableInfo.Column("user_id", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOrders.put("address_id", new TableInfo.Column("address_id", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOrders.put("order_date", new TableInfo.Column("order_date", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOrders.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOrders.put("delivery_type", new TableInfo.Column("delivery_type", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOrders.put("delivery_date", new TableInfo.Column("delivery_date", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOrders.put("delivery_fee", new TableInfo.Column("delivery_fee", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOrders.put("total_amount", new TableInfo.Column("total_amount", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOrders.put("instructions", new TableInfo.Column("instructions", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOrders.put("artwork_filename", new TableInfo.Column("artwork_filename", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysOrders = new HashSet<TableInfo.ForeignKey>(2);
        _foreignKeysOrders.add(new TableInfo.ForeignKey("users", "CASCADE", "NO ACTION", Arrays.asList("user_id"), Arrays.asList("user_id")));
        _foreignKeysOrders.add(new TableInfo.ForeignKey("addresses", "SET NULL", "NO ACTION", Arrays.asList("address_id"), Arrays.asList("address_id")));
        final HashSet<TableInfo.Index> _indicesOrders = new HashSet<TableInfo.Index>(2);
        _indicesOrders.add(new TableInfo.Index("index_orders_user_id", false, Arrays.asList("user_id"), Arrays.asList("ASC")));
        _indicesOrders.add(new TableInfo.Index("index_orders_address_id", false, Arrays.asList("address_id"), Arrays.asList("ASC")));
        final TableInfo _infoOrders = new TableInfo("orders", _columnsOrders, _foreignKeysOrders, _indicesOrders);
        final TableInfo _existingOrders = TableInfo.read(db, "orders");
        if (!_infoOrders.equals(_existingOrders)) {
          return new RoomOpenHelper.ValidationResult(false, "orders(lk.printxpress.data.local.entity.Order).\n"
                  + " Expected:\n" + _infoOrders + "\n"
                  + " Found:\n" + _existingOrders);
        }
        final HashMap<String, TableInfo.Column> _columnsOrderItems = new HashMap<String, TableInfo.Column>(6);
        _columnsOrderItems.put("order_item_id", new TableInfo.Column("order_item_id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOrderItems.put("order_id", new TableInfo.Column("order_id", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOrderItems.put("product_id", new TableInfo.Column("product_id", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOrderItems.put("quantity", new TableInfo.Column("quantity", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOrderItems.put("unit_price", new TableInfo.Column("unit_price", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsOrderItems.put("specifications", new TableInfo.Column("specifications", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysOrderItems = new HashSet<TableInfo.ForeignKey>(2);
        _foreignKeysOrderItems.add(new TableInfo.ForeignKey("orders", "CASCADE", "NO ACTION", Arrays.asList("order_id"), Arrays.asList("order_id")));
        _foreignKeysOrderItems.add(new TableInfo.ForeignKey("products", "CASCADE", "NO ACTION", Arrays.asList("product_id"), Arrays.asList("product_id")));
        final HashSet<TableInfo.Index> _indicesOrderItems = new HashSet<TableInfo.Index>(2);
        _indicesOrderItems.add(new TableInfo.Index("index_order_items_order_id", false, Arrays.asList("order_id"), Arrays.asList("ASC")));
        _indicesOrderItems.add(new TableInfo.Index("index_order_items_product_id", false, Arrays.asList("product_id"), Arrays.asList("ASC")));
        final TableInfo _infoOrderItems = new TableInfo("order_items", _columnsOrderItems, _foreignKeysOrderItems, _indicesOrderItems);
        final TableInfo _existingOrderItems = TableInfo.read(db, "order_items");
        if (!_infoOrderItems.equals(_existingOrderItems)) {
          return new RoomOpenHelper.ValidationResult(false, "order_items(lk.printxpress.data.local.entity.OrderItem).\n"
                  + " Expected:\n" + _infoOrderItems + "\n"
                  + " Found:\n" + _existingOrderItems);
        }
        final HashMap<String, TableInfo.Column> _columnsNotifications = new HashMap<String, TableInfo.Column>(6);
        _columnsNotifications.put("notification_id", new TableInfo.Column("notification_id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotifications.put("user_id", new TableInfo.Column("user_id", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotifications.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotifications.put("message", new TableInfo.Column("message", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotifications.put("created_date", new TableInfo.Column("created_date", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotifications.put("is_read", new TableInfo.Column("is_read", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysNotifications = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysNotifications.add(new TableInfo.ForeignKey("users", "CASCADE", "NO ACTION", Arrays.asList("user_id"), Arrays.asList("user_id")));
        final HashSet<TableInfo.Index> _indicesNotifications = new HashSet<TableInfo.Index>(1);
        _indicesNotifications.add(new TableInfo.Index("index_notifications_user_id", false, Arrays.asList("user_id"), Arrays.asList("ASC")));
        final TableInfo _infoNotifications = new TableInfo("notifications", _columnsNotifications, _foreignKeysNotifications, _indicesNotifications);
        final TableInfo _existingNotifications = TableInfo.read(db, "notifications");
        if (!_infoNotifications.equals(_existingNotifications)) {
          return new RoomOpenHelper.ValidationResult(false, "notifications(lk.printxpress.data.local.entity.NotificationItem).\n"
                  + " Expected:\n" + _infoNotifications + "\n"
                  + " Found:\n" + _existingNotifications);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "faf3c99010e85df0002c533a458b6be2", "25da46ec59b58e5a4084fa4513f0e760");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "users","addresses","categories","products","designs","orders","order_items","notifications");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    final boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `users`");
      _db.execSQL("DELETE FROM `addresses`");
      _db.execSQL("DELETE FROM `categories`");
      _db.execSQL("DELETE FROM `products`");
      _db.execSQL("DELETE FROM `designs`");
      _db.execSQL("DELETE FROM `orders`");
      _db.execSQL("DELETE FROM `order_items`");
      _db.execSQL("DELETE FROM `notifications`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(UserDao.class, UserDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(AddressDao.class, AddressDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ProductDao.class, ProductDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(OrderDao.class, OrderDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(DesignDao.class, DesignDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(NotificationDao.class, NotificationDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public UserDao userDao() {
    if (_userDao != null) {
      return _userDao;
    } else {
      synchronized(this) {
        if(_userDao == null) {
          _userDao = new UserDao_Impl(this);
        }
        return _userDao;
      }
    }
  }

  @Override
  public AddressDao addressDao() {
    if (_addressDao != null) {
      return _addressDao;
    } else {
      synchronized(this) {
        if(_addressDao == null) {
          _addressDao = new AddressDao_Impl(this);
        }
        return _addressDao;
      }
    }
  }

  @Override
  public ProductDao productDao() {
    if (_productDao != null) {
      return _productDao;
    } else {
      synchronized(this) {
        if(_productDao == null) {
          _productDao = new ProductDao_Impl(this);
        }
        return _productDao;
      }
    }
  }

  @Override
  public OrderDao orderDao() {
    if (_orderDao != null) {
      return _orderDao;
    } else {
      synchronized(this) {
        if(_orderDao == null) {
          _orderDao = new OrderDao_Impl(this);
        }
        return _orderDao;
      }
    }
  }

  @Override
  public DesignDao designDao() {
    if (_designDao != null) {
      return _designDao;
    } else {
      synchronized(this) {
        if(_designDao == null) {
          _designDao = new DesignDao_Impl(this);
        }
        return _designDao;
      }
    }
  }

  @Override
  public NotificationDao notificationDao() {
    if (_notificationDao != null) {
      return _notificationDao;
    } else {
      synchronized(this) {
        if(_notificationDao == null) {
          _notificationDao = new NotificationDao_Impl(this);
        }
        return _notificationDao;
      }
    }
  }
}
