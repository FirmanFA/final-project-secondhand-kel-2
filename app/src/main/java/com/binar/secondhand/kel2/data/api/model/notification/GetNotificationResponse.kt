package com.binar.secondhand.kel2.data.api.model.notification


import com.google.gson.annotations.SerializedName

class GetNotificationResponse : ArrayList<GetNotificationResponse.GetNotificationResponseItem>(){
    data class GetNotificationResponseItem(
        @SerializedName("id")
        val id: Int,
        @SerializedName("product_id")
        val productId: Int,
        @SerializedName("product_name")
        val productName: String,
        @SerializedName("base_price")
        val basePrice: String,
        @SerializedName("bid_price")
        val bidPrice: Int,
        @SerializedName("image_url")
        val imageUrl: String,
        @SerializedName("transaction_date")
        val transactionDate: String,
        @SerializedName("status")
        val status: String,
        @SerializedName("seller_name")
        val sellerName: String,
        @SerializedName("buyer_name")
        val buyerName: String,
        @SerializedName("receiver_id")
        val receiverId: Int,
        @SerializedName("read")
        val read: Boolean,
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
            @SerializedName("id")
            val id: Int,
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
            val status: String,
            @SerializedName("createdAt")
            val createdAt: String,
            @SerializedName("updatedAt")
            val updatedAt: String
        )
    
        data class User(
            @SerializedName("id")
            val id: Int,
            @SerializedName("full_name")
            val fullName: String,
            @SerializedName("email")
            val email: String,
            @SerializedName("phone_number")
            val phoneNumber: String,
            @SerializedName("address")
            val address: String,
            @SerializedName("image_url")
            val imageUrl: String,
            @SerializedName("city")
            val city: String
        )
    }
}