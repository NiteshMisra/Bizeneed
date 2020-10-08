package `in`.bizeneed.viewmodel

import `in`.bizeneed.model.OrderModel
import `in`.bizeneed.model.UpdateModel
import `in`.bizeneed.repository.MyRepository
import `in`.bizeneed.response.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import okhttp3.ResponseBody

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

    fun checkMobileNo(mobileNo : String, otp : String, token : String) : LiveData<LoginResponse>{
        return myRepository.checkMobileNo(mobileNo,otp,token)
    }

    fun cancelOrder(orderId : String, name : String, amount : String, subCategoryName : String) : LiveData<CancelOrderResponse>{
        return myRepository.cancelOrder(orderId, name, amount, subCategoryName)
    }

    fun fetchAllComment(subCategoryName : String) : LiveData<AllCommentResponse>{
        return myRepository.fetchAllComment(subCategoryName)
    }

    fun updateToken(){
        myRepository.updateToken()
    }

    fun fetchWalletHistory() : LiveData<WalletHistoryResponse>{
        return myRepository.fetchWalletHistory()
    }

    fun checkReferAll(couponCode : String) : LiveData<CheckReferResponse>{
        return myRepository.checkReferAll(couponCode)
    }

    fun updateUser(updateModel: UpdateModel, newUser : User) : LiveData<Boolean>{
        return myRepository.updateUser(updateModel,newUser)
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

    fun checkReferalNo(referNo: String, name : String) : LiveData<CheckPromoCodeResponse>{
        return myRepository.checkReferalNo(referNo,name)
    }

    fun sendFeedBack(feedBack: String) : LiveData<ResponseBody>{
        return myRepository.sendFeedBack(feedBack)
    }

    fun currentBalance() : LiveData<String>{
        return myRepository.currentBalance()
    }

    fun updateBalance(amount : String) : LiveData<String>{
        return myRepository.updateBalance(amount)
    }

}