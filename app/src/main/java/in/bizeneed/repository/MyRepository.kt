package `in`.bizeneed.repository

import `in`.bizeneed.extras.AppPrefData
import `in`.bizeneed.extras.errorOccurred
import `in`.bizeneed.extras.noInterConnection
import `in`.bizeneed.extras.slowInternetConnection
import `in`.bizeneed.model.OrderModel
import `in`.bizeneed.model.UpdateModel
import `in`.bizeneed.response.*
import `in`.bizeneed.rest.RetrofitClient
import androidx.lifecycle.MutableLiveData
import `in`.bizeneed.rest.Coroutines
import android.content.Context
import android.util.Log
import com.google.gson.Gson
import okhttp3.ResponseBody
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.*

class MyRepository(var context: Context) {

    private var retrofitClient: RetrofitClient = RetrofitClient.getInstance(context)

    fun getServices(): MutableLiveData<AllServicesResponse> {
        val data: MutableLiveData<AllServicesResponse> = MutableLiveData()

        Coroutines.io {
            try {
                val response = retrofitClient.api.getServices()
                if (response.isSuccessful) {
                    val body = response.body()
                    data.postValue(body)
                } else {
                    data.postValue(null)
                }
            } catch (e: UnknownHostException) {
                data.postValue(null)
                noInterConnection()
            } catch (e: SocketTimeoutException) {
                data.postValue(null)
                slowInternetConnection()
            } catch (e: SocketException) {
                data.postValue(null)
                errorOccurred()
            }
        }

        return data
    }

    fun getBanners(slot: String): MutableLiveData<BannerResponse> {
        val data: MutableLiveData<BannerResponse> = MutableLiveData()

        Coroutines.io {
            try {
                val response = retrofitClient.api.getBanners(slot)
                if (response.isSuccessful) {
                    val body = response.body()
                    data.postValue(body)
                } else {
                    data.postValue(null)
                }
            } catch (e: UnknownHostException) {
                data.postValue(null)
                noInterConnection()
            } catch (e: SocketTimeoutException) {
                data.postValue(null)
                slowInternetConnection()
            } catch (e: SocketException) {
                data.postValue(null)
                errorOccurred()
            }
        }

        return data
    }

    fun getAllCategory(serviceName: String): MutableLiveData<CategoryResponse> {
        val data: MutableLiveData<CategoryResponse> = MutableLiveData()

        Coroutines.io {
            try {
                val response = retrofitClient.api.getAllCategory(serviceName)
                if (response.isSuccessful) {
                    val body = response.body()
                    data.postValue(body)
                } else {
                    data.postValue(null)
                }
            } catch (e: UnknownHostException) {
                data.postValue(null)
                noInterConnection()
            } catch (e: SocketTimeoutException) {
                data.postValue(null)
                slowInternetConnection()
            } catch (e: SocketException) {
                data.postValue(null)
                errorOccurred()
            }
        }

        return data
    }

    fun checkMobileNo(
        mobileNo: String,
        otp: String,
        token: String
    ): MutableLiveData<LoginResponse> {
        val data: MutableLiveData<LoginResponse> = MutableLiveData()

        Coroutines.io {
            try {
                val response =
                    retrofitClient.api.checkMobileNo(mobileNo, otp, token.replace("\"", ""))
                if (response.isSuccessful) {
                    val body = response.body()
                    data.postValue(body)
                } else {
                    data.postValue(null)
                }
            } catch (e: UnknownHostException) {
                data.postValue(null)
                noInterConnection()
            } catch (e: SocketTimeoutException) {
                data.postValue(null)
                slowInternetConnection()
            } catch (e: SocketException) {
                data.postValue(null)
                errorOccurred()
            }
        }

        return data
    }

