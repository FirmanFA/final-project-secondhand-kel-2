package com.binar.secondhand.kel2.ui.search.page

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binar.secondhand.kel2.data.local.room.model.SearchHistoryEntity
import com.binar.secondhand.kel2.data.repository.SearchHistoryRepository
import com.binar.secondhand.kel2.data.resource.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class SearchPageViewModel(private val repository: SearchHistoryRepository) : ViewModel() {

    private val _searchHistory = MutableLiveData<Resource<List<SearchHistoryEntity>>>()
    val searchResponse : LiveData<Resource<List<SearchHistoryEntity>>> get() = _searchHistory

    fun getSearchHistory() {
        viewModelScope.launch {
            _searchHistory.postValue(Resource.loading())
            try {
                val searchData = Resource.success(repository.getSearchHistory())
                _searchHistory.postValue(searchData)
            } catch (exp: Exception) {
                _searchHistory.postValue(
                    Resource.error(
                        exp.localizedMessage ?: "Error occured"
                    )
                )
            }
        }
    }

    fun insertSearchHistory(searchHistoryEntity: SearchHistoryEntity){
        viewModelScope.launch {
            repository.insertSearchHistory(searchHistoryEntity)
        }
    }

}