package com.binar.secondhand.kel2.ui.preview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binar.secondhand.kel2.data.api.model.auth.user.GetAuthResponse
import com.binar.secondhand.kel2.data.api.model.seller.product.post.PostProductResponse
import com.binar.secondhand.kel2.data.repository.Repository
import com.binar.secondhand.kel2.data.resource.Resource
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

class PreviewViewModel(private val repository: Repository): ViewModel() {

    private val _getAuthResponse = MutableLiveData<Resource<Response<GetAuthResponse>>>()
    val getAuthResponse: LiveData<Resource<Response<GetAuthResponse>>> get() = _getAuthResponse

    private val _terbit = MutableLiveData<Resource<Response<PostProductResponse>>>()
    val terbit: LiveData<Resource<Response<PostProductResponse>>> get() = _terbit

    fun getAuth() {
        viewModelScope.launch {
            _getAuthResponse.postValue(Resource.loading())
            try {
                _getAuthResponse.postValue(Resource.success(repository.getAuth()))
            } catch (t: Throwable) {
                _getAuthResponse.postValue(Resource.error(t.message ?: "Error"))
            }
        }
    }

    fun terbit(
        name: RequestBody,
        description: RequestBody,
        base_price: RequestBody,
        category_ids: RequestBody,
        location: RequestBody,
        image: MultipartBody.Part?
    ){
        viewModelScope.launch {
            _terbit.postValue(Resource.loading())
            try {
                val data = repository.postProduct(
                    name,
                    description,
                    base_price,
                    category_ids,
                    location,
                    image
                )
                _terbit.postValue(Resource.success(data))
            } catch (t: Throwable) {
                _terbit.postValue(Resource.error(t.message ?: "Error"))
            }
        }
    }
}