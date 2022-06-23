package com.binar.secondhand.kel2.ui.notification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binar.secondhand.kel2.data.api.model.notification.GetNotificationResponse
import com.binar.secondhand.kel2.data.api.model.seller.product.id.get.GetProductIdResponse
import com.binar.secondhand.kel2.data.repository.Repository
import com.binar.secondhand.kel2.data.resource.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception

class NotificationViewModel(private val repository: Repository): ViewModel() {
    private val _notification = MutableLiveData<Resource<Response<GetNotificationResponse>>>()
    private val _sellerProductId = MutableLiveData<Resource<Response<GetProductIdResponse>>>()
    val notificationResponse: LiveData<Resource<Response<GetNotificationResponse>>> get() = _notification
    val sellerProductIdResponse: LiveData<Resource<Response<GetProductIdResponse>>> get() = _sellerProductId

    fun getNotification(){
        viewModelScope.launch {
            _notification.postValue(Resource.loading())
            try {
                val notification = Resource.success(repository.getNotification())
                _notification.postValue(notification)
            }catch (exception : Exception){
                _notification.postValue(Resource.error(exception.message?: "Error Occurred"))
            }
        }
    }

    fun getSellerProductId(){
        viewModelScope.launch {
            _sellerProductId.postValue(Resource.loading())
            try {
                val sellerProductId = Resource.success(repository.getProductId())
                _sellerProductId.postValue(sellerProductId)
            }catch (exception : Exception){
                _sellerProductId.postValue(Resource.error(exception.message?: "Error Occurred"))
            }
        }
    }
}