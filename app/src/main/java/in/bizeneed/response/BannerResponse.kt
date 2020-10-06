package `in`.bizeneed.response

data class BannerResponse(
    val data : List<BannerData>,
    val success : String
)

data class BannerData(
    val id : String,
    val image : String,
    val description : String,
    val subCategory : List<SubCategoryData>?
)