package `in`.bizeneed.response

data class OrderResponse(
    val data : List<OrderData>,
    val success : String
)

data class OrderData(
    val orderId : String,
    val subCategoryName : String,
    val userId : String,
    val name : String,
    val address : String,
    val state : String,
    val mobile : String,
    val totalAmount : String,
    val timeOfOrder : String,
    val dateOfOrder : String,
    val completed : String,
    val paymentType : String,
    val subCategoryDetails : List<SubCategoryData>
)