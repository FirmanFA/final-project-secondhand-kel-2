package com.binar.secondhand.kel2.data.repository

import com.binar.secondhand.kel2.data.api.model.auth.register.PostRegisterRequest
import com.binar.secondhand.kel2.data.api.model.auth.register.PostRegisterResponse
import com.binar.secondhand.kel2.data.api.service.ApiHelper
import com.binar.secondhand.kel2.data.api.service.ApiService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking

import org.junit.Before
import org.junit.Test
import retrofit2.Response

class RegisterRepositoryTest {

    //collaborator
    private lateinit var apiService: ApiService
    private lateinit var apiHelper: ApiHelper

    //system under test
    private lateinit var registerRepository: RegisterRepository

    @Before
    fun setUp() {
        apiService = mockk()
        apiHelper = mockk()

        registerRepository = RegisterRepository(apiHelper)
    }

    @Test
    fun postRegister(): Unit = runBlocking {
        val responsePostRegister = mockk<Response<PostRegisterResponse>>()

        val request = PostRegisterRequest(
            "Coba",
            "coba@mail.com",
            "000000",
            822233445,
            "Jakarta Barat",
            "coba.jpg",
            "Jakarta")

        every {
            runBlocking {
                apiHelper.postRegister(request)
            }
        } returns responsePostRegister

        registerRepository.postRegister(request)
        verify {
            runBlocking {
                apiHelper.postRegister(request)
            }
        }
    }
}