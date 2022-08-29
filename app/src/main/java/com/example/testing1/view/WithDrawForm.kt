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
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.testing1.MySharedPreferences
import com.example.testing1.R
import com.example.testing1.Retrofit.RetrofitManager
import com.example.testing1.Retrofit.RetrofitManager.Companion.instance
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
    private lateinit var dialog:DonateCustomDialog

    lateinit var multipartFile:MultipartBody.Part
    var amount:Int=0

    var fileUrl: LiveData<String>? = MutableLiveData<String>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var intent = getIntent()
        val campaignId: Int = intent.getIntExtra("id", 0)
        amount=intent.getIntExtra("amount", 0)
        Log.d("with1", "campaignID: ${campaignId}\n amount: ${amount}")

        binding= DataBindingUtil.setContentView<WithdrawFormBinding>(this, R.layout.withdraw_form)
        setContentView(binding.root)

        var actionBar: ActionBar?
        actionBar = supportActionBar
        actionBar?.hide()

        binding.amount=amount.toString()
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

                fileUrl = instance.imageUpload(multipartFile)

                fileUrl?.observe(this, Observer {
                    binding.fileName.text=fileUrl?.value.toString()
                })
                /*
                try{

                    binding.fileName.text=fileUrl?.value.toString()

                }catch (e: Exception){
                    e.printStackTrace()
                }

                 */
            }
        }

        binding.submitBtn.setOnClickListener{
            var data:WithDrawData = WithDrawData("캡스톤 어플", MySharedPreferences.getUserName(this), binding.purposeEdit.text.toString(), binding.pointEdit.text.toString().toInt(),fileUrl?.value.toString())
            //responseViewModel.getPoint(campaignId.toString(), data)
            instance.withDraw(campaignId.toString(),data,completion = { responseCode ->
                when(responseCode) {
                    200 -> { // 여기서 LoadingActivity에 콜백 사용하고 싶은데...
                        val loadingIntent: Intent = Intent(this, LoadingActivity::class.java)
                        startActivity(loadingIntent)
                    }
                    else -> {
                        Toast.makeText(this, "포인트 인출에 실패하였습니다", Toast.LENGTH_SHORT).show()
                    }
                }
            })
            dialog=DonateCustomDialog(this)
            dialog.show()
        }
        binding.inputFile.setOnClickListener{

            val intentFile=Intent(Intent.ACTION_PICK)
            intentFile.setType("image/*")
            getResult.launch(intentFile)


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