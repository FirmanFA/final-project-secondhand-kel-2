package com.binar.secondhand.kel2.data.api.model.seller.order


import com.google.gson.annotations.SerializedName

class GetOrderResponse : ArrayList<GetOrderResponse.GetOrderResponseItem>(){
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
        @SerializedName("createdAt")
        val createdAt: String,
        @SerializedName("updatedAt")
        val updatedAt: String,
        @SerializedName("Product")
        val product: Product,
        @SerializedName("User")
        val user: User
    ) {
        data class Product(
            @SerializedName("name")
            val name: String,
            @SerializedName("description")
            val description: String,
            @SerializedName("base_price")
            val basePrice: Int,
            @SerializedName("image_url")
            val imageUrl: String,
            @SerializedName("image_name")
            val imageName: String,
            @SerializedName("location")
            val location: String,
            @SerializedName("user_id")
            val userId: Int,
            @SerializedName("status")
            val status: String
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