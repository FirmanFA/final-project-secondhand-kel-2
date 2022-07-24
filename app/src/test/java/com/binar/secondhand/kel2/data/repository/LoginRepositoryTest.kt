package com.binar.secondhand.kel2.data.repository

import com.binar.secondhand.kel2.data.api.model.auth.login.PostLoginRequest
import com.binar.secondhand.kel2.data.api.model.auth.login.PostLoginResponse
import com.binar.secondhand.kel2.data.api.service.ApiHelper
import com.binar.secondhand.kel2.data.api.service.ApiService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking

import org.junit.Before
import org.junit.Test
import retrofit2.Response

class LoginRepositoryTest {

    //collaborator
    private lateinit var apiService: ApiService
    private lateinit var apiHelper: ApiHelper

    //system under test
    private lateinit var loginRepository: LoginRepository

    @Before
    fun setUp() {
        apiService = mockk()
        apiHelper = mockk()

        loginRepository = LoginRepository(apiHelper)
    }

    @Test
    fun postLogin(): Unit = runBlocking {
        val responsePostLogin = mockk< Response<PostLoginResponse>>()

        val request = PostLoginRequest("johnde@mail.com", "123456")

        every {
            runBlocking {
                apiHelper.postLogin(request)
            }
        } returns responsePostLogin

        loginRepository.postLogin(request)
        verify {
            runBlocking {
                apiHelper.postLogin(request)
            }
        }
    }
}