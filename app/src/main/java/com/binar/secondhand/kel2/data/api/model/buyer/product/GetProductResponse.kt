package com.binar.secondhand.kel2.data.api.model.buyer.product


import com.google.gson.annotations.SerializedName

data class GetProductResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("per_page")
    val perPage: Int,
    @SerializedName("data")
    val productResponseItem: List<GetProductResponseItem> = emptyList(),
    val nextPage: Int? = null
)