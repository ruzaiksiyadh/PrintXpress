package lk.printxpress.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import lk.printxpress.data.repository.AuthRepository
import lk.printxpress.data.repository.NotificationRepository
import lk.printxpress.data.repository.OrderRepository
import lk.printxpress.data.repository.ProductRepository

class ViewModelFactory(
    private val authRepository: AuthRepository,
    private val productRepository: ProductRepository,
    private val orderRepository: OrderRepository,
    private val notificationRepository: NotificationRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AuthViewModel::class.java) -> {
                AuthViewModel(authRepository) as T
            }
            modelClass.isAssignableFrom(ProductViewModel::class.java) -> {
                ProductViewModel(productRepository) as T
            }
            modelClass.isAssignableFrom(OrderViewModel::class.java) -> {
                OrderViewModel(orderRepository, authRepository) as T
            }
            modelClass.isAssignableFrom(NotificationViewModel::class.java) -> {
                NotificationViewModel(notificationRepository, authRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
