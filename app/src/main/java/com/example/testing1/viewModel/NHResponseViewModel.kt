package com.example.testing1.viewModel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testing1.Retrofit.RetrofitManager.Companion.instance
import com.example.testing1.model.ExchangeData
import com.example.testing1.model.ExchangeResponseData
import com.example.testing1.model.ResponseHeader
import kotlinx.coroutines.launch

class NHResponseViewModel:ViewModel() {
     var _response:MutableLiveData<ExchangeResponseData>? = MutableLiveData<ExchangeResponseData>()
    val response: LiveData<ExchangeResponseData>?
        get()=_response


    @RequiresApi(Build.VERSION_CODES.O)
    fun exchangePoint(data:ExchangeData)=viewModelScope.launch{
        _response?.value=instance.exchange(data)?.value
    }
}