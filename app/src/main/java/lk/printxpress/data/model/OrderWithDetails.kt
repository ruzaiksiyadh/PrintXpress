package lk.printxpress.data.model

import lk.printxpress.data.local.entity.Address
import lk.printxpress.data.local.entity.Order
import lk.printxpress.data.local.entity.OrderItem
import lk.printxpress.data.local.entity.Product

data class OrderItemWithProduct(
    val item: OrderItem,
    val product: Product?
)

data class OrderWithDetails(
    val order: Order,
    val address: Address?,
    val items: List<OrderItemWithProduct>
)
