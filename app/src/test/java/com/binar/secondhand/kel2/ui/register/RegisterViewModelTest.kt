package com.binar.secondhand.kel2.ui.register

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.binar.secondhand.kel2.data.api.model.auth.password.PutPassRequest
import com.binar.secondhand.kel2.data.api.model.auth.register.PostRegisterRequest
import com.binar.secondhand.kel2.data.api.model.auth.register.PostRegisterResponse
import com.binar.secondhand.kel2.data.api.model.auth.user.GetAuthResponse
import com.binar.secondhand.kel2.data.repository.RegisterRepository
import com.binar.secondhand.kel2.data.repository.Repository
import com.binar.secondhand.kel2.rule.MainCoroutineRule
import com.binar.secondhand.kel2.ui.profile.ProfileViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito
import org.mockito.kotlin.given
import org.mockito.kotlin.mock
import retrofit2.Response

class RegisterViewModelTest {

    private lateinit var viewModel: RegisterViewModel
    private lateinit var repository: RegisterRepository

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        repository = mock()
        viewModel = RegisterViewModel(repository)
    }

    @Test
    fun postRegister()= runTest{
        val postRegisterGetResponse = mock<Response<PostRegisterResponse>>()

        val request = PostRegisterRequest(
            "Coba",
            "coba@mail.com",
            "000000",
            822233445,
            "Jakarta Barat",
            "coba.jpg",
            "Jakarta")

        given(repository.postRegister(request)).willReturn(postRegisterGetResponse)

        viewModel.postRegister(request)

        advanceUntilIdle()

        Mockito.verify(repository, Mockito.times(1)).postRegister(request)
        kotlin.test.assertNotNull(viewModel.registerPostResponse)
        kotlin.test.assertEquals(viewModel.registerPostResponse.value?.data, postRegisterGetResponse)
    }
}