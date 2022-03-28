package com.example.testing1

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testing1.Retrofit.RetrofitManager
import com.example.testing1.Retrofit.RetrofitManager.Companion.instance
private val TAG="tag1"

class LoginViewModel:ViewModel() {
    val _member:MutableLiveData<MemberData>? = MutableLiveData()

    var _loginResponse:MutableLiveData<ResponseCode>?= MutableLiveData()

    val member: LiveData<MemberData>?
        get()=_member

    val loginResponse:LiveData<ResponseCode>?
        get()=_loginResponse

    fun getLoginResponse(idPwd:LoginData) {
       // _loginCode?.setValue(instance.loginCall(idPwd)?.value)
        _loginResponse= instance.loginCall(idPwd)
        Log.d(TAG,"viewModel에서 받은 데이터: "+loginResponse?.value.toString())
        //_loginCode.postValue(instance.loginCall((idPwd)?.va))

    }

}