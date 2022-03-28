package com.example.testing1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.testing1.databinding.FragmentShareBinding
import org.w3c.dom.Text

private const val NUM_PAGES = 5

class FragShare : Fragment() {
    private var mBinding: FragmentShareBinding? = null
    private val binding get() = mBinding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mBinding=DataBindingUtil.inflate(inflater,R.layout.fragment_share,container,false)
        binding.recommViewPager.adapter=RecommViewPagerAdapter(getRecommList())
        binding.recommViewPager.orientation=ViewPager2.ORIENTATION_HORIZONTAL
        binding.recommViewPager.offscreenPageLimit=3

        return binding.root
    }

    override fun onStart() {
        super.onStart()

    }
    override fun onDestroyView() { // onDestroyView 에서 binding class 인스턴스 참조를 정리해주어야 한다.
        mBinding = null
         super.onDestroyView() }


    private fun getRecommList(): ArrayList<Int> { // ArrayList가 아닌 retrofit에서 받아온 코드로 수정
        return arrayListOf<Int>(R.drawable.test1, R.drawable.test2, R.drawable.test4)
    }



}