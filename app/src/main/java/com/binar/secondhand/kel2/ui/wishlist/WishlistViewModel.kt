package com.binar.secondhand.kel2.ui.wishlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binar.secondhand.kel2.data.api.model.buyer.order.get.GetOrderResponse
import com.binar.secondhand.kel2.data.api.model.wishlist.get.GetWishlistItem
import com.binar.secondhand.kel2.data.api.model.wishlist.get.GetWishlistResponse
import com.binar.secondhand.kel2.data.repository.Repository
import com.binar.secondhand.kel2.data.resource.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class WishlistViewModel(private val repository: Repository): ViewModel() {

    private val _getWishlist :  MutableLiveData<Resource<List<GetWishlistResponse>>> = MutableLiveData()
    val getWishlist : LiveData<Resource<List<GetWishlistResponse>>> get() = _getWishlist

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

}