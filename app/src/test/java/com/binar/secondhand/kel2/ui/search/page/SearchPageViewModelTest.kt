package com.binar.secondhand.kel2.ui.search.page

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.binar.secondhand.kel2.data.local.room.model.SearchHistoryEntity
import com.binar.secondhand.kel2.data.repository.SearchHistoryRepository
import com.binar.secondhand.kel2.rule.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito
import org.mockito.kotlin.given
import org.mockito.kotlin.mock

@OptIn(ExperimentalCoroutinesApi::class)
class SearchPageViewModelTest {

    private lateinit var viewModel: SearchPageViewModel
    private lateinit var repository: SearchHistoryRepository

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        repository = mock()
        viewModel = SearchPageViewModel(repository)
    }

    @Test
    fun getSearchHistory()= runTest{
        val searchHistoryEntity = mock<List<SearchHistoryEntity>>()

        given(repository.getSearchHistory()).willReturn(searchHistoryEntity)

        viewModel.getSearchHistory()

        advanceUntilIdle()

        Mockito.verify(repository, Mockito.times(1)).getSearchHistory()
        kotlin.test.assertNotNull(viewModel.searchResponse)
        kotlin.test.assertEquals(viewModel.searchResponse.value?.data, searchHistoryEntity)
    }

    @Test
    fun insertSearchHistory()= runTest{

        val searchHistoryEntity = SearchHistoryEntity(
            1,
            "Pakaian"
        )

        given(repository.insertSearchHistory(searchHistoryEntity))

        viewModel.insertSearchHistory(searchHistoryEntity)

        advanceUntilIdle()

        Mockito.verify(repository, Mockito.times(1)).insertSearchHistory(searchHistoryEntity)
    }
}