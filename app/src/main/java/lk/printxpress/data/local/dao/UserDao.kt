package lk.printxpress.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import lk.printxpress.data.local.entity.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser(user: User): Long

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): User?

    @Query("SELECT * FROM users WHERE user_id = :userId LIMIT 1")
    suspend fun getUserById(userId: Long): User?

    @Query("SELECT * FROM users WHERE user_id = :userId LIMIT 1")
    fun observeUserById(userId: Long): Flow<User?>

    @Update
    suspend fun updateUser(user: User)

    @Query("UPDATE users SET name = :name, phone = :phone WHERE user_id = :userId")
    suspend fun updateProfile(userId: Long, name: String, phone: String)
}
