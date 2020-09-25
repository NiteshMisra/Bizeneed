package `in`.bizeneed.response

data class CouponResponse(
    val data : List<CouponData>,
    val success : String
)

data class CouponData(
    val id : String,
    val couponCode : String,
    val discountPrice : String,
    val description : String,
    var isSelected : Boolean = false
)