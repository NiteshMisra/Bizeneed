package `in`.bizeneed.response

data class CategoryResponse(
    val category : List<CategoryData>,
    val success : String
)

data class CategoryData(
    val id : String,
    val name : String,
    val image : String,
    val subCategory : List<SubCategoryData>
)

data class SubCategoryData(
    val id : String,
    val name : String,
    val image : String,
    val discount : String,
    val promoCode : String,
    val sellingPrice : String,
    val mrp : String,
    val description : String,
    val demoImages : List<DemoImages>
)

data class DemoImages(
    val id : String,
    val name : String,
    val image : String
)