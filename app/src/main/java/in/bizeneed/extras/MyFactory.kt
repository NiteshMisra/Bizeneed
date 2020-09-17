package `in`.bizeneed.extras

import `in`.bizeneed.repository.MyRepository
import `in`.bizeneed.viewmodel.MyViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MyFactory(var myRepository: MyRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MyViewModel(myRepository = myRepository) as T
    }
}