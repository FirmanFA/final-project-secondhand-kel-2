package com.binar.secondhand.kel2.data.repository

import com.binar.secondhand.kel2.data.api.model.auth.user.PutAuthRequest
import com.binar.secondhand.kel2.data.api.service.ApiHelper
import okhttp3.MultipartBody
import okhttp3.RequestBody
import com.binar.secondhand.kel2.data.api.model.auth.register.PostRegisterRequest
import com.binar.secondhand.kel2.data.api.model.auth.user.PutAuthRequest
import com.binar.secondhand.kel2.data.api.model.buyer.order.post.PostOrderRequest
import com.binar.secondhand.kel2.data.api.service.ApiHelper

class Repository(private val apiHelper: ApiHelper) {

    suspend fun getAuth() = apiHelper.getAuth()

    suspend fun putAuth(
        fullname: RequestBody,
        email: RequestBody ?= null,
        password: RequestBody ?= null,
        phone_number: RequestBody,
        address: RequestBody,
        city: RequestBody,
        image: MultipartBody.Part?
    ) = apiHelper.putAuth(
        fullname,
        email,
        password,
        phone_number,
        address,
        city,
        image
    )

    suspend fun getNotification() = apiHelper.getNotification()

    suspend fun getProductId(id: Int) = apiHelper.getProductId(id)
    suspend fun getProductDetail(productId: Int) = apiHelper.getProductDetail(productId)
    suspend fun getUserProfile(userId: Int) = apiHelper.getUserProfile(userId)
    suspend fun postBuyerOrder(request: PostOrderRequest) = apiHelper.postBuyerOrder(request)
}