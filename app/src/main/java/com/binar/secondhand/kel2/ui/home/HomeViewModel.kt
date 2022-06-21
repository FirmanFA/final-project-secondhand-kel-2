package com.binar.secondhand.kel2.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binar.secondhand.kel2.data.api.model.seller.banner.get.GetBannerResponse
import com.binar.secondhand.kel2.data.api.model.seller.category.get.GetCategoryResponse
import com.binar.secondhand.kel2.data.api.model.seller.product.get.GetProductResponse
import com.binar.secondhand.kel2.data.repository.HomeRepository
import com.binar.secondhand.kel2.data.resource.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class HomeViewModel(private val repository: HomeRepository) : ViewModel() {

    private val _getBannerResponse = MutableLiveData<Resource<Response<GetBannerResponse>>>()
    val getBannerResponse: LiveData<Resource<Response<GetBannerResponse>>> get() = _getBannerResponse

    fun getHomeBanner() {
        viewModelScope.launch {
            _getBannerResponse.postValue(Resource.loading())
            try {
                val dataExample = Resource.success(repository.getBanner())
                _getBannerResponse.postValue(dataExample)
            } catch (exp: Exception) {
                _getBannerResponse.postValue(
                    Resource.error(
                        exp.localizedMessage ?: "Error occured"
                    )
                )
            }
        }
    }


    private val _getHomeProductResponse =
        MutableLiveData<Resource<Response<GetProductResponse>>>()
    val getHomeProductResponse: LiveData<Resource<Response<GetProductResponse>>> get() = _getHomeProductResponse

    fun getHomeProduct() {
        viewModelScope.launch {
            _getHomeProductResponse.postValue(Resource.loading())
            try {
                val dataExample = Resource.success(repository.getProduct())
                _getHomeProductResponse.postValue(dataExample)
            } catch (exp: Exception) {
                _getHomeProductResponse.postValue(
                    Resource.error(
                        exp.localizedMessage ?: "Error occured"
                    )
                )
            }
        }
    }


    private val _getCategoryResponse =
        MutableLiveData<Resource<Response<GetCategoryResponse>>>()
    val getCategoryResponse: LiveData<Resource<Response<GetCategoryResponse>>> get() = _getCategoryResponse

    fun getHomeCategory() {
        viewModelScope.launch {
            _getCategoryResponse.postValue(Resource.loading())
            try {
                val dataExample = Resource.success(repository.getCategory())
                _getCategoryResponse.postValue(dataExample)
            } catch (exp: Exception) {
                _getCategoryResponse.postValue(
                    Resource.error(
                        exp.localizedMessage ?: "Error occured"
                    )
                )
            }
        }
    }

}