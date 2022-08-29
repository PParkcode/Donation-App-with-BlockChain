package com.example.testing1.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.testing1.MySharedPreferences
import com.example.testing1.R
import com.example.testing1.Retrofit.RetrofitManager
import com.example.testing1.Retrofit.RetrofitManager.Companion.instance
import com.example.testing1.databinding.ExchangeActivity2Binding
import com.example.testing1.model.ExchangeData
import com.example.testing1.model.ExchangeResponseData
import com.example.testing1.model.RequestHeader
import com.example.testing1.model.ResponseCode
import com.example.testing1.viewModel.NHResponseViewModel
import com.example.testing1.viewModel.ResponseViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private val AccessToken="c0597cc517ac901ab5ba5c178b50cdb7ff4140fb8a44029012d0a9decc0faf3b"
class ExchangeActivity2: AppCompatActivity(){

    private lateinit var binding:ExchangeActivity2Binding
    private lateinit var dialog:DonateCustomDialog
    var Acno=""
    var bncd=""
    var point=""
    var name=""
    private lateinit var viewModel: NHResponseViewModel
    private lateinit var payBackViewModel:ResponseViewModel
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView<ExchangeActivity2Binding>(this, R.layout.exchange_activity2)

        var actionBar: ActionBar?
        actionBar = supportActionBar
        actionBar?.hide()

        viewModel=ViewModelProvider(this).get(NHResponseViewModel::class.java)
        payBackViewModel=ViewModelProvider(this).get(ResponseViewModel::class.java)
        binding.activity=this




        var intent= getIntent()
        Acno=intent.getStringExtra("account").toString()
        bncd= intent.getStringExtra(("bncd")).toString()
        point=intent.getStringExtra("point").toString()
        name=MySharedPreferences.getUserName(this)

        /*
        viewModel._response?.observe(this, Observer {
            Log.d("day1","data change")

            if(it!=null) {
                Log.d("day1", "it is not null")

                if (it.Header.Rsms.equals("정상처리 되었습니다.")) {
                    Log.d("day1", "정상처리")
                    payBackViewModel.payBackPoint(point)
                    Toast.makeText(applicationContext, "입금되었습니다", Toast.LENGTH_LONG).show()
                    var intentToMain = Intent(this, MainNav::class.java)
                    startActivity(intentToMain)
                } else {
                    Toast.makeText(this, "입금이 실패되었습니다", Toast.LENGTH_LONG).show()
                }
            }
            else{
                Log.d("day1","it is null")
            }
        })

         */

        binding.submitBtn.setOnClickListener{
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
            val formatter2=DateTimeFormatter.ofPattern("HHmmss")
            val today = current.format(formatter).toString()
            val current_time=current.format(formatter2).toString()
            var header:RequestHeader=RequestHeader("ReceivedTransferOtherBank",today,current_time,"001491","001","ReceivedTransferA",today+current_time, AccessToken)
            var data:ExchangeData =ExchangeData(header, bncd,Acno,point)
            Log.d("NHResponse","request data:"+data)
            // viewModel.exchangePoint(data)
            //  Log.d("day1","1"+viewModel.response?.hasActiveObservers())
            var liveData:MutableLiveData<ExchangeResponseData>?= instance.exchange(data)

            liveData?.observe(this, Observer {
                Log.d("day1","data change")

                if(it!=null) {
                    Log.d("day1", "it is not null")

                    if (it.Header.Rsms.equals("정상처리 되었습니다.")) {
                        Log.d("day1", "정상처리")
                        //payBackViewModel.payBackPoint(point)
                        instance.payBack(point,completion = { responseCode ->
                            when(responseCode){
                                200 ->{ // 여기서 LoadingActivity에 콜백 사용하고 싶은데...
                                    val loadingIntent:Intent= Intent(this,LoadingActivity::class.java)
                                    startActivity(loadingIntent)
                                }
                                else ->{
                                    Toast.makeText(this,"입금에 실패하였습니다",Toast.LENGTH_SHORT).show()
                                }
                            }

                        })
                        dialog=DonateCustomDialog(this)
                        dialog.show()



                        /*
                        var intentToMain = Intent(this, MainNav::class.java)
                        startActivity(intentToMain)
                         */
                    } else {
                        Toast.makeText(this, "입금이 실패되었습니다", Toast.LENGTH_LONG).show()
                    }
                }
                else{
                    Log.d("day1","it is null")
                }
            })


        }





    }



}