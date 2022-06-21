package com.binar.secondhand.kel2.data.repository

import com.binar.secondhand.kel2.data.api.service.ApiHelper

class HomeRepository(private val apiHelper: ApiHelper) {

    suspend fun getBanner() = apiHelper.getBanner()

    suspend fun getProduct(
        status: String? = null,
        categoriId: Int? = null,
        searchKeyword: String? = null
    ) = apiHelper.getProduct(
        status,
        categoriId,
        searchKeyword
    )

    suspend fun getCategory() = apiHelper.getCategory()
}