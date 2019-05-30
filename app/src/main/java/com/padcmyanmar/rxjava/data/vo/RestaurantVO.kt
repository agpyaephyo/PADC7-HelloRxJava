package com.padcmyanmar.rxjava.data.vo

import com.google.gson.annotations.SerializedName

data class RestaurantVO(
    @SerializedName("title") val title: String,
    @SerializedName("image") val image: String,
    @SerializedName("addr-short") val shortAddress: String,
    @SerializedName("total-reating-count") val totalRatingCount: Int,
    @SerializedName("average-rating-value") val averageRatingValue: Double,
    @SerializedName("is-ad") val isAd: Boolean,
    @SerializedName("is-new") val isNew: Boolean,
    @SerializedName("lead-time-in-min") val leadTimeInMin: Int,
    @SerializedName("tags") val tagList: List<String>,
    @SerializedName("most-popular")  val mostPopular: String
)