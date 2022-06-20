package com.binar.secondhand.kel2.data.api.model.notification


import com.google.gson.annotations.SerializedName

data class PatchNotificationIdResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("product_id")
    val productId: Int,
    @SerializedName("bid_price")
    val bidPrice: Int,
    @SerializedName("transaction_date")
    val transactionDate: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("seller_name")
    val sellerName: String,
    @SerializedName("buyer_name")
    val buyerName: String,
    @SerializedName("receiver_id")
    val receiverId: Int,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("read")
    val read: Boolean,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String
)