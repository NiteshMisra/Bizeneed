package `in`.bizeneed.extras

import `in`.bizeneed.repository.MyRepository
import android.content.Context

fun getMyViewModelFactory(context: Context) : MyFactory {
    val myRepository = MyRepository(context)
    return MyFactory(myRepository)
}