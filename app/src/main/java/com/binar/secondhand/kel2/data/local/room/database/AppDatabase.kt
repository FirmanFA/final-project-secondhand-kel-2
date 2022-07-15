package com.binar.secondhand.kel2.data.local.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.binar.secondhand.kel2.data.api.model.buyer.product.GetProductResponseItem
import com.binar.secondhand.kel2.data.local.room.model.ExampleEntity
import com.binar.secondhand.kel2.data.local.room.model.RemoteKeys
import com.binar.secondhand.kel2.data.local.room.model.SearchHistoryEntity
import com.binar.secondhand.kel2.data.local.room.service.ProductDao
import com.binar.secondhand.kel2.data.local.room.service.RemoteKeysDao
import com.binar.secondhand.kel2.data.local.room.service.SearchHistoryDao

@Database(
    entities = [
        SearchHistoryEntity::class,
        GetProductResponseItem::class,
        RemoteKeys::class
               ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun searchHistoryDao(): SearchHistoryDao
    abstract fun productDao(): ProductDao
    abstract fun remoteKeysDao(): RemoteKeysDao

}