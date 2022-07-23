package com.binar.secondhand.kel2.ui.search.result

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.binar.secondhand.kel2.data.api.model.buyer.product.GetProductResponse
import com.binar.secondhand.kel2.data.local.room.model.SearchHistoryEntity
import com.binar.secondhand.kel2.data.repository.HomeRepository
import com.binar.secondhand.kel2.data.repository.SearchHistoryRepository
import com.binar.secondhand.kel2.rule.MainCoroutineRule
import com.binar.secondhand.kel2.ui.search.page.SearchPageViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito
import org.mockito.kotlin.given
import org.mockito.kotlin.mock
import retrofit2.Response

class SearchViewModelTest {

    private lateinit var viewModel: SearchViewModel
    private lateinit var repository: HomeRepository

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        repository = mock()
        viewModel = SearchViewModel(repository)
    }

    @Test
    fun getProduct()= runTest{
        val getProductResponse = mock<Response<GetProductResponse>>()

        given(repository.getProduct()).willReturn(getProductResponse)

        viewModel.getProduct()

        advanceUntilIdle()

        Mockito.verify(repository, Mockito.times(1)).getProduct()
        kotlin.test.assertNotNull(viewModel.searchResponse)
        kotlin.test.assertEquals(viewModel.searchResponse.value?.data, getProductResponse)
    }
}