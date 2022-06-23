package com.example.testing1.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.testing1.R
import com.example.testing1.databinding.FragmentIntroduceBinding
import com.example.testing1.viewModel.DetailCampaignViewModel
import com.example.testing1.viewModel.MemberViewModel

private val TAG="tag1"

class FragIntroduce: Fragment() {
    private var mBinding: FragmentIntroduceBinding? = null
    private val binding get() = mBinding!!

    private lateinit var campaignViewModel: DetailCampaignViewModel


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?):
            View? {
        mBinding= DataBindingUtil.inflate(inflater, R.layout.fragment_introduce,container,false)
        activity?.let {
            campaignViewModel = ViewModelProvider(it).get(DetailCampaignViewModel::class.java)
            binding.viewmodel = campaignViewModel
            binding.lifecycleOwner = this
        }

        return binding.root
    }
    override fun onDestroyView() { // onDestroyView 에서 binding class 인스턴스 참조를 정리해주어야 한다.
        mBinding = null
        super.onDestroyView()
    }

}