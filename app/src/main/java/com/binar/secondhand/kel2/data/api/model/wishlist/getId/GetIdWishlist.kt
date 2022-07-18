package com.binar.secondhand.kel2.data.api.model.wishlist.getId

data class GetIdWishlist(
    val Product: Product,
    val createdAt: String,
    val id: Int,
    val product_id: Int,
    val updatedAt: String,
    val user_id: Int
)