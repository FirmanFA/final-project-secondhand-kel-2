package com.binar.secondhand.kel2.data.local.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeys(
    @PrimaryKey val productId: Int,
    val prevKey: Int?,
    val nextKey: Int?
)