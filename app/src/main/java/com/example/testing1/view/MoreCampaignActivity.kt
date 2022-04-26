package com.example.testing1.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import com.example.testing1.MoreCampaignBinding
import com.example.testing1.R
import com.example.testing1.adapter.MoreCampAdapter
//import com.example.testing1.databinding.MoreCampaignBinding
import com.example.testing1.model.Campaign
import com.example.testing1.model.CampaignFullDto
import com.example.testing1.view.MainNav
import com.example.testing1.viewModel.CampaignViewModel

private val TAG="tag1"

private lateinit var binding: MoreCampaignBinding
class MoreCampaignActivity:AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding=DataBindingUtil.setContentView(this, R.layout.more_campaign)
        var actionBar: ActionBar?
        actionBar=supportActionBar
        actionBar?.hide()

        /*
        campaignViewModel = ViewModelProvider(this).get(CampaignViewModel::class.java)

        campaignViewModel.campaigns?.observe(this, Observer {
            Log.d(TAG, "MoreCampaignActivity 데이터 변경 ")
        })

         */
        binding.lifecycleOwner = this
        binding.viewModel = CampaignViewModel()
        setRecyclerView()


    }

    private fun setRecyclerView() {
        // Set contactItemClick & contactItemLongClick lambda
        val adapter = MoreCampAdapter({ campaign -> detailDialog(campaign)})

        binding.recyclerView.adapter = adapter
        binding.recyclerView.setHasFixedSize(true)
    }

    private fun detailDialog(campaign: Campaign) {

        val intent = Intent(this, DetailCampaign::class.java)
        intent.putExtra("id",campaign.campaignId)
        Log.d(TAG,"campaignID: "+campaign.campaignId)
        startActivity(intent)

    }
    private fun getMovieList():LiveData<List<Campaign>>{
        return CampaignViewModel().getCampaignData()
    }
}