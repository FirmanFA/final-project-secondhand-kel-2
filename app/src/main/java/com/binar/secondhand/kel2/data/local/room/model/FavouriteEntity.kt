package com.binar.secondhand.kel2.data.local.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.binar.secondhand.kel2.data.api.model.buyer.product.Category
import com.google.gson.annotations.SerializedName

@Entity
data class FavouriteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    val basePrice: Int,
    val categories: List<Category>?,
    val imageUrl: String,
    val location: String,
    val name: String,

)
