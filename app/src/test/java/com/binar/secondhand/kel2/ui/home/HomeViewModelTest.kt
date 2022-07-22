package com.binar.secondhand.kel2.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import com.binar.secondhand.kel2.data.api.model.auth.user.GetAuthResponse
import com.binar.secondhand.kel2.data.api.model.buyer.product.GetProductResponse
import com.binar.secondhand.kel2.data.api.model.buyer.product.GetProductResponseItem
import com.binar.secondhand.kel2.data.api.model.seller.banner.get.GetBannerResponse
import com.binar.secondhand.kel2.data.api.model.seller.category.get.GetCategoryResponse
import com.binar.secondhand.kel2.data.api.model.seller.order.id.GetOrderIdResponse
import com.binar.secondhand.kel2.data.repository.HomeRepository
import com.binar.secondhand.kel2.data.repository.Repository
import com.binar.secondhand.kel2.rule.MainCoroutineRule
import com.binar.secondhand.kel2.ui.edit.EditViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
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

class HomeViewModelTest {

    private lateinit var viewModel: HomeViewModel
    private lateinit var repository: HomeRepository

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        repository = mock()
        viewModel = HomeViewModel(repository)
    }

    @Test
    fun getProducts()= runTest{
        val getProductResponseItem = mock<Flow<PagingData<GetProductResponseItem>>>()

        given(repository.getProductStream()).willReturn(getProductResponseItem)

        viewModel.getProducts()

        advanceUntilIdle()

        Mockito.verify(repository, Mockito.times(1)).getProductStream()
    }

    @Test
    fun getHomeProduct()= runTest{
        val productGetResponse = mock<Response<GetProductResponse>>()

        given(repository.getProduct()).willReturn(productGetResponse)

        viewModel.getHomeProduct()

        advanceUntilIdle()

        Mockito.verify(repository, Mockito.times(1)).getProduct()
        kotlin.test.assertNotNull(viewModel.getHomeProductResponse)
        kotlin.test.assertEquals(viewModel.getHomeProductResponse.value?.data, productGetResponse)
    }

    @Test
    fun getHomeBanner()= runTest{
        val bannerGetResponse = mock<Response<GetBannerResponse>>()

        given(repository.getBanner()).willReturn(bannerGetResponse)

        viewModel.getHomeBanner()

        advanceUntilIdle()

        Mockito.verify(repository, Mockito.times(1)).getBanner()
        kotlin.test.assertNotNull(viewModel.getBannerResponse)
        kotlin.test.assertEquals(viewModel.getBannerResponse.value?.data, bannerGetResponse)
    }

    @Test
    fun getHomeCategory()= runTest{
        val categoryGetResponse = mock<Response<GetCategoryResponse>>()

        given(repository.getCategory()).willReturn(categoryGetResponse)

        viewModel.getHomeCategory()

        advanceUntilIdle()

        Mockito.verify(repository, Mockito.times(1)).getCategory()
        kotlin.test.assertNotNull(viewModel.getCategoryResponse)
        kotlin.test.assertEquals(viewModel.getCategoryResponse.value?.data, categoryGetResponse)
    }

    @Test
    fun getAuth()= runTest{
        val authGetResponse = mock<Response<GetAuthResponse>>()

        given(repository.getAuth()).willReturn(authGetResponse)

        viewModel.getAuth()

        advanceUntilIdle()

        Mockito.verify(repository, Mockito.times(1)).getAuth()
        kotlin.test.assertNotNull(viewModel.authGetResponse)
        kotlin.test.assertEquals(viewModel.authGetResponse.value?.data, authGetResponse)
    }
}