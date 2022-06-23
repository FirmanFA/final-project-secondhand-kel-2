package com.binar.secondhand.kel2.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binar.secondhand.kel2.data.api.model.auth.user.GetAuthResponse
import com.binar.secondhand.kel2.data.api.model.buyer.order.post.PostOrderRequest
import com.binar.secondhand.kel2.data.api.model.buyer.order.post.PostOrderResponse
import com.binar.secondhand.kel2.data.api.model.buyer.productid.GetProductIdResponse
import com.binar.secondhand.kel2.data.repository.Repository
import com.binar.secondhand.kel2.data.resource.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class PengajuanPenawaranViewModel(private val repository: Repository): ViewModel() {

    private val _detailProduct = MutableLiveData<Resource<Response<GetProductIdResponse>>>()
    val detailProduct: LiveData<Resource<Response<GetProductIdResponse>>> get() = _detailProduct


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

    private val _buyerOrder = MutableLiveData<Resource<Response<PostOrderResponse>>>()
    val buyerOrder: LiveData<Resource<Response<PostOrderResponse>>> get() = _buyerOrder


    fun postBuyerOrder(request: PostOrderRequest){
        viewModelScope.launch {
            _buyerOrder.postValue(Resource.loading())
            try {
                val Order = Resource.success(repository.postBuyerOrder(request))
                _buyerOrder.postValue(Order)
            }catch (exp: Exception){
                _buyerOrder.postValue(Resource.error(exp.localizedMessage ?: "Error occured"))
            }
        }
    }



}