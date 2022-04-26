package com.example.testing1.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.testing1.Retrofit.RetrofitManager
import com.example.testing1.model.MemberData

private val TAG="tag1"

class RegistViewModel {
    var _registCode: MutableLiveData<Int>?= MutableLiveData()

    val registCode: LiveData<Int>?
        get()=_registCode

    fun getLoginResponse(data: MemberData) {
        // _loginCode?.setValue(instance.loginCall(idPwd)?.value)
        _registCode= RetrofitManager.instance.registCall(data)
        Log.d(TAG,"viewModel에서 받은 데이터: "+registCode?.value.toString())
        //_loginCode.postValue(instance.loginCall((idPwd)?.va))

    }
}