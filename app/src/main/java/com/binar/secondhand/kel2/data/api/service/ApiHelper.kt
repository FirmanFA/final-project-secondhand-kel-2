package com.binar.secondhand.kel2.data.api.service

import com.binar.secondhand.kel2.data.api.model.ExampleGetResponse
import com.binar.secondhand.kel2.data.api.model.ExamplePostRequest
import com.binar.secondhand.kel2.data.api.model.ExamplePostResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET

class ApiHelper(private val apiService: ApiService) {

    suspend fun postExample(request: ExamplePostRequest) = apiService.postExample(request)

    suspend fun getExample() = apiService.getExample()

}