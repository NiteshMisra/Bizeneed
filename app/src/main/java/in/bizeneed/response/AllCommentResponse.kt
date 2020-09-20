package `in`.bizeneed.response

data class AllCommentResponse(
   val data : List<CommentData>,
   val success : String
)

data class CommentData(
    val id : String,
    val rating : String,
    val feedback : String,
    val userId : String,
    val userName : String,
    val likes : String,
    val timeOfComment : String
)