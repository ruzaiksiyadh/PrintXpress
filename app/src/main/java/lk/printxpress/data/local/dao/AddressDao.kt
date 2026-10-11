package lk.printxpress.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import lk.printxpress.data.local.entity.Address

@Dao
interface AddressDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAddress(address: Address): Long

    @Query("SELECT * FROM addresses WHERE user_id = :userId")
    fun getAddressesForUser(userId: Long): Flow<List<Address>>

    @Query("SELECT * FROM addresses WHERE address_id = :addressId LIMIT 1")
    suspend fun getAddressById(addressId: Long): Address?

    @Delete
    suspend fun deleteAddress(address: Address)

    @Query("DELETE FROM addresses WHERE address_id = :addressId")
    suspend fun deleteAddressById(addressId: Long)
}
