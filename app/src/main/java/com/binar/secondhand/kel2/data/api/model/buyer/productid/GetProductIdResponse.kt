package com.binar.secondhand.kel2.data.api.model.buyer.productid


import com.google.gson.annotations.SerializedName

data class GetProductIdResponse(
    @SerializedName("base_price")
    val basePrice: Int,
    @SerializedName("Categories")
    val categories: List<Category>,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("description")
    val description: Any?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image_name")
    val imageName: Any?,
    @SerializedName("image_url")
    val imageUrl: Any?,
    @SerializedName("location")
    val location: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("status")
    val status: Any?,
    @SerializedName("updatedAt")
    val updatedAt: String,
    @SerializedName("User")
    val user: UserProduct,
    @SerializedName("user_id")
    val userId: Int
)