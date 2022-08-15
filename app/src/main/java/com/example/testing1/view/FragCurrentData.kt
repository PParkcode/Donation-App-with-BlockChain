package com.example.testing1.view

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.testing1.R
import com.example.testing1.adapter.WithDrawAdapter
import com.example.testing1.databinding.FragmentCurrentdataBinding
import com.example.testing1.model.WithDrawData
import com.example.testing1.viewModel.DetailCampaignViewModel
import com.example.testing1.viewModel.MemberViewModel
import com.example.testing1.viewModel.ResponseViewModel
import com.example.testing1.viewModel.WithDrawViewModel

class FragCurrentData: Fragment() {
    private var mBinding: FragmentCurrentdataBinding?= null
    private val binding get() = mBinding!!

    private lateinit var campaignViewModel: DetailCampaignViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding= DataBindingUtil.inflate(inflater, R.layout.fragment_currentdata,container,false)

        activity?.let {
            campaignViewModel=ViewModelProvider(it).get(DetailCampaignViewModel::class.java)

        }


        binding.withDrawViewModel= WithDrawViewModel(campaignViewModel.campaignData?.value?.campaignId.toString())
        binding.lifecycleOwner=this


        setRecyclerView()

        /*

        val v= binding.withDrawViewModel
        if(v?.withDraws?.value==null){
            Log.d("history1","recylerView is null")
            binding.test.setText("recyclerView is null")
        }
        else{
            Log.d("history1",v?.withDraws?.value.toString())
        }

         */






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

    private fun setRecyclerView(){
        Log.d("history1", "setRecyclerView 실행")
        val adapter= WithDrawAdapter({withDraw -> detailWithDraw(withDraw)})

        binding.recyclerView.adapter=adapter
        binding.recyclerView.setHasFixedSize(true)
    }

    private fun detailWithDraw(withDraw: WithDrawData){
        val intent= Intent(activity,DetailWithDraw::class.java)

        intent.putExtra("object",withDraw)
        startActivity(intent)
    }

}