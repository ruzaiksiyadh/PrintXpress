package lk.printxpress.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import lk.printxpress.data.local.entity.NotificationItem

@Dao
interface NotificationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(notification: NotificationItem): Long

    @Query("SELECT * FROM notifications WHERE user_id = :userId ORDER BY created_date DESC")
    fun getNotificationsForUser(userId: Long): Flow<List<NotificationItem>>

    @Query("UPDATE notifications SET is_read = 1 WHERE notification_id = :notificationId")
    suspend fun markAsRead(notificationId: Long)

    @Query("UPDATE notifications SET is_read = 1 WHERE user_id = :userId")
    suspend fun markAllAsRead(userId: Long)

    @Query("SELECT COUNT(*) FROM notifications WHERE user_id = :userId AND is_read = 0")
    fun getUnreadCount(userId: Long): Flow<Int>

    @Query("DELETE FROM notifications WHERE user_id = :userId")
    suspend fun clearAllNotifications(userId: Long)
}
