package com.binar.secondhand.kel2.ui.lengkapi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binar.secondhand.kel2.data.api.model.seller.product.post.PostProductRequest
import com.binar.secondhand.kel2.data.api.model.seller.product.post.PostProductResponse
import com.binar.secondhand.kel2.data.repository.Repository
import com.binar.secondhand.kel2.data.resource.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class SellerDetailProductViewModel(private val repository: Repository): ViewModel() {
    private val _sellerPostProduct = MutableLiveData<Resource<Response<PostProductResponse>>>()
    val sellerPostProduct: LiveData<Resource<Response<PostProductResponse>>> get() = _sellerPostProduct

    fun postProduct(request: PostProductRequest){
        viewModelScope.launch {
            _sellerPostProduct.postValue(Resource.loading())
            try {
                val data = repository.postProduct(request)
                _sellerPostProduct.postValue(Resource.success(data))
            }
            catch (e: Exception){
                _sellerPostProduct.postValue(Resource.error(e.message.toString()))
            }
        }
    }
}