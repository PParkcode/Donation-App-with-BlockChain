package com.example.testing1.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.testing1.R
import com.example.testing1.databinding.DetailWithdrawBinding
import com.example.testing1.model.WithDrawData

class DetailWithDraw:AppCompatActivity() {
    private lateinit var binding: DetailWithdrawBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title="캠페인 인출 내역"

        binding=DataBindingUtil.setContentView(this, R.layout.detail_withdraw)

        val intent =intent
        val withDraw = intent.getSerializableExtra("object") as WithDrawData?

        binding.withDraw=withDraw

        binding.blockData.setOnClickListener{
            var intentWeb: Intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://baobab.scope.klaytn.com/tx/${withDraw?.blockChainTransactionId}?tabId=tokenTransfer"));
            startActivity(intentWeb);
        }

    }

}