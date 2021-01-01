package `in`.bizeneed.response

data class EffectivePriceResponse
(
    val data : List<EffectivePrice>,
    val success : String
)
data class EffectivePrice
(
    val image : String,
    val title : String
)