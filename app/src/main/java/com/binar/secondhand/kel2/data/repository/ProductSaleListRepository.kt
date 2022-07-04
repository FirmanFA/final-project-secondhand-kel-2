package com.binar.secondhand.kel2.data.repository

import com.binar.secondhand.kel2.data.api.service.ApiHelper

class ProductSaleListRepository(private val apiHelper: ApiHelper) {

    suspend fun getSellerProduct() = apiHelper.getSellerProduct()

    suspend fun getAuth() = apiHelper.getAuth()

    suspend fun getNotification() = apiHelper.getNotification()
}