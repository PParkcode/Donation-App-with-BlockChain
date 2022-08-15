package com.example.testing1.view

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.testing1.MySharedPreferences
import com.example.testing1.R
import com.example.testing1.Retrofit.RetrofitManager
import com.example.testing1.databinding.WithdrawFormBinding
import com.example.testing1.model.WithDrawData
import com.example.testing1.viewModel.ResponseViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File


class WithDrawForm:AppCompatActivity() {
    private lateinit var binding: WithdrawFormBinding
    val responseViewModel by viewModels<ResponseViewModel>()

    lateinit var multipartFile:MultipartBody.Part


    var fileUrl: LiveData<String>? = MutableLiveData<String>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var intent = getIntent()
        val campaignId: Int = intent.getIntExtra("id", 0)
        val amount=intent.getIntExtra("amount", 0)
        Log.d("with1", "campaignID: ${campaignId}\n amount: ${amount}")

        binding= DataBindingUtil.setContentView<WithdrawFormBinding>(this, R.layout.withdraw_form)
        setContentView(binding.root)

        binding.lifecycleOwner = this
        responseViewModel._response?.observe(this, Observer {
            Log.d("with1", "withDraw response 데이터 변화 감지!")
            var intent2 = Intent(this, MainNav::class.java)
            startActivity(intent2)
        })

        var getResult= registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode== RESULT_OK){
                val path: Uri? = it.data?.data

                val file = File(absolutelyPath(path, this))
               // val file= File(path.toString())

                Log.d("file1","path: ${path}")
                Log.d("file1","file: ${file}")
                var requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())

                Log.d("file1","requestFile: ${requestFile}")
                multipartFile = MultipartBody.Part.createFormData("file", file.name, requestFile)

                fileUrl = RetrofitManager.instance.imageUpload(multipartFile)


                try{

                    binding.fileName.text=fileUrl?.value.toString()

                }catch (e: Exception){
                    e.printStackTrace()
                }
            }
        }

        binding.submitBtn.setOnClickListener{
            var data:WithDrawData = WithDrawData("유동근", MySharedPreferences.getUserName(this), binding.purposeEdit.text.toString(), amount,fileUrl?.value.toString())
            responseViewModel.getPoint(campaignId.toString(), data)
        }
        binding.inputFile.setOnClickListener{

            if(binding.imgBtn.isChecked){
                val intentFile=Intent(Intent.ACTION_PICK)
                intentFile.setType("image/*")
                getResult.launch(intentFile)
            }
            else{

            }

        }

    }

    fun absolutelyPath(path: Uri?, context: Context): String {
        var proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
        var c: Cursor? = context.contentResolver.query(path!!, null, null, null, null)
        var index = c?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        c?.moveToFirst()
        var result = c?.getString(index!!)
        return result!!
    }

}