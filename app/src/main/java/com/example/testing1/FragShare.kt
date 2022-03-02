package com.example.testing1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.fragment_share.*
import org.w3c.dom.Text

private const val NUM_PAGES = 5

class FragShare : Fragment() {
    private lateinit var intro_nick_text:TextView
    private lateinit var intro_text:TextView
    private lateinit var more_camp_btn:Button
    private lateinit var recomm_viewPager:ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_share, container, false)
        recomm_viewPager=view.findViewById(R.id.recomm_viewPager)
        intro_nick_text=view.findViewById(R.id.intro_nick_text)
        intro_text=view.findViewById(R.id.intro_text)
        more_camp_btn=view.findViewById(R.id.more_camp_btn)

        recomm_viewPager.adapter = RecommViewPagerAdapter(getRecommList()) // 어댑터 생성
        recomm_viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL // 방향을 가로로
        recomm_viewPager.offscreenPageLimit=3



        return view
    }

    override fun onStart() {
        super.onStart()

    }
    private fun getRecommList(): ArrayList<Int> {
        return arrayListOf<Int>(R.drawable.test1, R.drawable.test2, R.drawable.test4)
    }





}