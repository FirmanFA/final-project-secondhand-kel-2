package com.binar.secondhand.kel2.ui.notification

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.binar.secondhand.kel2.data.api.model.notification.GetNotificationResponse
import com.binar.secondhand.kel2.rule.MainCoroutineRule
import com.binar.secondhand.kel2.data.repository.NotificationRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.kotlin.*
import retrofit2.Response
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@OptIn(ExperimentalCoroutinesApi::class)
class NotificationViewModelTest {

    private lateinit var viewModel: NotificationViewModel
    private lateinit var repository: NotificationRepository

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()


    @Before
    fun setUp() {
        repository = mock()
        viewModel = NotificationViewModel(repository)
    }

    @Test
    fun getNotificationTest()= runTest{
        val notificationResponse = mock<Response<GetNotificationResponse>>()

        given(repository.getNotification()).willReturn(notificationResponse)

        viewModel.getNotification()

        advanceUntilIdle()

        Mockito.verify(repository, times(1)).getNotification()
        assertNotNull(viewModel.notificationResponse.value?.status)
        assertEquals(viewModel.notificationResponse.value?.data, notificationResponse)

    }

    @Test
    fun readNotification()= runTest{
        val unitResponse = mock<Response<Unit>>()

        val id = 1

        given(repository.readNotification(id)).willReturn(unitResponse)

        viewModel.readNotification(id)

        advanceUntilIdle()

        Mockito.verify(repository, times(1)).readNotification(id)
        assertNotNull(viewModel.readNotificationResponse.value?.status)
        assertEquals(viewModel.readNotificationResponse.value?.data, unitResponse)

    }
}