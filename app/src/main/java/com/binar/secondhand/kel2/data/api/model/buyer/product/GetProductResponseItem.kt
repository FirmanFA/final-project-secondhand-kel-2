package com.binar.secondhand.kel2.data.api.model.buyer.product

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "product")
data class GetProductResponseItem(
    @PrimaryKey(autoGenerate = true)
    val productId: Int?=null,
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("description")
    val description: String?,
    @field:SerializedName("base_price")
    val basePrice: Int,
    @field:SerializedName("image_url")
    val imageUrl: String?,
    @field:SerializedName("image_name")
    val imageName: String?,
    @field:SerializedName("location")
    val location: String?,
    @field:SerializedName("user_id")
    val userId: Int,
    @field:SerializedName("status")
    val status: String,
    @field:SerializedName("createdAt")
    val createdAt: String,
    @field:SerializedName("updatedAt")
    val updatedAt: String,
    @ColumnInfo(name = "category_string")
    var categoryString:String?,

) {
    @Ignore
    @field:SerializedName("Categories")
    val categories: List<Category> = emptyList()

    data class Category(
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String
    )
}