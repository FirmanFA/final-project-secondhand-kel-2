package com.binar.secondhand.kel2.data.api.model.auth.login


import com.google.gson.annotations.SerializedName

data class PostLoginResponse(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("name")
    val name: String
)