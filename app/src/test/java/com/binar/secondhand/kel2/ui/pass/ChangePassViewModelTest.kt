package com.binar.secondhand.kel2.ui.pass

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.binar.secondhand.kel2.data.api.model.auth.login.PostLoginRequest
import com.binar.secondhand.kel2.data.api.model.auth.login.PostLoginResponse
import com.binar.secondhand.kel2.data.api.model.auth.password.PutPassRequest
import com.binar.secondhand.kel2.data.api.model.auth.password.PutPassResponse
import com.binar.secondhand.kel2.data.api.model.auth.register.PostRegisterRequest
import com.binar.secondhand.kel2.data.repository.LoginRepository
import com.binar.secondhand.kel2.data.repository.Repository
import com.binar.secondhand.kel2.rule.MainCoroutineRule
import com.binar.secondhand.kel2.ui.login.LoginViewModel
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

class ChangePassViewModelTest {

    private lateinit var viewModel: ChangePassViewModel
    private lateinit var repository: Repository

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        repository = mock()
        viewModel = ChangePassViewModel(repository)
    }

    @Test
    fun putChangePass()= runTest{
        val putPassGetResponse = mock<Response<PutPassResponse>>()

        val request = PutPassRequest(
            "000000",
            "111111",
            "111111")

        given(repository.putPass(request)).willReturn(putPassGetResponse)

        viewModel.putChangePass(request)

        advanceUntilIdle()

        Mockito.verify(repository, Mockito.times(1)).putPass(request)
        kotlin.test.assertNotNull(viewModel.putChangePassResponse)
        kotlin.test.assertEquals(viewModel.putChangePassResponse.value?.data, putPassGetResponse)
    }
}