package com.binar.secondhand.kel2.data.api.model.seller.product.id.patch


import com.google.gson.annotations.SerializedName

data class PatchProductIdRequest(
    @SerializedName("status")
    val status: String
)