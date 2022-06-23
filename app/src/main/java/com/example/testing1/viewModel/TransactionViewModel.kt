package com.example.testing1.viewModel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.testing1.Retrofit.RetrofitManager
import com.example.testing1.model.Campaign
import com.example.testing1.model.TransactionForm

class TransactionViewModel:ViewModel() {
    @RequiresApi(Build.VERSION_CODES.O)
    private val _transactions= RetrofitManager.instance.getTxList()
    val transactions: LiveData<List<TransactionForm>>
        @RequiresApi(Build.VERSION_CODES.O)
        get()=_transactions

}