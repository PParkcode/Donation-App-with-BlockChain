package com.example.testing1.view

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.testing1.R
import com.example.testing1.Retrofit.RetrofitManager
import com.example.testing1.databinding.MainNavBinding
import com.example.testing1.databinding.MyDataBinding
import com.example.testing1.viewModel.MemberViewModel

class MyData:AppCompatActivity() {
    private lateinit var binding: MyDataBinding
    private val memberViewModel by viewModels<MemberViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView<MyDataBinding>(this, R.layout.my_data)
        setContentView(binding.root)
        binding.viewmodel=memberViewModel
        binding.lifecycleOwner=this

        memberViewModel._member= RetrofitManager.instance.getMyData()
    }
}