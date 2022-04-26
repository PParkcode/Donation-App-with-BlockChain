package com.example.testing1.adapter

import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testing1.adapter.MoreCampAdapter
import com.example.testing1.model.Campaign

private val TAG="tag1"

object RecyclerViewBindingAdapter {

    @BindingAdapter("campaignData")
    @JvmStatic
    fun BindData(recyclerView: RecyclerView, campaigns: List<Campaign>?){
        Log.d(TAG,"BindingAdapter의 BindData()실행 됨")
        val adapter = recyclerView.adapter as MoreCampAdapter
        adapter.submitList(campaigns) //For ListAdapter
    }




}