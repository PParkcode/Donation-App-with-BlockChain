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
import com.example.testing1.model.CampaignFullDto

private val TAG="tag1"

class CampaignViewModel:ViewModel() {

    @RequiresApi(Build.VERSION_CODES.O)
    private val _campaigns=instance.getCampaignList()
    val campaigns : LiveData<List<Campaign>>
        @RequiresApi(Build.VERSION_CODES.O)
        get()=_campaigns


    fun getCampaignData():LiveData<List<Campaign>>{
        Log.d(TAG,"getCampaignData의 return 값: "+ campaigns.value)
        return campaigns
    }




}