package com.binar.secondhand.kel2.data.api.model.wishlist.post

import com.google.gson.annotations.SerializedName

data class PostWishlistRequest(
    @SerializedName("product_id")
    val product_id : Int
)
