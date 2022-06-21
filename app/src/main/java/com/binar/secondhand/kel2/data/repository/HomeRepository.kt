package com.binar.secondhand.kel2.data.repository

import com.binar.secondhand.kel2.data.api.service.ApiHelper

class HomeRepository(private val apiHelper: ApiHelper) {

    suspend fun getBanner() = apiHelper.getBanner()

    suspend fun getProduct(
        status: String? = null,
        categoryId: Int? = null,
        searchKeyword: String? = null
    ) = apiHelper.getProduct(
        status,
        categoryId,
        searchKeyword
    )

    suspend fun getCategory() = apiHelper.getCategory()
}