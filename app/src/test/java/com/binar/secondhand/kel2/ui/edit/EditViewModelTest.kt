package com.binar.secondhand.kel2.ui.edit

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.binar.secondhand.kel2.data.api.model.buyer.productid.GetProductIdResponse
import com.binar.secondhand.kel2.data.api.model.seller.category.get.GetCategoryResponse
import com.binar.secondhand.kel2.data.api.model.seller.product.put.PutSellerProductIdResponse
import com.binar.secondhand.kel2.data.repository.Repository
import com.binar.secondhand.kel2.rule.MainCoroutineRule
import com.binar.secondhand.kel2.ui.detail.DetailProductViewModel
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito
import org.mockito.kotlin.given
import org.mockito.kotlin.mock
import retrofit2.Response

class EditViewModelTest {

    private lateinit var viewModel: EditViewModel
    private lateinit var repository: Repository

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        repository = mock()
        viewModel = EditViewModel(repository)
    }

    @Test
    fun editDetailProduct()= runTest{
        val putSellerProductIdGetResponse = mock<Response<PutSellerProductIdResponse>>()

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

        given(repository.putProduct(
            1,
            name = nameBody,
            base_price = priceBody,
            location = cityBody,
            category_ids = categoryBody,
            description = descriptionBody,
            image = imageBody
        )).willReturn(putSellerProductIdGetResponse)

        viewModel.editDetailProduct(
            1,
            name = nameBody,
            base_price = priceBody,
            location = cityBody,
            category_ids = categoryBody,
            description = descriptionBody,
            image = imageBody
        )

        advanceUntilIdle()

        Mockito.verify(repository, Mockito.times(1)).putProduct(
            1,
            name = nameBody,
            base_price = priceBody,
            location = cityBody,
            category_ids = categoryBody,
            description = descriptionBody,
            image = imageBody
        )
        kotlin.test.assertNotNull(viewModel.editDetailProduct)
        kotlin.test.assertEquals(viewModel.editDetailProduct.value?.data, putSellerProductIdGetResponse)
    }

    @Test
    fun getDetailProduct()= runTest{
        val getProductIdResponse = mock<Response<GetProductIdResponse>>()

        val productId = 1

        given(repository.getProductDetail(productId)).willReturn(getProductIdResponse)

        viewModel.getDetailProduct(productId)

        advanceUntilIdle()

        Mockito.verify(repository, Mockito.times(1)).getProductDetail(productId)
        kotlin.test.assertNotNull(viewModel.detailProduct)
        kotlin.test.assertEquals(viewModel.detailProduct.value?.data, getProductIdResponse)
    }

    @Test
    fun getCategory()= runTest{
        val getCategoryResponse = mock<Response<GetCategoryResponse>>()

        given(repository.getCategory()).willReturn(getCategoryResponse)

        viewModel.getCategory()

        advanceUntilIdle()

        Mockito.verify(repository, Mockito.times(1)).getCategory()
        kotlin.test.assertNotNull(viewModel.getCategoryResponse)
        kotlin.test.assertEquals(viewModel.getCategoryResponse.value?.data, getCategoryResponse)
    }
}