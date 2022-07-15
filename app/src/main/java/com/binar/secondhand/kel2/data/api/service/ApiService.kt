package com.binar.secondhand.kel2.data.api.service

import com.binar.secondhand.kel2.data.api.model.buyer.order.post.PostOrderRequest
import com.binar.secondhand.kel2.data.api.model.buyer.order.post.PostOrderResponse
import com.binar.secondhand.kel2.data.api.model.auth.login.PostLoginRequest
import com.binar.secondhand.kel2.data.api.model.auth.login.PostLoginResponse
import com.binar.secondhand.kel2.data.api.model.auth.password.PutPassRequest
import com.binar.secondhand.kel2.data.api.model.auth.password.PutPassResponse
import com.binar.secondhand.kel2.data.api.model.auth.register.PostRegisterRequest
import com.binar.secondhand.kel2.data.api.model.auth.register.PostRegisterResponse
import com.binar.secondhand.kel2.data.api.model.auth.user.GetAuthResponse
import com.binar.secondhand.kel2.data.api.model.auth.user.PutAuthResponse
import com.binar.secondhand.kel2.data.api.model.buyer.order.get.GetOrderResponse
import com.binar.secondhand.kel2.data.api.model.notification.GetNotificationResponse
import com.binar.secondhand.kel2.data.api.model.seller.banner.get.GetBannerResponse
import com.binar.secondhand.kel2.data.api.model.seller.category.get.GetCategoryResponse
import com.binar.secondhand.kel2.data.api.model.buyer.product.GetProductResponse
import com.binar.secondhand.kel2.data.api.model.buyer.productid.GetProductIdResponse
import com.binar.secondhand.kel2.data.api.model.buyer.productid.UserProduct
import com.binar.secondhand.kel2.data.api.model.seller.order.PatchSellerOrderIdRequest
import com.binar.secondhand.kel2.data.api.model.seller.order.PatchSellerOrderIdResponse
import com.binar.secondhand.kel2.data.api.model.seller.order.SellerOrderIdResponse
import com.binar.secondhand.kel2.data.api.model.seller.product.get.GetSellerProductResponse
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
    suspend fun postRegister(@Body request: PostRegisterRequest):
            Response<PostRegisterResponse>

    @GET("auth/user")
    suspend fun getAuth(): Response<GetAuthResponse>

    @Multipart//req nya dibagi2
    @PUT("auth/user")
    suspend fun putAuth(
        @Part("full_name") fullname: RequestBody,
        @Part("email") email: RequestBody? = null,
        @Part("password") password: RequestBody? = null,
        @Part("phone_number") phone_number: RequestBody,
        @Part("address") address: RequestBody,
        @Part("city") city: RequestBody,
        @Part image: MultipartBody.Part?
    ): Response<PutAuthResponse>

    @PUT("auth/change-password")
    suspend fun changePassword(@Body request: PutPassRequest): Response<PutPassResponse>

    @GET("notification")
    suspend fun getNotification(@Query("notification_type") type: String = ""):
            Response<GetNotificationResponse>

    @GET("buyer/product/{id}")
    suspend fun getProductId(@Path("id") id: Int): Response<GetProductIdResponse>

    @GET("seller/product/{id}")
    suspend fun getProductId(): Response<GetProductIdResponse>

    @GET("buyer/product/{product_id}")
    suspend fun getProductDetail(@Path("product_id") productid: Int): Response<GetProductIdResponse>

    @GET("buyer/product/{user_id}")
    suspend fun getUserProfile(@Path("user_id") userid: Int): Response<UserProduct>


    @GET("buyer/order")
    suspend fun getBuyerOrder(): List<GetOrderResponse.GetOrderResponseItem>

    @POST("buyer/order")
    suspend fun postBuyerOrder(
        @Body requestBuyerOrder: PostOrderRequest
    ): Response<PostOrderResponse>

    @Multipart
    @POST("seller/product")
    suspend fun postProduct(
        @Part("name") name: RequestBody,
        @Part("description") description: RequestBody? = null,
        @Part("base_price") base_price: RequestBody? = null,
        @Part("category_ids") category_ids: RequestBody,
        @Part("location") location: RequestBody,
        @Part image: MultipartBody.Part?
    ): Response<PostProductResponse>

    //Home endpoint
    @GET("seller/banner")
    suspend fun getBanner(): Response<GetBannerResponse>

    @GET("buyer/product")
    suspend fun getProduct(
        @Query("status") status: String? = null,
        @Query("category_id") categoryId: Int? = null,
        @Query("search") searchKeyword: String? = null,
        @Query("page") page: Int=1,
        @Query("per_page") itemsPerPage: Int=20
    ): Response<GetProductResponse>

    @GET("seller/category")
    suspend fun getCategory(): Response<GetCategoryResponse>

    //product sale endpoint
    @GET("seller/product")
    suspend fun getSellerProduct(): Response<GetSellerProductResponse>

    @GET("seller/order")
    suspend fun getSellerOrder(): Response<com.binar.secondhand.kel2.data.api.model.seller.order.GetOrderResponse>

    @GET("seller/order/{id}")
    suspend fun getSellerOrderId(@Path("id") id: Int): Response<SellerOrderIdResponse>

    @PATCH("seller/order/{id}")
    suspend fun patchSellerOrderId(
        @Path("id") id: Int,
        @Body request: PatchSellerOrderIdRequest
    ): Response<PatchSellerOrderIdResponse>
}