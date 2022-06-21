package com.binar.secondhand.kel2.data.api.service


import com.binar.secondhand.kel2.data.api.model.auth.login.PostLoginRequest
import com.binar.secondhand.kel2.data.api.model.auth.login.PostLoginResponse
import com.binar.secondhand.kel2.data.api.model.auth.register.PostRegisterRequest
import com.binar.secondhand.kel2.data.api.model.auth.register.PostRegisterResponse
import com.binar.secondhand.kel2.data.api.model.auth.user.GetAuthResponse
import com.binar.secondhand.kel2.data.api.model.auth.user.PutAuthRequest
import com.binar.secondhand.kel2.data.api.model.auth.user.PutAuthResponse
import com.binar.secondhand.kel2.data.api.model.buyer.orderid.get.GetOrderIdResponse
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
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    //auth
    @POST("auth/login")
    suspend fun postLogin(@Body request: PostLoginRequest): Response<PostLoginResponse>

    @POST("auth/register")
    suspend fun postRegister(@Body request: PostRegisterRequest): Response<PostRegisterResponse>

    @GET("auth/user/{id}")
    suspend fun getAuth(): Response<GetAuthResponse>

    @PUT("auth/user/{id}")
    suspend fun putAuth(@Body request:PutAuthRequest): Response<PutAuthResponse>

    @GET("buyer/product/{product_id}")
    suspend fun getProductDetail(@Path("product_id")productid:Int): Response<GetProductIdResponse>



    //seller

//    @POST("seller/banner")
//    suspend fun postBanner(@Body request: PostBannerRequest): Response<PostBannerResponse>

//    @GET("seller/banner")
//    suspend fun getBanner(): GetBannerResponse
//
//    @GET("seller/banner/{id}")
//    suspend fun  getBannerId(): GetBannerIdResponse
//
//    @POST("seller/banner/category")
//    suspend fun postCategory(@Body request: PostBannerRequest): Response<PostBannerResponse>
//
//    @GET("seller/category")
//    suspend fun getCategory(): GetCategoryResponse
//
//    @GET("seller/category/{id}")
//    suspend fun getCategoryId(): GetCategoryIdResponse
//
//    @POST("seller/product")
//    suspend fun postProduct(@Body request: PostProductRequest): Response<PostProductResponse>
//
//    @GET("seller/product")
//    suspend fun getProduct(): GetProductResponse
//
//    @GET("seller/product/{id}")
//    suspend fun getProductId(): GetProductIdResponse
//
//    @PUT("seller/product/{id}")
//    suspend fun putProductId(@Body request:PutProductIdRequest): Response<PutProductIdResponse>
//
//    @GET("seller/order")
//    suspend fun getOrder() : GetOrderResponse

//    @GET("seller/order/{id}")
//    suspend fun getOrderId() :

//    @PATCH("seller/order/{id}")
//    suspend fun patchOrderId :

//    @GET("seller/order/product{product_id}")
//    suspend fun getOrderProductId():GetOrderIdResponse

}