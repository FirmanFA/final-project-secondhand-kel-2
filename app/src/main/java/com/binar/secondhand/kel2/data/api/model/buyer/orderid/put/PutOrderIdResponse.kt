package com.binar.secondhand.kel2.data.api.model.buyer.orderid.put

data class PutOrderIdResponse(
    val base_price: Int,
    val buyer_id: Int,
    val createdAt: String,
    val id: Int,
    val image_product: String,
    val price: Int,
    val product_id: Int,
    val product_name: String,
    val status: String,
    val transaction_date: String,
    val updatedAt: String
)