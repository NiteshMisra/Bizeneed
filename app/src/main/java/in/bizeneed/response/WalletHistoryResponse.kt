package `in`.bizeneed.response

data class WalletHistoryResponse(
    val data : List<WalletData>,
    val success : String
)

data class WalletData(
    val id : String,
    val operation : String,
    val amount : String,
    val timeOfTransaction : String
)