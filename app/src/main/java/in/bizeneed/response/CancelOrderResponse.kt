package `in`.bizeneed.response

import com.google.gson.annotations.SerializedName

data class CancelOrderResponse(
    @SerializedName("ErrorMessage") val errorMessage : String  // Success
)