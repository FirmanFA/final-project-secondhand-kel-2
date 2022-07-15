package com.binar.secondhand.kel2.data.repository

import com.binar.secondhand.kel2.data.api.model.seller.product.get.GetSellerProductResponse
import com.binar.secondhand.kel2.data.api.service.ApiHelper
import com.binar.secondhand.kel2.data.api.service.ApiService
import com.binar.secondhand.kel2.data.local.room.model.SearchHistoryEntity
import com.binar.secondhand.kel2.data.local.room.service.DbHelper
import com.binar.secondhand.kel2.data.local.room.service.SearchHistoryDao
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import retrofit2.Response

class SearchHistoryRepositoryTest {

    //collaborator
    private lateinit var dbHelper: DbHelper

    //system under test
    private lateinit var searchHistoryRepository: SearchHistoryRepository

    @Before
    fun setUp() {
        dbHelper = mockk()

        searchHistoryRepository = SearchHistoryRepository(dbHelper)
    }

    @Test
    fun getSearchHistory(): Unit = runBlocking {
        val searchHistoryEntity = mockk<List<SearchHistoryEntity>>()

        every {
            runBlocking {
                dbHelper.getSearchHistory()
            }
        } returns searchHistoryEntity

        searchHistoryRepository.getSearchHistory()

        verify {
            runBlocking {
                dbHelper.getSearchHistory()
            }
        }
    }

    @Test
    fun insertSearchHistory(): Unit = runBlocking {
        val insertSearchHistory = 1L

        val searchHistoryEntity = SearchHistoryEntity(
            1,
            "baju"
        )

        every {
            runBlocking {
                dbHelper.insertSearchHistory(searchHistoryEntity)
            }
        } returns insertSearchHistory

        searchHistoryRepository.insertSearchHistory(searchHistoryEntity)

        verify {
            runBlocking {
                dbHelper.insertSearchHistory(searchHistoryEntity)
            }
        }
    }

    @Test
    fun clearHistory(): Unit = runBlocking {
        val clearHistory = 1

        val searchHistoryEntity = SearchHistoryEntity(
            1,
            "baju"
        )

        every {
            runBlocking {
                dbHelper.clearHistory(searchHistoryEntity)
            }
        } returns clearHistory

        searchHistoryRepository.clearHistory(searchHistoryEntity)

        verify {
            runBlocking {
                dbHelper.clearHistory(searchHistoryEntity)
            }
        }
    }

    @Test
    fun clearAllHistory(): Unit = runBlocking {
        val clearAllHistory = 1

        every {
            runBlocking {
                dbHelper.clearAllHistory()
            }
        } returns clearAllHistory

        searchHistoryRepository.clearAllHistory()

        verify {
            runBlocking {
                dbHelper.clearAllHistory()
            }
        }
    }
}