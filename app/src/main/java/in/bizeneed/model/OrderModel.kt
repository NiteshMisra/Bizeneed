package `in`.bizeneed.model

data class OrderModel(
    val orderId: String,
    val userId: String,
    val name: String,
    val address: String,
    val state: String,
    val mobile: String,
    val email: String,
    val totalPrice: String,
    val timeOfOrder: String,
    val dateOfOrder: String,
    val subCategoryName: String,
    val paymentType: String,   // Online or Offline
    val cashBack: String,
    val gst: String,
    val businessName: String
)