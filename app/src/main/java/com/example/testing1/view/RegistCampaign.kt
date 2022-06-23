package com.example.testing1.view

import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.PopupMenu
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.IntegerRes
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.testing1.R
import com.example.testing1.Retrofit.RetrofitManager.Companion.instance
import com.example.testing1.databinding.RegistCampaignBinding
import com.example.testing1.model.Campaign
import kotlinx.android.synthetic.main.regist_campaign.*
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okio.BufferedSink
import java.io.ByteArrayOutputStream
import java.io.File
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import kotlin.collections.HashMap


class RegistCampaign:AppCompatActivity() {
    private lateinit var binding:RegistCampaignBinding
    lateinit var coverImg:MultipartBody.Part
    lateinit var detailImg:MultipartBody.Part
    lateinit var coverBitmap:Bitmap
    lateinit var detailBitmap:Bitmap



    var coverUrl:LiveData<String>? =MutableLiveData<String>()
    var detailUrl:LiveData<String>? = MutableLiveData<String>()
    @RequiresApi(Build.VERSION_CODES.O)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView<RegistCampaignBinding>(this, R.layout.regist_campaign)
        setContentView(binding.root)
        var actionBar: ActionBar?
        actionBar=supportActionBar
        actionBar?.hide()
        var intent = getIntent()
       var charityName=intent.getStringExtra("name")




        coverUrl?.observe(this, androidx.lifecycle.Observer {
            Log.d("img","coverUrl 변화 감지!!"+it.toString())
        })
        detailUrl?.observe(this, androidx.lifecycle.Observer {
            Log.d("img","detailUrl 변화 감지!!"+it.toString())
        })

       var getCoverResult= registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
           if(it.resultCode== RESULT_OK){
               val imagePath: Uri? = it.data?.data

               val file = File(absolutelyPath(imagePath, this))
               val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
               coverImg = MultipartBody.Part.createFormData("file", file.name, requestFile)
               Log.d("img",coverImg.toString())
               coverUrl = instance.imageUpload(coverImg)
               Log.d("img","coverUrl"+detailUrl.toString())
               Log.d("img", "file.name: ${file.name} \nrequestFile: ${requestFile}")

               try{
                   coverBitmap= MediaStore.Images.Media.getBitmap(contentResolver, imagePath)
                   binding.coverImageview.setImageBitmap(coverBitmap)

               }catch (e: Exception){
                   e.printStackTrace()
               }
           }
       }
        var getDetailResult= registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode== RESULT_OK){
                val imagePath: Uri? = it.data?.data

                val file = File(absolutelyPath(imagePath, this))
                val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                detailImg = MultipartBody.Part.createFormData("file", file.name, requestFile)
                Log.d("img",detailImg.toString())
                detailUrl=instance.imageUpload(detailImg)
                Log.d("img","detailUrl"+detailUrl.toString())
                Log.d("img", "file.name: ${file.name} \nrequestFile: ${requestFile}")


                try{
                    detailBitmap= MediaStore.Images.Media.getBitmap(contentResolver, imagePath)
                    binding.detailImageview.setImageBitmap(detailBitmap)
                }catch (e: Exception){
                    e.printStackTrace()
                }
            }
        }

        binding.uploadBtn.setOnClickListener{
            val intent=Intent(Intent.ACTION_PICK)
            intent.setType("image/*")
            getCoverResult.launch(intent)

        }
        binding.uploadBtn2.setOnClickListener{
            val intent=Intent(Intent.ACTION_PICK)
            intent.setType("image/*")
            getDetailResult.launch(intent)

        }

        binding.caBtn.setOnClickListener{
            var popupMenu=PopupMenu(applicationContext,it)
            menuInflater?.inflate(R.menu.category_menu,popupMenu.menu)

            var listener=PopupMenuListener()
            popupMenu.setOnMenuItemClickListener(listener)
            popupMenu.show()
        }




        binding.submitBtn.setOnClickListener{
            var campName=binding.editCampaignTitle.text.toString()
            var campDes=binding.editCampaignDes.text.toString()
            var goal=binding.editGoal.text.toString().toInt()

            var y=binding.deadlineDatePicker.year
            var m=binding.deadlineDatePicker.month+1
            var d=binding.deadlineDatePicker.dayOfMonth

            var date=LocalDate.of(y,m,d)

            Log.d("date1", "date: " + date)


            var campaign: Campaign =Campaign(0, "", campName, charityName.toString(), date.toString(), 0, goal, null, coverUrl?.value.toString(), detailUrl?.value.toString(), "", campDes)

            instance.registCampaginByJson(campaign)

            var intent=Intent(applicationContext, MainNav::class.java)
            startActivity(intent)
        }
    }


    // 절대경로 변환

    fun absolutelyPath(path: Uri?, context: Context): String {
        var proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
        var c: Cursor? = context.contentResolver.query(path!!, proj, null, null, null)
        var index = c?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        c?.moveToFirst()
        var result = c?.getString(index!!)
        return result!!
    }
    inner class BitmapRequestBody(private val bitmap: Bitmap) : RequestBody() {
        override fun contentType(): MediaType = "image/jpeg".toMediaType()
        override fun writeTo(sink: BufferedSink) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 99, sink.outputStream())
        }
    }
    inner class PopupMenuListener:PopupMenu.OnMenuItemClickListener{
        override fun onMenuItemClick(item: MenuItem?): Boolean {
            when(item?.itemId){
                R.id.ca1 ->{
                    binding.categoriesText.text=binding.categoriesText.text.toString() +"아동,"
                }
                R.id.ca2 ->{
                    binding.categoriesText.text=binding.categoriesText.text.toString()+"청년,"
                }


                R.id.clear->{
                    binding.categoriesText.text=""
                }
            }
            return false

        }
    }


}