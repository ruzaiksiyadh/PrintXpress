package lk.printxpress

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import lk.printxpress.data.local.AppDatabase
import lk.printxpress.data.repository.AuthRepository
import lk.printxpress.data.repository.NotificationRepository
import lk.printxpress.data.repository.OrderRepository
import lk.printxpress.data.repository.ProductRepository
import lk.printxpress.viewmodel.ViewModelFactory

class PrintXpressApp : Application() {

    val database by lazy { AppDatabase.getDatabase(this) }
    val authRepository by lazy { AuthRepository(database, this) }
    val productRepository by lazy { ProductRepository(database) }
    val orderRepository by lazy { OrderRepository(database) }
    val notificationRepository by lazy { NotificationRepository(database) }

    val viewModelFactory by lazy {
        ViewModelFactory(
            authRepository = authRepository,
            productRepository = productRepository,
            orderRepository = orderRepository,
            notificationRepository = notificationRepository
        )
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.IO).launch {
            AppDatabase.populateDatabase(database)
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "PrintXpress Orders & Promotions"
            val descriptionText = "Notifications about print orders and special offers"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        const val CHANNEL_ID = "printxpress_order_channel"
    }
}
