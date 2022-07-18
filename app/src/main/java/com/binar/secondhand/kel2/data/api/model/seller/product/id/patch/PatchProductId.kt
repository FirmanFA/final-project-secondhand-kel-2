package com.binar.secondhand.kel2.data.api.model.seller.product.id.patch

data class PatchProductId(
    val base_price: Int,
    val createdAt: String,
    val description: String,
    val id: Int,
    val image_name: String,
    val image_url: String,
    val location: String,
    val name: String,
    val status: String,
    val updatedAt: String,
    val user_id: Int
)