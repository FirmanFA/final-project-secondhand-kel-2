package com.binar.secondhand.kel2.ui.bidder.bottomDialog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binar.secondhand.kel2.data.api.model.seller.order.id.GetOrderIdResponse
import com.binar.secondhand.kel2.data.repository.Repository
import com.binar.secondhand.kel2.data.resource.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class BidderStatusViewModel(private val repository: Repository): ViewModel() {

    private val _bidderProduct = MutableLiveData<Resource<Response<GetOrderIdResponse>>>()
    val bidderProduct: LiveData<Resource<Response<GetOrderIdResponse>>> get() = _bidderProduct


}