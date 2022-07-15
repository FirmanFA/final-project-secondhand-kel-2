package com.binar.secondhand.kel2.ui.home

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import androidx.paging.map
import com.binar.secondhand.kel2.data.api.model.auth.user.GetAuthResponse
import com.binar.secondhand.kel2.data.api.model.seller.banner.get.GetBannerResponse
import com.binar.secondhand.kel2.data.api.model.seller.category.get.GetCategoryResponse
import com.binar.secondhand.kel2.data.api.model.buyer.product.GetProductResponse
import com.binar.secondhand.kel2.data.api.model.buyer.product.GetProductResponseItem
import com.binar.secondhand.kel2.data.repository.HomeRepository
import com.binar.secondhand.kel2.data.resource.Resource
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.Response

class HomeViewModel(private val repository: HomeRepository
) : ViewModel() {

    private fun getProducts(): Flow<PagingData<UiModel>> =
        repository.getProductStream()
            .map { pagingData -> pagingData.map { UiModel.ProductItem(it) } }

    private val _getHomeProductResponse =
        MutableLiveData<Resource<Response<GetProductResponse>>>()
    val getHomeProductResponse: LiveData<Resource<Response<GetProductResponse>>> get() = _getHomeProductResponse

    fun getHomeProduct(
        status: String? = null,
        categoryId: Int? = null,
        searchKeyword: String? = null
    ) {
        viewModelScope.launch {
            _getHomeProductResponse.postValue(Resource.loading())
            try {
                val dataExample = Resource.success(repository.getProduct(
                    status,
                    categoryId,
                    searchKeyword
                ))
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


    //banner
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

    private val _authGetResponse = MutableLiveData<Resource<Response<GetAuthResponse>>>()
    val authGetResponse: LiveData<Resource<Response<GetAuthResponse>>> get() = _authGetResponse

    fun getAuth() {
        viewModelScope.launch {
            _authGetResponse.postValue(Resource.loading())
            try {
                val dataAuth = Resource.success(repository.getAuth())
                _authGetResponse.postValue(dataAuth)
            } catch (exp: Exception) {
                _authGetResponse.postValue(Resource.error(exp.localizedMessage ?: "Error occured"))
            }
        }
    }


}

sealed class UiModel {
    data class ProductItem(val products: GetProductResponseItem) : UiModel()
}