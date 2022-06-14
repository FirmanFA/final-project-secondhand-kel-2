package com.binar.secondhand.kel2.data.api.model.seller.banner.getBanner


import com.google.gson.annotations.SerializedName

data class GetBannerResponseItem(
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image_name")
    val imageName: String,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("updatedAt")
    val updatedAt: String,
    @SerializedName("user_id")
    val userId: Int
)