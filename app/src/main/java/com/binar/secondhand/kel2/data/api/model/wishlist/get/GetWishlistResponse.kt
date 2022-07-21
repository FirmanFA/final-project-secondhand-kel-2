package com.binar.secondhand.kel2.data.api.model.wishlist.get


import com.google.gson.annotations.SerializedName

data class GetWishlistResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("Product")
    val product: Product,
    @SerializedName("product_id")
    val productId: Int,
    @SerializedName("user_id")
    val userId: Int
)
