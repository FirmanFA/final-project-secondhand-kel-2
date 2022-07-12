package com.binar.secondhand.kel2.data.api.model.seller.order


import com.google.gson.annotations.SerializedName

data class PatchSellerOrderIdResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("product_id")
    val productId: Int,
    @SerializedName("buyer_id")
    val buyerId: Int,
    @SerializedName("price")
    val price: Int,
    @SerializedName("transcaction_date")
    val transcactionDate: String,
    @SerializedName("product_name")
    val productName: String,
    @SerializedName("base_price")
    val basePrice: Int,
    @SerializedName("image_product")
    val imageProduct: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("updatedAt")
    val updatedAt: String
)