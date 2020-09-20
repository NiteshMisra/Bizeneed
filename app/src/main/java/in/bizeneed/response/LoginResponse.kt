package `in`.bizeneed.response

data class LoginResponse(
    val data : List<User>,
    val ErrorMessage : String,
    val success : String,
    val registeration : String
)

data class User(
    val id : Int,
    val mobile : String,
    val name : String?,
    val email : String?,
    val address : String?,
    val city : String?,
    val state : String?,
    val pincode : String?
)