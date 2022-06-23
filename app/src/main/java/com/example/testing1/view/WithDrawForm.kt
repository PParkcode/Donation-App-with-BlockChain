package com.example.testing1.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.testing1.MySharedPreferences
import com.example.testing1.R
import com.example.testing1.databinding.WithdrawFormBinding
import com.example.testing1.model.WithDrawData
import com.example.testing1.viewModel.ResponseViewModel


class WithDrawForm:AppCompatActivity() {
    private lateinit var binding: WithdrawFormBinding
    val responseViewModel by viewModels<ResponseViewModel>()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var intent = getIntent()
        val campaignId: Int = intent.getIntExtra("id", 0)
        val amount=intent.getIntExtra("amount",0)
        Log.d("with1","campaignID: ${campaignId}\n amount: ${amount}")

        binding= DataBindingUtil.setContentView<WithdrawFormBinding>(this, R.layout.withdraw_form)
        setContentView(binding.root)

        binding.lifecycleOwner = this

        binding.submitBtn.setOnClickListener{
            var data:WithDrawData = WithDrawData("유동근",MySharedPreferences.getUserName(this),binding.purposeEdit.text.toString(),amount)
            responseViewModel.getPoint(campaignId.toString(),data)
        }
        responseViewModel._response?.observe(this, Observer {
            Log.d("with1","withDraw response 데이터 변화 감지!")
            var intent2= Intent(this,MainNav::class.java)
            startActivity(intent2)
        })
    }
}