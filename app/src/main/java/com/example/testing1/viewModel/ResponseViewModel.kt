package com.example.testing1.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testing1.Retrofit.RetrofitManager.Companion.instance
import com.example.testing1.model.ResponseCode

class ResponseViewModel: ViewModel() {
    var _response: MutableLiveData<ResponseCode>?= MutableLiveData()
    val response: LiveData<ResponseCode>?
        get()=_response

    fun logout(){
        //_response=instance.logout()
    }
}