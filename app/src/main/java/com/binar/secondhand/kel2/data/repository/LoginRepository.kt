package com.binar.secondhand.kel2.data.repository

import com.binar.secondhand.kel2.data.api.model.auth.login.PostLoginRequest
import com.binar.secondhand.kel2.data.api.model.auth.register.PostRegisterRequest
import com.binar.secondhand.kel2.data.api.service.ApiHelper

class LoginRepository(private val apiHelper: ApiHelper) {

    suspend fun postLogin(request: PostLoginRequest) = apiHelper.postLogin(request)

}