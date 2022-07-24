package com.binar.secondhand.kel2.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.binar.secondhand.kel2.data.api.model.buyer.order.get.GetOrderResponse
import com.binar.secondhand.kel2.data.api.model.buyer.order.get.GetOrderResponse.GetOrderResponseItem
import com.binar.secondhand.kel2.data.api.model.buyer.order.post.PostOrderRequest
import com.binar.secondhand.kel2.data.api.model.buyer.order.post.PostOrderResponse
import com.binar.secondhand.kel2.data.api.model.buyer.orderid.get.GetBuyerOrderId
import com.binar.secondhand.kel2.data.api.model.buyer.orderid.put.PutOrderIdResponse
import com.binar.secondhand.kel2.data.api.model.buyer.productid.GetProductIdResponse
import com.binar.secondhand.kel2.data.api.model.buyer.productid.UserProduct
import com.binar.secondhand.kel2.data.api.model.seller.order.SellerOrderIdResponse
import com.binar.secondhand.kel2.data.api.model.wishlist.delete.DeleteWishlist
import com.binar.secondhand.kel2.data.api.model.wishlist.get.GetWishlist
import com.binar.secondhand.kel2.data.api.model.wishlist.getId.GetIdWishlist
import com.binar.secondhand.kel2.data.api.model.wishlist.post.PostWishlist
import com.binar.secondhand.kel2.data.api.model.wishlist.post.PostWishlistRequest
import com.binar.secondhand.kel2.data.repository.Repository
import com.binar.secondhand.kel2.rule.MainCoroutineRule
import com.binar.secondhand.kel2.ui.bidder.BidderViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito
import org.mockito.kotlin.given
import org.mockito.kotlin.mock
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class DetailProductViewModelTest {

    private lateinit var viewModel: DetailProductViewModel
    private lateinit var repository: Repository

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        repository = mock()
        viewModel = DetailProductViewModel(repository)
    }

    @Test
    fun getWishlist()= runTest{
        val getWishlist = mock<Response<GetWishlist>>()

        given(repository.getWishlist()).willReturn(getWishlist)

        viewModel.getWishlist()

        advanceUntilIdle()

        Mockito.verify(repository, Mockito.times(1)).getWishlist()
        kotlin.test.assertNotNull(viewModel.getWishlist)
        kotlin.test.assertEquals(viewModel.getWishlist.value?.data, getWishlist)
    }

    @Test
    fun getDetailProduct()= runTest{
        val productIdGetResponse = mock<Response<GetProductIdResponse>>()

        given(repository.getProductDetail(1)).willReturn(productIdGetResponse)

        viewModel.getDetailProduct(1)

        advanceUntilIdle()

        Mockito.verify(repository, Mockito.times(1)).getProductDetail(1)
        kotlin.test.assertNotNull(viewModel.detailProduct)
        kotlin.test.assertEquals(viewModel.detailProduct.value?.data, productIdGetResponse)
    }

    @Test
    fun getProductOrder()= runTest{
        val getBuyerOrderIdResponse = mock<Response<GetBuyerOrderId>>()

        val productId = 1

        given(repository.getProductOrder(productId)).willReturn(getBuyerOrderIdResponse)

        viewModel.getProductOrder(productId)

        advanceUntilIdle()

        Mockito.verify(repository, Mockito.times(1)).getProductOrder(productId)
        kotlin.test.assertNotNull(viewModel.orderProduct)
        kotlin.test.assertEquals(viewModel.orderProduct.value?.data, getBuyerOrderIdResponse)
    }

    @Test
    fun getBuyerOrder()= runTest{
        val productIdGetResponse = mock<List<GetOrderResponseItem>>()

        given(repository.getBuyerOrder()).willReturn(productIdGetResponse)

        viewModel.getBuyerOrder()

        advanceUntilIdle()

        Mockito.verify(repository, Mockito.times(1)).getBuyerOrder()
        kotlin.test.assertNotNull(viewModel.getBuyerOrder)
        kotlin.test.assertEquals(viewModel.getBuyerOrder.value?.data, productIdGetResponse)
    }

    @Test
    fun buyerOrder()= runTest{
        val postOrderResponse = mock<Response<PostOrderResponse>>()

        val requestBuyerOrder = PostOrderRequest(
            1,
            100000
        )

        given(repository.postBuyerOrder(requestBuyerOrder)).willReturn(postOrderResponse)

        viewModel.buyerOrder(requestBuyerOrder)

        advanceUntilIdle()

        Mockito.verify(repository, Mockito.times(1)).postBuyerOrder(requestBuyerOrder)
        kotlin.test.assertNotNull(viewModel.buyerOrder)
        kotlin.test.assertEquals(viewModel.buyerOrder.value?.data, postOrderResponse)
    }

    @Test
    fun deleteOrder()= runTest{
        val unitResponse = mock<Response<Unit>>()

        val productId = 1

        given(repository.deleteOrder(productId)).willReturn(unitResponse)

        viewModel.deleteOrder(productId)

        advanceUntilIdle()

        Mockito.verify(repository, Mockito.times(1)).deleteOrder(productId)
        kotlin.test.assertNotNull(viewModel.deleteOrder)
        kotlin.test.assertEquals(viewModel.deleteOrder.value?.data, unitResponse)
    }

    @Test
    fun postWishlist()= runTest{
        val postWishlist = mock<Response<PostWishlist>>()

        val requestBuyerWishlist = PostWishlistRequest(1)

        given(repository.postWishlist(requestBuyerWishlist)).willReturn(postWishlist)

        viewModel.postWishlist(requestBuyerWishlist)

        advanceUntilIdle()

        Mockito.verify(repository, Mockito.times(1)).postWishlist(requestBuyerWishlist)
        kotlin.test.assertNotNull(viewModel.postWishlist)
        kotlin.test.assertEquals(viewModel.postWishlist.value?.data, postWishlist)
    }

    @Test
    fun deleteWishlist()= runTest{
        val deleteWishlist = mock<Response<DeleteWishlist>>()

        val productId = 1

        given(repository.deleteWishlist(productId)).willReturn(deleteWishlist)

        viewModel.deleteWishlist(productId)

        advanceUntilIdle()

        Mockito.verify(repository, Mockito.times(1)).deleteWishlist(productId)
        kotlin.test.assertNotNull(viewModel.deleteWishlist)
        kotlin.test.assertEquals(viewModel.deleteWishlist.value?.data, deleteWishlist)
    }

    @Test
    fun getIdWishlist()= runTest{
        val getIdWishlist = mock<Response<GetIdWishlist>>()

        val productId = 1

        given(repository.getIdWishlist(productId)).willReturn(getIdWishlist)

        viewModel.getIdWishlist(productId)

        advanceUntilIdle()

        Mockito.verify(repository, Mockito.times(1)).getIdWishlist(productId)
        kotlin.test.assertNotNull(viewModel.getIdWishlist)
        kotlin.test.assertEquals(viewModel.getIdWishlist.value?.data, getIdWishlist)
    }

    @Test
    fun editOrder()= runTest{
        val putOrderIdResponse = mock<Response<PutOrderIdResponse>>()

        val id = 1
        val bidOrder = 100000
        val bidOrderBody = bidOrder.toString().toRequestBody("text/plain".toMediaTypeOrNull())

        given(repository.putOrder(
            id,
            bidOrderBody
        )).willReturn(putOrderIdResponse)

        viewModel.editOrder(
            id,
            bidOrderBody,
        )

        advanceUntilIdle()

        Mockito.verify(repository, Mockito.times(1)).putOrder(
            id,
            bidOrderBody
        )
        kotlin.test.assertNotNull(viewModel.editOrder)
        kotlin.test.assertEquals(viewModel.editOrder.value?.data, putOrderIdResponse)
    }
}