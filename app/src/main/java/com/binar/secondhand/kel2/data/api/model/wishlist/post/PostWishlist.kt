package com.binar.secondhand.kel2.data.api.model.wishlist.post

import com.google.gson.annotations.SerializedName

data class PostWishlist(
    @SerializedName("name")
    val name: String,
    @SerializedName("Product")
    val product: Product
)