package com.binar.secondhand.kel2.data.local.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavouriteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    val basePrice: Int,
    val imageUrl: String,
    val location: String,
    val name: String,

)
