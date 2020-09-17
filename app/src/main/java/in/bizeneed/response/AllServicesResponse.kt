package `in`.bizeneed.response

data class AllServicesResponse(
    val data : List<ServiceData>,
    val success : String
)

data class ServiceData(
    val id : String,
    val name : String,
    val image : String
)