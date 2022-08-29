package com.example.testing1.view

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.testing1.R
import com.example.testing1.databinding.ActivityLoadingBinding


class LoadingActivity:AppCompatActivity() {

    private lateinit var binding: ActivityLoadingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView<ActivityLoadingBinding>(this,R.layout.activity_loading)

        var actionBar: ActionBar?
        actionBar = supportActionBar
        actionBar?.hide()

        binding.checkBtn.setOnClickListener{
            var intent= Intent(this,MainNav::class.java)
            startActivity(intent)
        }


    }





}