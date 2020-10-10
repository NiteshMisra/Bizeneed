package `in`.bizeneed.rest

import `in`.bizeneed.model.OrderModel
import `in`.bizeneed.model.UpdateModel
import `in`.bizeneed.response.*
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface Api {

    @GET("fetchAllServices.php")
    suspend fun getServices() : Response<AllServicesResponse>

    @FormUrlEncoded
    @POST("fetchBanner.php")
    suspend fun getBanners(
        @Field("slot") slot : String
    ) : Response<BannerResponse>

    @FormUrlEncoded
    @POST("fetchAllCategory.php")
    suspend fun getAllCategory(
        @Field("serviceName") serviceName : String
    ) : Response<CategoryResponse>

    @FormUrlEncoded
    @POST("checkMobileNo.php")
    suspend fun checkMobileNo(
        @Field("mobileNo") mobileNo : String,
        @Field("otp") otp : String,
        @Field("token") token : String
    ) : Response<LoginResponse>

    @FormUrlEncoded
    @POST("fetchAllComments.php")
    suspend fun allComments(
        @Field("subCategoryName") subCategoryName : String
    ) : Response<AllCommentResponse>

    @FormUrlEncoded
    @POST("updateToken.php")
    suspend fun updateToken(
        @Field("userId") userId : String,
        @Field("token") token : String
    ) : Response<ResponseBody>

    @FormUrlEncoded
    @POST("cancelOrder.php")
    suspend fun cancelOrder(
        @Field("orderId") orderId : String,
        @Field("name") name : String,
        @Field("amount") amount : String,
        @Field("subCategoryName") subCategoryName : String
    ) : Response<CancelOrderResponse>

    @FormUrlEncoded
    @POST("fetchAllTransaction.php")
    suspend fun fetchWalletHistory(
        @Field("userId") id : String
    ) : Response<WalletHistoryResponse>

    @FormUrlEncoded
    @POST("checkWalletReferall.php")
    suspend fun checkReferAll(
        @Field("promoCode") promoCode : String,
        @Field("userId") userId : String
    ) : Response<CheckReferResponse>

    @FormUrlEncoded
    @POST("registeration.php")
    suspend fun updateUser(
        @Field("name") name : String,
        @Field("email") email : String,
        @Field("address") address : String,
        @Field("city") city : String,
        @Field("state") state : String,
        @Field("pincode") pincode : String,
        @Field("id") id : String,
        @Field("profile") profile : String,
        @Field("referalNo") referalNo : String
    ) : Response<ResponseBody>

    @FormUrlEncoded
    @POST("proceed_order.php")
    suspend fun createOrder(
        @Field("orderId") orderId: String,
        @Field("userId") userId: String,
        @Field("name") name: String,
        @Field("address") address: String,
        @Field("state") state: String,
        @Field("mobile") mobile: String,
        @Field("email") email: String,
        @Field("totalPrice") totalPrice: String,
        @Field("timeOfOrder") timeOfOrder: String,
        @Field("dateOfOrder") dateOfOrder: String,
        @Field("subCategoryName") subCategoryName: String,
        @Field("paymentType") paymentType: String,   // Online or Offline
        @Field("cashBack") cashBack: String
    ) : Response<ResponseBody>

    @FormUrlEncoded
    @POST("fetchUserOrder.php")
    suspend fun fetchUserOrder(
        @Field("userId") userId : String
    ) : Response<OrderResponse>

    @FormUrlEncoded
    @POST("fetchCoupon.php")
    suspend fun fetchCoupons(
        @Field("subCategoryName") subCategoryName : String
    ) : Response<CouponResponse>

    @FormUrlEncoded
    @POST("checkPromoCode.php")
    suspend fun checkPromoCode(
        @Field("subCategoryName") subCategoryName : String,
        @Field("promoCode") promoCode : String
    ) : Response<CheckPromoCodeResponse>

    @FormUrlEncoded
    @POST("checkReferralNo.php")
    suspend fun checkReferralNo(
        @Field("referalNo") referalNo : String,
        @Field("name") name : String,
        @Field("id") id : String
    ) : Response<CheckPromoCodeResponse>

    @FormUrlEncoded
    @POST("addSuggestion.php")
    suspend fun sendFeedBack(
        @Field("name") name : String,
        @Field("suggestion") suggestion : String,
        @Field("mobile") mobile : String
    ) : Response<ResponseBody>

    @FormUrlEncoded
    @POST("fetchCurrentBalance.php")
    suspend fun currentBalance(
        @Field("userId") userId : String
    ) : Response<String>

    @FormUrlEncoded
    @POST("updateUserBalance.php")
    suspend fun updateBalance(
        @Field("userId") userId : String,
        @Field("amount") amount : String
    ) : Response<ResponseBody>

}