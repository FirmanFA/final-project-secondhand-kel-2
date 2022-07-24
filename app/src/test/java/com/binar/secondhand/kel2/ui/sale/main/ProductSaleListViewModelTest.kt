package com.binar.secondhand.kel2.ui.sale.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.binar.secondhand.kel2.data.api.model.auth.user.GetAuthResponse
import com.binar.secondhand.kel2.data.api.model.seller.order.GetOrderResponse
import com.binar.secondhand.kel2.data.api.model.seller.product.get.GetSellerProductResponse
import com.binar.secondhand.kel2.data.repository.ProductSaleListRepository
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
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class ProductSaleListViewModelTest {

    private lateinit var viewModel: ProductSaleListViewModel
    private lateinit var repository: ProductSaleListRepository

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        repository = mock()
        viewModel = ProductSaleListViewModel(repository)
    }

    @Test
    fun getSellerProduct()= runTest{
        val sellerProductGetResponse = mock<Response<GetSellerProductResponse>>()

        given(repository.getSellerProduct()).willReturn(sellerProductGetResponse)

        viewModel.getSellerProduct()

        advanceUntilIdle()

        Mockito.verify(repository, Mockito.times(1)).getSellerProduct()
        kotlin.test.assertNotNull(viewModel.getSellerProductResponse)
        kotlin.test.assertEquals(viewModel.getSellerProductResponse.value?.data, sellerProductGetResponse)
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

    @Test
    fun getNotification()= runTest{
        val orderResponse = mock<Response<GetOrderResponse>>()

        given(repository.getSellerOrder()).willReturn(orderResponse)

        viewModel.getNotification()

        advanceUntilIdle()

        Mockito.verify(repository, Mockito.times(1)).getSellerOrder()
        kotlin.test.assertNotNull(viewModel.notificationResponse.value?.status)
        kotlin.test.assertEquals(viewModel.notificationResponse.value?.data, orderResponse)
    }

    @Test
    fun deleteProduct()= runTest{
        val unit = mock<Response<Unit>>()

        given(repository.deleteSellerProductId(1)).willReturn(unit)

        viewModel.deleteProduct(1)

        advanceUntilIdle()

        Mockito.verify(repository, Mockito.times(1)).deleteSellerProductId(1)
        kotlin.test.assertNotNull(viewModel.deleteProduct)
        kotlin.test.assertEquals(viewModel.deleteProduct.value?.data, unit)
    }
}