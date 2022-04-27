package com.example.testing1.view

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Base64.NO_WRAP
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.testing1.R
import com.example.testing1.Retrofit.RetrofitManager.Companion.instance
import com.example.testing1.databinding.RegistCampaignBinding
import com.example.testing1.model.Campaign
import kotlinx.android.synthetic.main.regist_campaign.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream


class RegistCampaign:AppCompatActivity() {
    private lateinit var binding:RegistCampaignBinding
    lateinit var coverImg:MultipartBody.Part
    lateinit var detailImg:MultipartBody.Part
    lateinit var coverBitmap:Bitmap
    lateinit var detailBitmap:Bitmap
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

       var getCoverResult= registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
           if(it.resultCode== RESULT_OK){
               val imagePath: Uri? = it.data?.data

               val file = File(absolutelyPath(imagePath, this))
               val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
               coverImg = MultipartBody.Part.createFormData("coverImagePath", file.name, requestFile)


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
                detailImg = MultipartBody.Part.createFormData("detailImagePath", file.name, requestFile)
                Log.d("tag1","file.name: ${file.name} \nrequestFile: ${requestFile}")

                /*
                //json 이미지 압축 통신 방법
                val ins: InputStream? = imagePath?.let {
                    applicationContext.contentResolver.openInputStream(
                            it
                    )
                }
                val img: Bitmap = BitmapFactory.decodeStream(ins)
                ins?.close()
                val resized = Bitmap.createScaledBitmap(img, 256, 256, true)
                val byteArrayOutputStream = ByteArrayOutputStream()
                resized.compress(Bitmap.CompressFormat.JPEG, 60, byteArrayOutputStream)
                val byteArray: ByteArray = byteArrayOutputStream.toByteArray()
                val outStream = ByteArrayOutputStream()
                val res: Resources = resources
                var profileImageBase64 = Base64.encodeToString(byteArray, NO_WRAP)
                // 여기까지 인코딩 끝

                 */

                try{
                    detailBitmap= MediaStore.Images.Media.getBitmap(contentResolver, imagePath)
                    binding.detailImageview.setImageBitmap(detailBitmap)
                }catch (e: Exception){
                    e.printStackTrace()
                }
            }
        }
        binding.deadlineDatePicker.setOnDateChangedListener{view,year,month,dayOfMonth->

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

        binding.submitBtn.setOnClickListener{
            var campName=binding.editCampaignTitle.text.toString()
            var campDes=binding.editCampaignDes.text.toString()
            var goal=binding.editGoal.text.toString().toInt()
            Log.d("tag1","goal: "+goal)
            var deadline=binding.deadlineDatePicker.toString()
            Log.d("tag1","deadline: "+deadline)
            var categories=binding.editCategories.text.toString()

            var charityNameBody:RequestBody=charityName!!.toRequestBody("text/plain".toMediaTypeOrNull())
            var nameBody:RequestBody= campName.toRequestBody("text/plain".toMediaTypeOrNull())
            var desBody:RequestBody= campDes.toRequestBody("text/plain".toMediaTypeOrNull())
            //var goalBody:RequestBody= goal.toRequestBody("text/plain".toMediaTypeOrNull())
            var deadlineBody:RequestBody=deadline.toRequestBody("text/plain".toMediaTypeOrNull())
            var categoriesBody:RequestBody=categories.toRequestBody("text/plain".toMediaTypeOrNull())
            val requestMap: HashMap<String, RequestBody> = HashMap()
            requestMap["campaignName"] = nameBody
            requestMap["charityName"] = charityNameBody
            requestMap["deadline"] = deadlineBody
           // requestMap["goalAmount"]=goalBody
            requestMap["categories"]=categoriesBody

            var coverImg=BitmapToString(coverBitmap)
            var detailImg=BitmapToString(detailBitmap)

            var campaign: Campaign =Campaign(0,"",campName,charityName,null,0,goal,null,"","","",campDes)



            instance.registCampaginByJson(campaign)


            //instance.registCampaign(requestMap, coverImg, detailImg)
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

    fun BitmapToString(bitmap: Bitmap): String {
        val baos =
            ByteArrayOutputStream() //바이트 배열을 차례대로 읽어 들이기위한 ByteArrayOutputStream클래스 선언
        bitmap.compress(Bitmap.CompressFormat.PNG, 10, baos) //bitmap을 압축 (숫자 70은 70%로 압축한다는 뜻)
        val bytes = baos.toByteArray() //해당 bitmap을 byte배열로 바꿔준다.
        return Base64.encodeToString(bytes, Base64.DEFAULT) //Base 64 방식으로byte 배열을 String으로 변환
        //String을 retrurn
    }
    fun StringToBitmap(encodedString: String?): Bitmap? {
        return try {
            val encodeByte: ByteArray = Base64.decode(
                encodedString,
                Base64.DEFAULT
            ) // String 화 된 이미지를  base64방식으로 인코딩하여 byte배열을 만듬
            BitmapFactory.decodeByteArray(
                encodeByte,
                0,
                encodeByte.size
            ) //byte배열을 bitmapfactory 메소드를 이용하여 비트맵으로 바꿔준다.
            //만들어진 bitmap을 return
        } catch (e: Exception) {
            e.message
            null
        }
    }

}