package com.binar.secondhand.kel2.data.api.model.seller.banner.post

import com.google.gson.annotations.SerializedName

data class PostBannerRequest(
    @SerializedName("name")
    val name: String,

    @SerializedName("image")
    val image: String,
)
