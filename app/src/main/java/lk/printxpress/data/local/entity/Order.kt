package lk.printxpress.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import lk.printxpress.data.model.OrderStatus

@Entity(
    tableName = "orders",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["user_id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Address::class,
            parentColumns = ["address_id"],
            childColumns = ["address_id"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [
        Index(value = ["user_id"]),
        Index(value = ["address_id"])
    ]
)
data class Order(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "order_id")
    val orderId: Long = 0,

    @ColumnInfo(name = "user_id")
    val userId: Long,

    @ColumnInfo(name = "address_id")
    val addressId: Long? = null,

    @ColumnInfo(name = "order_date")
    val orderDate: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "status")
    val status: String = OrderStatus.CONFIRMED.name,

    @ColumnInfo(name = "delivery_type")
    val deliveryType: String = "PICKUP", // "PICKUP" or "DELIVERY"

    @ColumnInfo(name = "delivery_date")
    val deliveryDate: String = "",

    @ColumnInfo(name = "delivery_fee")
    val deliveryFee: Double = 0.0,

    @ColumnInfo(name = "total_amount")
    val totalAmount: Double = 0.0,

    @ColumnInfo(name = "instructions")
    val instructions: String = "",

    @ColumnInfo(name = "artwork_filename")
    val artworkFilename: String = ""
) {
    val orderStatus: OrderStatus
        get() = OrderStatus.fromString(status)
}
