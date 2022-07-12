package com.binar.secondhand.kel2.ui.bidder

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binar.secondhand.kel2.data.api.model.seller.order.SellerOrderIdResponse
import com.binar.secondhand.kel2.data.repository.Repository
import com.binar.secondhand.kel2.data.resource.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class BidderViewModel(private val repository: Repository) : ViewModel() {

    private val _bidder = MutableLiveData<Resource<Response<SellerOrderIdResponse>>>()
    val bidder: LiveData<Resource<Response<SellerOrderIdResponse>>> get() = _bidder

    fun bidder(id:Int) {
        viewModelScope.launch {
            _bidder.postValue(Resource.loading())
            try {
                val response = Resource.success(repository.getSellerOrderId(id))
                _bidder.postValue(response)
            } catch (t: Throwable) {
                _bidder.postValue(Resource.error(t.message))
            }
        }
    }
}