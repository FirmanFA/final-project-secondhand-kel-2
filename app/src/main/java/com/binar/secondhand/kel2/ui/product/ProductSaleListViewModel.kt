package com.binar.secondhand.kel2.ui.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binar.secondhand.kel2.data.api.model.seller.product.get.GetSellerProductResponse
import com.binar.secondhand.kel2.data.repository.ProductSaleListRepository
import com.binar.secondhand.kel2.data.resource.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class ProductSaleListViewModel(private val repository: ProductSaleListRepository):ViewModel() {

    private val _getSellerProductResponse =
        MutableLiveData<Resource<Response<GetSellerProductResponse>>>()
    val getSellerProductResponse: LiveData<Resource<Response<GetSellerProductResponse>>> get() = _getSellerProductResponse

    fun getSellerProduct() {
        viewModelScope.launch {
            _getSellerProductResponse.postValue(Resource.loading())
            try {
                val dataSellerProduct = Resource.success(repository.getSellerProduct())
                _getSellerProductResponse.postValue(dataSellerProduct)
            } catch (exp: Exception) {
                _getSellerProductResponse.postValue(
                    Resource.error(
                        exp.localizedMessage ?: "Error occured"
                    )
                )
            }
        }
    }

}