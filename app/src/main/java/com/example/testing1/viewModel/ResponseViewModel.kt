package com.example.testing1.viewModel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testing1.Retrofit.RetrofitManager.Companion.instance
import com.example.testing1.model.ResponseCode
import com.example.testing1.model.WithDrawData
import kotlinx.coroutines.launch

class ResponseViewModel: ViewModel() {
    var _response: MutableLiveData<ResponseCode>?= MutableLiveData()
    val response: LiveData<ResponseCode>?
        get()=_response


    @RequiresApi(Build.VERSION_CODES.O)
    fun getPoint(campaignId:String, data:WithDrawData){
        _response =instance.withDraw(campaignId,data)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun payBackPoint(amount:String){
        _response=instance.payBack(amount)
    }

}