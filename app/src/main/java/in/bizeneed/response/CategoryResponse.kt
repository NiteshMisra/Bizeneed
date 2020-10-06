package `in`.bizeneed.response

data class CategoryResponse(
    val category : List<CategoryData>,
    val success : String
)

data class CategoryData(
    val id : String,
    val name : String,
    val image : String,
    var isShowing : Boolean = false,
    var showPosition : Int = 0,
    val subCategory : List<SubCategoryData>
)

data class SubCategoryData(
    val id : String,
    val name : String,
    val headerImage : String,
    val discount : String,
    val promoCode : String,
    val sellingPrice : String,
    val walletWithdrawalPercent : String,
    val mrp : String,
    var mulitpleDescription : List<DescriptionData>?,
    val demoImages : List<DemoImages>
)

data class DescriptionData(
    val id : String,
    val heading : String,
    val body : String
)

data class DemoImages(
    val id : String,
    val name : String,
    val image : String
)