package com.binar.secondhand.kel2.data.api.model.auth.user


import com.google.gson.annotations.SerializedName

data class PutAuthResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("phone_number")
    val phoneNumber: Long,
    @SerializedName("address")
    val address: String,
    @SerializedName("image_url")
    val imageUrl: Any?,
    @SerializedName("city")
    val city: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("updatedAt")
    val updatedAt: String
)