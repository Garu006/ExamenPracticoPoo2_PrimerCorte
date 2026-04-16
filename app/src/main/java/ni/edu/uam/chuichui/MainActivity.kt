package ni.edu.uam.chuichui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import ni.edu.uam.chuichui.ui.screens.*
import ni.edu.uam.chuichui.ui.theme.ChuiChuiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChuiChuiTheme {
                ChuiChuiApp()
            }
        }
    }
}

@Composable
fun ChuiChuiApp() {
    var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.HOME) }
    // State to handle the "Order Registered" screen which is not in the navbar
    var showOrderRegistered by rememberSaveable { mutableStateOf(false) }

    if (showOrderRegistered) {
        OrderRegisteredScreen(onBackToMain = {
            showOrderRegistered = false
            currentDestination = AppDestinations.HOME
        })
    } else {
        NavigationSuiteScaffold(
            navigationSuiteItems = {
                AppDestinations.entries.forEach {
                    item(
                        icon = {
                            Icon(
                                imageVector = it.icon,
                                contentDescription = it.label
                            )
                        },
                        label = { Text(it.label) },
                        selected = it == currentDestination,
                        onClick = { currentDestination = it }
                    )
                }
            }
        ) {
            when (currentDestination) {
                AppDestinations.HOME -> MainScreen()
                AppDestinations.PRODUCTS -> ProductsScreen()
                AppDestinations.ORDER_FORM -> OrderFormScreen(onOrderPlaced = {
                    showOrderRegistered = true
                })
                AppDestinations.ORDERS_DONE -> OrdersDoneScreen()
            }
        }
    }
}

enum class AppDestinations(
    val label: String,
    val icon: ImageVector,
) {
    HOME("Inicio", Icons.Default.Home),
    PRODUCTS("Productos", Icons.Default.Favorite),
    ORDER_FORM("Pedido", Icons.Default.Add),
    ORDERS_DONE("Historial", Icons.Default.List),
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    ChuiChuiTheme {
        ChuiChuiApp()
    }
}
