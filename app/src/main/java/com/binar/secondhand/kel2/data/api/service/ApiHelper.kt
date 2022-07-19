package com.binar.secondhand.kel2.data.api.service

import com.binar.secondhand.kel2.data.api.model.auth.login.PostLoginRequest
import com.binar.secondhand.kel2.data.api.model.auth.password.PutPassRequest
import com.binar.secondhand.kel2.data.api.model.auth.register.PostRegisterRequest
import com.binar.secondhand.kel2.data.api.model.buyer.order.post.PostOrderRequest
import com.binar.secondhand.kel2.data.api.model.seller.order.PatchSellerOrderIdRequest
import com.binar.secondhand.kel2.data.api.model.seller.product.id.patch.PatchProductIdRequest
import com.binar.secondhand.kel2.data.api.model.wishlist.post.PostWishlistRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Query


class ApiHelper(val apiService: ApiService) {
    suspend fun postLogin(request: PostLoginRequest) = apiService.postLogin(request)

    suspend fun postRegister(request: PostRegisterRequest) = apiService.postRegister(request)

    suspend fun getAuth() = apiService.getAuth()

    suspend fun putAuth(
        fullname: RequestBody,
        email: RequestBody ?= null,
        password: RequestBody ?= null,
        phone_number: RequestBody,
        address: RequestBody,
        city: RequestBody,
        image: MultipartBody.Part?
    ) = apiService.putAuth(
        fullname,
        email,
        password,
        phone_number,
        address,
        city,
        image
    )

    suspend fun putPass(request: PutPassRequest) = apiService.changePassword(request)

    suspend fun getNotification(type: String = "") = apiService.getNotification(type)

    suspend fun getBanner() = apiService.getBanner()

    suspend fun getProduct(
        status: String? = null,
        categoryId: Int? = null,
        searchKeyword: String? = null,
        page: Int=1,
        itemsPerPage: Int=20
    ) = apiService.getProduct(
        status,
        categoryId,
        searchKeyword,
        page,
        itemsPerPage
    )
    suspend fun getProductDetail(productId: Int) = apiService.getProductDetail(productId)
    suspend fun getOrderProductId(productId: Int) = apiService.getOrderProductId(productId)
    suspend fun getProductOrder(productId: Int) = apiService.getProductOrder(productId)
    suspend fun getUserProfile(userId: Int) = apiService.getUserProfile(userId)
    suspend fun getBuyerOrder() = apiService.getBuyerOrder()
    suspend fun postBuyerOrder(requestBuyerOrder: PostOrderRequest) = apiService.postBuyerOrder(requestBuyerOrder)
    suspend fun deleteOrder(productId: Int) = apiService.deleteOrder(productId)

    suspend fun getCategory() = apiService.getCategory()
    suspend fun getProductId(id: Int) = apiService.getProductId(id)

    suspend fun postProduct(
        name: RequestBody,
        description: RequestBody,
        base_price: RequestBody,
        category_ids: RequestBody,
        location: RequestBody,
        image: MultipartBody.Part?
    ) = apiService.postProduct(
        name,
        description,
        base_price,
        category_ids,
        location,
        image
    )

    //product sale list
    suspend fun getSellerProduct() = apiService.getSellerProduct()

    suspend fun getSellerOrder() = apiService.getSellerOrder()

    suspend fun getSellerOrderId(id:Int) = apiService.getSellerOrderId(id)

    suspend fun patchSellerOrderId(id:Int, request: PatchSellerOrderIdRequest) = apiService.patchSellerOrderId(id, request)

    suspend fun patchSellerProductId(id:Int, request: PatchProductIdRequest) = apiService.patchSellerProductId(id, request)

    suspend fun deleteSellerProduct(id:Int) = apiService.deleteSellerProductId(id)

    suspend fun putProduct(
        id:Int,
        name: RequestBody,
        description: RequestBody,
        base_price: RequestBody,
        category_ids: RequestBody,
        location: RequestBody,
        image: MultipartBody.Part?
    ) = apiService.putProduct(
        id,
        name,
        description,
        base_price,
        category_ids,
        location,
        image
    )

    suspend fun readNotification(id: Int) = apiService.readNotification(id)

    suspend fun getIdWishlist(productId: Int) = apiService.getIdWishlist(productId)

    suspend fun postWishlist(requestBuyerWishlist: PostWishlistRequest) = apiService.postWishlist(requestBuyerWishlist)

    suspend fun getWishlist() = apiService.getWishlist()

    suspend fun deleteWishlist(productId: Int) = apiService.deleteWishlist(productId)

}