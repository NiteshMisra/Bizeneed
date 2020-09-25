package `in`.bizeneed.response

class CheckPromoCodeResponse(
    val data: List<PromoData>,
    val success : String
)

data class PromoData(
    val isValid : String,
    val discountPrice : String
)