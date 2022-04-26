package com.example.testing1.adapter

import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.testing1.model.Campaign

object RecommViewPagerBindingAdapter {
    @BindingAdapter("recommCampaign")
    @JvmStatic
    fun bindData(recyclerView: ViewPager2, campaigns: List<Campaign>?){
        Log.d("tag1","RecommBindingAdapter의 BindData()실행 됨")
        val adapter = recyclerView.adapter as RecommViewPagerAdapter
        adapter.submitList(campaigns) //For ListAdapter
    }
}