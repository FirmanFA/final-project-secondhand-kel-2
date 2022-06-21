package com.binar.secondhand.kel2.data.repository

import com.binar.secondhand.kel2.data.api.model.auth.user.PutAuthRequest
import com.binar.secondhand.kel2.data.api.service.ApiHelper

class Repository(private val apiHelper: ApiHelper) {

    suspend fun getAuth() = apiHelper.getAuth()

    suspend fun putAuth(request: PutAuthRequest) = apiHelper.putAuth(request)
    suspend fun getProductDetail(productId: Int) = apiHelper.getProductDetail(productId)
}