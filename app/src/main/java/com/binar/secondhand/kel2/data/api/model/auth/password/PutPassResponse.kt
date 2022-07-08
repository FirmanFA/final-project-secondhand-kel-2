package com.binar.secondhand.kel2.data.api.model.auth.password


import com.google.gson.annotations.SerializedName

data class PutPassResponse(
    @SerializedName("name")
    val name: String,
    @SerializedName("message")
    val message: String
)