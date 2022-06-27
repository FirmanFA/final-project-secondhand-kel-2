package com.binar.secondhand.kel2.data.api.service

import com.binar.secondhand.kel2.data.api.model.auth.login.PostLoginRequest
import com.binar.secondhand.kel2.data.api.model.auth.register.PostRegisterRequest
import com.binar.secondhand.kel2.data.api.model.auth.user.PutAuthRequest
import com.binar.secondhand.kel2.data.api.model.buyer.order.post.PostOrderRequest
import com.binar.secondhand.kel2.data.api.model.seller.product.id.get.GetProductIdResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Part
import com.binar.secondhand.kel2.data.api.model.seller.banner.get.GetBannerResponse
import com.binar.secondhand.kel2.data.api.model.seller.product.get.GetProductResponse
import com.binar.secondhand.kel2.data.api.model.seller.product.post.PostProductRequest
import retrofit2.http.Query

class ApiHelper(val apiService: ApiService) {
    suspend fun postLogin(request: PostLoginRequest) = apiService.postLogin(request)

    suspend fun postRegister(request: PostRegisterRequest) = apiService.postRegister(request)

    suspend fun getAuth() = apiService.getAuth()

    suspend fun putAuth(
        fullname: RequestBody,
        email: RequestBody ?= null,
        password: RequestBody ?= null,
        phone_number: RequestBody,
        address: RequestBody,
        city: RequestBody,
        image: MultipartBody.Part?
    ) = apiService.putAuth(
        fullname,
        email,
        password,
        phone_number,
        address,
        city,
        image
    )

    suspend fun getNotification() = apiService.getNotification()

    suspend fun getProductId() = apiService.getProductId()

    suspend fun getBanner() = apiService.getBanner()

    suspend fun getProduct(
        status: String? = null,
        categoryId: Int? = null,
        searchKeyword: String? = null
    ) = apiService.getProduct(
        status,
        categoryId,
        searchKeyword
    )
    suspend fun getProductDetail(productId: Int) = apiService.getProductDetail(productId)
    suspend fun getUserProfile(userId: Int) = apiService.getUserProfile(userId)

    suspend fun postBuyerOrder(request: PostOrderRequest) = apiService.postBuyerOrder(request)
    suspend fun getCategory() = apiService.getCategory()
    suspend fun getProductId(id: Int) = apiService.getProductId(id)

    suspend fun postProduct(request: PostProductRequest) = apiService.postProduct(request)
}