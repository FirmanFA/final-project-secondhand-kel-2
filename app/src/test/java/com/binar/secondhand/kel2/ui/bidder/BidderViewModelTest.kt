package com.binar.secondhand.kel2.ui.bidder

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.binar.secondhand.kel2.data.api.model.seller.order.PatchSellerOrderIdRequest
import com.binar.secondhand.kel2.data.api.model.seller.order.PatchSellerOrderIdResponse
import com.binar.secondhand.kel2.data.api.model.seller.order.SellerOrderIdResponse
import com.binar.secondhand.kel2.data.api.model.seller.order.id.GetOrderIdResponse
import com.binar.secondhand.kel2.data.api.model.seller.product.id.patch.PatchProductId
import com.binar.secondhand.kel2.data.api.model.seller.product.id.patch.PatchProductIdRequest
import com.binar.secondhand.kel2.data.repository.Repository
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
class BidderViewModelTest {

    private lateinit var viewModel: BidderViewModel
    private lateinit var repository: Repository

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        repository = mock()
        viewModel = BidderViewModel(repository)
    }

    @Test
    fun bidder()= runTest{
        val sellerOrderIdGetResponse = mock<Response<SellerOrderIdResponse>>()

        given(repository.getSellerOrderId(1)).willReturn(sellerOrderIdGetResponse)

        viewModel.bidder(1)

        advanceUntilIdle()

        Mockito.verify(repository, Mockito.times(1)).getSellerOrderId(1)
        kotlin.test.assertNotNull(viewModel.bidder)
        kotlin.test.assertEquals(viewModel.bidder.value?.data, sellerOrderIdGetResponse)
    }

    @Test
    fun getOrderProductId()= runTest{
        val orderIdGetResponse = mock<Response<GetOrderIdResponse>>()

        given(repository.getOrderProductId(1)).willReturn(orderIdGetResponse)

        viewModel.getOrderProductId(1)

        advanceUntilIdle()

        Mockito.verify(repository, Mockito.times(1)).getOrderProductId(1)
        kotlin.test.assertNotNull(viewModel.bidderProduct)
        kotlin.test.assertEquals(viewModel.bidderProduct.value?.data, orderIdGetResponse)
    }

    @Test
    fun statusItem()= runTest{
        val patchSellerOrderIdGetResponse = mock<Response<PatchSellerOrderIdResponse>>()

        val request = PatchSellerOrderIdRequest("status")

        given(repository.patchSellerOrderId(1,request)).willReturn(patchSellerOrderIdGetResponse)

        viewModel.statusItem(1, request)

        advanceUntilIdle()

        Mockito.verify(repository, Mockito.times(1)).patchSellerOrderId(1,request)
        kotlin.test.assertNotNull(viewModel.status)
        kotlin.test.assertEquals(viewModel.status.value?.data, patchSellerOrderIdGetResponse)
    }

    @Test
    fun statusProduct()= runTest{
        val patchProductIdGetResponse = mock<Response<PatchProductId>>()

        val request = PatchProductIdRequest("status")

        given(repository.patchSellerProductId(1, request)).willReturn(patchProductIdGetResponse)

        viewModel.statusProduct(1, request)

        advanceUntilIdle()

        Mockito.verify(repository, Mockito.times(1)).patchSellerProductId(1,request)
        kotlin.test.assertNotNull(viewModel.statusProduct)
        kotlin.test.assertEquals(viewModel.statusProduct.value?.data, patchProductIdGetResponse)
    }
}