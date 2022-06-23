package com.example.testing1.adapter

import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testing1.model.Campaign
import com.example.testing1.model.MemberData

object RequestBindingAdapter {
    @BindingAdapter("charityData")
    @JvmStatic
    fun BindData(recyclerView: RecyclerView, charities: List<MemberData>?){
        Log.d("Bind1","RequestBindingAdapter의 BindData()실행 됨")
        val adapter = recyclerView.adapter as RequestListAdapter
        adapter.submitList(charities) //For ListAdapter
    }
}