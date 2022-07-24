package com.binar.secondhand.kel2.data.repository

import com.binar.secondhand.kel2.data.api.service.ApiHelper

class MyOrderRepository(private val apiHelper: ApiHelper) {

    suspend fun getMyOrder() = apiHelper.getMyOrder()

}