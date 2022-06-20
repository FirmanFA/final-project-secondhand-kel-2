package com.binar.secondhand.kel2.data.api.service

import com.binar.secondhand.kel2.data.api.model.auth.login.PostLoginRequest
import com.binar.secondhand.kel2.data.api.model.auth.register.PostRegisterRequest

class ApiHelper(private val apiService: ApiService) {

    suspend fun postLogin(request: PostLoginRequest)= apiService.postLogin(request)

    suspend fun postRegister(request: PostRegisterRequest)= apiService.postRegister(request)

}