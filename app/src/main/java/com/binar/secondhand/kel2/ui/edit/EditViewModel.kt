package com.binar.secondhand.kel2.ui.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binar.secondhand.kel2.data.api.model.buyer.productid.GetProductIdResponse
import com.binar.secondhand.kel2.data.api.model.seller.category.get.GetCategoryResponse
import com.binar.secondhand.kel2.data.api.model.seller.product.put.PutSellerProductIdResponse
import com.binar.secondhand.kel2.data.repository.Repository
import com.binar.secondhand.kel2.data.resource.Resource
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

class EditViewModel(private val repository: Repository) : ViewModel() {
    private val _editDetailProduct =
        MutableLiveData<Resource<Response<PutSellerProductIdResponse>>>()
    val editDetailProduct: LiveData<Resource<Response<PutSellerProductIdResponse>>>
        get() = _editDetailProduct

    fun editDetailProduct(
        id: Int,
        name: RequestBody,
        description: RequestBody,
        base_price: RequestBody,
        category_ids: RequestBody,
        location: RequestBody,
        image: MultipartBody.Part?
    ) {
        viewModelScope.launch {
            _editDetailProduct.postValue(Resource.loading())
            try {
                val data = repository.putProduct(
                    id,
                    name,
                    description,
                    base_price,
                    category_ids,
                    location,
                    image
                )
                _editDetailProduct.postValue(Resource.success(data))
            } catch (e: Throwable) {
                _editDetailProduct.postValue(Resource.error(e.message.toString()))
            }
        }
    }

    private val _detailProduct = MutableLiveData<Resource<Response<GetProductIdResponse>>>()
    val detailProduct: LiveData<Resource<Response<GetProductIdResponse>>> get() = _detailProduct

    fun getDetailProduct(productId: Int){
        viewModelScope.launch {
            _detailProduct.postValue(Resource.loading())
            try {
                val dataProduct = Resource.success(repository.getProductDetail(productId))
                _detailProduct.postValue(dataProduct)
            }catch (exp: Exception){
                _detailProduct.postValue(Resource.error(exp.localizedMessage ?: "Error occured"))
            }
        }
    }

    private val _getCategoryResponse = MutableLiveData<Resource<Response<GetCategoryResponse>>>()
    val getCategoryResponse: LiveData<Resource<Response<GetCategoryResponse>>> get() = _getCategoryResponse

    fun getCategory() {
        viewModelScope.launch {
            _getCategoryResponse.postValue(Resource.loading())
            try {
                val dataExample = Resource.success(repository.getCategory())
                _getCategoryResponse.postValue(dataExample)
            } catch (exp: Exception) {
                _getCategoryResponse.postValue(
                    Resource.error(
                        exp.localizedMessage ?: "Error occured"
                    )
                )
            }
        }
    }
}