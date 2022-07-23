package com.binar.secondhand.kel2.ui.wishlist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.binar.secondhand.kel2.data.api.model.seller.product.get.GetSellerProductResponse
import com.binar.secondhand.kel2.data.api.model.wishlist.get.GetWishlist
import com.binar.secondhand.kel2.data.repository.ProductSaleListRepository
import com.binar.secondhand.kel2.data.repository.Repository
import com.binar.secondhand.kel2.rule.MainCoroutineRule
import com.binar.secondhand.kel2.ui.sale.main.ProductSaleListViewModel
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

class WishlistViewModelTest {

    private lateinit var viewModel: WishlistViewModel
    private lateinit var repository: Repository

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        repository = mock()
        viewModel = WishlistViewModel(repository)
    }

    @Test
    fun getWishlist()= runTest{
        val wishlistGetResponse = mock<Response<GetWishlist>>()

        given(repository.getWishlist()).willReturn(wishlistGetResponse)

        viewModel.getWishlist()

        advanceUntilIdle()

        Mockito.verify(repository, Mockito.times(1)).getWishlist()
        kotlin.test.assertNotNull(viewModel.getWishlist)
        kotlin.test.assertEquals(viewModel.getWishlist.value?.data, wishlistGetResponse)
    }
}