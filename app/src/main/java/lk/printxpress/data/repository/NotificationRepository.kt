package lk.printxpress.data.repository

import kotlinx.coroutines.flow.Flow
import lk.printxpress.data.local.AppDatabase
import lk.printxpress.data.local.entity.NotificationItem

class NotificationRepository(private val database: AppDatabase) {

    fun getNotifications(userId: Long): Flow<List<NotificationItem>> {
        return database.notificationDao().getNotificationsForUser(userId)
    }

    fun getUnreadCount(userId: Long): Flow<Int> {
        return database.notificationDao().getUnreadCount(userId)
    }

    suspend fun markAsRead(notificationId: Long) {
        database.notificationDao().markAsRead(notificationId)
    }

    suspend fun markAllAsRead(userId: Long) {
        database.notificationDao().markAllAsRead(userId)
    }

    suspend fun clearAll(userId: Long) {
        database.notificationDao().clearAllNotifications(userId)
    }
}
