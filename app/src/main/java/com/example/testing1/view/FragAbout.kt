package com.example.testing1.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.testing1.R
import com.example.testing1.databinding.FragmentAboutBinding


class FragAbout : Fragment() {

    private var mBinding:FragmentAboutBinding?=null
    private val binding get() = mBinding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding= DataBindingUtil.inflate(inflater, R.layout.fragment_about, container, false)

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        //binding.btn1.setOnClickListener{

        }
    }

/*
    override fun onDestroy() {
        mBinding = null
        super.onDestroy()
    }

 */


