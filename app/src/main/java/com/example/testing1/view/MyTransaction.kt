package com.example.testing1.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.testing1.R
import com.example.testing1.adapter.TransactionListAdapter
import com.example.testing1.databinding.MyTransactionBinding
import com.example.testing1.model.TransactionForm
import com.example.testing1.viewModel.TransactionViewModel

private lateinit var binding:MyTransactionBinding
class MyTransaction:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.my_transaction)
        var actionBar: ActionBar?
        actionBar=supportActionBar
        actionBar?.hide()


        binding.lifecycleOwner = this
        binding.viewModel = TransactionViewModel()

        setRecyclerView()


    }

    private fun setRecyclerView() {
        // Set contactItemClick & contactItemLongClick lambda
        val adapter = TransactionListAdapter({ tx -> detailTx(tx)})

        binding.recyclerView.adapter = adapter
        binding.recyclerView.setHasFixedSize(true)
    }


    private fun detailTx(tx: TransactionForm) {

        var intent: Intent =Intent(Intent.ACTION_VIEW, Uri.parse("https://baobab.scope.klaytn.com/tx/"+tx.id
                +"?tabId=tokenTransfer"));
        startActivity(intent);

    }
}