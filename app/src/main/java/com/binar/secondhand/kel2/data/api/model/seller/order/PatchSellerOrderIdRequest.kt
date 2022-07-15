package com.binar.secondhand.kel2.data.api.model.seller.order


import com.google.gson.annotations.SerializedName

data class PatchSellerOrderIdRequest(
    @SerializedName("status")
    val status: String
)