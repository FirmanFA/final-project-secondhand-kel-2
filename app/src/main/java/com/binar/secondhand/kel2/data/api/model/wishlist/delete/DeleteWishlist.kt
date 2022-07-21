package com.binar.secondhand.kel2.data.api.model.wishlist.delete

import com.google.gson.annotations.SerializedName

data class DeleteWishlist(
    val message: String,
    @SerializedName("name")
    val name: String
)