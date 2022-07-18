package com.binar.secondhand.kel2.data.api.model.wishlist.delete

import com.google.gson.annotations.SerializedName

data class DeleteWishlistRequest(
    @SerializedName("product_id")
    val productId: Int,
)
