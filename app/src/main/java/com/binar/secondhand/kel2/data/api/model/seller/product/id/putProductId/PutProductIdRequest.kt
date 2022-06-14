package com.binar.secondhand.kel2.data.api.model.seller.product.id.putProductId

import com.google.gson.annotations.SerializedName

data class PutProductIdRequest(
    @SerializedName("name")
    val name: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("base_price")
    val basePrice: Int,

    @SerializedName("category_ids")
    val categoryIds: List<Int>,

    @SerializedName("location")
    val location: String,

    @SerializedName("image")
    val image: String
)
