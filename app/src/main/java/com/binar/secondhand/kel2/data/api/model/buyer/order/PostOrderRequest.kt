package com.binar.secondhand.kel2.data.api.model.buyer.order


import com.google.gson.annotations.SerializedName

data class PostOrderRequest(
    @SerializedName("product_id")
    val productId: Int,
    @SerializedName("bid_price")
    val bidPrice: Int
)