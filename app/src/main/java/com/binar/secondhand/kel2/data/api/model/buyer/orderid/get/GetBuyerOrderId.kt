package com.binar.secondhand.kel2.data.api.model.buyer.orderid.get

data class GetBuyerOrderId(
    val Product: Product,
    val User: UserX,
    val base_price: String,
    val buyer_id: Int,
    val id: Int,
    val image_product: Any,
    val price: Int,
    val product_id: Int,
    val product_name: String,
    val status: String,
    val transaction_date: String
)