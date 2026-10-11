package lk.printxpress.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class Category(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "category_id")
    val categoryId: Long = 0,

    @ColumnInfo(name = "category_name")
    val categoryName: String,

    @ColumnInfo(name = "icon_name")
    val iconName: String = ""
)
