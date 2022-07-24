package com.binar.secondhand.kel2.data.repository

import com.binar.secondhand.kel2.data.api.model.auth.login.PostLoginRequest
import com.binar.secondhand.kel2.data.api.model.auth.login.PostLoginResponse
import com.binar.secondhand.kel2.data.api.model.notification.GetNotificationResponse
import com.binar.secondhand.kel2.data.api.service.ApiHelper
import com.binar.secondhand.kel2.data.api.service.ApiService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import retrofit2.Response

class NotificationRepositoryTest {

    //collaborator
    private lateinit var apiService: ApiService
    private lateinit var apiHelper: ApiHelper

    //system under test
    private lateinit var notificationRepository: NotificationRepository

    @Before
    fun setUp() {
        apiService = mockk()
        apiHelper = mockk()

        notificationRepository = NotificationRepository(apiHelper)
    }

    @Test
    fun getNotification(): Unit = runBlocking {
        val getNotificationResponse = mockk<Response<GetNotificationResponse>>()

        every {
            runBlocking {
                apiHelper.getNotification()
            }
        } returns getNotificationResponse

        notificationRepository.getNotification()

        verify {
            runBlocking {
                apiHelper.getNotification()
            }
        }
    }

    @Test
    fun readNotification(): Unit = runBlocking {
        val unit = mockk<Response<Unit>>()

        val id = 1

        every {
            runBlocking {
                apiHelper.readNotification(id)
            }
        } returns unit

        notificationRepository.readNotification(id)

        verify {
            runBlocking {
                apiHelper.readNotification(id)
            }
        }
    }
}