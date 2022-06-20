package com.binar.secondhand.kel2.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binar.secondhand.kel2.data.api.model.auth.login.PostLoginRequest
import com.binar.secondhand.kel2.data.api.model.auth.login.PostLoginResponse
import com.binar.secondhand.kel2.data.repository.LoginRepository
import com.binar.secondhand.kel2.data.resource.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginViewModel(private val repository: LoginRepository): ViewModel() {

    private val _loginPostResponse = MutableLiveData<Resource<Response<PostLoginResponse>>>()
    val loginPostResponse: LiveData<Resource<Response<PostLoginResponse>>> get() = _loginPostResponse

    fun postLogin(request: PostLoginRequest){
        viewModelScope.launch {
            _loginPostResponse.postValue(Resource.loading())
            try {
                val dataExample = Resource.success(repository.postLogin(request))
                _loginPostResponse.postValue(dataExample)
            }catch (exp: Exception){
                _loginPostResponse.postValue(Resource.error(exp.localizedMessage ?: "Error occured"))
            }
        }
    }

}