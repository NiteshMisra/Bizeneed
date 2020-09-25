package `in`.bizeneed.viewmodel

import `in`.bizeneed.model.OrderModel
import `in`.bizeneed.model.UpdateModel
import `in`.bizeneed.repository.MyRepository
import `in`.bizeneed.response.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class MyViewModel(
    private var myRepository: MyRepository
) : ViewModel() {

    fun getServices() : LiveData<AllServicesResponse>{
        return myRepository.getServices()
    }

    fun getBanners(slot : String) : LiveData<BannerResponse>{
        return myRepository.getBanners(slot)
    }

    fun getAllCategory(serviceName : String) : LiveData<CategoryResponse>{
        return myRepository.getAllCategory(serviceName)
    }

    fun checkMobileNo(mobileNo : String, otp : String) : LiveData<LoginResponse>{
        return myRepository.checkMobileNo(mobileNo,otp)
    }

    fun fetchAllComment(subCategoryName : String) : LiveData<AllCommentResponse>{
        return myRepository.fetchAllComment(subCategoryName)
    }

    fun updateUser(updateModel: UpdateModel) : LiveData<Boolean>{
        return myRepository.updateUser(updateModel)
    }

    fun createOrder(orderModel: OrderModel) : LiveData<Boolean>{
        return myRepository.createOrder(orderModel)
    }

    fun fetchOrder() : LiveData<OrderResponse>{
        return myRepository.fetchOrder()
    }

    fun fetchCoupons(subCategoryName: String) : LiveData<CouponResponse>{
        return myRepository.fetchCoupons(subCategoryName)
    }

    fun checkPromoCode(subCategoryName: String,promoCode : String) : LiveData<CheckPromoCodeResponse>{
        return myRepository.checkPromoCode(subCategoryName,promoCode)
    }

    fun currentBalance() : LiveData<String>{
        return myRepository.currentBalance()
    }

    fun updateBalance(amount : String) : LiveData<String>{
        return myRepository.updateBalance(amount)
    }

}