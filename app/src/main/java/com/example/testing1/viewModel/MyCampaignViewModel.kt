package com.example.testing1.viewModel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import com.example.testing1.Retrofit.RetrofitManager
import com.example.testing1.model.Campaign

class MyCampaignViewModel {
    @RequiresApi(Build.VERSION_CODES.O)
    private val _campaigns= RetrofitManager.instance.getMyCampaign()
    val campaigns : LiveData<List<Campaign>>
        @RequiresApi(Build.VERSION_CODES.O)
        get()=_campaigns

}