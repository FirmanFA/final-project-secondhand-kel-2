package com.binar.secondhand.kel2.data.repository

import androidx.core.content.ContentProviderCompat.requireContext
import com.binar.secondhand.kel2.data.api.model.auth.password.PutPassRequest
import com.binar.secondhand.kel2.data.api.model.auth.password.PutPassResponse
import com.binar.secondhand.kel2.data.api.model.auth.register.PostRegisterRequest
import com.binar.secondhand.kel2.data.api.model.auth.user.GetAuthResponse
import com.binar.secondhand.kel2.data.api.model.auth.user.PutAuthResponse
import com.binar.secondhand.kel2.data.api.model.buyer.order.get.GetOrderResponse
import com.binar.secondhand.kel2.data.api.model.buyer.order.post.PostOrderRequest
import com.binar.secondhand.kel2.data.api.model.buyer.order.post.PostOrderResponse
import com.binar.secondhand.kel2.data.api.model.buyer.productid.GetProductIdResponse
import com.binar.secondhand.kel2.data.api.model.buyer.productid.UserProduct
import com.binar.secondhand.kel2.data.api.model.notification.GetNotificationResponse
import com.binar.secondhand.kel2.data.api.model.seller.product.post.PostProductResponse
import com.binar.secondhand.kel2.data.api.service.ApiHelper
import com.binar.secondhand.kel2.data.api.service.ApiService
import com.binar.secondhand.kel2.utils.URIPathHelper
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.io.File

class RepositoryTest {

    //collaborator
    private lateinit var apiService: ApiService
    private lateinit var apiHelper: ApiHelper
    private lateinit var uriPathHelper: URIPathHelper

    //system under test
    private lateinit var repository: Repository

    @Before
    fun setUp() {
        apiService = mockk()
        apiHelper = mockk()
        uriPathHelper = mockk()

        repository = Repository(apiHelper)
    }

    @Test
    fun getAuth(): Unit = runBlocking {
        val responseGetAuth = mockk<Response<GetAuthResponse>>()

        every {
            runBlocking {
                apiHelper.getAuth()
            }
        } returns responseGetAuth

        repository.getAuth()

        verify {
            runBlocking {
                apiHelper.getAuth()
            }
        }
    }

    @Test
    fun putAuth(): Unit = runBlocking {
        val responsePutAuth = mockk<Response<PutAuthResponse>>()

        val name = "Coba"
        val city = "Jakarta"
        val address = "Jakarta Barat"
        val phoneNumber = 822233445

        val imageFile = mockk<File>()

        val nameBody = name.toRequestBody("text/plain".toMediaTypeOrNull())
        val cityBody = city.toRequestBody("text/plain".toMediaTypeOrNull())
        val addressBody = address.toRequestBody("text/plain".toMediaTypeOrNull())
        val phoneNumberBody = phoneNumber.toString().toRequestBody("text/plain".toMediaTypeOrNull())

//        val requestImage = imageFile?.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val imageBody = mockk<MultipartBody.Part>()

        every {
            runBlocking {
                apiHelper.putAuth(
                    fullname = nameBody,
                    city = cityBody,
                    address = addressBody,
                    phone_number = phoneNumberBody,
                    image = imageBody
                )
            }
        } returns responsePutAuth

        repository.putAuth(
            fullname = nameBody,
            city = cityBody,
            address = addressBody,
            phone_number = phoneNumberBody,
            image = imageBody
        )

        verify {
            runBlocking {
                apiHelper.putAuth(
                    fullname = nameBody,
                    city = cityBody,
                    address = addressBody,
                    phone_number = phoneNumberBody,
                    image = imageBody
                )
            }
        }
    }

