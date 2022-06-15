package com.binar.secondhand.kel2.data.api.model.buyer.orderid.get


import com.google.gson.annotations.SerializedName

data class GetOrderIdResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("product_id")
    val productId: Int,
    @SerializedName("buyer_id")
    val buyerId: Int,
    @SerializedName("price")
    val price: Int,
    @SerializedName("status")
    val status: String,
    @SerializedName("Product")
    val product: Any?,
    @SerializedName("User")
    val user: User
) {
    data class User(
        @SerializedName("full_name")
        val fullName: String,
        @SerializedName("email")
        val email: String,
        @SerializedName("phone_number")
        val phoneNumber: String,
        @SerializedName("address")
        val address: String
    )
}