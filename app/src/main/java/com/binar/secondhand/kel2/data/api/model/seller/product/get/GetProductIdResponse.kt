package com.binar.secondhand.kel2.data.api.model.seller.product.get


import com.google.gson.annotations.SerializedName

data class GetProductIdResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("base_price")
    val basePrice: Int,
    @SerializedName("image_url")
    val imageUrl: Any?,
    @SerializedName("image_name")
    val imageName: Any?,
    @SerializedName("location")
    val location: String,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("status")
    val status: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("updatedAt")
    val updatedAt: String,
    @SerializedName("Categories")
    val categories: List<Any>
)