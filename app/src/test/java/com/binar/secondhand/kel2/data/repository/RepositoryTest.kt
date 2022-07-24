package com.binar.secondhand.kel2.data.repository

import com.binar.secondhand.kel2.data.api.model.auth.password.PutPassRequest
import com.binar.secondhand.kel2.data.api.model.auth.password.PutPassResponse
import com.binar.secondhand.kel2.data.api.model.auth.user.GetAuthResponse
import com.binar.secondhand.kel2.data.api.model.auth.user.PutAuthResponse
import com.binar.secondhand.kel2.data.api.model.buyer.order.get.GetOrderResponse
import com.binar.secondhand.kel2.data.api.model.buyer.order.post.PostOrderRequest
import com.binar.secondhand.kel2.data.api.model.buyer.order.post.PostOrderResponse
import com.binar.secondhand.kel2.data.api.model.buyer.orderid.get.GetBuyerOrderId
import com.binar.secondhand.kel2.data.api.model.buyer.orderid.put.PutOrderIdResponse
import com.binar.secondhand.kel2.data.api.model.buyer.productid.GetProductIdResponse
import com.binar.secondhand.kel2.data.api.model.buyer.productid.UserProduct
import com.binar.secondhand.kel2.data.api.model.notification.GetNotificationResponse
import com.binar.secondhand.kel2.data.api.model.seller.category.get.GetCategoryResponse
import com.binar.secondhand.kel2.data.api.model.seller.order.PatchSellerOrderIdRequest
import com.binar.secondhand.kel2.data.api.model.seller.order.PatchSellerOrderIdResponse
import com.binar.secondhand.kel2.data.api.model.seller.order.SellerOrderIdResponse
import com.binar.secondhand.kel2.data.api.model.seller.order.id.GetOrderIdResponse
import com.binar.secondhand.kel2.data.api.model.seller.product.id.patch.PatchProductId
import com.binar.secondhand.kel2.data.api.model.seller.product.id.patch.PatchProductIdRequest
import com.binar.secondhand.kel2.data.api.model.seller.product.post.PostProductResponse
import com.binar.secondhand.kel2.data.api.model.seller.product.put.PutSellerProductIdResponse
import com.binar.secondhand.kel2.data.api.model.wishlist.delete.DeleteWishlist
import com.binar.secondhand.kel2.data.api.model.wishlist.get.GetWishlist
import com.binar.secondhand.kel2.data.api.model.wishlist.getId.GetIdWishlist
import com.binar.secondhand.kel2.data.api.model.wishlist.post.PostWishlist
import com.binar.secondhand.kel2.data.api.model.wishlist.post.PostWishlistRequest
import com.binar.secondhand.kel2.data.api.service.ApiHelper
import com.binar.secondhand.kel2.data.api.service.ApiService
import com.binar.secondhand.kel2.utils.URIPathHelper
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

