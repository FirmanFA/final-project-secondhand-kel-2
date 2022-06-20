package com.binar.secondhand.kel2.data.api.model.auth.user

import com.google.gson.annotations.SerializedName

data class PutAuthRequest(
    @SerializedName("full_name")
    val fullName: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    val password: String,

    @SerializedName("phone_number")
    val phoneNumber: Int,

    @SerializedName("address")
    val address: String,

    @SerializedName("image")
    val image: String,

    @SerializedName("city")
    val city: String
)
