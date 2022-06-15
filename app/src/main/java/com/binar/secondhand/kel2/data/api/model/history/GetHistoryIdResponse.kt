package com.binar.secondhand.kel2.data.api.model.history


import com.google.gson.annotations.SerializedName

data class GetHistoryIdResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("product_name")
    val productName: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("category")
    val category: String,
    @SerializedName("transaction_date")
    val transactionDate: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("updatedAt")
    val updatedAt: String
)