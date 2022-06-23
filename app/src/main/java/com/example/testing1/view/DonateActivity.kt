package com.example.testing1.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.testing1.R
import com.example.testing1.Retrofit.RetrofitManager
import com.example.testing1.databinding.DonateActivityBinding

class DonateActivity: AppCompatActivity() {
    private lateinit var binding: DonateActivityBinding
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= DataBindingUtil.setContentView<DonateActivityBinding>(this, R.layout.donate_activity)
        setContentView(binding.root)

        binding.donateButton.setOnClickListener{
            RetrofitManager.instance.donate(binding.editPoint.text.toString())
            val intent = Intent(this, MainNav::class.java)
            startActivity(intent)
        }

    }
}