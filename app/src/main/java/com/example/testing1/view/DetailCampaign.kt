package com.example.testing1.view

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.testing1.MySharedPreferences
import com.example.testing1.R
import com.example.testing1.Retrofit.RetrofitManager
import com.example.testing1.Retrofit.RetrofitManager.Companion.instance
import com.example.testing1.databinding.DetailCampaignBinding
import com.example.testing1.model.ResponseCode
import com.example.testing1.viewModel.DetailCampaignViewModel
import com.example.testing1.viewModel.LoginViewModel
import com.example.testing1.viewModel.ResponseViewModel
import com.google.android.material.tabs.TabLayout
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_introduce.*


private val TAG="tag1"
class DetailCampaign:AppCompatActivity() {
    private lateinit var binding: DetailCampaignBinding
    lateinit var code:ResponseCode
    private lateinit var responseViewmodel:ResponseViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        val campaignViewModel by viewModels<DetailCampaignViewModel>()
        super.onCreate(savedInstanceState)
        var intent = getIntent()
        val campaignId: Int? = intent.getIntExtra("id", 0)

        binding = DataBindingUtil.setContentView<DetailCampaignBinding>(this, R.layout.detail_campaign)
        setContentView(binding.root)

        var actionBar: ActionBar?
        actionBar = supportActionBar
        actionBar?.hide()

        //campaignViewModel._campaignData = instance.getCampaignDetail(campaignId.toString())
        campaignViewModel.getDetailCampaign(campaignId.toString())
        Log.d("tag1", "campaignViewModel: " + campaignViewModel.campaignData?.value)

        binding.detailViewModel = campaignViewModel
        binding.lifecycleOwner = this


        campaignViewModel._campaignData?.observe(this, Observer {
           // var tmpDeadline:String?=campaignViewModel.campaignData?.value?.deadline

            Log.d("day1",MySharedPreferences.getUserName(this))
            var tmpDeadline:String?=it.deadline
            Log.d("day1","tmpDeadline: "+tmpDeadline.toString())

            var deadline= tmpDeadline!!.replace("-","").toInt()
            val current = LocalDate.now()
            Log.d("day1","current: "+current)
            val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
            val today = current.format(formatter).toInt()
            Log.d("day1","deadline: ${deadline}// today: ${today}")

            if(deadline-today<0){
                if(it.charityName.equals(MySharedPreferences.getUserName(this))){
                    binding.saveBtn.visibility= View.VISIBLE
                    binding.donateBtn.visibility=View.GONE
                }
                else{
                    binding.donateBtn.setBackgroundColor(Color.parseColor("#D3D3D3"));
                    binding.donateBtn.setText("마감된 캠페인입니다")
                    binding.donateBtn.isEnabled=false
                }
            }
        })



        val fragmentIntroduce = FragIntroduce()
        supportFragmentManager.beginTransaction().add(R.id.frame_layout, fragmentIntroduce).commit()
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {

                when (tab!!.position) {
                    0 -> {
                        Log.d(TAG, "position 0 진입")

                        supportFragmentManager.beginTransaction().replace(R.id.frame_layout, fragmentIntroduce).commit()
                    }
                    1 -> {
                        val fragmentCurrentData = FragCurrentData()
                        supportFragmentManager.beginTransaction().replace(R.id.frame_layout, fragmentCurrentData).commit()
                    }
                    2 -> {
                        val fragmentReview = FragReview()
                        supportFragmentManager.beginTransaction().replace(R.id.frame_layout, fragmentReview).commit()
                    }

                }
            }

        })


        binding.donateBtn.setOnClickListener {
            val intent = Intent(this, DonateActivity::class.java)

            startActivity(intent)
        }
        binding.saveBtn.setOnClickListener{

            val intent = Intent(this, WithDrawForm::class.java)
            intent.putExtra("id",campaignId)
            intent.putExtra("amount",campaignViewModel.campaignData?.value?.currentAmount)
            startActivity(intent)
        }
    }


}