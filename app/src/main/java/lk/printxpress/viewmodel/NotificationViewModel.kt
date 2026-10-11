package lk.printxpress.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import lk.printxpress.data.local.entity.NotificationItem
import lk.printxpress.data.repository.AuthRepository
import lk.printxpress.data.repository.NotificationRepository

class NotificationViewModel(
    private val notificationRepository: NotificationRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val currentUser = authRepository.currentUser

    @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    val notifications: StateFlow<List<NotificationItem>> = currentUser.flatMapLatest { user ->
        if (user != null) {
            notificationRepository.getNotifications(user.userId)
        } else {
            flowOf(emptyList())
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    val unreadCount: StateFlow<Int> = currentUser.flatMapLatest { user ->
        if (user != null) {
            notificationRepository.getUnreadCount(user.userId)
        } else {
            flowOf(0)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    fun markAsRead(notificationId: Long) {
        viewModelScope.launch {
            notificationRepository.markAsRead(notificationId)
        }
    }

    fun markAllAsRead() {
        val user = currentUser.value ?: return
        viewModelScope.launch {
            notificationRepository.markAllAsRead(user.userId)
        }
    }

    fun clearAll() {
        val user = currentUser.value ?: return
        viewModelScope.launch {
            notificationRepository.clearAll(user.userId)
        }
    }
}
