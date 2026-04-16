package ni.edu.uam.chuichui.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ni.edu.uam.chuichui.model.Pedido
import ni.edu.uam.chuichui.model.PedidoDataSource
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrdersDoneScreen() {
    // Estado para la lista de pedidos
    val ordersList = remember { mutableStateListOf<Pedido>().apply { addAll(PedidoDataSource.sampleOrders) } }
    
    // Estado para el Snackbar (mensajes dinámicos)
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Pedidos Realizados", style = MaterialTheme.typography.headlineMedium) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                actions = {
                    IconButton(onClick = {
                        ordersList.clear()
                        ordersList.addAll(PedidoDataSource.sampleOrders)
                        scope.launch {
                            snackbarHostState.showSnackbar("Lista reiniciada")
                        }
                    }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Reiniciar lista")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            if (ordersList.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        "No hay pedidos registrados",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            } else {
                Text(
                    text = "Total de pedidos: ${ordersList.size}",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    items(ordersList, key = { it.id }) { pedido ->
                        OrderCard(
                            pedido = pedido,
                            onDelete = {
                                ordersList.remove(pedido)
                                scope.launch {
                                    snackbarHostState.showSnackbar("Pedido de ${pedido.cliente} eliminado")
                                }
                            },
                            onInfo = {
                                scope.launch {
                                    snackbarHostState.showSnackbar("Enviando a: ${pedido.direccion}")
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun OrderCard(
    pedido: Pedido,
    onDelete: () -> Unit,
    onInfo: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = pedido.cliente,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = pedido.fecha,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "${pedido.producto.nombre} x ${pedido.cantidad}",
                style = MaterialTheme.typography.bodyLarge
            )

            Text(
                text = "Total: $${String.format("%.2f", pedido.total)}",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.secondary
            )

            Divider(modifier = Modifier.padding(vertical = 8.dp), thickness = 0.5.dp)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                // Componente interactivo 1: Ver Info
                IconButton(onClick = onInfo) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Ver dirección",
                        tint = MaterialTheme.colorScheme.tertiary
                    )
                }
                
                // Componente interactivo 2: Eliminar
                IconButton(onClick = onDelete) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Eliminar pedido",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}
