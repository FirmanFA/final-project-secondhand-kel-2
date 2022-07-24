package com.binar.secondhand.kel2.data.repository

import com.binar.secondhand.kel2.data.api.model.auth.user.GetAuthResponse
import com.binar.secondhand.kel2.data.api.model.notification.GetNotificationResponse
import com.binar.secondhand.kel2.data.api.model.seller.banner.get.GetBannerResponse
import com.binar.secondhand.kel2.data.api.model.seller.order.GetOrderResponse
import com.binar.secondhand.kel2.data.api.model.seller.product.get.GetSellerProductResponse
import com.binar.secondhand.kel2.data.api.service.ApiHelper
import com.binar.secondhand.kel2.data.api.service.ApiService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import retrofit2.Response

class ProductSaleListRepositoryTest {

    //collaborator
    private lateinit var apiService: ApiService
    private lateinit var apiHelper: ApiHelper

    //system under test
    private lateinit var productSaleListRepository: ProductSaleListRepository

    @Before
    fun setUp() {
        apiService = mockk()
        apiHelper = mockk()

        productSaleListRepository = ProductSaleListRepository(apiHelper)
    }

    @Test
    fun getSellerProduct(): Unit = runBlocking {
        val responseGetSellerProduct = mockk<Response<GetSellerProductResponse>>()

        every {
            runBlocking {
                apiHelper.getSellerProduct()
            }
        } returns responseGetSellerProduct

        productSaleListRepository.getSellerProduct()

        verify {
            runBlocking {
                apiHelper.getSellerProduct()
            }
        }
    }

    @Test
    fun getAuth(): Unit = runBlocking {
        val responseGetAuth = mockk<Response<GetAuthResponse>>()

        every {
            runBlocking {
                apiHelper.getAuth()
            }
        } returns responseGetAuth

        productSaleListRepository.getAuth()

        verify {
            runBlocking {
                apiHelper.getAuth()
            }
        }
    }

    @Test
    fun getNotification(): Unit = runBlocking {
        val responseGetNotification = mockk<Response<GetNotificationResponse>>()

        every {
            runBlocking {
                apiHelper.getNotification()
            }
        } returns responseGetNotification

        productSaleListRepository.getNotification()

        verify {
            runBlocking {
                apiHelper.getNotification()
            }
        }
    }

    @Test
    fun getSellerOrder(): Unit = runBlocking {
        val responseGetOrder = mockk<Response<GetOrderResponse>>()

        every {
            runBlocking {
                apiHelper.getSellerOrder()
            }
        } returns responseGetOrder

        productSaleListRepository.getSellerOrder()

        verify {
            runBlocking {
                apiHelper.getSellerOrder()
            }
        }
    }

    @Test
    fun deleteSellerProductId(): Unit = runBlocking {
        val unit = mockk<Response<Unit>>()

        val id = 1

        every {
            runBlocking {
                apiHelper.deleteSellerProduct(id)
            }
        } returns unit

        productSaleListRepository.deleteSellerProductId(id)

        verify {
            runBlocking {
                apiHelper.deleteSellerProduct(id)
            }
        }
    }
}