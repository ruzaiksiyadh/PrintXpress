package lk.printxpress.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import lk.printxpress.data.local.entity.Design

@Dao
interface DesignDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDesign(design: Design): Long

    @Query("SELECT * FROM designs WHERE user_id = :userId ORDER BY created_date DESC")
    fun getDesignsForUser(userId: Long): Flow<List<Design>>

    @Delete
    suspend fun deleteDesign(design: Design)
}
