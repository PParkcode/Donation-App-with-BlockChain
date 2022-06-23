package com.example.testing1.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testing1.MoreItemBinding
import com.example.testing1.R
import com.example.testing1.databinding.RequestItemBinding
import com.example.testing1.model.Campaign
import com.example.testing1.model.MemberData

class RequestListAdapter(val campaignItemClick: (MemberData) -> Unit): ListAdapter<MemberData, RequestListAdapter.RequestViewHolder>(CampDiffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val binding= DataBindingUtil.inflate<RequestItemBinding>(layoutInflater,viewType,parent,false)
        return RequestViewHolder(binding)
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.request_item
    }

    override fun getItemCount(): Int {
        return super.getItemCount()
    }

    override fun onBindViewHolder(holder: RequestViewHolder, position: Int) {
        val charity=getItem(position)
        holder.bind(charity)
    }

    inner class RequestViewHolder(private val binding: RequestItemBinding): RecyclerView.ViewHolder(binding.root)
    {
        fun bind(memberData: MemberData) {
            binding.charity = memberData
            binding.executePendingBindings() //데이터가 수정되면 즉각 바인딩

            binding.root.setOnClickListener {
                campaignItemClick(memberData)
            }
        }
    }
    companion object CampDiffUtil: DiffUtil.ItemCallback<MemberData>(){
        override fun areItemsTheSame(oldItem: MemberData, newItem: MemberData): Boolean {
            //각 아이템들의 고유한 값을 비교해야 한다.
            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: MemberData, newItem: MemberData): Boolean {
            return oldItem==newItem
        }
    }
}