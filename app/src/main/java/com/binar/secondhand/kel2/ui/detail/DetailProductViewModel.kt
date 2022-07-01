package com.binar.secondhand.kel2.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binar.secondhand.kel2.data.api.model.buyer.productid.GetProductIdResponse
import com.binar.secondhand.kel2.data.api.model.buyer.productid.UserProduct
import com.binar.secondhand.kel2.data.repository.Repository
import com.binar.secondhand.kel2.data.resource.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class DetailProductViewModel(private val repository: Repository): ViewModel() {

    private val _detailProduct = MutableLiveData<Resource<Response<GetProductIdResponse>>>()
    val detailProduct: LiveData<Resource<Response<GetProductIdResponse>>> get() = _detailProduct

    private val _getProfile = MutableLiveData<Resource<Response<UserProduct>>>()
    val getProfile : LiveData<Resource<Response<UserProduct>>> get() = _getProfile

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

    fun getUserProfile(userId: Int)
    {
        viewModelScope.launch {
            _getProfile .postValue(Resource.loading())
            try {
                val dataUser = Resource.success(repository.getUserProfile(userId))
                _getProfile .postValue(dataUser)
            }catch (exp: Exception){
                _getProfile .postValue(Resource.error(exp.localizedMessage ?: "Error occured"))
            }
        }
    }

}