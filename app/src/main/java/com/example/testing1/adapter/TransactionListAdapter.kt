package com.example.testing1.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testing1.R
import com.example.testing1.databinding.MoreTxBinding


import com.example.testing1.model.TransactionForm

class TransactionListAdapter(val txItemClick: (TransactionForm) -> Unit): ListAdapter<TransactionForm, TransactionListAdapter.TxViewHolder>(TxDiffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TxViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val binding= DataBindingUtil.inflate<MoreTxBinding>(layoutInflater,viewType,parent,false)
        return TxViewHolder(binding)
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.more_tx
    }

    override fun getItemCount(): Int {
        return super.getItemCount()
    }

    override fun onBindViewHolder(holder: TxViewHolder, position: Int) {
        val tx=getItem(position)
        holder.bind(tx)
    }

    inner class TxViewHolder(private val binding: MoreTxBinding): RecyclerView.ViewHolder(binding.root)
    {
        fun bind(tx:TransactionForm) {
            binding.tx = tx
            binding.executePendingBindings() //데이터가 수정되면 즉각 바인딩

            binding.root.setOnClickListener {
                txItemClick(tx)
            }
        }
    }
    companion object TxDiffUtil: DiffUtil.ItemCallback<TransactionForm>(){
        override fun areItemsTheSame(oldItem: TransactionForm, newItem: TransactionForm): Boolean {
            //각 아이템들의 고유한 값을 비교해야 한다.
            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: TransactionForm, newItem: TransactionForm): Boolean {
            return oldItem==newItem
        }
    }
}