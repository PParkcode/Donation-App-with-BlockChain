package com.example.testing1.viewModel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testing1.Retrofit.RetrofitManager
import com.example.testing1.Retrofit.RetrofitManager.Companion.instance
import com.example.testing1.model.Campaign
import com.example.testing1.model.MemberData
import java.text.SimpleDateFormat
import java.util.*

private val TAG="tag1"
class DetailCampaignViewModel:ViewModel() {

    var _campaignData: MutableLiveData<Campaign>? = MutableLiveData()
    val campaignData: LiveData<Campaign>?
        get()=_campaignData

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDetailCampaign(id: String){
        _campaignData=instance.getCampaignDetail(id)
        //return campaignData
    }




}