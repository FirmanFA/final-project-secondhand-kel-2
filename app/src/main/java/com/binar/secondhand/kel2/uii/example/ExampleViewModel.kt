package com.binar.secondhand.kel2.uii.example

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.binar.secondhand.kel2.data.api.model.ExampleGetResponse
import com.binar.secondhand.kel2.data.api.model.ExamplePostRequest
import com.binar.secondhand.kel2.data.api.model.ExamplePostResponse
import com.binar.secondhand.kel2.data.repository.ExampleRepository
import com.binar.secondhand.kel2.data.resource.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class ExampleViewModel(private val repository: ExampleRepository): ViewModel() {

    private val _examplePostResponse = MutableLiveData<Resource<Response<ExamplePostResponse>>>()
    val examplePostResponse: LiveData<Resource<Response<ExamplePostResponse>>> get() = _examplePostResponse

    fun postExample(request: ExamplePostRequest){
        viewModelScope.launch {
            _examplePostResponse.postValue(Resource.loading())
            try {
                val dataExample = Resource.success(repository.postExample(request))
                _examplePostResponse.postValue(dataExample)
            }catch (exp: Exception){
                _examplePostResponse.postValue(Resource.error(exp.localizedMessage ?: "Error occured"))
            }
        }
    }

    private val _exampleGetResponse = MutableLiveData<Resource<Response<ExampleGetResponse>>>()
    val exampleGetResponse: LiveData<Resource<Response<ExampleGetResponse>>> get() = _exampleGetResponse

    fun getExample(){
        viewModelScope.launch {
            _exampleGetResponse.postValue(Resource.loading())
            try {
                val dataExample = Resource.success(repository.getExample())
                _exampleGetResponse.postValue(dataExample)
            }catch (exp: Exception){
                _exampleGetResponse.postValue(Resource.error(exp.localizedMessage ?: "Error occured"))
            }
        }
    }

}