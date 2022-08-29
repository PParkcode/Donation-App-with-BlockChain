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
import com.example.testing1.DialogInterface
import com.example.testing1.MySharedPreferences
import com.example.testing1.R
import com.example.testing1.Retrofit.RetrofitManager
import com.example.testing1.databinding.ExchangeActivityBinding
import com.example.testing1.viewModel.MemberViewModel



class ExchangeActivity:AppCompatActivity(),DialogInterface {
    private lateinit var binding: ExchangeActivityBinding
    private lateinit var dialog:ExchangeDialog


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView<ExchangeActivityBinding>(this,R.layout.exchange_activity)

        var actionBar: ActionBar?
        actionBar = supportActionBar
        actionBar?.hide()


        var point=intent.getStringExtra("point")


        binding.nextBtn.setOnClickListener{

            dialog= ExchangeDialog(this,point.toString(),this)
            dialog.show()


            /*
            var account:String=binding.accountEdit.text.toString()
            var bncd:String=binding.bncdEdit.text.toString()
            var intent=Intent(this,ExchangeActivity2::class.java)
            intent.putExtra("account",account)
            intent.putExtra("bncd",bncd)
            intent.putExtra("point",point)
            startActivity(intent)

             */
        }
    }

    override fun onNextBtnClick(point:String) {
        Log.d("tag2","ExchangeActivity onNextBtnClick. point is ${point}")
        var account:String=binding.accountEdit.text.toString()
        var bncd:String=binding.bncdEdit.text.toString()
        var intent=Intent(this,ExchangeActivity2::class.java)
        intent.putExtra("account",account)
        intent.putExtra("bncd",bncd)
        intent.putExtra("point",point)
        startActivity(intent)

    }
}