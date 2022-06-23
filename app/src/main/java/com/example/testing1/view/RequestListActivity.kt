package com.example.testing1.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.testing1.R
import com.example.testing1.adapter.MoreCampAdapter
import com.example.testing1.adapter.RequestListAdapter
import com.example.testing1.databinding.RequestListActivityBinding
import com.example.testing1.model.Campaign
import com.example.testing1.model.MemberData
import com.example.testing1.viewModel.CampaignViewModel
import com.example.testing1.viewModel.RequestListViewModel




class RequestListActivity:AppCompatActivity() {
    private lateinit var binding: RequestListActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= DataBindingUtil.setContentView(this, R.layout.request_list_activity)
        var actionBar: ActionBar?

        actionBar=supportActionBar
        actionBar?.hide()

        binding.lifecycleOwner = this
        binding.viewModel = RequestListViewModel()
        setRecyclerView()


   }

    private fun setRecyclerView() {
        // Set contactItemClick & contactItemLongClick lambda
        val adapter = RequestListAdapter({ charity -> payBackDialog(charity)})

        binding.recyclerView.adapter = adapter
        binding.recyclerView.setHasFixedSize(true)
    }

    private fun payBackDialog(charity: MemberData) {

        val intent = Intent(this, ExchangeActivity::class.java)  //detailCampaign이 아닌 다른 액티비티로 이동
        intent.putExtra("id",charity.email)
        //intent.putExtra("amount",campaign.currentAmount)
        startActivity(intent)

    }

}