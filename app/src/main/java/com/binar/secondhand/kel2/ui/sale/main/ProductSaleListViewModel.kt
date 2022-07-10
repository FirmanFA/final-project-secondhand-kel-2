package com.binar.secondhand.kel2.ui.sale.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binar.secondhand.kel2.data.api.model.auth.user.GetAuthResponse
import com.binar.secondhand.kel2.data.api.model.buyer.productid.GetProductIdResponse
import com.binar.secondhand.kel2.data.api.model.notification.GetNotificationResponse
import com.binar.secondhand.kel2.data.api.model.seller.order.GetOrderResponse
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

    private val _authGetResponse = MutableLiveData<Resource<Response<GetAuthResponse>>>()
    val authGetResponse: LiveData<Resource<Response<GetAuthResponse>>> get() = _authGetResponse

    fun getAuth() {
        viewModelScope.launch {
            _authGetResponse.postValue(Resource.loading())
            try {
                val dataAuth = Resource.success(repository.getAuth())
                _authGetResponse.postValue(dataAuth)
            } catch (exp: Exception) {
                _authGetResponse.postValue(Resource.error(exp.localizedMessage ?: "Error occured"))
            }
        }
    }

    private val _notificationResponse = MutableLiveData<Resource<Response<GetOrderResponse>>>()
    val notificationResponse: LiveData<Resource<Response<GetOrderResponse>>> get() = _notificationResponse

    fun getNotification(){
        viewModelScope.launch {
            _notificationResponse.postValue(Resource.loading())
            try {
                val notification = Resource.success(repository.getSellerOrder())
                _notificationResponse.postValue(notification)
            }catch (exception : java.lang.Exception){
                _notificationResponse.postValue(Resource.error(exception.message?: "Error Occurred"))
            }
        }
    }

}