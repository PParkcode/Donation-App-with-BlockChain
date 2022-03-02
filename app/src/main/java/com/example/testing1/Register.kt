package com.example.testing1

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.google.gson.Gson
import com.google.gson.GsonBuilder
private const val TAG="tag1"

class Register :Activity(){
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup)

        lateinit var text_name: TextView
        lateinit var text_phone: TextView
        lateinit var text_birth: TextView
        lateinit var rGroup_mode:RadioGroup
        lateinit var text_sex:TextView
        lateinit var rb_usermode:RadioButton
        lateinit var rb_charitymode:RadioButton
        lateinit var edit_id:EditText
        lateinit var edit_pwd:EditText
        lateinit var edit_pwd2:EditText
        lateinit var edit_name:EditText
        lateinit var edit_phone:EditText
        lateinit var edit_birth:EditText
        lateinit var rGroup_sex:RadioGroup
        lateinit var rb_m:RadioButton
        lateinit var rb_f:RadioButton
        lateinit var edit_nick:EditText
        lateinit var btn_testid:Button
        lateinit var btn_testnick:Button
        lateinit var btn_submit:Button

        text_name=findViewById(R.id.text_name)
        text_phone=findViewById(R.id.text_phone)
        text_birth=findViewById(R.id.text_birth)
        text_sex=findViewById(R.id.text_sex)
        rGroup_mode=findViewById(R.id.rGroup_mode)
        rb_usermode=findViewById(R.id.rb_usermode)
        rb_charitymode=findViewById(R.id.rb_charitymode)
        edit_id=findViewById(R.id.edit_id)
        edit_pwd=findViewById(R.id.edit_pwd)
        edit_pwd2=findViewById(R.id.edit_pwd2)
        edit_name=findViewById(R.id.edit_name)
        edit_phone=findViewById(R.id.edit_phone)
        edit_birth=findViewById(R.id.edit_birth)
        rGroup_sex=findViewById(R.id.rGroup_sex)
        rb_m=findViewById(R.id.rb_m)
        rb_f=findViewById(R.id.rb_f)
        edit_nick=findViewById(R.id.edit_nick)
        btn_testid=findViewById(R.id.btn_testid)
        btn_testnick=findViewById(R.id.btn_testnick)
        btn_submit=findViewById(R.id.btn_submit)



        rGroup_mode.setOnCheckedChangeListener { compoundButton, i ->
            if(rb_charitymode.isChecked==true)
            {
                text_name.setText(R.string.regist_cname)
                edit_name.setHint("단체명을 입력해주세요")
                text_phone.setText(R.string.regist_cphone)
                edit_phone.setHint("단체의 전화번호를 - 없이 입력해주세요")
                text_birth.visibility=android.view.View.GONE
                edit_birth.visibility=android.view.View.GONE
                rGroup_sex.visibility=android.view.View.GONE
                text_sex.visibility=android.view.View.GONE
            }
            else
            {
                text_name.setText(R.string.regist_name)
                edit_name.setHint("본인 실명을 입력해주세요")
                text_phone.setText(R.string.regist_phone)
                edit_phone.setHint("  - 구분없이 입력해주세요")
                text_birth.visibility=android.view.View.VISIBLE
                edit_birth.visibility=android.view.View.VISIBLE
                rGroup_sex.visibility=android.view.View.VISIBLE
                text_sex.visibility=android.view.View.VISIBLE
            }
        }
        btn_submit.setOnClickListener{
            val memberData:MemberData=MemberData(edit_id.text.toString(),
                edit_pwd.text.toString(),edit_name.text.toString(),
                edit_phone.text.toString(),edit_birth.text.toString(),1,edit_nick.text.toString(),1)
            val json:String= makeJson(memberData)
            Log.d(TAG,json)
        }


    }
    private fun makeJson(memberData:MemberData):String
    {
        var gson: Gson = GsonBuilder().create()
        var json:String=gson.toJson(memberData)
        return json
    }
}