    fun fetchAllComment(subCategoryName: String): MutableLiveData<AllCommentResponse> {
        val data: MutableLiveData<AllCommentResponse> = MutableLiveData()

        Coroutines.io {
            try {
                val response = retrofitClient.api.allComments(subCategoryName)
                if (response.isSuccessful) {
                    val body = response.body()
                    data.postValue(body)
                } else {
                    data.postValue(null)
                }
            } catch (e: UnknownHostException) {
                data.postValue(null)
                noInterConnection()
            } catch (e: SocketTimeoutException) {
                data.postValue(null)
                slowInternetConnection()
            } catch (e: SocketException) {
                data.postValue(null)
                errorOccurred()
            }
        }

        return data
    }

    fun updateToken(token: String): MutableLiveData<Boolean> {
        val data: MutableLiveData<Boolean> = MutableLiveData()

        Coroutines.io {
            try {
                val response = retrofitClient.api.updateToken(
                    AppPrefData.user()!!.id.toString(),
                    token.replace("\"", "")
                )
                if (response.isSuccessful) {
                    val body = response.body()?.string()
                    if (body != null){
                        val it = body.toString()
                        if (it.toLowerCase(Locale.getDefault()) == "token updated"){
                            data.postValue(true)
                        }else{
                            data.postValue(false)
                        }
                    }else{
                        data.postValue(false)
                    }
                } else {
                    data.postValue(false)
                }
            } catch (e: UnknownHostException) {
                data.postValue(null)
                noInterConnection()
            } catch (e: SocketTimeoutException) {
                data.postValue(null)
                slowInternetConnection()
            } catch (e: SocketException) {
                data.postValue(null)
                errorOccurred()
            }
        }

        return data
    }

    fun cancelOrder(
        orderId: String,
        name: String,
        amount: String,
        subCategoryName: String
    ): MutableLiveData<CancelOrderResponse> {
        val data: MutableLiveData<CancelOrderResponse> = MutableLiveData()

        Coroutines.io {
            try {
                val response =
                    retrofitClient.api.cancelOrder(orderId, name, amount, subCategoryName)
                if (response.isSuccessful) {
                    val body = response.body()
                    data.postValue(body)
                } else {
                    data.postValue(null)
                }
            } catch (e: UnknownHostException) {
                data.postValue(null)
                noInterConnection()
            } catch (e: SocketTimeoutException) {
                data.postValue(null)
                slowInternetConnection()
            } catch (e: SocketException) {
                data.postValue(null)
                errorOccurred()
            }
        }

        return data
    }

    fun fetchWalletHistory(): MutableLiveData<WalletHistoryResponse> {
        val data: MutableLiveData<WalletHistoryResponse> = MutableLiveData()

        Coroutines.io {
            try {
                val response =
                    retrofitClient.api.fetchWalletHistory(AppPrefData.user()!!.id.toString())
                if (response.isSuccessful) {
                    val body = response.body()
                    data.postValue(body)
                } else {
                    data.postValue(null)
                }
            } catch (e: UnknownHostException) {
                data.postValue(null)
                noInterConnection()
            } catch (e: SocketTimeoutException) {
                data.postValue(null)
                slowInternetConnection()
            } catch (e: SocketException) {
                data.postValue(null)
                errorOccurred()
            }
        }

        return data
    }

    fun checkReferAll(couponCode: String): MutableLiveData<CheckReferResponse> {
        val data: MutableLiveData<CheckReferResponse> = MutableLiveData()

        Coroutines.io {
            try {
                val response =
                    retrofitClient.api.checkReferAll(couponCode, AppPrefData.user()!!.id.toString())
                if (response.isSuccessful) {
                    val body = response.body()
                    data.postValue(body)
                } else {
                    data.postValue(null)
                }
            } catch (e: UnknownHostException) {
                data.postValue(null)
                noInterConnection()
            } catch (e: SocketTimeoutException) {
                data.postValue(null)
                slowInternetConnection()
            } catch (e: SocketException) {
                data.postValue(null)
                errorOccurred()
            }
        }

        return data
    }

