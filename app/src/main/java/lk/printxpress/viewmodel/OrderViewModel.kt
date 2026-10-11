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
import lk.printxpress.data.local.entity.Order
import lk.printxpress.data.model.OrderStatus
import lk.printxpress.data.model.OrderWithDetails
import lk.printxpress.data.repository.AuthRepository
import lk.printxpress.data.repository.OrderRepository

data class OrderActionState(
    val isLoading: Boolean = false,
    val successMessage: String? = null,
    val errorMessage: String? = null
)

class OrderViewModel(
    private val orderRepository: OrderRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val currentUser = authRepository.currentUser

    @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    val orders: StateFlow<List<Order>> = currentUser.flatMapLatest { user ->
        if (user != null) {
            orderRepository.getOrdersForUser(user.userId)
        } else {
            flowOf(emptyList())
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _selectedOrderDetails = MutableStateFlow<OrderWithDetails?>(null)
    val selectedOrderDetails: StateFlow<OrderWithDetails?> = _selectedOrderDetails.asStateFlow()

    private val _actionState = MutableStateFlow(OrderActionState())
    val actionState: StateFlow<OrderActionState> = _actionState.asStateFlow()

    fun clearActionState() {
        _actionState.value = OrderActionState()
    }

    fun loadOrderDetails(orderId: Long) {
        viewModelScope.launch {
            _selectedOrderDetails.value = orderRepository.getOrderWithDetails(orderId)
        }
    }

    fun placeOrder(
        productId: Long,
        quantity: Int,
        specifications: String,
        deliveryType: String,
        addressId: Long?,
        deliveryDate: String,
        instructions: String,
        artworkFilename: String,
        onSuccess: (Long) -> Unit,
        onError: (String) -> Unit
    ) {
        val user = currentUser.value
        if (user == null) {
            onError("Please log in to place an order")
            return
        }

        viewModelScope.launch {
            _actionState.value = OrderActionState(isLoading = true)
            val result = orderRepository.placeOrder(
                userId = user.userId,
                productId = productId,
                quantity = quantity,
                specifications = specifications,
                deliveryType = deliveryType,
                addressId = addressId,
                deliveryDate = deliveryDate,
                instructions = instructions,
                artworkFilename = artworkFilename
            )
            _actionState.value = OrderActionState(isLoading = false)

            result.onSuccess { orderId ->
                _actionState.value = OrderActionState(
                    successMessage = "Order #$orderId placed successfully!"
                )
                onSuccess(orderId)
            }.onFailure { e ->
                _actionState.value = OrderActionState(
                    errorMessage = e.message ?: "Failed to place order"
                )
                onError(e.message ?: "Failed to place order")
            }
        }
    }

    fun cancelOrder(orderId: Long, onDone: (Boolean) -> Unit) {
        val user = currentUser.value ?: return
        viewModelScope.launch {
            _actionState.value = OrderActionState(isLoading = true)
            val result = orderRepository.cancelOrder(orderId, user.userId)
            _actionState.value = OrderActionState(isLoading = false)

            result.onSuccess {
                _actionState.value = OrderActionState(
                    successMessage = "Order #$orderId has been cancelled"
                )
                loadOrderDetails(orderId)
                onDone(true)
            }.onFailure { e ->
                _actionState.value = OrderActionState(
                    errorMessage = e.message ?: "Cannot cancel order"
                )
                onDone(false)
            }
        }
    }

    fun rescheduleOrder(orderId: Long, newDate: String, onDone: (Boolean) -> Unit) {
        viewModelScope.launch {
            _actionState.value = OrderActionState(isLoading = true)
            val result = orderRepository.rescheduleOrder(orderId, newDate)
            _actionState.value = OrderActionState(isLoading = false)

            result.onSuccess {
                _actionState.value = OrderActionState(
                    successMessage = "Delivery rescheduled to $newDate"
                )
                loadOrderDetails(orderId)
                onDone(true)
            }.onFailure { e ->
                _actionState.value = OrderActionState(
                    errorMessage = e.message ?: "Cannot reschedule order"
                )
                onDone(false)
            }
        }
    }

    fun advanceOrderStatus(orderId: Long) {
        viewModelScope.launch {
            val result = orderRepository.advanceOrderStatus(orderId)
            result.onSuccess {
                loadOrderDetails(orderId)
            }
        }
    }
}
