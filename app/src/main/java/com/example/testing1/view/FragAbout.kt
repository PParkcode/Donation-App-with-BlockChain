package com.example.testing1.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.testing1.R
import com.example.testing1.adapter.MoreCampAdapter
import com.example.testing1.databinding.FragmentAboutBinding
import com.example.testing1.model.Campaign
import com.example.testing1.viewModel.MyCampaignViewModel


class FragAbout : Fragment() {

    private var mBinding: FragmentAboutBinding? = null
    private val binding get() = mBinding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_about, container, false)

        binding.lifecycleOwner = this
        binding.viewModel = MyCampaignViewModel()
        setRecyclerView()

        return binding.root
    }

    override fun onStart() {
        super.onStart()

    }


    override fun onDestroy() {
        mBinding = null
        super.onDestroy()
    }

    private fun setRecyclerView() {
        // Set contactItemClick & contactItemLongClick lambda
        val adapter = MoreCampAdapter({ campaign -> detailDialog(campaign)})

        binding.recyclerView.adapter = adapter
        binding.recyclerView.setHasFixedSize(true)
    }

    private fun detailDialog(campaign: Campaign) {


        val intent = Intent(activity, DetailCampaign::class.java)
        intent.putExtra("id",campaign.campaignId)

        startActivity(intent)

    }
}




