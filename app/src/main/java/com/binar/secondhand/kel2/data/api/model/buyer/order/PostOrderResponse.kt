package com.binar.secondhand.kel2.data.api.model.buyer.order


import com.google.gson.annotations.SerializedName

data class PostOrderResponse(
    @SerializedName("buyer_id")
    val buyerId: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("product_id")
    val productId: Int,
    @SerializedName("status")
    val status: String,
    @SerializedName("updated_at")
    val updatedAt: String
)