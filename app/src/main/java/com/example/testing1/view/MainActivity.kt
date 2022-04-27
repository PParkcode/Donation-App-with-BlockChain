package com.example.testing1.view


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.testing1.MySharedPreferences
import com.example.testing1.model.LoginData
import com.example.testing1.Retrofit.RetrofitManager
import com.example.testing1.databinding.ActivityMainBinding
import com.example.testing1.model.ResponseCode
import com.example.testing1.viewModel.LoginViewModel


private const val TAG="tag1"
private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    init{
        instance = this
    }
    companion object{
        private var instance:Context? = null
        fun getInstance(): Context? {
            return instance
        }
    }


    lateinit var myLoginViewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var actionBar: ActionBar?
        actionBar=supportActionBar
        actionBar?.hide()
        myLoginViewModel=ViewModelProvider(this).get(LoginViewModel::class.java)


        if(MySharedPreferences.getUserId(this).isNullOrBlank() || MySharedPreferences.getUserPass(this).isNullOrBlank()) {
            Log.d(TAG,"자동 로그인 안되었을 때")
            //Login()

        }
        else { // SharedPreferences 안에 값이 저장되어 있을 때 -> MainActivity로 이동
            Toast.makeText(this, "${MySharedPreferences.getUserNick(this)}님 자동 로그인 되었습니다.", Toast.LENGTH_SHORT).show()
            autoLogin(MySharedPreferences.getUserId(this),MySharedPreferences.getUserPass(this))
            //val intent = Intent(this, MainNav::class.java)
            //startActivity(intent)
            //finish()
        }


        binding.signupBtn.setOnClickListener{
            var intent= Intent(applicationContext,Register::class.java)
            startActivity(intent)
        }


        binding.loginBtn.setOnClickListener{
            val loginData: LoginData = LoginData(binding.editId.text.toString(),binding.editPwd.text.toString())

            Log.d(TAG,loginData.toString())

            val loginLiveData: MutableLiveData<ResponseCode>? = RetrofitManager().loginCall(loginData)

            loginLiveData?.observe(this, Observer {
                Log.d(TAG,"데이터 변경 login LiveData: ${it}")

                if(loginLiveData?.value!=null)
                {
                    if(loginLiveData.value!!.code!=200)
                    {
                        Toast.makeText(this, "아이디와 비밀번호를 확인하세요", Toast.LENGTH_SHORT).show()
                    }else{

                        MySharedPreferences.setUserId(this, binding.editId.text.toString())
                        MySharedPreferences.setUserPass(this,binding.editPwd.text.toString())


                        Log.d(TAG,"일반 로그인: "+MySharedPreferences.getUserId(this))
                        Log.d(TAG,"일반 로그인: "+MySharedPreferences.getUserPass(this))
                        Log.d(TAG,"일반 로그인: "+MySharedPreferences.getUserCookie(this))

                        var intent = Intent(this, MainNav::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            })
        }


    }


    fun Login() {
        Log.d(TAG,"Login()함수 진입")

        binding.loginBtn.setOnClickListener {
            Log.d(TAG,"버튼 리스너 작동")

            val loginData: LoginData = LoginData(binding.editId.text.toString(),binding.editPwd.text.toString())

            Log.d(TAG,loginData.toString())

            val loginLiveData: MutableLiveData<ResponseCode>? = RetrofitManager().loginCall(loginData)

            loginLiveData?.observe(this, Observer {
                Log.d(TAG,"데이터 변경 login LiveData: ${it}")

                if(loginLiveData?.value!=null)
                {
                    if(loginLiveData.value!!.code!=200)
                    {
                        Toast.makeText(this, "아이디와 비밀번호를 확인하세요", Toast.LENGTH_SHORT).show()
                    }else{

                        MySharedPreferences.setUserId(this, binding.editId.text.toString())
                        MySharedPreferences.setUserPass(this,binding.editPwd.text.toString())


                        Log.d(TAG,"일반 로그인: "+MySharedPreferences.getUserId(this))
                        Log.d(TAG,"일반 로그인: "+MySharedPreferences.getUserPass(this))
                        Log.d(TAG,"일반 로그인: "+MySharedPreferences.getUserCookie(this))




                        var intent = Intent(this, MainNav::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            })

            //myLoginViewModel.getLoginResponse(loginData)
        }
    }



    fun autoLogin(id:String,pwd:String)
    {
        val loginData: LoginData = LoginData(id,pwd)

        Log.d(TAG,loginData.toString())

        val autoLoginLiveData: MutableLiveData<ResponseCode>? = RetrofitManager().loginCall(loginData)

        autoLoginLiveData?.observe(this, Observer {
            Log.d(TAG,"데이터 변경 login LiveData: ${it}")

            if(autoLoginLiveData?.value!=null)
            {
                if(autoLoginLiveData.value!!.code!=200)
                {
                    Toast.makeText(this, "아이디와 비밀번호를 확인하세요", Toast.LENGTH_SHORT).show()
                }else{

                    //MySharedPreferences.setUserCookie(this, autoLoginLiveData.value!!.message)
                    Log.d(TAG,"${id}님이 자동 로그인")
                    Log.d(TAG,"자동 로그인: "+MySharedPreferences.getUserId(this))
                    Log.d(TAG,"자동 로그인: "+MySharedPreferences.getUserPass(this))
                   // Log.d(TAG,"자동 로그인: "+MySharedPreferences.getUserCookie(this))
                    var intent = Intent(this, MainNav::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        })
    }




}