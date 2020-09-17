package `in`.bizeneed.extras

import `in`.bizeneed.repository.MyRepository

fun getMyViewModelFactory() : MyFactory {
    val myRepository = MyRepository()
    return MyFactory(myRepository)
}