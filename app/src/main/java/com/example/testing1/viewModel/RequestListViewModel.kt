package com.example.testing1.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testing1.Retrofit.RetrofitManager
import com.example.testing1.model.Campaign
import com.example.testing1.model.MemberData

class RequestListViewModel:ViewModel() {

    private val _list: MutableLiveData<List<MemberData>> = MutableLiveData()  // 이부분은 api 생성해서 수정
    val list : LiveData<List<MemberData>>
        get()=_list


    fun getRequestList(): LiveData<List<MemberData>> {
        Log.d("requestList1","getCampaignData의 return 값: "+ list.value)
        return list
    }
}