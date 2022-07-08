package com.binar.secondhand.kel2.ui.search.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binar.secondhand.kel2.data.api.model.buyer.product.GetProductResponse
import com.binar.secondhand.kel2.data.repository.HomeRepository
import com.binar.secondhand.kel2.data.resource.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class SearchViewModel(private val repository: HomeRepository) : ViewModel() {

    private val _searchResponse = MutableLiveData<Resource<Response<GetProductResponse>>>()
    val searchResponse : LiveData<Resource<Response<GetProductResponse>>> get() = _searchResponse

    fun getProduct(
        status: String? = null,
        categoryId: Int? = null,
        searchKeyword: String? = null
    ) {
        viewModelScope.launch {
            _searchResponse.postValue(Resource.loading())
            try {
                val dataExample = Resource.success(repository.getProduct(
                    status,
                    categoryId,
                    searchKeyword
                ))
                _searchResponse.postValue(dataExample)
            } catch (exp: Exception) {
                _searchResponse.postValue(
                    Resource.error(
                        exp.localizedMessage ?: "Error occured"
                    )
                )
            }
        }
    }
}