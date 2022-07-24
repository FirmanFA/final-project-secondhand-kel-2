package com.binar.secondhand.kel2.ui.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.binar.secondhand.kel2.data.api.model.auth.login.PostLoginRequest
import com.binar.secondhand.kel2.data.api.model.auth.login.PostLoginResponse
import com.binar.secondhand.kel2.data.repository.LoginRepository
import com.binar.secondhand.kel2.rule.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito
import org.mockito.kotlin.given
import org.mockito.kotlin.mock
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    private lateinit var viewModel: LoginViewModel
    private lateinit var repository: LoginRepository

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        repository = mock()
        viewModel = LoginViewModel(repository)
    }

    @Test
    fun postLogin()= runTest{
        val postLoginGetResponse = mock<Response<PostLoginResponse>>()

        val request = PostLoginRequest("johnde@mail.com", "123456")

        given(repository.postLogin(request)).willReturn(postLoginGetResponse)

        viewModel.postLogin(request)

        advanceUntilIdle()

        Mockito.verify(repository, Mockito.times(1)).postLogin(request)
        kotlin.test.assertNotNull(viewModel.loginPostResponse)
        kotlin.test.assertEquals(viewModel.loginPostResponse.value?.data, postLoginGetResponse)
    }
}