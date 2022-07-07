package com.binar.secondhand.kel2.data.repository

import com.binar.secondhand.kel2.data.local.room.model.SearchHistoryEntity
import com.binar.secondhand.kel2.data.local.room.service.DbHelper

class SearchHistoryRepository(private val dbHelper: DbHelper) {

    suspend fun getSearchHistory() = dbHelper.getSearchHistory()

    suspend fun insertSearchHistory(searchHistoryEntity: SearchHistoryEntity) =
        dbHelper.insertSearchHistory(searchHistoryEntity)

    suspend fun clearHistory(searchHistoryEntity: SearchHistoryEntity) =
        dbHelper.clearHistory(searchHistoryEntity)

    suspend fun clearAllHistory() = dbHelper.clearAllHistory()

}