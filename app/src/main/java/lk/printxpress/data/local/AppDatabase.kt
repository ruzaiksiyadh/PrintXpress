package lk.printxpress.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import lk.printxpress.data.local.dao.AddressDao
import lk.printxpress.data.local.dao.DesignDao
import lk.printxpress.data.local.dao.NotificationDao
import lk.printxpress.data.local.dao.OrderDao
import lk.printxpress.data.local.dao.ProductDao
import lk.printxpress.data.local.dao.UserDao
import lk.printxpress.data.local.entity.Address
import lk.printxpress.data.local.entity.Category
import lk.printxpress.data.local.entity.Design
import lk.printxpress.data.local.entity.NotificationItem
import lk.printxpress.data.local.entity.Order
import lk.printxpress.data.local.entity.OrderItem
import lk.printxpress.data.local.entity.Product
import lk.printxpress.data.local.entity.User
import lk.printxpress.data.model.OrderStatus
import lk.printxpress.utils.SecurityUtils

@Database(
    entities = [
        User::class,
        Address::class,
        Category::class,
        Product::class,
        Design::class,
        Order::class,
        OrderItem::class,
        NotificationItem::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun addressDao(): AddressDao
    abstract fun productDao(): ProductDao
    abstract fun orderDao(): OrderDao
    abstract fun designDao(): DesignDao
    abstract fun notificationDao(): NotificationDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "printxpress_database"
                )
                    .addCallback(DatabaseCallback())
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class DatabaseCallback : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    CoroutineScope(Dispatchers.IO).launch {
                        populateDatabase(database)
                    }
                }
            }
        }

        suspend fun populateDatabase(db: AppDatabase) {
            // Check if already populated
            if (db.productDao().getProductCount() > 0) return

            // 1. Insert Categories
            val categories = listOf(
                Category(categoryId = 1, categoryName = "Business Cards", iconName = "badge"),
                Category(categoryId = 2, categoryName = "Flyers", iconName = "description"),
                Category(categoryId = 3, categoryName = "Posters", iconName = "wallpaper"),
                Category(categoryId = 4, categoryName = "Banners", iconName = "flag"),
                Category(categoryId = 5, categoryName = "Stickers", iconName = "loyalty"),
                Category(categoryId = 6, categoryName = "T-Shirts", iconName = "checkroom")
            )
            db.productDao().insertCategories(categories)

            // 2. Insert Products
            val products = listOf(
                Product(
                    productId = 1,
                    categoryId = 1,
                    name = "Premium Matte Business Cards",
                    description = "Ultra-smooth 300gsm cards with a velvety non-reflective finish. Perfect for networking and corporate identity.",
                    material = "300gsm Matte Card",
                    size = "90 × 55 mm",
                    price = 12.00
                ),
                Product(
                    productId = 2,
                    categoryId = 1,
                    name = "Spot UV Luxury Business Cards",
                    description = "Raised high-gloss lacquer highlights on selected logos and elements with a sleek soft-touch background.",
                    material = "350gsm Silk Board",
                    size = "90 × 55 mm",
                    price = 18.00
                ),
                Product(
                    productId = 3,
                    categoryId = 2,
                    name = "Promotional A5 Gloss Flyers",
                    description = "Vibrant full-color double-sided flyers for marketing events, sales campaigns, and retail distribution.",
                    material = "150gsm Art Gloss Paper",
                    size = "A5 (148 × 210 mm)",
                    price = 25.00
                ),
                Product(
                    productId = 4,
                    categoryId = 2,
                    name = "Corporate Tri-Fold Brochures",
                    description = "Elegant 6-panel informational brochure, precision folded with crisp scoring and rich color reproduction.",
                    material = "170gsm Matte Art Paper",
                    size = "A4 Folded to DL",
                    price = 45.00
                ),
                Product(
                    productId = 5,
                    categoryId = 3,
                    name = "High-Res Event Posters",
                    description = "High-definition posters with deep contrast and vivid pigments. Ideal for exhibitions, movies, and store display.",
                    material = "200gsm Photo Semi-Gloss",
                    size = "A3 (297 × 420 mm)",
                    price = 350.00
                ),
                Product(
                    productId = 6,
                    categoryId = 3,
                    name = "Large Format Wall Poster",
                    description = "Stunning architectural, graphic, and art prints printed on premium fade-resistant heavy paper.",
                    material = "250gsm Premium Satin",
                    size = "A2 (420 × 594 mm)",
                    price = 750.00
                ),
                Product(
                    productId = 7,
                    categoryId = 4,
                    name = "Heavy-Duty Outdoor PVC Banner",
                    description = "Weather-resistant, reinforced hems with brass eyelets every 2 feet. Resists wind, sun, and tropical rain.",
                    material = "440gsm Heavy PVC Flex",
                    size = "6 × 3 Feet",
                    price = 2800.00
                ),
                Product(
                    productId = 8,
                    categoryId = 4,
                    name = "Roll-Up Pull-Up Display Banner",
                    description = "Includes lightweight anodized aluminum cartridge stand, tension pole, and padded carry bag.",
                    material = "Polypropylene Anti-Curl Film",
                    size = "85 × 200 cm",
                    price = 6500.00
                ),
                Product(
                    productId = 9,
                    categoryId = 5,
                    name = "Die-Cut Vinyl Logo Stickers",
                    description = "Individually cut contour shape stickers. Waterproof, scratch-resistant, and UV-proof laminate coating.",
                    material = "Gloss Vinyl with UV Coat",
                    size = "5 × 5 cm",
                    price = 15.00
                ),
                Product(
                    productId = 10,
                    categoryId = 5,
                    name = "Custom Product Label Sheets",
                    description = "Multiple peel-and-stick labels per sheet for food packaging, bottle labels, jars, and cosmetic boxes.",
                    material = "Waterproof Matte Film Sheet",
                    size = "A4 Sheet (24 labels)",
                    price = 180.00
                ),
                Product(
                    productId = 11,
                    categoryId = 6,
                    name = "Custom Screen-Printed T-Shirt",
                    description = "100% combed ring-spun cotton. High-density textile inks that withstand 50+ wash cycles without fading.",
                    material = "180gsm Ring-Spun Cotton",
                    size = "Unisex M / L / XL",
                    price = 1650.00
                ),
                Product(
                    productId = 12,
                    categoryId = 6,
                    name = "Polo Embroidered Collar Shirt",
                    description = "Breathable pique knit with fine precision needlework embroidery for corporate team uniforms and club apparel.",
                    material = "220gsm Cotton Pique Blend",
                    size = "Unisex M / L / XL",
                    price = 2450.00
                )
            )
            db.productDao().insertProducts(products)

            // 3. Insert Test Customer (as specified in Task E.2)
            val testUser = User(
                userId = 1,
                name = "Test Customer",
                email = "testcustomer@example.com",
                phone = "0771234567",
                passwordHash = SecurityUtils.hashPassword("TestPassword123")
            )
            db.userDao().insertUser(testUser)

            // 4. Insert Addresses for Test Customer
            val address1 = Address(
                addressId = 1,
                userId = 1,
                addressLine = "45 Galle Road, Bambalapitiya",
                city = "Colombo 04",
                postalCode = "00400"
            )
            val address2 = Address(
                addressId = 2,
                userId = 1,
                addressLine = "12 Peradeniya Road",
                city = "Kandy",
                postalCode = "20000"
            )
            db.addressDao().insertAddress(address1)
            db.addressDao().insertAddress(address2)

            // 5. Pre-seed Orders for Test Customer to test tracking and cancellation states
            val order1 = Order(
                orderId = 1001,
                userId = 1,
                addressId = null,
                orderDate = System.currentTimeMillis() - 3600000 * 5,
                status = OrderStatus.PROCESSING.name,
                deliveryType = "PICKUP",
                deliveryDate = "Tomorrow (10:00 AM - 6:00 PM)",
                deliveryFee = 0.0,
                totalAmount = 6000.0,
                instructions = "Double check margins before cutting. Rounded corners if possible.",
                artworkFilename = "business_card_front_back.pdf"
            )
            val order2 = Order(
                orderId = 1002,
                userId = 1,
                addressId = 1,
                orderDate = System.currentTimeMillis() - 3600000 * 24,
                status = OrderStatus.PRINTING.name,
                deliveryType = "DELIVERY",
                deliveryDate = "Oct 12, 2026",
                deliveryFee = 450.0,
                totalAmount = 25450.0,
                instructions = "Urgent event flyers. Deliver to reception desk.",
                artworkFilename = "promo_event_flyer_a5.png"
            )
            val order3 = Order(
                orderId = 1003,
                userId = 1,
                addressId = 1,
                orderDate = System.currentTimeMillis() - 3600000 * 72,
                status = OrderStatus.COMPLETED.name,
                deliveryType = "DELIVERY",
                deliveryDate = "Oct 06, 2026",
                deliveryFee = 450.0,
                totalAmount = 33450.0,
                instructions = "Black tees with white front chest logo.",
                artworkFilename = "team_tshirt_print.ai"
            )
            db.orderDao().insertOrder(order1)
            db.orderDao().insertOrder(order2)
            db.orderDao().insertOrder(order3)

            val item1 = OrderItem(
                orderItemId = 1,
                orderId = 1001,
                productId = 1,
                quantity = 500,
                unitPrice = 12.0,
                specifications = "Size: 90 × 55 mm | Material: 300gsm Matte Card"
            )
            val item2 = OrderItem(
                orderItemId = 2,
                orderId = 1002,
                productId = 3,
                quantity = 1000,
                unitPrice = 25.0,
                specifications = "Size: A5 (148 × 210 mm) | Material: 150gsm Art Gloss Paper"
            )
            val item3 = OrderItem(
                orderItemId = 3,
                orderId = 1003,
                productId = 11,
                quantity = 20,
                unitPrice = 1650.0,
                specifications = "Size: Unisex L | Material: 180gsm Ring-Spun Cotton"
            )
            db.orderDao().insertOrderItems(listOf(item1, item2, item3))

            // 6. Pre-seed Notifications
            val notifications = listOf(
                NotificationItem(
                    notificationId = 1,
                    userId = 1,
                    title = "Order #1002 In Production",
                    message = "Your order for 1,000 A5 Gloss Flyers is currently on the printing press at our Colombo center.",
                    createdDate = System.currentTimeMillis() - 3600000 * 6,
                    isRead = false
                ),
                NotificationItem(
                    notificationId = 2,
                    userId = 1,
                    title = "Order #1001 Confirmed & Processing",
                    message = "Your Business Cards order has been verified by prepress and is scheduled for digital printing.",
                    createdDate = System.currentTimeMillis() - 3600000 * 5,
                    isRead = true
                ),
                NotificationItem(
                    notificationId = 3,
                    userId = 1,
                    title = "Exclusive Promo: 20% Off Banners",
                    message = "Use code PRINTLK20 at checkout for 20% off all Outdoor Vinyl and Pull-Up Banners this month!",
                    createdDate = System.currentTimeMillis() - 3600000 * 48,
                    isRead = false
                ),
                NotificationItem(
                    notificationId = 4,
                    userId = 1,
                    title = "Welcome to PrintXpress!",
                    message = "Welcome to Sri Lanka's leading digital printing portal. Discover premium quality prints with rapid delivery.",
                    createdDate = System.currentTimeMillis() - 3600000 * 72,
                    isRead = true
                )
            )
            for (notif in notifications) {
                db.notificationDao().insertNotification(notif)
            }
        }
    }
}
