package `in`.bizeneed.response

data class CheckReferResponse(
    val data : List<CheckRefer>,
    val success : String
)

data class CheckRefer(
    val isValid : String,  // In Valid
    val discountPrice : String,
    val cashBack : String,
    val transaction : String,
    val cashbackTabelUpdated : String
)