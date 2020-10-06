package `in`.bizeneed.model

data class UpdateModel(
    var name : String,
    var email : String,
    var address : String,
    var city : String,
    var state : String,
    var pincode : String,
    var id : String,
    val profile : String,
    val referCode : String
)