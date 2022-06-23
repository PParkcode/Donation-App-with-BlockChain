package com.example.testing1.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.testing1.MySharedPreferences
import com.example.testing1.R
import com.example.testing1.databinding.FragmentReviewBinding
import com.example.testing1.viewModel.DetailCampaignViewModel
import com.example.testing1.viewModel.MemberViewModel


class FragReview:Fragment() {
    private var mBinding:FragmentReviewBinding?=null
    private val binding get() = mBinding!!
    var wallet_id:String=""
    private lateinit var campaignViewModel: DetailCampaignViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding= DataBindingUtil.inflate(inflater, R.layout.fragment_review, container, false)

        activity?.let {
            wallet_id=MySharedPreferences.getUserWallet(it)
            Log.d("wallet_id1","walletID: "+wallet_id)
            campaignViewModel = ViewModelProvider(it).get(DetailCampaignViewModel::class.java)
            binding.viewmodel = campaignViewModel
            binding.fragment=this

            binding.lifecycleOwner = this
        }


        return binding.root
    }

    override fun onStart() {
        super.onStart()

        binding.urlBtn.setOnClickListener{
            var intent: Intent =Intent(Intent.ACTION_VIEW, Uri.parse("https://baobab.scope.klaytn.com/token/0x2979c52ad2a065dc406779c020ebe9a6b548ac22?tabId=tokenTransfer"));
            startActivity(intent);


        }


    }

    override fun onDestroy() {
        mBinding=null
        super.onDestroy()
    }

}