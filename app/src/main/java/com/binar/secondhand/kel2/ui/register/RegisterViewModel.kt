package com.binar.secondhand.kel2.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binar.secondhand.kel2.data.api.model.auth.register.PostRegisterRequest
import com.binar.secondhand.kel2.data.api.model.auth.register.PostRegisterResponse
import com.binar.secondhand.kel2.data.repository.RegisterRepository
import com.binar.secondhand.kel2.data.resource.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class RegisterViewModel(private val repository: RegisterRepository): ViewModel() {

    private val _registerPostResponse = MutableLiveData<Resource<Response<PostRegisterResponse>>>()
    val registerPostResponse: LiveData<Resource<Response<PostRegisterResponse>>> get() = _registerPostResponse

    fun postRegister(request: PostRegisterRequest){
        viewModelScope.launch {
            _registerPostResponse.postValue(Resource.loading())
            try {
                val dataExample = Resource.success(repository.postRegister(request))
                _registerPostResponse.postValue(dataExample)
            }catch (exp: Exception){
                _registerPostResponse.postValue(Resource.error(exp.localizedMessage ?: "Error occured"))
            }
        }
    }

}