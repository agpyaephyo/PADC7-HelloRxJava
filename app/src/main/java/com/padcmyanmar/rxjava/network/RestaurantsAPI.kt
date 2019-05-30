package com.padcmyanmar.rxjava.network

import com.padcmyanmar.rxjava.RxJavaApp
import com.padcmyanmar.rxjava.network.responses.RestaurantListResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface RestaurantsAPI {

    @GET(RxJavaApp.API_GET_RESTAURANTS_V2)
    fun getRestaurantList(): Observable<RestaurantListResponse>
}