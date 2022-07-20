package com.binar.secondhand.kel2.data.api.model.wishlist.get

import com.google.gson.annotations.SerializedName

data class GetWishlistResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("Product")
    val product: Product,
    @SerializedName("product_id")
    val productId: Int,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("user_id")
    val userId: Int
)
data class Product(
    @SerializedName("base_price")
    val basePrice: Int,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("description")
    val description: String,
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
    val userId: Int
)