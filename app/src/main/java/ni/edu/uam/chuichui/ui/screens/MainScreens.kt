package ni.edu.uam.chuichui.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ni.edu.uam.chuichui.model.Producto
import ni.edu.uam.chuichui.ui.theme.ChuiChuiTheme

private val productosPulperia = listOf(
    Producto(1, "Arroz", "Pulperia San Jose", 18.50f, 30),
    Producto(2, "Frijoles", "Pulperia San Jose", 32.00f, 24),
    Producto(3, "Azucar", "Pulperia San Jose", 20.00f, 18),
    Producto(4, "Cafe", "Pulperia San Jose", 45.00f, 12)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onStartOrder: () -> Unit = {}
) {
    var selectedProduct by remember { mutableStateOf(productosPulperia.first()) }
    var quantity by remember { mutableIntStateOf(1) }
    var message by remember { mutableStateOf("Selecciona un producto para preparar tu pedido.") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pedido de Pulperia") },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.Store,
                        contentDescription = "Pulperia",
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Pide rapido en tu pulperia",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = "Elige un producto, ajusta la cantidad y pasa al formulario para registrar los datos del cliente.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Text(
                            text = "Productos disponibles",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            productosPulperia.take(2).forEach { producto ->
                                ProductChip(
                                    producto = producto,
                                    selected = producto == selectedProduct,
                                    onClick = {
                                        selectedProduct = producto
                                        message = "${producto.nombre} seleccionado. Stock disponible: ${producto.stockDisponible}."
                                    },
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            productosPulperia.drop(2).forEach { producto ->
                                ProductChip(
                                    producto = producto,
                                    selected = producto == selectedProduct,
                                    onClick = {
                                        selectedProduct = producto
                                        message = "${producto.nombre} seleccionado. Stock disponible: ${producto.stockDisponible}."
                                    },
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "Pedido rapido",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "Producto: ${selectedProduct.nombre}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = "Precio unitario: C$ ${"%.2f".format(selectedProduct.precio)}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Cantidad",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                IconButton(
                                    onClick = {
                                        if (quantity > 1) {
                                            quantity--
                                            message = "Cantidad actualizada: $quantity unidad(es)."
                                        }
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Remove,
                                        contentDescription = "Disminuir cantidad"
                                    )
                                }
                                Text(
                                    text = quantity.toString(),
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold
                                )
                                IconButton(
                                    onClick = {
                                        if (quantity < selectedProduct.stockDisponible) {
                                            quantity++
                                            message = "Cantidad actualizada: $quantity unidad(es)."
                                        } else {
                                            message = "No hay mas stock disponible para ${selectedProduct.nombre}."
                                        }
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = "Aumentar cantidad"
                                    )
                                }
                            }
                        }

                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            color = MaterialTheme.colorScheme.secondaryContainer,
                            shape = MaterialTheme.shapes.medium
                        ) {
                            Text(
                                text = message,
                                modifier = Modifier.padding(14.dp),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        }

                        Button(
                            onClick = onStartOrder,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                imageVector = Icons.Default.ShoppingCart,
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Realizar pedido")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))
            }
        }
    }
}

@Composable
private fun ProductChip(
    producto: Producto,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FilterChip(
        selected = selected,
        onClick = onClick,
        label = {
            Column {
                Text(
                    text = producto.nombre,
                    style = MaterialTheme.typography.labelLarge
                )
                Text(
                    text = "C$ ${"%.2f".format(producto.precio)}",
                    style = MaterialTheme.typography.labelMedium
                )
            }
        },
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
private fun MainScreenPreview() {
    ChuiChuiTheme {
        MainScreen()
    }
}
