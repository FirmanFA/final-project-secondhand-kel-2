package com.binar.secondhand.kel2.data.api.model.seller.product.put


import com.google.gson.annotations.SerializedName

data class PutSellerProductIdResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("base_price")
    val basePrice: Int,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("image_name")
    val imageName: String,
    @SerializedName("location")
    val location: String,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String
)