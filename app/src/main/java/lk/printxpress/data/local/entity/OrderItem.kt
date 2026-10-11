package lk.printxpress.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "order_items",
    foreignKeys = [
        ForeignKey(
            entity = Order::class,
            parentColumns = ["order_id"],
            childColumns = ["order_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Product::class,
            parentColumns = ["product_id"],
            childColumns = ["product_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["order_id"]),
        Index(value = ["product_id"])
    ]
)
data class OrderItem(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "order_item_id")
    val orderItemId: Long = 0,

    @ColumnInfo(name = "order_id")
    val orderId: Long,

    @ColumnInfo(name = "product_id")
    val productId: Long,

    @ColumnInfo(name = "quantity")
    val quantity: Int,

    @ColumnInfo(name = "unit_price")
    val unitPrice: Double,

    @ColumnInfo(name = "specifications")
    val specifications: String = "" // e.g. "Size: A4 | Material: 300gsm Glossy"
)
