package com.binar.secondhand.kel2.data.api.model.seller.product.post

import com.google.gson.annotations.SerializedName

data class PostProductRequest(
    @SerializedName("name")
    val name: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("base_price")
    val basePrice: Int,

    @SerializedName("category_ids")
    val category_ids: List<Int>,

    @SerializedName("location")
    val location: String,

    @SerializedName("image")
    val image: String
)
