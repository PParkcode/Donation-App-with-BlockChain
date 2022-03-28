package com.example.testing1

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testing1.Retrofit.RetrofitManager
import com.example.testing1.Retrofit.RetrofitManager.Companion.instance
private val TAG="tag1"

class CampaignViewModel:ViewModel() {
    private var _campaigns:MutableLiveData<List<Campaign>>? = MutableLiveData()
    //private val campaigns=instance.getRecommList()

    val campaigns : LiveData<List<Campaign>>?
        get()=_campaigns


    fun getCampList(){
        _campaigns= instance.getCampaignList()
        Log.d(TAG,"CampaignViewModel에서 받은 데이터: "+campaigns?.value.toString())

    }
    fun getCampaignData():LiveData<List<Campaign>>{
        return instance.getCampaignList()
    }




}