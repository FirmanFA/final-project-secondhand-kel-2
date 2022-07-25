package com.binar.secondhand.kel2.data.repository

import com.binar.secondhand.kel2.data.api.model.buyer.order.get.GetOrderResponse
import com.binar.secondhand.kel2.data.api.service.ApiHelper
import com.binar.secondhand.kel2.data.api.service.ApiService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking

import org.junit.Before
import org.junit.Test
import retrofit2.Response

class MyOrderRepositoryTest {

    //collaborator
    private lateinit var apiService: ApiService
    private lateinit var apiHelper: ApiHelper

    //system under test
    private lateinit var myOrderRepository: MyOrderRepository

    @Before
    fun setUp() {
        apiService = mockk()
        apiHelper = mockk()

        myOrderRepository = MyOrderRepository(apiHelper)
    }

    @Test
    fun getMyOrder(): Unit = runBlocking {
        val getOrderResponse = mockk<Response<GetOrderResponse>>()

        every {
            runBlocking {
                apiHelper.getMyOrder()
            }
        } returns getOrderResponse

        myOrderRepository.getMyOrder()

        verify {
            runBlocking {
                apiHelper.getMyOrder()
            }
        }
    }
}