package lk.printxpress

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import lk.printxpress.ui.navigation.PrintXpressNavGraph
import lk.printxpress.ui.theme.PrintXpressTheme
import lk.printxpress.viewmodel.AuthViewModel
import lk.printxpress.viewmodel.NotificationViewModel
import lk.printxpress.viewmodel.OrderViewModel
import lk.printxpress.viewmodel.ProductViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val app = application as PrintXpressApp
        val factory = app.viewModelFactory

        val authViewModel = ViewModelProvider(this, factory)[AuthViewModel::class.java]
        val productViewModel = ViewModelProvider(this, factory)[ProductViewModel::class.java]
        val orderViewModel = ViewModelProvider(this, factory)[OrderViewModel::class.java]
        val notificationViewModel = ViewModelProvider(this, factory)[NotificationViewModel::class.java]

        setContent {
            val systemDark = isSystemInDarkTheme()
            var isDarkTheme by remember { mutableStateOf(systemDark) }

            PrintXpressTheme(darkTheme = isDarkTheme) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    PrintXpressNavGraph(
                        navController = navController,
                        authViewModel = authViewModel,
                        productViewModel = productViewModel,
                        orderViewModel = orderViewModel,
                        notificationViewModel = notificationViewModel,
                        isDarkTheme = isDarkTheme,
                        onToggleDarkTheme = { isDarkTheme = it }
                    )
                }
            }
        }
    }
}
