package com.binar.secondhand.kel2.data.api.model.buyer.productid


import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)