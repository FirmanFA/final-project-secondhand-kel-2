package com.binar.secondhand.kel2.data.api.model.buyer.productid

import com.google.gson.annotations.SerializedName

data class UserProduct
    (
    @SerializedName("address")
    val address: String,
    @SerializedName("city")
    val city: Any?,
    @SerializedName("email")
    val email: String,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image_url")
    val imageUrl: Any?,
    @SerializedName("phone_number")
    val phoneNumber: String
    )