package ni.edu.uam.chuichui.model

data class Producto(
    val id: Int,
    val nombre: String,
    val distribuidora: String,
    val precio: Float,
    val stockDisponible: Int
)

object ProductDataSource {
    val sampleProducts = listOf(
        Producto(1, "Smartphone Galaxy", "TechDistrib", 599.99f, 15),
        Producto(2, "Laptop Pro 14", "Computers S.A.", 1200.00f, 8),
        Producto(3, "Audífonos Noise Cancel", "AudioWorld", 199.50f, 25),
        Producto(4, "Monitor 4K 27\"", "VisionCorp", 349.00f, 10),
        Producto(5, "Teclado Mecánico", "GamerX", 85.00f, 50),
        Producto(6, "Mouse Ergonómico", "OfficeSupplies", 45.00f, 30),
        Producto(7, "Tablet Air 10", "TechDistrib", 499.00f, 12),
        Producto(8, "Cámara Mirrorless", "PhotoHub", 899.99f, 5),
        Producto(9, "Impresora Láser", "PrintMaster", 150.00f, 20),
        Producto(10, "Smartwatch Series 7", "Wearables Inc", 299.00f, 18)
    )
}
