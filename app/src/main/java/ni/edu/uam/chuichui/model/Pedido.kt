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
