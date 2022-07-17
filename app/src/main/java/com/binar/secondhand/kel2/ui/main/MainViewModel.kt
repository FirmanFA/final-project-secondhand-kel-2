package com.binar.secondhand.kel2.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel(): ViewModel() {

    private val _notificationCount = MutableLiveData<Int?>()
    val notificationCount : LiveData<Int?> get() = _notificationCount

    fun setNotificationCount(count: Int?){
        _notificationCount.postValue(count)
    }


}