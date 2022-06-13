package com.binar.secondhand.kel2.data.api.model

import com.google.gson.annotations.SerializedName

data class ExamplePostRequest (
    @SerializedName("email")
    val email: String
)