    fun updateUser(updateModel: UpdateModel, newUser: User): MutableLiveData<Boolean> {
        val data: MutableLiveData<Boolean> = MutableLiveData()
        Coroutines.io {
            try {
                val response = retrofitClient.api.updateUser(
                    updateModel.name,
                    updateModel.email,
                    updateModel.address,
                    updateModel.city,
                    updateModel.state,
                    updateModel.pincode,
                    updateModel.id,
                    updateModel.profile,
                    updateModel.referCode
                )
                if (response.isSuccessful) {
                    val body = response.body()?.string()

                    if (body != null) {
                        val it = body.toString()
                        var imageName = ""
                        if (it.contains(",")) {
                            imageName = it.substringBefore(",")
                            if (!imageName.toLowerCase(Locale.getDefault()).equals("no")) {
                                newUser.profile = imageName
                                AppPrefData.user(newUser)
                            } else {
                                AppPrefData.user(newUser)
                            }
                        } else {
                            AppPrefData.user(newUser)
                        }
                    } else {
                        AppPrefData.user(newUser)
                    }
                    data.postValue(true)
                } else {
                    data.postValue(null)
                }
            } catch (e: UnknownHostException) {
                data.postValue(null)
                noInterConnection()
            } catch (e: SocketTimeoutException) {
                data.postValue(null)
                slowInternetConnection()
            } catch (e: SocketException) {
                data.postValue(null)
                errorOccurred()
            }
        }
        return data
    }

    fun createOrder(orderModel: OrderModel): MutableLiveData<Boolean> {
        val data: MutableLiveData<Boolean> = MutableLiveData()

        Coroutines.io {
            try {
                val response = retrofitClient.api.createOrder(
                    orderModel.orderId,
                    orderModel.userId,
                    orderModel.name,
                    orderModel.address,
                    orderModel.state,
                    orderModel.mobile,
                    orderModel.email,
                    orderModel.totalPrice,
                    orderModel.timeOfOrder,
                    orderModel.dateOfOrder,
                    orderModel.subCategoryName,
                    orderModel.paymentType,
                    orderModel.cashBack
                )
                if (response.isSuccessful) {
                    data.postValue(true)
                } else {
                    data.postValue(null)
                }
            } catch (e: UnknownHostException) {
                data.postValue(null)
                noInterConnection()
            } catch (e: SocketTimeoutException) {
                data.postValue(null)
                slowInternetConnection()
            } catch (e: SocketException) {
                data.postValue(null)
                errorOccurred()
            }
        }
        return data
    }

    fun fetchOrder(): MutableLiveData<OrderResponse> {
        val data: MutableLiveData<OrderResponse> = MutableLiveData()

        Coroutines.io {
            try {
                val response = retrofitClient.api.fetchUserOrder(AppPrefData.user()!!.id.toString())
                if (response.isSuccessful) {
                    val body = response.body()
                    data.postValue(body)
                } else {
                    data.postValue(null)
                }
            } catch (e: UnknownHostException) {
                data.postValue(null)
                noInterConnection()
            } catch (e: SocketTimeoutException) {
                data.postValue(null)
                slowInternetConnection()
            } catch (e: SocketException) {
                data.postValue(null)
                errorOccurred()
            }
        }
        return data
    }

    fun fetchCoupons(subCategoryName: String): MutableLiveData<CouponResponse> {
        val data: MutableLiveData<CouponResponse> = MutableLiveData()

        Coroutines.io {
            try {
                val response = retrofitClient.api.fetchCoupons(subCategoryName)
                if (response.isSuccessful) {
                    val body = response.body()
                    data.postValue(body)
                } else {
                    data.postValue(null)
                }
            } catch (e: UnknownHostException) {
                data.postValue(null)
                noInterConnection()
            } catch (e: SocketTimeoutException) {
                data.postValue(null)
                slowInternetConnection()
            } catch (e: SocketException) {
                data.postValue(null)
                errorOccurred()
            }
        }
        return data
    }

