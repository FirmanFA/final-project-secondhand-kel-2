package com.binar.secondhand.kel2.ui.preview

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.binar.secondhand.kel2.data.api.model.auth.user.GetAuthResponse
import com.binar.secondhand.kel2.data.api.model.seller.product.post.PostProductResponse
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
class PreviewViewModelTest {

    private lateinit var viewModel: PreviewViewModel
    private lateinit var repository: Repository

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        repository = mock()
        viewModel = PreviewViewModel(repository)
    }

    @Test
    fun getAuth()= runTest{
        val authGetResponse = mock<Response<GetAuthResponse>>()

        given(repository.getAuth()).willReturn(authGetResponse)

        viewModel.getAuth()

        advanceUntilIdle()

        Mockito.verify(repository, Mockito.times(1)).getAuth()
        kotlin.test.assertNotNull(viewModel.getAuthResponse)
        kotlin.test.assertEquals(viewModel.getAuthResponse.value?.data, authGetResponse)
    }

    @Test
    fun terbit()= runTest{
        val postProductGetResponse = mock<Response<PostProductResponse>>()

        val name = "Coba"
        val description = "Baju"
        val price = 100000
        val category = "Pakaian"
        val city = "Jakarta"

        val nameBody = name.toRequestBody("text/plain".toMediaTypeOrNull())
        val priceBody = price.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val cityBody = city.toRequestBody("text/plain".toMediaTypeOrNull())
        val categoryBody = category.toRequestBody("text/plain".toMediaTypeOrNull())
        val descriptionBody = description.toRequestBody("text/plain".toMediaTypeOrNull())
        val imageBody = mockk<MultipartBody.Part>()

        given(repository.postProduct(
            name = nameBody,
            base_price = priceBody,
            location = cityBody,
            category_ids = categoryBody,
            description = descriptionBody,
            image = imageBody
        )).willReturn(postProductGetResponse)

        viewModel.terbit(
            name = nameBody,
            base_price = priceBody,
            location = cityBody,
            category_ids = categoryBody,
            description = descriptionBody,
            image = imageBody
        )

        advanceUntilIdle()

        Mockito.verify(repository, Mockito.times(1)).postProduct(
            name = nameBody,
            base_price = priceBody,
            location = cityBody,
            category_ids = categoryBody,
            description = descriptionBody,
            image = imageBody
        )
        kotlin.test.assertNotNull(viewModel.terbit)
        kotlin.test.assertEquals(viewModel.terbit.value?.data, postProductGetResponse)
    }
}