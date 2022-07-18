package com.binar.secondhand.kel2.data.repository

import com.binar.secondhand.kel2.data.api.model.auth.password.PutPassRequest
import com.binar.secondhand.kel2.data.api.model.auth.user.PutAuthRequest
import com.binar.secondhand.kel2.data.api.service.ApiHelper
import okhttp3.MultipartBody
import okhttp3.RequestBody
import com.binar.secondhand.kel2.data.api.model.auth.register.PostRegisterRequest
import com.binar.secondhand.kel2.data.api.model.buyer.order.post.PostOrderRequest
import com.binar.secondhand.kel2.data.api.model.seller.order.PatchSellerOrderIdRequest
import com.binar.secondhand.kel2.data.api.model.seller.product.post.PostProductRequest
import com.binar.secondhand.kel2.data.api.model.wishlist.post.PostWishlistRequest
import retrofit2.http.Path

class Repository(private val apiHelper: ApiHelper) {

    suspend fun getAuth() = apiHelper.getAuth()

    suspend fun putAuth(
        fullname: RequestBody,
        email: RequestBody ?= null,
        password: RequestBody ?= null,
        phone_number: RequestBody,
        address: RequestBody,
        city: RequestBody,
        image: MultipartBody.Part?
    ) = apiHelper.putAuth(
        fullname,
        email,
        password,
        phone_number,
        address,
        city,
        image
    )

    suspend fun putPass(request: PutPassRequest) = apiHelper.putPass(request)

    suspend fun getNotification() = apiHelper.getNotification()
    suspend fun getBuyerOrder() = apiHelper.getBuyerOrder()
    suspend fun getOrderProductId(productId: Int) = apiHelper.getOrderProductId(productId)
    suspend fun getProductId(id: Int) = apiHelper.getProductId(id)
    suspend fun getProductDetail(productId: Int) = apiHelper.getProductDetail(productId)
    suspend fun getUserProfile(userId: Int) = apiHelper.getUserProfile(userId)
    suspend fun postBuyerOrder(requestBuyerOrder: PostOrderRequest) = apiHelper.postBuyerOrder(requestBuyerOrder)
    suspend fun postProduct(
        name: RequestBody,
        description: RequestBody,
        base_price: RequestBody,
        category_ids: RequestBody,
        location: RequestBody,
        image: MultipartBody.Part?
    ) = apiHelper.postProduct(
        name,
        description,
        base_price,
        category_ids,
        location,
        image
    )
    suspend fun getSellerOrderId(id:Int) = apiHelper.getSellerOrderId(id)
    suspend fun patchSellerOrderId(id:Int, request: PatchSellerOrderIdRequest) = apiHelper.patchSellerOrderId(id, request)
    suspend fun putProduct(
        id: Int,
        name: RequestBody,
        description: RequestBody,
        base_price: RequestBody,
        category_ids: RequestBody,
        location: RequestBody,
        image: MultipartBody.Part?
    ) = apiHelper.putProduct(
        id,
        name,
        description,
        base_price,
        category_ids,
        location,
        image
    )
    suspend fun getWishlist() = apiHelper.getWishlist()
    suspend fun deleteWishlist(productId: Int) = apiHelper.deleteWishlist(productId)
    suspend fun getIdWishlist(productId: Int) = apiHelper.getIdWishlist(productId)
    suspend fun postWishlist(requestBuyerWishlist: PostWishlistRequest) = apiHelper.postWishlist(requestBuyerWishlist)
    suspend fun getCategory() = apiHelper.getCategory()

}