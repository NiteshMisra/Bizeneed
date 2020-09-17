package `in`.bizeneed.viewmodel

import `in`.bizeneed.repository.MyRepository
import `in`.bizeneed.response.AllServicesResponse
import `in`.bizeneed.response.BannerResponse
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class MyViewModel(
    private var myRepository: MyRepository
) : ViewModel() {

    fun getServices() : LiveData<AllServicesResponse>{
        return myRepository.getServices()
    }

    fun getBanners() : LiveData<BannerResponse>{
        return myRepository.getBanners()
    }

}