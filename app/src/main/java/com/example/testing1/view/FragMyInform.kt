package com.example.testing1.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.testing1.MySharedPreferences
import com.example.testing1.R
import com.example.testing1.databinding.FragmentMyInformBinding
import com.example.testing1.viewModel.CampaignViewModel
import com.example.testing1.viewModel.MemberViewModel

private val TAG="tag1"


class FragMyInform : Fragment() {
    private var mBinding: FragmentMyInformBinding? = null
    private val binding get() = mBinding!!
    private lateinit var memberViewModel: MemberViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }
    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding=DataBindingUtil.inflate(inflater, R.layout.fragment_my_inform,container,false)
        Log.d(TAG,"onCreateView start and binding is inited")

        activity?.let{
            memberViewModel=ViewModelProvider(it).get(MemberViewModel::class.java)
            binding.member=memberViewModel
            binding.lifecycleOwner=this
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG,"onViewCreate start ")
        binding.logoutBtn.setOnClickListener{

            var intent = Intent(getActivity(), MainActivity::class.java)
            startActivity(intent)

            MySharedPreferences.clearUser(requireContext())
            Toast.makeText(requireContext(), "로그아웃", Toast.LENGTH_SHORT).show()
            getActivity()?.finish()
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG,"onAttach start ")
        val activity= context as Activity
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG,"onStart start ")

    }
    override fun onDestroyView() { // onDestroyView 에서 binding class 인스턴스 참조를 정리해주어야 한다.
        mBinding = null
        super.onDestroyView()
    }











}