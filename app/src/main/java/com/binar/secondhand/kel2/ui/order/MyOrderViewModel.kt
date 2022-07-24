package com.binar.secondhand.kel2.ui.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binar.secondhand.kel2.data.api.model.buyer.order.get.GetOrderResponse
import com.binar.secondhand.kel2.data.repository.MyOrderRepository
import com.binar.secondhand.kel2.data.resource.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.http.GET

class MyOrderViewModel(private val repository: MyOrderRepository): ViewModel() {

    private val _myOrderResponse = MutableLiveData<Resource<Response<GetOrderResponse>>>()
    val myOrderResponse: LiveData<Resource<Response<GetOrderResponse>>> get() = _myOrderResponse

    fun getMyOrder(){
        viewModelScope.launch {
            _myOrderResponse.postValue(Resource.loading())
            try {
                val myOrder = Resource.success(repository.getMyOrder())
                _myOrderResponse.postValue(myOrder)
            }catch (exception : java.lang.Exception){
                _myOrderResponse.postValue(Resource.error(exception.message?: "Error Occurred"))
            }
        }
    }

}