package lk.printxpress.data.repository

import kotlinx.coroutines.flow.Flow
import lk.printxpress.data.local.AppDatabase
import lk.printxpress.data.local.entity.NotificationItem
import lk.printxpress.data.local.entity.Order
import lk.printxpress.data.local.entity.OrderItem
import lk.printxpress.data.model.OrderItemWithProduct
import lk.printxpress.data.model.OrderStatus
import lk.printxpress.data.model.OrderWithDetails
import lk.printxpress.utils.Validators

class OrderRepository(private val database: AppDatabase) {

    fun getOrdersForUser(userId: Long): Flow<List<Order>> {
        return database.orderDao().getOrdersForUser(userId)
    }

    suspend fun getOrderWithDetails(orderId: Long): OrderWithDetails? {
        val order = database.orderDao().getOrderById(orderId) ?: return null
        val address = order.addressId?.let { database.addressDao().getAddressById(it) }
        val items = database.orderDao().getOrderItems(orderId)
        val itemsWithProducts = items.map { item ->
            val product = database.productDao().getProductById(item.productId)
            OrderItemWithProduct(item, product)
        }
        return OrderWithDetails(order, address, itemsWithProducts)
    }

    suspend fun placeOrder(
        userId: Long,
        productId: Long,
        quantity: Int,
        specifications: String,
        deliveryType: String, // "PICKUP" or "DELIVERY"
        addressId: Long?,
        deliveryDate: String,
        instructions: String,
        artworkFilename: String
    ): Result<Long> {
        if (!Validators.isValidQuantity(quantity)) {
            return Result.failure(IllegalArgumentException("Quantity must be greater than 0"))
        }

        if (deliveryType.equals("DELIVERY", ignoreCase = true) && addressId == null) {
            return Result.failure(IllegalArgumentException("Delivery address is required for home delivery"))
        }

        val product = database.productDao().getProductById(productId)
            ?: return Result.failure(IllegalArgumentException("Product not found"))

        val unitPrice = product.price
        val subtotal = unitPrice * quantity
        val deliveryFee = if (deliveryType.equals("DELIVERY", ignoreCase = true)) 450.0 else 0.0
        val totalAmount = subtotal + deliveryFee

        val newOrder = Order(
            userId = userId,
            addressId = if (deliveryType.equals("DELIVERY", ignoreCase = true)) addressId else null,
            orderDate = System.currentTimeMillis(),
            status = OrderStatus.CONFIRMED.name,
            deliveryType = deliveryType,
            deliveryDate = deliveryDate.ifEmpty { "Estimated 2-3 business days" },
            deliveryFee = deliveryFee,
            totalAmount = totalAmount,
            instructions = instructions.take(500),
            artworkFilename = artworkFilename.ifEmpty { "Standard Design Template" }
        )

        val orderId = database.orderDao().insertOrder(newOrder)

        val orderItem = OrderItem(
            orderId = orderId,
            productId = productId,
            quantity = quantity,
            unitPrice = unitPrice,
            specifications = specifications
        )
        database.orderDao().insertOrderItem(orderItem)

        // Notification: Order Confirmed (Task D.8)
        database.notificationDao().insertNotification(
            NotificationItem(
                userId = userId,
                title = "Order #$orderId Confirmed!",
                message = "Thank you! Your order for $quantity x ${product.name} has been received and scheduled for processing.",
                createdDate = System.currentTimeMillis(),
                isRead = false
            )
        )

        return Result.success(orderId)
    }

    suspend fun cancelOrder(orderId: Long, userId: Long): Result<Unit> {
        val order = database.orderDao().getOrderById(orderId)
            ?: return Result.failure(IllegalArgumentException("Order not found"))

        if (!order.orderStatus.canCancel()) {
            return Result.failure(
                IllegalStateException("Order cannot be cancelled because printing has already commenced (Status: ${order.orderStatus.displayName})")
            )
        }

        database.orderDao().updateOrderStatus(orderId, OrderStatus.CANCELLED.name)

        database.notificationDao().insertNotification(
            NotificationItem(
                userId = userId,
                title = "Order #$orderId Cancelled",
                message = "Your order #$orderId has been successfully cancelled as requested.",
                createdDate = System.currentTimeMillis(),
                isRead = false
            )
        )

        return Result.success(Unit)
    }

    suspend fun rescheduleOrder(orderId: Long, newDate: String): Result<Unit> {
        val order = database.orderDao().getOrderById(orderId)
            ?: return Result.failure(IllegalArgumentException("Order not found"))

        if (!order.orderStatus.canReschedule()) {
            return Result.failure(
                IllegalStateException("Order cannot be rescheduled once in production")
            )
        }

        database.orderDao().updateDeliveryDate(orderId, newDate)
        return Result.success(Unit)
    }

    suspend fun advanceOrderStatus(orderId: Long): Result<OrderStatus> {
        val order = database.orderDao().getOrderById(orderId)
            ?: return Result.failure(IllegalArgumentException("Order not found"))

        val current = order.orderStatus
        val next = when (current) {
            OrderStatus.CONFIRMED -> OrderStatus.PROCESSING
            OrderStatus.PROCESSING -> OrderStatus.PRINTING
            OrderStatus.PRINTING -> OrderStatus.READY_FOR_PICKUP
            OrderStatus.READY_FOR_PICKUP -> OrderStatus.COMPLETED
            OrderStatus.COMPLETED -> OrderStatus.COMPLETED
            OrderStatus.CANCELLED -> OrderStatus.CANCELLED
        }

        if (next != current) {
            database.orderDao().updateOrderStatus(orderId, next.name)

            // Trigger notification
            val notifMessage = when (next) {
                OrderStatus.PROCESSING -> "Your order #$orderId is now being prepared for printing."
                OrderStatus.PRINTING -> "Your order #$orderId is currently running on the high-speed print press."
                OrderStatus.READY_FOR_PICKUP -> "Your order #$orderId is printed, trimmed, and ready for pickup/dispatch!"
                OrderStatus.COMPLETED -> "Your order #$orderId has been completed. Thank you for choosing PrintXpress!"
                else -> ""
            }
            if (notifMessage.isNotEmpty()) {
                database.notificationDao().insertNotification(
                    NotificationItem(
                        userId = order.userId,
                        title = "Order #$orderId: ${next.displayName}",
                        message = notifMessage,
                        createdDate = System.currentTimeMillis(),
                        isRead = false
                    )
                )
            }
        }

        return Result.success(next)
    }
}
