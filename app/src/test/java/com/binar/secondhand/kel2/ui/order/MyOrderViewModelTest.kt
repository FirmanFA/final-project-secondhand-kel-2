package com.binar.secondhand.kel2.ui.order

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.binar.secondhand.kel2.data.api.model.buyer.order.get.GetOrderResponse
import com.binar.secondhand.kel2.data.repository.MyOrderRepository
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
class MyOrderViewModelTest {

    private lateinit var viewModel: MyOrderViewModel
    private lateinit var repository: MyOrderRepository

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()


    @Before
    fun setUp() {
        repository = mock()
        viewModel = MyOrderViewModel(repository)
    }

    @Test
    fun getMyOrder()= runTest{
        val getOrderResponse = mock<Response<GetOrderResponse>>()

        given(repository.getMyOrder()).willReturn(getOrderResponse)

        viewModel.getMyOrder()

        advanceUntilIdle()

        Mockito.verify(repository, Mockito.times(1)).getMyOrder()
        kotlin.test.assertNotNull(viewModel.myOrderResponse.value?.status)
        kotlin.test.assertEquals(viewModel.myOrderResponse.value?.data, getOrderResponse)

    }
}