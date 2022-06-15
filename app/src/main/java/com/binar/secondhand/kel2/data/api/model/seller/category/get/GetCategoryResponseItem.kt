package com.binar.secondhand.kel2.data.api.model.seller.category.get


import com.google.gson.annotations.SerializedName

data class GetCategoryResponseItem(
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("updatedAt")
    val updatedAt: String
)