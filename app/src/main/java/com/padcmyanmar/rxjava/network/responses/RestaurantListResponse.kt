package com.padcmyanmar.rxjava.network.responses

import com.google.gson.annotations.SerializedName
import com.padcmyanmar.rxjava.data.vo.RestaurantVO

data class RestaurantListResponse(
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("timestamp") val timestamp: String,
    @SerializedName("restaurants") val restaurantList: List<RestaurantVO>
)