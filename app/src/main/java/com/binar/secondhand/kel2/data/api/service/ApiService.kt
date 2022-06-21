package com.binar.secondhand.kel2.data.api.service


import com.binar.secondhand.kel2.data.api.model.auth.login.PostLoginRequest
import com.binar.secondhand.kel2.data.api.model.auth.login.PostLoginResponse
import com.binar.secondhand.kel2.data.api.model.auth.register.PostRegisterRequest
import com.binar.secondhand.kel2.data.api.model.auth.register.PostRegisterResponse
import com.binar.secondhand.kel2.data.api.model.auth.user.GetAuthResponse
import com.binar.secondhand.kel2.data.api.model.auth.user.PutAuthRequest
import com.binar.secondhand.kel2.data.api.model.auth.user.PutAuthResponse
import com.binar.secondhand.kel2.data.api.model.buyer.orderid.get.GetOrderIdResponse
import com.binar.secondhand.kel2.data.api.model.notification.GetNotificationResponse
import com.binar.secondhand.kel2.data.api.model.seller.banner.get.GetBannerResponse
import com.binar.secondhand.kel2.data.api.model.seller.banner.id.GetBannerIdResponse
import com.binar.secondhand.kel2.data.api.model.seller.banner.post.PostBannerRequest
import com.binar.secondhand.kel2.data.api.model.seller.banner.post.PostBannerResponse
import com.binar.secondhand.kel2.data.api.model.seller.category.get.GetCategoryResponse
import com.binar.secondhand.kel2.data.api.model.seller.category.id.GetCategoryIdResponse
import com.binar.secondhand.kel2.data.api.model.seller.order.GetOrderResponse
import com.binar.secondhand.kel2.data.api.model.seller.product.get.GetProductResponse
import com.binar.secondhand.kel2.data.api.model.seller.product.id.get.GetProductIdResponse
import com.binar.secondhand.kel2.data.api.model.seller.product.id.put.PutProductIdRequest
import com.binar.secondhand.kel2.data.api.model.seller.product.id.put.PutProductIdResponse
import com.binar.secondhand.kel2.data.api.model.seller.product.post.PostProductRequest
import com.binar.secondhand.kel2.data.api.model.seller.product.post.PostProductResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    //auth
    @POST("auth/login")
    suspend fun postLogin(@Body request: PostLoginRequest): Response<PostLoginResponse>

    @POST("auth/register")
    suspend fun postRegister(@Body request: PostRegisterRequest): Response<PostRegisterResponse>

    @GET("auth/user")
    suspend fun getAuth(): Response<GetAuthResponse>

    @Multipart//req nya dibagi2
    @PUT("auth/user")
    suspend fun putAuth(
        @Part("full_name") fullname: RequestBody,
        @Part("email") email: RequestBody ?= null,
        @Part("password") password: RequestBody ?= null,
        @Part("phone_number") phone_number: RequestBody,
        @Part("address") address: RequestBody,
        @Part("city") city: RequestBody,
        @Part image: MultipartBody.Part?
    ): Response<PutAuthResponse>

    @GET("notification")
    suspend fun getNotification(): Response<GetNotificationResponse>

    @GET("seller/product/{id}")
    suspend fun getProductId(): Response<GetProductIdResponse>
}