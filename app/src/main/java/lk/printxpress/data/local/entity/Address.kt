package lk.printxpress.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "addresses",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["user_id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["user_id"])]
)
data class Address(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "address_id")
    val addressId: Long = 0,

    @ColumnInfo(name = "user_id")
    val userId: Long,

    @ColumnInfo(name = "address_line")
    val addressLine: String,

    @ColumnInfo(name = "city")
    val city: String,

    @ColumnInfo(name = "postal_code")
    val postalCode: String
)
