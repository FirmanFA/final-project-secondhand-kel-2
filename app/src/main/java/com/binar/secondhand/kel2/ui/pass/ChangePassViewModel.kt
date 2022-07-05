package com.binar.secondhand.kel2.ui.pass

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binar.secondhand.kel2.data.api.model.auth.password.PutPassRequest
import com.binar.secondhand.kel2.data.api.model.auth.password.PutPassResponse
import com.binar.secondhand.kel2.data.api.model.auth.user.PutAuthResponse
import com.binar.secondhand.kel2.data.repository.Repository
import com.binar.secondhand.kel2.data.resource.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class ChangePassViewModel(private val repository: Repository): ViewModel() {
    private val _putChangePassResponse = MutableLiveData<Resource<Response<PutPassResponse>>>()
    val putChangePassResponse : LiveData<Resource<Response<PutPassResponse>>> get() = _putChangePassResponse

    fun putChangePass(request: PutPassRequest){
        viewModelScope.launch {
            _putChangePassResponse.postValue(Resource.loading())
            try {
                val dataPass = Resource.success(repository.putPass(request))
                _putChangePassResponse.postValue(dataPass)
            }catch (t: Exception){
                _putChangePassResponse.postValue(Resource.error(t.message))
            }
        }
    }
}