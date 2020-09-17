package `in`.bizeneed.rest

import `in`.bizeneed.response.AllServicesResponse
import `in`.bizeneed.response.BannerResponse
import retrofit2.Response
import retrofit2.http.GET

interface Api {

    @GET("fetchAllServices.php")
    suspend fun getServices() : Response<AllServicesResponse>

    @GET("fetchBanner.php")
    suspend fun getBanners() : Response<BannerResponse>

}