import org.junit.Before
import org.junit.Test
import retrofit2.Response

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

        val nameBody = name.toRequestBody("text/plain".toMediaTypeOrNull())
        val cityBody = city.toRequestBody("text/plain".toMediaTypeOrNull())
        val addressBody = address.toRequestBody("text/plain".toMediaTypeOrNull())
        val phoneNumberBody = phoneNumber.toString().toRequestBody("text/plain".toMediaTypeOrNull())
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
    fun getOrderProductId(): Unit = runBlocking {
        val getOrderIdResponse = mockk<Response<GetOrderIdResponse>>()

        val productId = 1

        every {
            runBlocking {
                apiHelper.getOrderProductId(productId)
            }
        } returns getOrderIdResponse

        repository.getOrderProductId(productId)

        verify {
            runBlocking {
                apiHelper.getOrderProductId(productId)
            }
        }
    }

    @Test
    fun getProductOrder(): Unit = runBlocking {
        val getBuyerOrderId = mockk<Response<GetBuyerOrderId>>()

        val productId = 1

        every {
            runBlocking {
                apiHelper.getProductOrder(productId)
            }
        } returns getBuyerOrderId

        repository.getProductOrder(productId)

        verify {
            runBlocking {
                apiHelper.getProductOrder(productId)
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
    fun deleteOrder(): Unit = runBlocking {
        val unit = mockk<Response<Unit>>()

        val productId = 1

        every {
            runBlocking {
                apiHelper.deleteOrder(productId)
            }
        } returns unit

        repository.deleteOrder(productId)

        verify {
            runBlocking {
                apiHelper.deleteOrder(productId)
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

    @Test
    fun getSellerOrderId(): Unit = runBlocking {
        val sellerOrderIdResponse = mockk<Response<SellerOrderIdResponse>>()

        val id = 1

        every {
            runBlocking {
                apiHelper.getSellerOrderId(id)
            }
        } returns sellerOrderIdResponse

        repository.getSellerOrderId(id)

        verify {
            runBlocking {
                apiHelper.getSellerOrderId(id)
            }
        }
    }

    @Test
    fun patchSellerOrderId(): Unit = runBlocking {
        val patchSellerOrderIdResponse = mockk<Response<PatchSellerOrderIdResponse>>()

        val id = 1
        val request = PatchSellerOrderIdRequest("Sold")

        every {
            runBlocking {
                apiHelper.patchSellerOrderId(id, request)
            }
        } returns patchSellerOrderIdResponse

        repository.patchSellerOrderId(id, request)

        verify {
            runBlocking {
                apiHelper.patchSellerOrderId(id, request)
            }
        }
    }

    @Test
    fun patchSellerProductId(): Unit = runBlocking {
        val patchProductId = mockk<Response<PatchProductId>>()

        val id = 1
        val request = PatchProductIdRequest("Sold")

        every {
            runBlocking {
                apiHelper.patchSellerProductId(id, request)
            }
        } returns patchProductId

        repository.patchSellerProductId(id, request)

        verify {
            runBlocking {
                apiHelper.patchSellerProductId(id, request)
            }
        }
    }

    @Test
    fun putProduct(): Unit = runBlocking {
        val putSellerProductIdResponse = mockk<Response<PutSellerProductIdResponse>>()

        val id = 1
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
                apiHelper.putProduct(
                    id,
                    name = nameBody,
                    base_price = priceBody,
                    location = cityBody,
                    category_ids = categoryBody,
                    description = descriptionBody,
                    image = imageBody
                )
            }
        } returns putSellerProductIdResponse

        repository.putProduct(
            id,
            name = nameBody,
            base_price = priceBody,
            location = cityBody,
            category_ids = categoryBody,
            description = descriptionBody,
            image = imageBody
        )

        verify {
            runBlocking {
                apiHelper.putProduct(
                    id,
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

    @Test
    fun putOrder(): Unit = runBlocking {
        val putOrderIdResponse = mockk<Response<PutOrderIdResponse>>()

        val id = 1
        val bidPrice = 100000
        val bidPriceBody = bidPrice.toString().toRequestBody("text/plain".toMediaTypeOrNull())

        every {
            runBlocking {
                apiHelper.putOrder(
                    id,
                    bidPriceBody
                )
            }
        } returns putOrderIdResponse

        repository.putOrder(
            id,
            bidPriceBody
        )

        verify {
            runBlocking {
                apiHelper.putOrder(
                    id,
                    bidPriceBody
                )
            }
        }
    }

    @Test
    fun getWishlist(): Unit = runBlocking {
        val getWishlist = mockk<Response<GetWishlist>>()

        every {
            runBlocking {
                apiHelper.getWishlist()
            }
        } returns getWishlist

        repository.getWishlist()

        verify {
            runBlocking {
                apiHelper.getWishlist()
            }
        }
    }

    @Test
    fun deleteWishlist(): Unit = runBlocking {
        val deleteWishlist = mockk<Response<DeleteWishlist>>()

        val productId = 1

        every {
            runBlocking {
                apiHelper.deleteWishlist(productId)
            }
        } returns deleteWishlist

        repository.deleteWishlist(productId)

        verify {
            runBlocking {
                apiHelper.deleteWishlist(productId)
            }
        }
    }

    @Test
    fun getIdWishlist(): Unit = runBlocking {
        val getIdWishlist = mockk<Response<GetIdWishlist>>()

        val productId = 1

        every {
            runBlocking {
                apiHelper.getIdWishlist(productId)
            }
        } returns getIdWishlist

        repository.getIdWishlist(productId)

        verify {
            runBlocking {
                apiHelper.getIdWishlist(productId)
            }
        }
    }

    @Test
    fun postWishlist(): Unit = runBlocking {
        val postWishlist = mockk<Response<PostWishlist>>()

        val requestBuyerWishlist = PostWishlistRequest(1)

        every {
            runBlocking {
                apiHelper.postWishlist(requestBuyerWishlist)
            }
        } returns postWishlist

        repository.postWishlist(requestBuyerWishlist)

        verify {
            runBlocking {
                apiHelper.postWishlist(requestBuyerWishlist)
            }
        }
    }

    @Test
    fun getCategory(): Unit = runBlocking {
        val getCategoryResponse = mockk<Response<GetCategoryResponse>>()

        every {
            runBlocking {
                apiHelper.getCategory()
            }
        } returns getCategoryResponse

        repository.getCategory()

        verify {
            runBlocking {
                apiHelper.getCategory()
            }
        }
    }
}