    fun checkPromoCode(
        subCategoryName: String,
        promoCode: String
    ): MutableLiveData<CheckPromoCodeResponse> {
        val data: MutableLiveData<CheckPromoCodeResponse> = MutableLiveData()

        Coroutines.io {
            try {
                val response = retrofitClient.api.checkPromoCode(
                    subCategoryName,
                    promoCode.toUpperCase(Locale.getDefault())
                )
                if (response.isSuccessful) {
                    val body = response.body()
                    data.postValue(body)
                } else {
                    data.postValue(null)
                }
            } catch (e: UnknownHostException) {
                data.postValue(null)
                noInterConnection()
            } catch (e: SocketTimeoutException) {
                data.postValue(null)
                slowInternetConnection()
            } catch (e: SocketException) {
                data.postValue(null)
                errorOccurred()
            }
        }
        return data
    }

    fun checkReferalNo(
        referalNo: String,
        name: String
    ): MutableLiveData<CheckPromoCodeResponse> {
        val data: MutableLiveData<CheckPromoCodeResponse> = MutableLiveData()

        Coroutines.io {
            try {
                val response = retrofitClient.api.checkReferralNo(
                    referalNo.toUpperCase(Locale.getDefault()),
                    name,
                    AppPrefData.user()!!.id.toString()
                )
                if (response.isSuccessful) {
                    val body = response.body()
                    data.postValue(body)
                } else {
                    data.postValue(null)
                }
            } catch (e: UnknownHostException) {
                data.postValue(null)
                noInterConnection()
            } catch (e: SocketTimeoutException) {
                data.postValue(null)
                slowInternetConnection()
            } catch (e: SocketException) {
                data.postValue(null)
                errorOccurred()
            }
        }
        return data
    }

    fun sendFeedBack(
        feedBack: String
    ): MutableLiveData<ResponseBody> {
        val data: MutableLiveData<ResponseBody> = MutableLiveData()

        Coroutines.io {
            try {
                val response = retrofitClient.api.sendFeedBack(
                    AppPrefData.user()!!.name!!,
                    feedBack,
                    AppPrefData.user()!!.mobile
                )
                if (response.isSuccessful) {
                    val body = response.body()
                    data.postValue(body)
                } else {
                    data.postValue(null)
                }
            } catch (e: UnknownHostException) {
                data.postValue(null)
                noInterConnection()
            } catch (e: SocketTimeoutException) {
                data.postValue(null)
                slowInternetConnection()
            } catch (e: SocketException) {
                data.postValue(null)
                errorOccurred()
            }
        }
        return data
    }

    fun currentBalance(): MutableLiveData<String> {
        val data: MutableLiveData<String> = MutableLiveData()

        Coroutines.io {
            try {
                val response = retrofitClient.api.currentBalance(AppPrefData.user()!!.id.toString())
                if (response.isSuccessful) {
                    val body = response.body()
                    data.postValue(body)
                } else {
                    data.postValue(null)
                }
            } catch (e: UnknownHostException) {
                data.postValue(null)
                noInterConnection()
            } catch (e: SocketTimeoutException) {
                data.postValue(null)
                slowInternetConnection()
            } catch (e: SocketException) {
                data.postValue(null)
                errorOccurred()
            }
        }
        return data
    }

    fun updateBalance(amount: String): MutableLiveData<String> {
        val data: MutableLiveData<String> = MutableLiveData()

        Coroutines.io {
            try {
                val response =
                    retrofitClient.api.updateBalance(AppPrefData.user()!!.id.toString(), amount)
                if (response.isSuccessful) {
                    val body = response.body()?.string()
                    if (body != null){
                        data.postValue(body.toString())
                    }else{
                        data.postValue(null)
                    }
                } else {
                    data.postValue(null)
                }
            } catch (e: UnknownHostException) {
                data.postValue(null)
                noInterConnection()
            } catch (e: SocketTimeoutException) {
                data.postValue(null)
                slowInternetConnection()
            } catch (e: SocketException) {
                data.postValue(null)
                errorOccurred()
            }
        }
        return data
    }

}