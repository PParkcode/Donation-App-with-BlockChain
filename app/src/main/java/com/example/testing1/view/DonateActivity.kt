package com.example.testing1.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.testing1.R
import com.example.testing1.Retrofit.RetrofitManager
import com.example.testing1.databinding.DonateActivityBinding

class DonateActivity: AppCompatActivity() {

    private lateinit var binding: DonateActivityBinding
    private lateinit var dialog:DonateCustomDialog
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var actionBar: ActionBar?
        actionBar = supportActionBar
        actionBar?.hide()

        binding= DataBindingUtil.setContentView<DonateActivityBinding>(this, R.layout.donate_activity)
        setContentView(binding.root)


        binding.donateButton.setOnClickListener{

            RetrofitManager.instance.donate(binding.editPoint.text.toString(),completion = { responseCode ->
                when(responseCode){
                    200 ->{ // 여기서 LoadingActivity에 콜백 사용하고 싶은데...
                        val loadingIntent:Intent= Intent(this,LoadingActivity::class.java)
                        startActivity(loadingIntent)
                    }
                    else ->{
                        Toast.makeText(this,"기부에 실패하였습니다",Toast.LENGTH_SHORT).show()
                    }
                }

            })
            dialog=DonateCustomDialog(this)
            dialog.show()



        }

    }

}