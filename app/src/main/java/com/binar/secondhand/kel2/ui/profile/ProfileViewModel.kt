package com.binar.secondhand.kel2.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binar.secondhand.kel2.data.api.model.auth.user.GetAuthResponse
import com.binar.secondhand.kel2.data.api.model.auth.user.PutAuthRequest
import com.binar.secondhand.kel2.data.api.model.auth.user.PutAuthResponse
import com.binar.secondhand.kel2.data.repository.Repository
import com.binar.secondhand.kel2.data.resource.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class ProfileViewModel(private val repository: Repository): ViewModel() {

    private val _authPutResponse = MutableLiveData<Resource<Response<PutAuthResponse>>>()
    val authPutResponse: LiveData<Resource<Response<PutAuthResponse>>> get() = _authPutResponse

    fun putAuth(request: PutAuthRequest){
        viewModelScope.launch {
            _authPutResponse.postValue(Resource.loading())
            try {
                val dataAuth = Resource.success(repository.putAuth(request))
                _authPutResponse.postValue(dataAuth)
            }catch (exp: Exception){
                _authPutResponse.postValue(Resource.error(exp.localizedMessage ?: "Error occured"))
            }
        }
    }

    private val _authGetResponse = MutableLiveData<Resource<Response<GetAuthResponse>>>()
    val authGetResponse: LiveData<Resource<Response<GetAuthResponse>>> get() = _authGetResponse

    fun getAuth(){
        viewModelScope.launch {
            _authGetResponse.postValue(Resource.loading())
            try {
                val dataAuth = Resource.success(repository.getAuth())
                _authGetResponse.postValue(dataAuth)
            }catch (exp: Exception){
                _authGetResponse.postValue(Resource.error(exp.localizedMessage ?: "Error occured"))
            }
        }
    }

}