    @Test
    fun putPass(): Unit = runBlocking {
        val responsePutPass = mockk<Response<PutPassResponse>>()

        val request = PutPassRequest(
            "000000",
            "000000",
            "000000")

        every {
            runBlocking {
                apiHelper.putPass(request)
            }
        } returns responsePutPass

        repository.putPass(request)

        verify {
            runBlocking {
                apiHelper.putPass(request)
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

        repository.getNotification()

        verify {
            runBlocking {
                apiHelper.getNotification()
            }
        }
    }

    @Test
    fun getBuyerOrder(): Unit = runBlocking {
        val responseGetOrder = mockk<List<GetOrderResponse.GetOrderResponseItem>>()

        every {
            runBlocking {
                apiHelper.getBuyerOrder()
            }
        } returns responseGetOrder

        repository.getBuyerOrder()

        verify {
            runBlocking {
                apiHelper.getBuyerOrder()
            }
        }
    }

    @Test
    fun getProductId(): Unit = runBlocking {
        val responseGetProductId = mockk<Response<GetProductIdResponse>>()

        every {
            runBlocking {
                apiHelper.getProductId(1)
            }
        } returns responseGetProductId

        repository.getProductId(1)

        verify {
            runBlocking {
                apiHelper.getProductId(1)
            }
        }
    }

    @Test
    fun getProductDetail(): Unit = runBlocking {
        val responseGetProductId = mockk<Response<GetProductIdResponse>>()

        val productId = 1

        every {
            runBlocking {
                apiHelper.getProductDetail(productId)
            }
        } returns responseGetProductId

        repository.getProductDetail(productId)

        verify {
            runBlocking {
                apiHelper.getProductDetail(productId)
            }
        }
    }

    @Test
    fun getUserProfile(): Unit = runBlocking {
        val userProduct = mockk<Response<UserProduct>>()

        val userid = 1

        every {
            runBlocking {
                apiHelper.getUserProfile(userid)
            }
        } returns userProduct

        repository.getUserProfile(userid)

        verify {
            runBlocking {
                apiHelper.getUserProfile(userid)
            }
        }
    }

    @Test
    fun postBuyerOrder(): Unit = runBlocking {
        val responsePostOrder = mockk<Response<PostOrderResponse>>()

        val requestBuyerOrder = PostOrderRequest(
            1,
            10000)

        every {
            runBlocking {
                apiHelper.postBuyerOrder(requestBuyerOrder)
            }
        } returns responsePostOrder

        repository.postBuyerOrder(requestBuyerOrder)

        verify {
            runBlocking {
                apiHelper.postBuyerOrder(requestBuyerOrder)
            }
        }
    }

    @Test
    fun postProduct(): Unit = runBlocking {
        val responsePostProduct = mockk<Response<PostProductResponse>>()

        val name = "Coba"
        val description = "Baju"
        val price = 100000
        val category = "Pakaian"
        val city = "Jakarta"

        val nameBody = name.toRequestBody("text/plain".toMediaTypeOrNull())
        val priceBody = price.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val cityBody = city.toRequestBody("text/plain".toMediaTypeOrNull())
        val categoryBody = category.toRequestBody("text/plain".toMediaTypeOrNull())
        val descriptionBody = description.toRequestBody("text/plain".toMediaTypeOrNull())
        val imageBody = mockk<MultipartBody.Part>()

        every {
            runBlocking {
                apiHelper.postProduct(
                    name = nameBody,
                    base_price = priceBody,
                    location = cityBody,
                    category_ids = categoryBody,
                    description = descriptionBody,
                    image = imageBody
                )
            }
        } returns responsePostProduct

        repository.postProduct(
            name = nameBody,
            base_price = priceBody,
            location = cityBody,
            category_ids = categoryBody,
            description = descriptionBody,
            image = imageBody
        )

        verify {
            runBlocking {
                apiHelper.postProduct(
                    name = nameBody,
                    base_price = priceBody,
                    location = cityBody,
                    category_ids = categoryBody,
                    description = descriptionBody,
                    image = imageBody
                )
            }
        }
    }
}