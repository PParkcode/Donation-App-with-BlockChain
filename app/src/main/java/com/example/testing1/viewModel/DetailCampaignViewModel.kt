package com.example.testing1.viewModel

import android.util.Log
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

    var dDay:MutableLiveData<Int>?= MutableLiveData()


    fun getMyDataResponse(campaignId:String) {

        _campaignData= RetrofitManager.instance.getCampaignDetail(campaignId)
        Log.d(TAG,"viewModel에서 받은 데이터: "+campaignData?.value.toString())
    }


    fun Date.dateToString(format: String, local : Locale = Locale.getDefault()): String{
        val formatter = SimpleDateFormat(format, local)
        return formatter.format(this)
    }

    fun currentDate(): Date {
        return Calendar.getInstance().time
    }
}