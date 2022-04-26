package com.example.testing1.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider

import androidx.viewpager2.widget.ViewPager2
import com.example.testing1.FragmentShareBinding
import com.example.testing1.R
import com.example.testing1.Retrofit.RetrofitManager.Companion.instance
import com.example.testing1.adapter.MoreCampAdapter
import com.example.testing1.adapter.RecommViewPagerAdapter

import com.example.testing1.model.Campaign
import com.example.testing1.viewModel.CampaignViewModel
import com.example.testing1.viewModel.MemberViewModel
import org.w3c.dom.Text

private const val NUM_PAGES = 5

class FragShare : Fragment() {
    private var mBinding: FragmentShareBinding? = null
    private val binding get() = mBinding!!

   // private val memberViewModel by activityViewModels<MemberViewModel>()
    private lateinit var memberViewModel:MemberViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mBinding=DataBindingUtil.inflate(inflater, R.layout.fragment_share,container,false)
       // binding.recommViewPager.adapter= RecommViewPagerAdapter(getRecommList())
        //binding.recommViewPager
        //binding.recommViewPager.orientation=ViewPager2.ORIENTATION_HORIZONTAL
        //binding.recommViewPager.offscreenPageLimit=3
        activity?.let{
            memberViewModel=ViewModelProvider(it).get(MemberViewModel::class.java)
            binding.viewmodel=memberViewModel


            binding.campaignViewModel = CampaignViewModel()
            binding.lifecycleOwner=this
            setViewPager()

            /*
            campaignViewModel=ViewModelProvider(it).get(CampaignViewModel::class.java)
            binding.campaignViewModel=campaignViewModel

             */


        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
    override fun onStart() {
        super.onStart()
        binding.moreCampBtn.setOnClickListener{
            var intent= Intent(activity,MoreCampaignActivity::class.java)
            startActivity(intent)
        }

    }
    override fun onDestroyView() { // onDestroyView 에서 binding class 인스턴스 참조를 정리해주어야 한다.
        mBinding = null
         super.onDestroyView() }


    /*
    private fun getRecommList(): ArrayList<Int> { // ArrayList가 아닌 retrofit에서 받아온 코드로 수정
        return arrayListOf<Int>(R.drawable.test5, R.drawable.test6, R.drawable.test7)
    }

    private fun getList(): MutableLiveData<List<Campaign>>{
        return instance.getCampaignList()
    }
     */

    private fun setViewPager() {

        val adapter = RecommViewPagerAdapter({ campaign -> detailDialog(campaign)})

        binding.recommViewPager.adapter = adapter
        binding.recommViewPager.orientation=ViewPager2.ORIENTATION_HORIZONTAL
        binding.recommViewPager.offscreenPageLimit=3
    }


    private fun detailDialog(campaign: Campaign) {

        val intent = Intent(activity, DetailCampaign::class.java)
        intent.putExtra("id",campaign.campaignId)
        Log.d("tag1","campaignID: "+campaign.campaignId)
        startActivity(intent)

    }





}