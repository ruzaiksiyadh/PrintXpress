package lk.printxpress.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import lk.printxpress.data.local.entity.Order
import lk.printxpress.data.local.entity.OrderItem

@Dao
interface OrderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: Order): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrderItem(item: OrderItem): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrderItems(items: List<OrderItem>)

    @Query("SELECT * FROM orders WHERE user_id = :userId ORDER BY order_date DESC")
    fun getOrdersForUser(userId: Long): Flow<List<Order>>

    @Query("SELECT * FROM orders WHERE order_id = :orderId LIMIT 1")
    suspend fun getOrderById(orderId: Long): Order?

    @Query("SELECT * FROM orders WHERE order_id = :orderId LIMIT 1")
    fun observeOrderById(orderId: Long): Flow<Order?>

    @Query("SELECT * FROM order_items WHERE order_id = :orderId")
    suspend fun getOrderItems(orderId: Long): List<OrderItem>

    @Query("SELECT * FROM order_items WHERE order_id = :orderId")
    fun observeOrderItems(orderId: Long): Flow<List<OrderItem>>

    @Query("UPDATE orders SET status = :status WHERE order_id = :orderId")
    suspend fun updateOrderStatus(orderId: Long, status: String)

    @Query("UPDATE orders SET delivery_date = :deliveryDate WHERE order_id = :orderId")
    suspend fun updateDeliveryDate(orderId: Long, deliveryDate: String)
}
