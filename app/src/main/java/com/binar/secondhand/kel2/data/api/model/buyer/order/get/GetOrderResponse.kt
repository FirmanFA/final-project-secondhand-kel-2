package com.binar.secondhand.kel2.data.api.model.buyer.order.get


import com.google.gson.annotations.SerializedName

class GetOrderResponse : ArrayList<GetOrderResponseItem>(){
    data class GetOrderResponseItem(
        @SerializedName("id")
        val id: Int,
        @SerializedName("product_id")
        val productId: Int,
        @SerializedName("buyer_id")
        val buyerId: Int,
        @SerializedName("price")
        val price: Int,
        @SerializedName("status")
        val status: String,
        @SerializedName("Product")
        val product: Product,
        @SerializedName("User")
        val user: User
    ) {
        data class Product(
            @SerializedName("name")
            val name: String,
            @SerializedName("description")
            val description: Any?,
            @SerializedName("base_price")
            val basePrice: Int,
            @SerializedName("image_url")
            val imageUrl: Any?,
            @SerializedName("image_name")
            val imageName: Any?,
            @SerializedName("location")
            val location: String,
            @SerializedName("user_id")
            val userId: Int,
            @SerializedName("status")
            val status: Any?
        )
    
        data class User(
            @SerializedName("full_name")
            val fullName: String,
            @SerializedName("email")
            val email: String,
            @SerializedName("phone_number")
            val phoneNumber: String,
            @SerializedName("address")
            val address: String
        )
    }
}