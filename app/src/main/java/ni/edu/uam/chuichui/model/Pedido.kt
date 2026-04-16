package ni.edu.uam.chuichui.model

import java.util.UUID

data class Pedido(
    val id: String = UUID.randomUUID().toString(),
    val cliente: String,
    val producto: Producto,
    val cantidad: Int,
    val direccion: String,
    val fecha: String = java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(java.util.Date())
) {
    val total: Float get() = producto.precio * cantidad
}

object PedidoDataSource {
    val sampleOrders = listOf(
        Pedido(
            cliente = "Juan Pérez",
            producto = ProductDataSource.sampleProducts[0],
            cantidad = 1,
            direccion = "Av. Central 123"
        ),
        Pedido(
            cliente = "María López",
            producto = ProductDataSource.sampleProducts[2],
            cantidad = 2,
            direccion = "Calle Luna 456"
        ),
        Pedido(
            cliente = "Carlos Ruiz",
            producto = ProductDataSource.sampleProducts[4],
            cantidad = 3,
            direccion = "Residencial El Sol"
        )
    )
}
