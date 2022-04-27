package com.example.testing1.view

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.testing1.MySharedPreferences
import com.example.testing1.R
import com.example.testing1.Retrofit.RetrofitManager.Companion.instance
import com.example.testing1.databinding.FragmentMyInformBinding
import com.example.testing1.viewModel.CampaignViewModel
import com.example.testing1.viewModel.MemberViewModel
import com.example.testing1.viewModel.ResponseViewModel
import com.iamport.sdk.data.sdk.IamPortRequest
import com.iamport.sdk.data.sdk.PayMethod
import com.iamport.sdk.domain.core.Iamport
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Response
import java.util.*

private val TAG="tag1"


class FragMyInform : Fragment() {
    private var mBinding: FragmentMyInformBinding? = null
    private val binding get() = mBinding!!
    private lateinit var memberViewModel: MemberViewModel
    private lateinit var logoutRespone:ResponseViewModel

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
            logoutRespone=ViewModelProvider(it).get(ResponseViewModel::class.java)
            binding.member=memberViewModel
            binding.lifecycleOwner=this
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG,"onViewCreate start ")
        binding.logoutBtn.setOnClickListener{

            instance.logout()
            MySharedPreferences.clearUser(requireContext())
            var intent = Intent(getActivity(), MainActivity::class.java)
            startActivity(intent)
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
        binding.chargeBtn.setOnClickListener{
            var edit:EditText= EditText(context)
            edit.setInputType(InputType.TYPE_CLASS_NUMBER)
            AlertDialog.Builder(context)
                    .setTitle("결제하기")
                    .setMessage("결제금액을 입력해주세요.")
                    .setView(edit)
                    .setPositiveButton("결제하기"){dialog,which ->
                        chargeMoney(edit.text.toString())
                    }
                    .setNegativeButton("x") { dialog, _ -> dialog.dismiss() }
                    .show()

        }
        binding.myinformBtn.setOnClickListener{
            var intent=Intent(activity, MyData::class.java)
            startActivity(intent)
        }


    }
    override fun onDestroyView() { // onDestroyView 에서 binding class 인스턴스 참조를 정리해주어야 한다.
        mBinding = null
        super.onDestroyView()
    }
    private fun onClickPayment(_amount:String) {

        val request = IamPortRequest(
                pg = "kakopay",              // PG 사
                pay_method = PayMethod.trans.name,                   // 결제수단
                name = "충전",                         // 주문명
                merchant_uid = memberViewModel.member!!.value!!.email+Date().time,                 // 주문번호
                amount = _amount,                            // 결제금액
                buyer_name = memberViewModel.member!!.value!!.name
        )
        // 결제호출


    }
    fun chargeMoney(amount:String){
        instance.charge(amount)
    }












}