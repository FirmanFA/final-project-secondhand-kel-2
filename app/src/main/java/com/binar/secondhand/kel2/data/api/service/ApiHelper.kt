package com.binar.secondhand.kel2.data.api.service

import com.binar.secondhand.kel2.data.api.model.auth.login.PostLoginRequest
import com.binar.secondhand.kel2.data.api.model.auth.password.PutPassRequest
import com.binar.secondhand.kel2.data.api.model.auth.register.PostRegisterRequest
import com.binar.secondhand.kel2.data.api.model.buyer.order.post.PostOrderRequest
import com.binar.secondhand.kel2.data.api.model.seller.product.get.GetSellerProductResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Part
import com.binar.secondhand.kel2.data.api.model.seller.banner.get.GetBannerResponse
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

    suspend fun putPass(request: PutPassRequest) = apiService.changePassword(request)

    suspend fun getNotification() = apiService.getNotification()

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

    suspend fun postProduct(
        name: RequestBody,
        description: RequestBody,
        base_price: RequestBody,
        category_ids: RequestBody,
        location: RequestBody,
        image: MultipartBody.Part?
    ) = apiService.postProduct(
        name,
        description,
        base_price,
        category_ids,
        location,
        image
    )

    //product sale list
    suspend fun getSellerProduct() = apiService.getSellerProduct()
}