package com.binar.secondhand.kel2.data.api.model.wishlist.get

class GetWishlist : ArrayList<GetWishlistItem>(){
    data class GetWhistlistItem(
        val product: Product,
        val createdAt: String,
        val id: Int,
        val product_id: Int,
        val updatedAt: String,
        val user_id: Int
    ){
        data class Product(
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
    }
}