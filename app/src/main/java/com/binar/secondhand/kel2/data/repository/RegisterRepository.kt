package com.binar.secondhand.kel2.data.repository

import com.binar.secondhand.kel2.data.api.model.auth.login.PostLoginRequest
import com.binar.secondhand.kel2.data.api.model.auth.register.PostRegisterRequest
import com.binar.secondhand.kel2.data.api.service.ApiHelper

class RegisterRepository(private val apiHelper: ApiHelper) {

    suspend fun postRegister(request: PostRegisterRequest) = apiHelper.postRegister(request)

}