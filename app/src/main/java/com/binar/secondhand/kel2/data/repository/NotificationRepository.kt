package com.binar.secondhand.kel2.data.repository

import com.binar.secondhand.kel2.data.api.service.ApiHelper

class NotificationRepository(private val apiHelper: ApiHelper) {
    suspend fun getNotification(type: String = "") = apiHelper.getNotification(type)
}