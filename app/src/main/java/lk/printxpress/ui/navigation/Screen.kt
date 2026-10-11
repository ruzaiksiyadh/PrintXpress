package lk.printxpress.ui.navigation

sealed class Screen(val route: String) {
    data object Login : Screen("login")
    data object Register : Screen("register")
    data object Home : Screen("home")
    data object Orders : Screen("orders")
    data object OrderDetail : Screen("order_detail/{orderId}") {
        fun createRoute(orderId: Long) = "order_detail/$orderId"
    }
    data object ProductDetail : Screen("product_detail/{productId}") {
        fun createRoute(productId: Long) = "product_detail/$productId"
    }
    data object Checkout : Screen("checkout/{productId}") {
        fun createRoute(productId: Long) = "checkout/$productId"
    }
    data object DesignResources : Screen("design_resources")
    data object Notifications : Screen("notifications")
    data object Profile : Screen("profile")
}
