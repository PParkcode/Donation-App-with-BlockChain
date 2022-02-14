package com.example.testing1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory


private const val TAG="MainActivity"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lateinit var edit_id:EditText
        lateinit var edit_pwd:EditText
        lateinit var Rgroup_login:RadioGroup
        lateinit var user_mode:RadioButton
        lateinit var charity_mode:RadioButton
        lateinit var signup_btn:Button
        lateinit var login_btn:Button


        edit_id=findViewById<EditText>(R.id.edit_id)
        edit_pwd=findViewById<EditText>(R.id.edit_pwd)
        Rgroup_login=findViewById(R.id.Rgroup_login)
        user_mode=findViewById(R.id.user_mode)
        charity_mode=findViewById(R.id.charity_mode)
        signup_btn=findViewById<Button>(R.id.signup_btn)
        login_btn=findViewById(R.id.login_btn)

        val tmpRetrofit = Retrofit.Builder()
            .baseUrl("")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var server=tmpRetrofit.create(ConnectApi::class.java)
        signup_btn.setOnClickListener{
            var intent= Intent(applicationContext,Register::class.java)
            startActivity(intent)
        }

        Rgroup_login.setOnCheckedChangeListener{compoundButton,b->
            if(charity_mode.isChecked==true){

            }
        }

        login_btn.setOnClickListener{
            val loginData:LoginData=LoginData(edit_id.text.toString(),edit_pwd.text.toString())
            val json:String= makeJson(loginData)
            Log.d(TAG,json)
            server.loginFunc(json).enqueue(object : Callback<String>{
                override fun onFailure(call: Call<String>, t: Throwable) {
                }
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    Log.d(TAG,response?.body().toString())
                }
            })

        }



    }

    private fun makeJson(loginData:LoginData):String
    {
        var gson:Gson=GsonBuilder().create()
        var json:String=gson.toJson(loginData)

        return json


    }


}