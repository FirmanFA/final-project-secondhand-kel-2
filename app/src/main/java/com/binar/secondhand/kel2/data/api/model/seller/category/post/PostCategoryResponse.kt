package com.binar.secondhand.kel2.data.api.model.seller.category.post


import com.google.gson.annotations.SerializedName

data class PostCategoryResponse(
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("updatedAt")
    val updatedAt: String
)