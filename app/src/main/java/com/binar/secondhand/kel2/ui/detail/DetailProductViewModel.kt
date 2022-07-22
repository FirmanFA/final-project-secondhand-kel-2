package com.binar.secondhand.kel2.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binar.secondhand.kel2.data.api.model.buyer.order.get.GetOrderResponse.GetOrderResponseItem
import com.binar.secondhand.kel2.data.api.model.buyer.order.post.PostOrderRequest
import com.binar.secondhand.kel2.data.api.model.buyer.order.post.PostOrderResponse
import com.binar.secondhand.kel2.data.api.model.buyer.orderid.get.GetBuyerOrderId
import com.binar.secondhand.kel2.data.api.model.buyer.orderid.put.PutOrderIdResponse
import com.binar.secondhand.kel2.data.api.model.buyer.productid.GetProductIdResponse
import com.binar.secondhand.kel2.data.api.model.buyer.productid.UserProduct
import com.binar.secondhand.kel2.data.api.model.seller.product.put.PutSellerProductIdResponse
import com.binar.secondhand.kel2.data.api.model.wishlist.delete.DeleteWishlist
import com.binar.secondhand.kel2.data.api.model.wishlist.get.GetWishlist
import com.binar.secondhand.kel2.data.api.model.wishlist.get.GetWishlistResponse

import com.binar.secondhand.kel2.data.api.model.wishlist.getId.GetIdWishlist
import com.binar.secondhand.kel2.data.api.model.wishlist.post.PostWishlistRequest
import com.binar.secondhand.kel2.data.api.model.wishlist.post.PostWishlist
import com.binar.secondhand.kel2.data.repository.Repository
import com.binar.secondhand.kel2.data.resource.Resource
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

class DetailProductViewModel(private val repository: Repository): ViewModel() {

    private val _detailProduct = MutableLiveData<Resource<Response<GetProductIdResponse>>>()
    val detailProduct: LiveData<Resource<Response<GetProductIdResponse>>> get() = _detailProduct

    private val _getProfile = MutableLiveData<Resource<Response<UserProduct>>>()
    val getProfile : LiveData<Resource<Response<UserProduct>>> get() = _getProfile

    private val _getBuyerOrder :  MutableLiveData<Resource<List<GetOrderResponseItem>>> = MutableLiveData()
    val getBuyerOrder : LiveData<Resource<List<GetOrderResponseItem>>> get() = _getBuyerOrder

    private val _orderProduct = MutableLiveData<Resource<Response<GetBuyerOrderId>>>()
    val orderProduct: LiveData<Resource<Response<GetBuyerOrderId>>> get() = _orderProduct

    private val _buyerOrder = MutableLiveData<Resource<Response<PostOrderResponse>>>()
    val buyerOrder: LiveData<Resource<Response<PostOrderResponse>>> get() = _buyerOrder

    private val _deleteOrder = MutableLiveData<Resource<Response<Unit>>>()
    val deleteOrder: LiveData<Resource<Response<Unit>>> get() = _deleteOrder

    private val _deleteWishlist : MutableLiveData<Resource<Response<DeleteWishlist>>> = MutableLiveData()
    val deleteWishlist : LiveData<Resource<Response<DeleteWishlist>>> get() = _deleteWishlist


    private val _postWishList : MutableLiveData<Resource<Response<PostWishlist>>> = MutableLiveData()
    val postWishlist : LiveData<Resource<Response<PostWishlist>>> get() = _postWishList

    private val _getIdWishlist :  MutableLiveData<Resource<Response<GetIdWishlist>>> = MutableLiveData()
    val getIdWishlist : LiveData<Resource<Response<GetIdWishlist>>> get() = _getIdWishlist


    private val _getWishlist :  MutableLiveData<Resource<Response<GetWishlist>>> = MutableLiveData()
    val getWishlist : LiveData<Resource<Response<GetWishlist>>> get() = _getWishlist

    fun getWishlist(){
        viewModelScope.launch {
            _getWishlist.postValue(Resource.loading())
            try {
                val getWishlist = Resource.success(repository.getWishlist())
                _getWishlist.postValue(getWishlist)
            }catch (exception : Exception){
                _getWishlist.postValue(Resource.error(exception.message?: "Error Occurred"))
            }
        }
    }

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

    fun getProductOrder(productId: Int){
        viewModelScope.launch {
            _orderProduct.postValue(Resource.loading())
            try {
                val dataProduct = Resource.success(repository.getProductOrder(productId))
                _orderProduct.postValue(dataProduct)
            }catch (exp: Exception){
                _orderProduct.postValue(Resource.error(exp.localizedMessage ?: "Error occured"))
            }
        }
    }

    fun getBuyerOrder (){
        viewModelScope.launch {
            _getBuyerOrder.postValue(Resource.loading())
            try{
                _getBuyerOrder.postValue(Resource.success(repository.getBuyerOrder()))
            } catch (e:Exception){
                _getBuyerOrder.postValue(Resource.error(e.localizedMessage?:"Error occurred"))
            }
        }
    }

    fun buyerOrder(requestBuyerOrder: PostOrderRequest){
        viewModelScope.launch {
            _buyerOrder.postValue(Resource.loading())
            try{
                _buyerOrder.postValue(Resource.success(repository.postBuyerOrder(requestBuyerOrder)))
            } catch (e:Exception){
                _buyerOrder.postValue(Resource.error(e.localizedMessage?:"Error occurred"))
            }
        }
    }


    fun deleteOrder(productId: Int){
        viewModelScope.launch {
            _deleteOrder.postValue(Resource.loading())
            try {
                val dataProduct = Resource.success(repository.deleteOrder(productId))
                _deleteOrder.postValue(dataProduct)
            }catch (exp: Exception){
                _deleteOrder.postValue(Resource.error(exp.localizedMessage ?: "Error occured"))
            }
        }
    }


    fun postWishlist(requestBody : PostWishlistRequest){
        viewModelScope.launch {
            _postWishList.postValue(Resource.loading())
            try {
                _postWishList.postValue(Resource.success(repository.postWishlist(requestBody)))
            }catch (exception : Exception){
                _postWishList.postValue(Resource.error(exception.message ?: "Error"))
            }
        }
    }


    fun deleteWishlist(id: Int){
        viewModelScope.launch {
            _deleteWishlist.postValue(Resource.loading())
            try {
                val deleteWishlist = Resource.success(repository.deleteWishlist(id))
                _deleteWishlist.postValue(deleteWishlist)
            }catch (exception : Exception){
                _deleteWishlist.postValue(Resource.error(exception.message ?: "Error"))
            }
        }
    }


    fun getIdWishlist(productId: Int){
        viewModelScope.launch {
            _getIdWishlist.postValue(Resource.loading())
            try {
                val getWishlist = Resource.success(repository.getIdWishlist(productId))
                _getIdWishlist.postValue(getWishlist)
            }catch (exception : Exception){
                _getIdWishlist.postValue(Resource.error(exception.message?: "Error Occurred"))
            }
        }
    }

    private val _editOrder =
        MutableLiveData<Resource<Response<PutOrderIdResponse>>>()
    val editOrder: LiveData<Resource<Response<PutOrderIdResponse>>>
        get() = _editOrder

    fun editOrder(
        id: Int,
        bid_order: RequestBody,
    ) {
        viewModelScope.launch {
            _editOrder.postValue(Resource.loading())
            try {
                val data = repository.putOrder(
                    id,
                    bid_order
                )
                _editOrder.postValue(Resource.success(data))
            } catch (e: Throwable) {
                _editOrder.postValue(Resource.error(e.message.toString()))
            }
        }
    }
}