package com.binar.secondhand.kel2.ui.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binar.secondhand.kel2.data.api.model.auth.user.GetAuthResponse
import com.binar.secondhand.kel2.data.repository.Repository
import com.binar.secondhand.kel2.data.resource.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class AccountViewModel(private val repository: Repository) : ViewModel() {

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