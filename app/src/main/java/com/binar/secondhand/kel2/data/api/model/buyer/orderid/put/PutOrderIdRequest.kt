package com.binar.secondhand.kel2.data.api.model.buyer.orderid.put


import com.google.gson.annotations.SerializedName

data class PutOrderIdRequest(
    @SerializedName("bid_price")
    val bidPrice: Int
)