package com.example.testing1.view

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.testing1.DialogInterface
import com.example.testing1.R
import com.example.testing1.databinding.DialogExchangeBinding

class ExchangeDialog(context: Context,myPoint:String,mDialogInterface:DialogInterface): Dialog(context), View.OnClickListener {

    private lateinit var myPoint:String
    private var mDialogInterface:DialogInterface?=null

    init {

        this.myPoint=myPoint
        this.mDialogInterface=mDialogInterface //인터페이스에 연결
    }
    private lateinit var binding:DialogExchangeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.dialog_exchange,null,false)
        setContentView(binding.root)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.myPoint=myPoint
        binding.nextBtn.setOnClickListener(this)



    }

    override fun onClick(view: View?) {
        Log.d("tag2","onClick on")
        when(view){
            binding.nextBtn->{
                var str:String= binding.exchangeEdit.text.toString().trim()
                Log.d("tag2","str: ${str}")
                Log.d("tag2","nextBtn onClick on")
                if(str.isEmpty()){
                    Toast.makeText(context,"값을 입력해주세요",Toast.LENGTH_SHORT).show()
                }
                else if(str.toInt() > myPoint.toInt()){
                    Log.d("tag2","text size: "+str)
                    Log.d("tag2","over point")
                    binding.exchangeEdit.setText(null)
                    binding.alertText.visibility=View.VISIBLE
                }
                else{
                    Log.d("tag2","interfacte onNextBtnClick on")
                    this.mDialogInterface?.onNextBtnClick(binding.exchangeEdit.text.toString())
                }
            }
        }

    }
}