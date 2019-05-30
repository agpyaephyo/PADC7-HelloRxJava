package com.padcmyanmar.rxjava

import android.app.Application
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.padcmyanmar.rxjava.network.RestaurantsAPI
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RxJavaApp : Application() {

    companion object {
        const val TAG = "RxJavaApp"
        const val RESTAURANTS_BASE_URL = "http://padcmyanmar.com/padc-7/padc-restaurants/"
        const val API_GET_RESTAURANTS_V2 = "get-restaurants-v2.php"

    }

    lateinit var theApi: RestaurantsAPI

    override fun onCreate() {
        super.onCreate()
        initRestaurantApi()
    }

    private fun initRestaurantApi() {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(RESTAURANTS_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()

        theApi = retrofit.create(RestaurantsAPI::class.java)
    }
}