package `in`.bizeneed.rest

import `in`.bizeneed.BuildConfig
import android.content.Context
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient {

    private val retrofit: Retrofit

    companion object {

        private var instance : RetrofitClient?= null
        private lateinit var context: Context

        @Synchronized
        fun getInstance(context1: Context): RetrofitClient {
            context = context1
            if (instance == null) {
                instance = RetrofitClient()
            }
            return instance!!
        }
    }

    val api: Api
        get() {
            return retrofit.create(Api::class.java)
        }

    init {
        val interceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG){
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()


        val gson = GsonBuilder().setLenient().create()

        retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }
}