package lk.printxpress.data.repository

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import lk.printxpress.data.local.AppDatabase
import lk.printxpress.data.local.entity.Address
import lk.printxpress.data.local.entity.NotificationItem
import lk.printxpress.data.local.entity.User
import lk.printxpress.utils.SecurityUtils
import lk.printxpress.utils.Validators

class AuthRepository(
    private val database: AppDatabase,
    private val context: Context
) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("printxpress_auth", Context.MODE_PRIVATE)

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    suspend fun initialize() {
        AppDatabase.populateDatabase(database)
        val savedUserId = prefs.getLong("logged_in_user_id", -1L)
        if (savedUserId != -1L) {
            val user = database.userDao().getUserById(savedUserId)
            if (user != null) {
                _currentUser.value = user
            }
        }
    }

    suspend fun login(email: String, plainPassword: String): Result<User> {
        AppDatabase.populateDatabase(database)
        val trimmedEmail = email.trim()
        if (trimmedEmail.isEmpty()) {
            return Result.failure(IllegalArgumentException("Email address cannot be empty"))
        }
        if (!Validators.isValidEmail(trimmedEmail)) {
            return Result.failure(IllegalArgumentException("Invalid email format"))
        }
        if (plainPassword.isEmpty()) {
            return Result.failure(IllegalArgumentException("Password cannot be empty"))
        }

        val user = database.userDao().getUserByEmail(trimmedEmail)
            ?: return Result.failure(IllegalArgumentException("Invalid email or password"))

        if (!SecurityUtils.verifyPassword(plainPassword, user.passwordHash)) {
            return Result.failure(IllegalArgumentException("Invalid email or password"))
        }

        // Set session
        _currentUser.value = user
        prefs.edit().putLong("logged_in_user_id", user.userId).apply()
        return Result.success(user)
    }

    suspend fun register(
        name: String,
        email: String,
        phone: String,
        password: String
    ): Result<User> {
        val trimmedName = name.trim()
        val trimmedEmail = email.trim()
        val trimmedPhone = phone.trim()

        if (!Validators.isValidName(trimmedName)) {
            return Result.failure(IllegalArgumentException("Name must be at least 2 characters"))
        }
        if (!Validators.isValidEmail(trimmedEmail)) {
            return Result.failure(IllegalArgumentException("Please enter a valid email address"))
        }
        if (!Validators.isValidPhone(trimmedPhone)) {
            return Result.failure(IllegalArgumentException("Invalid phone number. Must be a valid Sri Lankan number (e.g., 0771234567)"))
        }
        if (!Validators.isValidPassword(password)) {
            return Result.failure(IllegalArgumentException("Password must be at least 6 characters and contain both letters and numbers"))
        }

        val existing = database.userDao().getUserByEmail(trimmedEmail)
        if (existing != null) {
            return Result.failure(IllegalArgumentException("An account with this email already exists"))
        }

        val newUser = User(
            name = trimmedName,
            email = trimmedEmail,
            phone = trimmedPhone,
            passwordHash = SecurityUtils.hashPassword(password)
        )

        val newId = database.userDao().insertUser(newUser)
        val createdUser = newUser.copy(userId = newId)

        // Send Welcome notification
        database.notificationDao().insertNotification(
            NotificationItem(
                userId = newId,
                title = "Welcome to PrintXpress!",
                message = "Your account has been created. Start browsing our digital print catalogue today!",
                isRead = false
            )
        )

        // Auto login
        _currentUser.value = createdUser
        prefs.edit().putLong("logged_in_user_id", newId).apply()
        return Result.success(createdUser)
    }

    suspend fun updateProfile(name: String, phone: String): Result<Unit> {
        val current = _currentUser.value ?: return Result.failure(IllegalStateException("Not logged in"))
        val trimmedName = name.trim()
        val trimmedPhone = phone.trim()

        if (!Validators.isValidName(trimmedName)) {
            return Result.failure(IllegalArgumentException("Name must be at least 2 characters"))
        }
        if (!Validators.isValidPhone(trimmedPhone)) {
            return Result.failure(IllegalArgumentException("Invalid phone number format"))
        }

        database.userDao().updateProfile(current.userId, trimmedName, trimmedPhone)
        val updated = current.copy(name = trimmedName, phone = trimmedPhone)
        _currentUser.value = updated
        return Result.success(Unit)
    }

    fun getAddresses(userId: Long): Flow<List<Address>> {
        return database.addressDao().getAddressesForUser(userId)
    }

    suspend fun addAddress(userId: Long, addressLine: String, city: String, postalCode: String): Result<Long> {
        if (addressLine.trim().isEmpty()) {
            return Result.failure(IllegalArgumentException("Address line cannot be empty"))
        }
        if (city.trim().isEmpty()) {
            return Result.failure(IllegalArgumentException("City cannot be empty"))
        }
        val id = database.addressDao().insertAddress(
            Address(
                userId = userId,
                addressLine = addressLine.trim(),
                city = city.trim(),
                postalCode = postalCode.trim()
            )
        )
        return Result.success(id)
    }

    suspend fun deleteAddress(address: Address) {
        database.addressDao().deleteAddress(address)
    }

    fun logout() {
        _currentUser.value = null
        prefs.edit().remove("logged_in_user_id").apply()
    }
}
