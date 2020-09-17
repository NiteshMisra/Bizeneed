package `in`.bizeneed.repository

import `in`.bizeneed.response.AllServicesResponse
import `in`.bizeneed.response.BannerResponse
import `in`.bizeneed.rest.RetrofitClient
import androidx.lifecycle.MutableLiveData
import `in`.bizeneed.rest.Coroutines

class MyRepository {

    private var retrofitClient: RetrofitClient = RetrofitClient.getInstance()

    fun getServices() : MutableLiveData<AllServicesResponse>{
        val data : MutableLiveData<AllServicesResponse> = MutableLiveData()

        Coroutines.io {
            val response = retrofitClient.api.getServices()
            if (response.isSuccessful){
                val body = response.body()
                body?.let {
                    if (body.success.toInt() == 1){
                        data.postValue(body)
                    }else{
                        data.postValue(null)
                    }
                }
            }else {
                data.postValue(null)
            }
        }

        return data;
    }

    fun getBanners() : MutableLiveData<BannerResponse>{
        val data : MutableLiveData<BannerResponse> = MutableLiveData()

        Coroutines.io {
            val response = retrofitClient.api.getBanners()
            if (response.isSuccessful){
                val body = response.body()
                body?.let {
                    if (body.success.toInt() == 1){
                        data.postValue(body)
                    }else{
                        data.postValue(null)
                    }
                }
            }else {
                data.postValue(null)
            }
        }

        return data;
    }

}