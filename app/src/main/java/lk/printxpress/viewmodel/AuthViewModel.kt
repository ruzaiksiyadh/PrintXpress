package lk.printxpress.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import lk.printxpress.data.local.entity.Address
import lk.printxpress.data.local.entity.User
import lk.printxpress.data.repository.AuthRepository

data class AuthUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null
)

class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {

    val currentUser: StateFlow<User?> = authRepository.currentUser

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    val userAddresses: StateFlow<List<Address>> = currentUser.flatMapLatest { user ->
        if (user != null) {
            authRepository.getAddresses(user.userId)
        } else {
            flowOf(emptyList())
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        viewModelScope.launch {
            authRepository.initialize()
        }
    }

    fun clearMessages() {
        _uiState.value = _uiState.value.copy(errorMessage = null, successMessage = null)
    }

    fun login(email: String, password: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            val result = authRepository.login(email, password)
            _uiState.value = _uiState.value.copy(isLoading = false)

            result.onSuccess {
                onSuccess()
            }.onFailure { e ->
                _uiState.value = _uiState.value.copy(errorMessage = e.message ?: "Login failed")
            }
        }
    }

    fun register(
        name: String,
        email: String,
        phone: String,
        password: String,
        confirmPassword: String,
        onSuccess: () -> Unit
    ) {
        if (password != confirmPassword) {
            _uiState.value = _uiState.value.copy(errorMessage = "Passwords do not match")
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            val result = authRepository.register(name, email, phone, password)
            _uiState.value = _uiState.value.copy(isLoading = false)

            result.onSuccess {
                onSuccess()
            }.onFailure { e ->
                _uiState.value = _uiState.value.copy(errorMessage = e.message ?: "Registration failed")
            }
        }
    }

    fun updateProfile(name: String, phone: String, onDone: (Boolean) -> Unit) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
            val result = authRepository.updateProfile(name, phone)
            _uiState.value = _uiState.value.copy(isLoading = false)

            result.onSuccess {
                _uiState.value = _uiState.value.copy(successMessage = "Profile updated successfully")
                onDone(true)
            }.onFailure { e ->
                _uiState.value = _uiState.value.copy(errorMessage = e.message ?: "Update failed")
                onDone(false)
            }
        }
    }

    fun addAddress(addressLine: String, city: String, postalCode: String, onDone: (Boolean) -> Unit) {
        val user = currentUser.value ?: return
        viewModelScope.launch {
            val result = authRepository.addAddress(user.userId, addressLine, city, postalCode)
            result.onSuccess {
                _uiState.value = _uiState.value.copy(successMessage = "Address added successfully")
                onDone(true)
            }.onFailure { e ->
                _uiState.value = _uiState.value.copy(errorMessage = e.message ?: "Failed to add address")
                onDone(false)
            }
        }
    }

    fun deleteAddress(address: Address) {
        viewModelScope.launch {
            authRepository.deleteAddress(address)
        }
    }

    fun logout() {
        authRepository.logout()
    }
}
