package com.binar.secondhand.kel2.data.api.model.buyer.productid


import com.google.gson.annotations.SerializedName

data class GetProductIdResponse(
    @SerializedName("base_price")
    val basePrice: Int,
    @SerializedName("categories")
    val categories: List<Category>,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image_name")
    val imageName: String,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("location")
    val location: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("description")
    val description: String
)