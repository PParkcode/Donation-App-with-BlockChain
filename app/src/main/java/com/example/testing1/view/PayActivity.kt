package com.example.testing1.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import com.example.testing1.MySharedPreferences
import com.example.testing1.R
import com.example.testing1.Retrofit.RetrofitManager.Companion.instance
import com.example.testing1.databinding.PayActivityBinding
import kotlinx.android.synthetic.main.pay_activity.*
import kr.co.bootpay.Bootpay
import kr.co.bootpay.BootpayAnalytics
import kr.co.bootpay.enums.Method
import kr.co.bootpay.enums.PG
import kr.co.bootpay.enums.UX
import kr.co.bootpay.model.BootExtra
import kr.co.bootpay.model.BootUser
import java.time.LocalDate
import java.time.LocalDateTime

class PayActivity:FragmentActivity() {
    val application_id ="625bdbe1270180001ef693aa"

    private lateinit var binding: PayActivityBinding
    private lateinit var dialog: DonateCustomDialog
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView<PayActivityBinding>(this,R.layout.pay_activity)
        setContentView(binding.root)

        BootpayAnalytics.init(this, application_id)

        binding.buttonFirst.setOnClickListener{
            goBootpayRequest()
        }
    }



    @RequiresApi(Build.VERSION_CODES.O)
    private fun goBootpayRequest() {
        val bootUser = BootUser().setPhone("010-1233-5678")
        val bootExtra = BootExtra().setQuotas(intArrayOf(0, 2, 3))

        val stuck = 1 //재고 있음

        Bootpay.init(this)
                .setApplicationId(application_id) // 해당 프로젝트(안드로이드)의 application id 값
                .setContext(this)
                .setBootUser(bootUser)
                //.setBootExtra(bootExtra)
                .setUX(UX.PG_DIALOG)
                .setPG(PG.NICEPAY)
                //.setMethod(Method.KAKAO)
//                .setUserPhone("010-1234-5678") // 구매자 전화번호
                .setName("포인트 충전") // 결제할 상품명
                .setOrderId(MySharedPreferences.getUserId(this)+LocalDateTime.now()) // 결제 고유번호expire_month
                .setPrice(edit_amount.text.toString().toInt()) // 결제할 금액
                .onConfirm { message ->
                    if (0 < stuck) Bootpay.confirm(message); // 재고가 있을 경우.
                    else Bootpay.removePaymentWindow(); // 재고가 없어 중간에 결제창을 닫고 싶을 경우
                    Log.d("confirm", message);
                }
                .onDone { message ->
                    Log.d("done", message)
                    instance.charge(edit_amount.text.toString(),completion = {responseCode ->
                        when(responseCode){
                            200 ->{ // 여기서 LoadingActivity에 콜백 사용하고 싶은데...
                                val loadingIntent:Intent= Intent(this,LoadingActivity::class.java)
                                startActivity(loadingIntent)
                            }
                            else ->{
                                Toast.makeText(this,"기부에 실패하였습니다", Toast.LENGTH_SHORT).show()
                            }
                        }
                    })
                    dialog=DonateCustomDialog(this)
                    dialog.show()
//                    var intent = Intent(this,MainNav::class.java)
//                    startActivity(intent)

                }
                .onReady { message ->
                    Log.d("tag3", message)
                }
                .onCancel { message ->
                    Log.d("tag3", message)
                }
                .onError{ message ->
                    Log.d("tag3", message)
                }
                .onClose { message ->
                    Log.d("tag3", "close")
                }
                .request();
    }
}