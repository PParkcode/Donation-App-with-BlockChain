package com.example.testing1

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

object RecyclerViewBindingAdapter {
    @BindingAdapter("campaignData")
    @JvmStatic
    fun BindData(recyclerView: RecyclerView, campaigns: List<Campaign>?){
        val adapter = recyclerView.adapter as MoreCampAdapter
        adapter.submitList(campaigns) //For ListAdapter
    }
}