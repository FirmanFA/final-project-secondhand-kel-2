package com.binar.secondhand.kel2.data.repository

import com.binar.secondhand.kel2.data.api.model.ExamplePostRequest
import com.binar.secondhand.kel2.data.api.service.ApiHelper

class ExampleRepository(private val apiHelper: ApiHelper) {

    suspend fun postExample(request: ExamplePostRequest) = apiHelper.postExample(request)

    suspend fun getExample() = apiHelper.getExample()


}