package com.binar.secondhand.kel2.data.local.room.service

import com.binar.secondhand.kel2.data.local.room.model.SearchHistoryEntity

class DbHelper(private val searchHistoryDao: SearchHistoryDao) {

    suspend fun getSearchHistory() = searchHistoryDao.getSearchHistory()

    suspend fun insertSearchHistory(searchHistoryEntity: SearchHistoryEntity) =
        searchHistoryDao.insertSearchHistory(searchHistoryEntity)

    suspend fun clearHistory(searchHistoryEntity: SearchHistoryEntity) =
        searchHistoryDao.clearHistory(searchHistoryEntity)

    suspend fun clearAllHistory() = searchHistoryDao.clearAllHistory()

}