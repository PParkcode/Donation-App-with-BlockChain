package com.example.testing1.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testing1.R
import com.example.testing1.databinding.WithdrawListBinding

import com.example.testing1.model.WithDrawData

class WithDrawAdapter(val withDrawItemClick: (WithDrawData) -> Unit): ListAdapter<WithDrawData, WithDrawAdapter.WithDrawViewHolder>(WithDrawDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WithDrawViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val binding= DataBindingUtil.inflate<WithdrawListBinding>(layoutInflater,viewType,parent,false)
        return WithDrawViewHolder(binding)
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.withdraw_list
    }

    override fun getItemCount(): Int {
        return super.getItemCount()
    }

    override fun onBindViewHolder(holder: WithDrawViewHolder, position: Int) {
        val withDraw=getItem(position)
        holder.bind(withDraw)
    }

    inner class WithDrawViewHolder(private val binding: WithdrawListBinding): RecyclerView.ViewHolder(binding.root)
    {
        fun bind(withDraw: WithDrawData) {
            binding.withDraw = withDraw
            binding.executePendingBindings() //데이터가 수정되면 즉각 바인딩

            binding.root.setOnClickListener {
                withDrawItemClick(withDraw)
            }
        }
    }
    companion object WithDrawDiffUtil: DiffUtil.ItemCallback<WithDrawData>(){
        override fun areItemsTheSame(oldItem: WithDrawData, newItem: WithDrawData): Boolean {
            //각 아이템들의 고유한 값을 비교해야 한다.
            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: WithDrawData, newItem: WithDrawData): Boolean {
            return oldItem==newItem
        }
    }
}