package com.example.testing1.adapter

import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testing1.adapter.MoreCampAdapter
import com.example.testing1.model.Campaign
import com.example.testing1.model.TransactionForm

private val TAG="tag1"

object RecyclerViewBindingAdapter {

    @BindingAdapter("campaignData")
    @JvmStatic
    fun BindData(recyclerView: RecyclerView, campaigns: List<Campaign>?){
        Log.d(TAG,"BindingAdapter의 BindData()실행 됨")
        val adapter = recyclerView.adapter as MoreCampAdapter
        adapter.submitList(campaigns) //For ListAdapter
    }

    @BindingAdapter("txData")
    @JvmStatic
    fun BindTx(recyclerView: RecyclerView, transactions: List<TransactionForm>?){

        val adapter = recyclerView.adapter as TransactionListAdapter
        adapter.submitList(transactions) //For ListAdapter
    }




}