package com.binar.secondhand.kel2.data.repository

import com.binar.secondhand.kel2.data.api.model.auth.user.GetAuthResponse
import com.binar.secondhand.kel2.data.api.model.buyer.product.GetProductResponse
import com.binar.secondhand.kel2.data.api.model.seller.banner.get.GetBannerResponse
import com.binar.secondhand.kel2.data.api.model.seller.category.get.GetCategoryResponse
import com.binar.secondhand.kel2.data.api.service.ApiHelper
import com.binar.secondhand.kel2.data.api.service.ApiService
import com.binar.secondhand.kel2.data.local.room.database.AppDatabase
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking

import org.junit.Before
import org.junit.Test
import retrofit2.Response

class HomeRepositoryTest {

    //collaborator
    private lateinit var apiService: ApiService
    private lateinit var apiHelper: ApiHelper
    private lateinit var appDatabase: AppDatabase

    //system under test
    private lateinit var homeRepository: HomeRepository

    @Before
    fun setUp() {
        apiService = mockk()
        apiHelper = mockk()
        appDatabase = mockk()

        homeRepository = HomeRepository(apiHelper, appDatabase)
    }

    @Test
    fun getBanner(): Unit = runBlocking {
        val responseGetBanner = mockk<Response<GetBannerResponse>>()

        every {
            runBlocking {
                apiHelper.getBanner()
            }
        } returns responseGetBanner

            homeRepository.getBanner()

        verify {
            runBlocking {
                apiHelper.getBanner()
            }
        }
    }

    @Test
    fun getProduct(): Unit = runBlocking {
        val responseGetProduct = mockk<Response<GetProductResponse>>()

        every {
            runBlocking {
                apiHelper.getProduct()
            }
        } returns responseGetProduct

        homeRepository.getProduct()

        verify {
            runBlocking {
                apiHelper.getProduct()
            }
        }
    }

//    @Test
//    fun getProductStream(): Unit = runBlocking {
//        val getProductResponseItem = mockk<PagingSource<1, GetProductResponseItem>>()
//
//        every {
//            runBlocking {
//                appDatabase.productDao().getProducts()
//            }
//        } returns getProductResponseItem
//
//        homeRepository.getProductStream()
//
//        verify {
//            runBlocking {
//                appDatabase.productDao().getProducts()
//            }
//        }
//    }

    @Test
    fun getCategory(): Unit = runBlocking {
        val responseGetCategory = mockk<Response<GetCategoryResponse>>()

        every {
            runBlocking {
                apiHelper.getCategory()
            }
        } returns responseGetCategory

        homeRepository.getCategory()

        verify {
            runBlocking {
                apiHelper.getCategory()
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

        homeRepository.getAuth()

        verify {
            runBlocking {
                apiHelper.getAuth()
            }
        }
    }
}