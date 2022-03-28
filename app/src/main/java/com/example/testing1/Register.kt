package com.example.testing1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.testing1.Retrofit.RetrofitManager
import com.example.testing1.databinding.SignupBinding
private const val TAG="tag1"

private lateinit var binding: SignupBinding
class Register :AppCompatActivity(){
    lateinit var registLoginViewModel:LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = SignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var mode="USER"

        registLoginViewModel=ViewModelProvider(this).get(LoginViewModel::class.java)

        registLoginViewModel.member?.observe(this, Observer {
            Log.d(TAG,"회원가입 데이터 변경")


        })

        binding.rGroupMode.setOnCheckedChangeListener { compoundButton, i ->
            if(binding.rbCharitymode.isChecked==true)
            {
                binding.textName.setText(R.string.regist_cname)
                binding.editName.setHint("단체명을 입력해주세요")
                binding.textPhone.setText(R.string.regist_cphone)
                binding.editPhone.setHint("단체의 전화번호를 - 없이 입력해주세요")
                mode="CHARITY"

            }
            else
            {
                binding.textName.setText(R.string.regist_name)
                binding.editName.setHint("본인 실명을 입력해주세요")
                binding.textPhone.setText(R.string.regist_phone)
                binding.editPhone.setHint("  - 구분없이 입력해주세요")
                mode="USER"

            }
        }

        binding.btnSubmit.setOnClickListener{

            val memberData:MemberData=MemberData(binding.editId.text.toString(),
                binding.editPwd.text.toString(),binding.editName.text.toString(),
                binding.editPhone.text.toString(),binding.editNick.text.toString(),mode,0,null)

             val registLiveData: MutableLiveData<Int>? = RetrofitManager().registCall(memberData)
            registLiveData?.observe(this, Observer {
                Log.d(TAG,"데이터 변경 login LiveData: ${it}")

                when(registLiveData.value){
                    null->{

                    }
                    200->{
                        var intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                        Toast.makeText(this, "회원가입 성공!", Toast.LENGTH_SHORT).show()
                    }
                    else-> {
                        Toast.makeText(this, "가입 양식을 확인하세요", Toast.LENGTH_SHORT).show()
                    }
                }
            })




        }










    }

}