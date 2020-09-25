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
        @Field("otp") otp : String
    ) : Response<LoginResponse>

    @FormUrlEncoded
    @POST("fetchAllComments.php")
    suspend fun allComments(
        @Field("subCategoryName") subCategoryName : String
    ) : Response<AllCommentResponse>

    @POST("registeration.php")
    suspend fun updateUser(
        @Body updateModel: UpdateModel
    ) : Response<ResponseBody>

    @POST("proceed_order.php")
    suspend fun createOrder(
        @Body orderModel: OrderModel
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
    @POST("fetchCurrentBalance.php")
    suspend fun currentBalance(
        @Field("userId") userId : String
    ) : Response<String>

    @FormUrlEncoded
    @POST("updateUserBalance.php")
    suspend fun updateBalance(
        @Field("userId") userId : String,
        @Field("amount") amount : String
    ) : Response<String>

}