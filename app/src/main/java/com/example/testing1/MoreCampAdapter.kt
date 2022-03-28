package com.example.testing1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testing1.databinding.MoreItemBinding


class MoreCampAdapter(val campaignItemClick: (Campaign) -> Unit):ListAdapter<Campaign,MoreCampAdapter.MoreViewHolder>(CampDiffUtil){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoreCampAdapter.MoreViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val binding=DataBindingUtil.inflate<MoreItemBinding>(layoutInflater,viewType,parent,false)
        return MoreViewHolder(binding)
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.more_item
    }

    override fun getItemCount(): Int {
        return super.getItemCount()
    }

    override fun onBindViewHolder(holder: MoreViewHolder, position: Int) {
        val campaign=getItem(position)
        holder.bind(campaign)
    }

    inner class MoreViewHolder(private val binding:MoreItemBinding):RecyclerView.ViewHolder(binding.root)
    {
        fun bind(campaign: Campaign) {
            binding.campaign = campaign
            binding.executePendingBindings() //데이터가 수정되면 즉각 바인딩

            binding.root.setOnClickListener {
                campaignItemClick(campaign)
            }
        }
    }
    companion object CampDiffUtil: DiffUtil.ItemCallback<Campaign>(){
        override fun areItemsTheSame(oldItem: Campaign, newItem: Campaign): Boolean {
            //각 아이템들의 고유한 값을 비교해야 한다.
            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: Campaign, newItem: Campaign): Boolean {
            return oldItem==newItem
        }
    }




}