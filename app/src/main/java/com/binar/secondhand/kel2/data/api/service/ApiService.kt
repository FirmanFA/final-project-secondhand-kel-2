package com.binar.secondhand.kel2.data.api.service

import com.binar.secondhand.kel2.data.api.model.ExampleGetResponse
import com.binar.secondhand.kel2.data.api.model.ExamplePostRequest
import com.binar.secondhand.kel2.data.api.model.ExamplePostResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @POST("api/postExample")
    suspend fun postExample(@Body request: ExamplePostRequest): Response<ExamplePostResponse>

    @GET("api/getExample")
    suspend fun getExample(): Response<ExampleGetResponse>

}