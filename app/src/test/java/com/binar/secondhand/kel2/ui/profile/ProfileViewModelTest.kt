package com.binar.secondhand.kel2.ui.profile

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.binar.secondhand.kel2.data.api.model.auth.user.GetAuthResponse
import com.binar.secondhand.kel2.data.api.model.auth.user.PutAuthResponse
import com.binar.secondhand.kel2.data.repository.Repository
import com.binar.secondhand.kel2.rule.MainCoroutineRule
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito
import org.mockito.kotlin.given
import org.mockito.kotlin.mock
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class ProfileViewModelTest {

    private lateinit var viewModel: ProfileViewModel
    private lateinit var repository: Repository

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        repository = mock()
        viewModel = ProfileViewModel(repository)
    }

    @Test
    fun putAuth()= runTest{
        val putAuthGetResponse = mock<Response<PutAuthResponse>>()

        val name = "Coba"
        val city = "Jakarta"
        val address = "Jakarta Barat"
        val phoneNumber = 822233445

        val nameBody = name.toRequestBody("text/plain".toMediaTypeOrNull())
        val cityBody = city.toRequestBody("text/plain".toMediaTypeOrNull())
        val addressBody = address.toRequestBody("text/plain".toMediaTypeOrNull())
        val phoneNumberBody = phoneNumber.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val imageBody = mockk<MultipartBody.Part>()

        given(repository.putAuth(
            fullname = nameBody,
            city = cityBody,
            address = addressBody,
            phone_number = phoneNumberBody,
            image = imageBody
        )).willReturn(putAuthGetResponse)

        viewModel.putAuth(
            fullname = nameBody,
            city = cityBody,
            address = addressBody,
            phone_number = phoneNumberBody,
            image = imageBody
        )

        advanceUntilIdle()

        Mockito.verify(repository, Mockito.times(1)).putAuth(
            fullname = nameBody,
            city = cityBody,
            address = addressBody,
            phone_number = phoneNumberBody,
            image = imageBody
        )
        kotlin.test.assertNotNull(viewModel.authPutResponse)
        kotlin.test.assertEquals(viewModel.authPutResponse.value?.data, putAuthGetResponse)
    }

    @Test
    fun getAuth()= runTest{
        val authGetResponse = mock<Response<GetAuthResponse>>()

        given(repository.getAuth()).willReturn(authGetResponse)

        viewModel.getAuth()

        advanceUntilIdle()

        Mockito.verify(repository, Mockito.times(1)).getAuth()
        kotlin.test.assertNotNull(viewModel.authGetResponse)
        kotlin.test.assertEquals(viewModel.authGetResponse.value?.data, authGetResponse)
    }
}