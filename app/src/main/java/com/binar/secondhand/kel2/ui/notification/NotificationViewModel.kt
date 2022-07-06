package com.binar.secondhand.kel2.ui.notification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binar.secondhand.kel2.data.api.model.notification.GetNotificationResponse
import com.binar.secondhand.kel2.data.api.model.buyer.productid.GetProductIdResponse
import com.binar.secondhand.kel2.data.repository.Repository
import com.binar.secondhand.kel2.data.resource.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception

class NotificationViewModel(private val repository: Repository): ViewModel() {

    private val _notification = MutableLiveData<Resource<Response<GetNotificationResponse>>>()
    val notificationResponse: LiveData<Resource<Response<GetNotificationResponse>>> get() = _notification

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
}