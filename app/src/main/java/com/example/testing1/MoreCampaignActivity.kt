package com.example.testing1

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.testing1.databinding.ActivityMainBinding
import com.example.testing1.databinding.MoreCampaignBinding

private val TAG="tag1"

private lateinit var binding:MoreCampaignBinding
class MoreCampaignActivity:AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding=DataBindingUtil.setContentView(this,R.layout.more_campaign)

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

        val intent = Intent(this, MainNav::class.java)
        startActivity(intent)


    }
}