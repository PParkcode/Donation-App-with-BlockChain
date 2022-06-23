package com.example.testing1.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.testing1.R
import com.example.testing1.databinding.FragmentCurrentdataBinding
import com.example.testing1.viewModel.MemberViewModel
import com.example.testing1.viewModel.ResponseViewModel

class FragCurrentData: Fragment() {
    private var mBinding: FragmentCurrentdataBinding?= null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding= DataBindingUtil.inflate(inflater, R.layout.fragment_currentdata,container,false)

        binding.circlebar.progress=70

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()

    }

    override fun onDestroy() {
        mBinding = null
        super.onDestroy()
    }



}