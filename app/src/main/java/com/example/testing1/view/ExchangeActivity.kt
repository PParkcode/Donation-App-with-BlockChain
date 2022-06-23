package com.example.testing1.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.testing1.MySharedPreferences
import com.example.testing1.R
import com.example.testing1.Retrofit.RetrofitManager
import com.example.testing1.databinding.ExchangeActivityBinding
import com.example.testing1.viewModel.MemberViewModel
import kotlinx.android.synthetic.main.pay_activity.*
import kr.co.bootpay.Bootpay
import kr.co.bootpay.BootpayAnalytics
import kr.co.bootpay.enums.PG
import kr.co.bootpay.enums.UX
import kr.co.bootpay.model.BootExtra
import kr.co.bootpay.model.BootUser
import java.time.LocalDateTime


class ExchangeActivity:AppCompatActivity() {
    private lateinit var binding: ExchangeActivityBinding



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView<ExchangeActivityBinding>(this,R.layout.exchange_activity)


        var point=intent.getStringExtra("point")


        binding.nextBtn.setOnClickListener{
            var account:String=binding.accountEdit.text.toString()
            var bncd:String=binding.bncdEdit.text.toString()
            var intent=Intent(this,ExchangeActivity2::class.java)
            intent.putExtra("account",account)
            intent.putExtra("bncd",bncd)
            intent.putExtra("point",point)
            startActivity(intent)
        }
    }
}