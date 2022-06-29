package com.binar.secondhand.kel2.data.local.room.database

import androidx.room.*
import com.binar.secondhand.kel2.data.local.room.model.FavouriteEntity

@Dao
interface FavouriteDao {
    @Query("SELECT * FROM FavouriteEntity")
    suspend fun readFavorites(): List<FavouriteEntity>

    @Query("SELECT * FROM FavouriteEntity WHERE id=:id")
    fun readFavoriteById(id: Int): FavouriteEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(favorite: FavouriteEntity):Long

    @Delete
    fun deleteFavorite(favorite: FavouriteEntity):Int
}