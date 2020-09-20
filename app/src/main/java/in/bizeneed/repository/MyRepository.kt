package `in`.bizeneed.repository

import `in`.bizeneed.extras.AppPrefData
import `in`.bizeneed.model.OrderModel
import `in`.bizeneed.model.UpdateModel
import `in`.bizeneed.response.*
import `in`.bizeneed.rest.RetrofitClient
import androidx.lifecycle.MutableLiveData
import `in`.bizeneed.rest.Coroutines
import android.content.Context

class MyRepository(var context : Context) {

    private var retrofitClient: RetrofitClient = RetrofitClient.getInstance(context)

    fun getServices() : MutableLiveData<AllServicesResponse>{
        val data : MutableLiveData<AllServicesResponse> = MutableLiveData()

        Coroutines.io {
            val response = retrofitClient.api.getServices()
            if (response.isSuccessful){
                val body = response.body()
                data.postValue(body)
            }else {
                data.postValue(null)
            }
        }

        return data;
    }

    fun getBanners(slot : String) : MutableLiveData<BannerResponse>{
        val data : MutableLiveData<BannerResponse> = MutableLiveData()

        Coroutines.io {
            val response = retrofitClient.api.getBanners(slot)
            if (response.isSuccessful){
                val body = response.body()
                data.postValue(body)
            }else {
                data.postValue(null)
            }
        }

        return data;
    }

    fun getAllCategory(serviceName : String) : MutableLiveData<CategoryResponse>{
        val data : MutableLiveData<CategoryResponse> = MutableLiveData()

        Coroutines.io {
            val response = retrofitClient.api.getAllCategory(serviceName)
            if (response.isSuccessful){
                val body = response.body()
                data.postValue(body)
            }else {
                data.postValue(null)
            }
        }

        return data;
    }

    fun checkMobileNo(mobileNo : String, otp : String) : MutableLiveData<LoginResponse>{
        val data : MutableLiveData<LoginResponse> = MutableLiveData()

        Coroutines.io {
            val response = retrofitClient.api.checkMobileNo(mobileNo,otp)
            if (response.isSuccessful){
                val body = response.body()
                data.postValue(body)
            }else {
                data.postValue(null)
            }
        }

        return data;
    }

    fun fetchAllComment(subCategoryName : String) : MutableLiveData<AllCommentResponse>{
        val data : MutableLiveData<AllCommentResponse> = MutableLiveData()

        Coroutines.io {
            val response = retrofitClient.api.allComments(subCategoryName)
            if (response.isSuccessful){
                val body = response.body()
                data.postValue(body)
            }else {
                data.postValue(null)
            }
        }

        return data;
    }

    fun updateUser(updateModel: UpdateModel) : MutableLiveData<Boolean>{
        val data : MutableLiveData<Boolean> = MutableLiveData()

        Coroutines.io {
            val response = retrofitClient.api.updateUser(updateModel)
            if (response.isSuccessful){
                data.postValue(true)
            }else {
                data.postValue(null)
            }
        }
        return data;
    }

    fun createOrder(orderModel: OrderModel) : MutableLiveData<Boolean>{
        val data : MutableLiveData<Boolean> = MutableLiveData()

        Coroutines.io {
            val response = retrofitClient.api.createOrder(orderModel)
            if (response.isSuccessful){
                data.postValue(true)
            }else {
                data.postValue(null)
            }
        }
        return data;
    }

    fun fetchOrder() : MutableLiveData<OrderResponse>{
        val data : MutableLiveData<OrderResponse> = MutableLiveData()

        Coroutines.io {
            val response = retrofitClient.api.fetchUserOrder(AppPrefData.user()!!.id.toString())
            if (response.isSuccessful){
                val body = response.body()
                data.postValue(body)
            }else {
                data.postValue(null)
            }
        }
        return data;
    }

}