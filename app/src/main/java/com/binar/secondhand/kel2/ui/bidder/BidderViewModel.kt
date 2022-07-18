package com.binar.secondhand.kel2.ui.bidder

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binar.secondhand.kel2.data.api.model.seller.order.PatchSellerOrderIdRequest
import com.binar.secondhand.kel2.data.api.model.seller.order.PatchSellerOrderIdResponse
import com.binar.secondhand.kel2.data.api.model.seller.order.SellerOrderIdResponse
import com.binar.secondhand.kel2.data.api.model.seller.order.id.GetOrderIdResponse
import com.binar.secondhand.kel2.data.api.model.seller.product.id.patch.PatchProductId
import com.binar.secondhand.kel2.data.api.model.seller.product.id.patch.PatchProductIdRequest
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

    private val _bidderProduct = MutableLiveData<Resource<Response<GetOrderIdResponse>>>()
    val bidderProduct: LiveData<Resource<Response<GetOrderIdResponse>>> get() = _bidderProduct

    fun getOrderProductId(orderId: Int) {
        viewModelScope.launch {
            _bidderProduct.postValue(Resource.loading())
            try {
                val dataProduct = Resource.success(repository.getOrderProductId(orderId))
                _bidderProduct.postValue(dataProduct)
            } catch (exp: Exception) {
                _bidderProduct.postValue(Resource.error(exp.localizedMessage ?: "Error occured"))
            }
        }
    }

    private val _status = MutableLiveData<Resource<Response<PatchSellerOrderIdResponse>>>()
    val status: LiveData<Resource<Response<PatchSellerOrderIdResponse>>> get() = _status

    fun statusItem(id:Int,request: PatchSellerOrderIdRequest) {
        viewModelScope.launch {
            _status.postValue(Resource.loading())
            try {
                val response = Resource.success(repository.patchSellerOrderId(id,request))
                _status.postValue(response)
            } catch (t: Throwable) {
                _status.postValue(Resource.error(t.message))
            }
        }
    }

    private val _statusProduct = MutableLiveData<Resource<Response<PatchProductId>>>()
    val statusProduct: LiveData<Resource<Response<PatchProductId>>> get() = _statusProduct

    fun statusProduct(id:Int, request: PatchProductIdRequest) {
        viewModelScope.launch {
            _statusProduct.postValue(Resource.loading())
            try {
                val response = Resource.success(repository.patchSellerProductId(id,request))
                _statusProduct.postValue(response)
            } catch (t: Throwable) {
                _statusProduct.postValue(Resource.error(t.message))
            }
        }
    }
}