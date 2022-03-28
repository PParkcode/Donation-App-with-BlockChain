package com.example.testing1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.testing1.databinding.SearchLayoutBinding
import com.example.testing1.databinding.SearchLayoutBinding.inflate

private lateinit var binding: SearchLayoutBinding

class SearchActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.search_layout)
    }

}