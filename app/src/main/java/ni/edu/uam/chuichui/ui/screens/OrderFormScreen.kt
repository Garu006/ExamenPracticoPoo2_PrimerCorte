package ni.edu.uam.chuichui.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ni.edu.uam.chuichui.Pedido
import ni.edu.uam.chuichui.Producto

val productosDisponibles = listOf(
    Producto("Arroz", 25f),
    Producto("Azúcar", 18f),
    Producto("Aceite", 70f),
    Producto("Jabón", 15f)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderFormScreen(onOrderPlaced: (Pedido) -> Unit) {
    var cliente by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("Completa los datos del pedido") }

    var expanded by remember { mutableStateOf(false) }
    var productoSeleccionado by remember { mutableStateOf<Producto?>(null) }

    var pedidoRegistrado by remember { mutableStateOf<Pedido?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Formulario de Pedido") }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Pedido de Pulpería",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = cliente,
                onValueChange = { cliente = it },
                label = { Text("Nombre del cliente") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = productoSeleccionado?.nombre ?: "",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Producto") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    productosDisponibles.forEach { producto ->
                        DropdownMenuItem(
                            text = { Text("${producto.nombre} - C$ ${producto.precio}") },
                            onClick = {
                                productoSeleccionado = producto
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = cantidad,
                onValueChange = { cantidad = it },
                label = { Text("Cantidad") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = direccion,
                onValueChange = { direccion = it },
                label = { Text("Dirección de entrega") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    val cantidadInt = cantidad.toIntOrNull()

                    mensaje = when {
                        cliente.isBlank() || direccion.isBlank() || cantidad.isBlank() || productoSeleccionado == null -> {
                            "Completa todos los campos."
                        }
                        cantidadInt == null || cantidadInt <= 0 -> {
                            "La cantidad debe ser mayor que 0."
                        }
                        else -> {
                            val pedido = Pedido(
                                cliente = cliente,
                                producto = productoSeleccionado!!,
                                cantidad = cantidadInt,
                                direccion = direccion
                            )

                            pedidoRegistrado = pedido
                            onOrderPlaced(pedido)
                            "Pedido registrado correctamente."
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrar pedido")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = mensaje,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(20.dp))

            pedidoRegistrado?.let { pedido ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Pedido registrado",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text("ID: ${pedido.id}")
                        Text("Cliente: ${pedido.cliente}")
                        Text("Producto: ${pedido.producto.nombre}")
                        Text("Cantidad: ${pedido.cantidad}")
                        Text("Dirección: ${pedido.direccion}")
                        Text("Fecha: ${pedido.fecha}")
                        Text("Total: C$ ${pedido.total}")
                    }
                }
            }
        }
    }
}