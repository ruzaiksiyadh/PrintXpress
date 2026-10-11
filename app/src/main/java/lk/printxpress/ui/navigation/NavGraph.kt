package lk.printxpress.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import lk.printxpress.ui.screens.auth.LoginScreen
import lk.printxpress.ui.screens.auth.RegisterScreen
import lk.printxpress.ui.screens.design.DesignResourcesScreen
import lk.printxpress.ui.screens.home.HomeScreen
import lk.printxpress.ui.screens.notifications.NotificationScreen
import lk.printxpress.ui.screens.order.CheckoutScreen
import lk.printxpress.ui.screens.order.OrderDetailScreen
import lk.printxpress.ui.screens.order.OrderListScreen
import lk.printxpress.ui.screens.product.ProductDetailScreen
import lk.printxpress.ui.screens.profile.ProfileScreen
import lk.printxpress.viewmodel.AuthViewModel
import lk.printxpress.viewmodel.NotificationViewModel
import lk.printxpress.viewmodel.OrderViewModel
import lk.printxpress.viewmodel.ProductViewModel

@Composable
fun PrintXpressNavGraph(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    productViewModel: ProductViewModel,
    orderViewModel: OrderViewModel,
    notificationViewModel: NotificationViewModel,
    isDarkTheme: Boolean,
    onToggleDarkTheme: (Boolean) -> Unit
) {
    val currentUser by authViewModel.currentUser.collectAsState()

    val startDestination = if (currentUser != null) Screen.Home.route else Screen.Login.route

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                authViewModel = authViewModel,
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                },
                onLoginSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                authViewModel = authViewModel,
                onNavigateToLogin = {
                    navController.popBackStack()
                },
                onRegisterSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Home.route) {
            HomeScreen(
                authViewModel = authViewModel,
                productViewModel = productViewModel,
                notificationViewModel = notificationViewModel,
                onNavigate = { route ->
                    navController.navigate(route) {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(
            route = Screen.ProductDetail.route,
            arguments = listOf(navArgument("productId") { type = NavType.LongType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getLong("productId") ?: 0L
            ProductDetailScreen(
                productId = productId,
                productViewModel = productViewModel,
                onNavigateBack = { navController.popBackStack() },
                onProceedToCheckout = { pId, quantity, specs, instructions, artwork ->
                    navController.currentBackStackEntry?.savedStateHandle?.set("checkout_qty", quantity)
                    navController.currentBackStackEntry?.savedStateHandle?.set("checkout_specs", specs)
                    navController.currentBackStackEntry?.savedStateHandle?.set("checkout_instructions", instructions)
                    navController.currentBackStackEntry?.savedStateHandle?.set("checkout_artwork", artwork)
                    navController.navigate(Screen.Checkout.createRoute(pId))
                }
            )
        }

        composable(
            route = Screen.Checkout.route,
            arguments = listOf(navArgument("productId") { type = NavType.LongType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getLong("productId") ?: 0L
            val prevEntry = navController.previousBackStackEntry
            val quantity = prevEntry?.savedStateHandle?.get<Int>("checkout_qty") ?: 100
            val specs = prevEntry?.savedStateHandle?.get<String>("checkout_specs") ?: ""
            val instructions = prevEntry?.savedStateHandle?.get<String>("checkout_instructions") ?: ""
            val artwork = prevEntry?.savedStateHandle?.get<String>("checkout_artwork") ?: ""

            CheckoutScreen(
                productId = productId,
                quantity = quantity,
                specifications = specs,
                instructions = instructions,
                artworkFilename = artwork,
                authViewModel = authViewModel,
                productViewModel = productViewModel,
                orderViewModel = orderViewModel,
                onNavigateBack = { navController.popBackStack() },
                onOrderPlaced = { orderId ->
                    navController.navigate(Screen.OrderDetail.createRoute(orderId)) {
                        popUpTo(Screen.Home.route)
                    }
                }
            )
        }

        composable(Screen.Orders.route) {
            OrderListScreen(
                orderViewModel = orderViewModel,
                onNavigate = { route ->
                    navController.navigate(route) {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(
            route = Screen.OrderDetail.route,
            arguments = listOf(navArgument("orderId") { type = NavType.LongType })
        ) { backStackEntry ->
            val orderId = backStackEntry.arguments?.getLong("orderId") ?: 0L
            OrderDetailScreen(
                orderId = orderId,
                orderViewModel = orderViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.DesignResources.route) {
            DesignResourcesScreen(
                onNavigate = { route ->
                    navController.navigate(route) {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(Screen.Notifications.route) {
            NotificationScreen(
                notificationViewModel = notificationViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Profile.route) {
            ProfileScreen(
                authViewModel = authViewModel,
                isDarkTheme = isDarkTheme,
                onToggleDarkTheme = onToggleDarkTheme,
                onNavigate = { route ->
                    navController.navigate(route) {
                        launchSingleTop = true
                    }
                },
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}
