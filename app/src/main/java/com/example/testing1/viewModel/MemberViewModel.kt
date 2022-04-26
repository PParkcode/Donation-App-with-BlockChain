package com.example.testing1.viewModel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testing1.Retrofit.RetrofitManager
import com.example.testing1.model.LoginData
import com.example.testing1.model.MemberData

private val TAG="tag1"

class MemberViewModel:ViewModel() {
    var _member: MutableLiveData<MemberData>? = MutableLiveData()
    val member: LiveData<MemberData>?
        get()=_member

    @RequiresApi(Build.VERSION_CODES.O)
    fun getMyDataResponse() {

       _member= RetrofitManager.instance.getMyData()
        Log.d(TAG,"viewModel에서 받은 데이터: "+member?.value.toString())
        //_loginCode.postValue(instance.loginCall((idPwd)?.va))

    }
}