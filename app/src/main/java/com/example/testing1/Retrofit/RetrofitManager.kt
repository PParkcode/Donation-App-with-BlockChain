package com.example.testing1.Retrofit

import android.content.ContentValues.TAG
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.example.testing1.*
import com.example.testing1.model.*
import com.example.testing1.view.MainActivity
import okhttp3.MultipartBody
import okhttp3.Request
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate

private const val TAG="tag1"
const val BASE_URL ="http://10.0.2.2:8080/"
//repository와 같은 역할을 한다
class RetrofitManager {

    companion object{
        val instance= RetrofitManager()
    }


    //http콜 만들기
    //레트로핏 인터페이스 가져오기
    @RequiresApi(Build.VERSION_CODES.O)
    private val iRetrofit: IRetrofit?= RetrofitClient.getClient(com.example.testing1.Retrofit.BASE_URL)?.create(IRetrofit::class.java)


    @RequiresApi(Build.VERSION_CODES.O)
    fun loginCall(idAndPwd: LoginData):MutableLiveData<ResponseCode>?{
        val data=MutableLiveData<ResponseCode>()
        Log.d(TAG,"loginCall()함수 실행")

        iRetrofit?.loginApi(idAndPwd)?.enqueue(object: Callback<ResponseCode>{

            override fun onResponse(call: Call<ResponseCode>, response: Response<ResponseCode>) {
                Log.d(TAG, "login onResponse 진입")
                if(response.isSuccessful)
                {
                    Log.d(TAG,"ReuestBody: ${idAndPwd}")
                    Log.d(TAG,"isSuccesful entered")
                    Log.d(TAG,"response.body(): ${response.body()}")
                    Log.d(TAG,"response.headers(): ${response.headers()}")

                    val statusData = response.body()!!.code
                    var msg:String? = response.body()!!.message
                    val tmpCookie=response.headers().get("Set-Cookie")

                    Log.d(TAG,"tempCookie: ${tmpCookie}")

                    //var cookie=tmpCookie?.split(";")!!.get(0)
                    //var cookie:String=cookieArr!!.get(0)

                    Log.d(TAG,"Header.(): "+ tmpCookie.toString())
                    //Log.d(TAG,"cookie: ${cookie}")
                    Log.d(TAG,"code:${statusData}")
                    Log.d(TAG, msg.toString())
                    var codeCookie:ResponseCode?=null
                    if(tmpCookie==null){
                        codeCookie=ResponseCode(statusData,"")
                    }
                    else{

                        codeCookie=ResponseCode(statusData,tmpCookie)
                    }



                    data.value=codeCookie!!
                    Log.d(TAG,"data.value: ${data.value}")
                }
                else{
                    val errResponse:ResponseCode=ResponseCode(response.code(),"error")
                    data.value=errResponse
                    Log.d(TAG,response.code().toString())
                    Log.d(TAG,"response is not successful")
                }
            }
            override fun onFailure(call: Call<ResponseCode>, t: Throwable) {
                Log.d(TAG,"login함수 onFailure 진입\n t:${t}")
               // data?.value?.pointAmount=999
                t.stackTrace
            }
        })
        Log.d(TAG,"리턴 전 마지막 출력 : ${data.value}")
        return data
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun registCall(registData: MemberData):MutableLiveData<Int>?{
        val data=MutableLiveData<Int>()
        Log.d(TAG,"registCall()함수 실행")

        iRetrofit?.registApi(registData)?.enqueue(object: Callback<ResponseCode>{

            override fun onResponse(call: Call<ResponseCode>, response: Response<ResponseCode>) {
                Log.d(TAG, "regist onResponse 진입")
                if(response.isSuccessful)
                {
                    val statusData = response.body()!!.code
                    val msg = response.body()!!.message
                    val tmpCookie=response.headers().get("Set-Cookie")
                    var cookieArr=tmpCookie?.split(";")
                    var cookie:String=cookieArr!!.get(0)

                    Log.d(TAG,"Header.(): "+ tmpCookie.toString())
                    Log.d(TAG,"cookie:${cookie}")
                    Log.d(TAG,"code:${statusData}")
                    Log.d(TAG, msg)

                    data.value=statusData
                    Log.d(TAG,"data.value: ${data.value}")
                }
                else{
                    data.value=response.code()
                    Log.d(TAG,response.code().toString())
                    Log.d(TAG,"response is not successful")
                }
            }
            override fun onFailure(call: Call<ResponseCode>, t: Throwable) {
                Log.d(TAG,"regist함수 onFailure 진입\n t:${t}")

                t.stackTrace
            }
        })
        Log.d(TAG,"리턴 전 마지막 출력 : ${data.value}")
        return data
    }



    @RequiresApi(Build.VERSION_CODES.O)
    fun getCampaignList(): MutableLiveData<List<Campaign>>{
        Log.d(TAG,"getCampaignList() 실행")
        val data=MutableLiveData<List<Campaign>>()

        iRetrofit?.campListApi(CampaignSearchConditionDto())?.enqueue(object: Callback<List<Campaign>>{
            override fun onResponse(call: Call<List<Campaign>>, response: Response<List<Campaign>>) {
                Log.d(TAG,"getRecommList() onResponse 진입")
                if(response.isSuccessful){
                    Log.d(TAG,"response is Successful")
                    data.value=response.body()!!
                    Log.d(TAG,"campaignList on: ${response.body()}")
                }
                Log.d(TAG,response.code().toString())
            }
            override fun onFailure(call: Call<List<Campaign>>, t: Throwable) {
                Log.d(TAG,"getRecommList() onFailure()")
                Log.d(TAG,t.stackTraceToString())
                t.stackTrace
            }
        })
        return data
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getMyData():MutableLiveData<MemberData> {
        Log.d(TAG, "getMyData() 실행")
        val data = MutableLiveData<MemberData>()

        iRetrofit?.getUserApi()?.enqueue(object : Callback<MemberData> {
            override fun onResponse(call: Call<MemberData>, response: Response<MemberData>) {
                Log.d(TAG,"getMyData() onResponse 진입")
                if(response.isSuccessful){
                    Log.d(TAG,"response is Successful")
                    data.value=response.body()!!
                    Log.d(TAG,"campaignList on: ${response.body()}")
                    MainActivity.getInstance()?.let { MySharedPreferences.setUserMode(it, response.body()!!.memberType) }

                }
                Log.d(TAG,response.code().toString())

            }

            override fun onFailure(call: Call<MemberData>, t: Throwable) {
                Log.d(TAG, "getMyData() onFailure 진입")
            }
        })

        return data
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun getCampaignDetail(campaignId:String):MutableLiveData<Campaign>{
        Log.d(TAG,"getCampaignDetail 실행")
        val data=MutableLiveData<Campaign>()

        iRetrofit?.detailCampApi(campaignId = campaignId)?.enqueue(object :Callback<Campaign>{
            override fun onResponse(call: Call<Campaign>, response: Response<Campaign>) {
                Log.d(TAG,"detailCamapign onResponse 진입")
                if(response.isSuccessful){
                    Log.d(TAG,"response is successful")
                    data.value=response.body()!!
                    Log.d(TAG,"DetailCamapin Response: "+response.body())
                }
                Log.d(TAG,response.code().toString())
            }

            override fun onFailure(call: Call<Campaign>, t: Throwable) {
                Log.d(TAG, "getCampaignDetail onFailure 진입")
            }
        })
        return data

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun registCampaign(//campaignName:RequestBody, charityName:RequestBody, deadline: RequestBody, goalAmount:RequestBody, categories:RequestBody,
                      map:HashMap<String,RequestBody>, coverImage:MultipartBody.Part, detailImage:MultipartBody.Part):ResponseCode?{
        var code:ResponseCode?=null
        Log.d(TAG,"RequestBody(): "+ RequestBody.toString())
        iRetrofit?.registCampaignApi(
            //campaignName,charityName,deadline,goalAmount,categories,coverImage,detailImage
            map,coverImage,detailImage)?.enqueue(object :Callback<ResponseCode>{
            override fun onResponse(call: Call<ResponseCode>, response: Response<ResponseCode>) {
                if(response.isSuccessful){
                    Log.d(TAG,"response.body(): "+response.body().toString())
                    code?.code=response.code()
                    code?.message=response.message()

                }
                else{
                    Log.d(TAG,"통신 실패--> response is not successful")
                }

            }

            override fun onFailure(call: Call<ResponseCode>, t: Throwable) {
                Log.d(TAG,"registCampaign onFailure")
                Log.d(TAG,t.message.toString())
            }
        })
        return code



    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun registCampaginByJson(campaign:Campaign):ResponseCode?{
        var code:ResponseCode?=null
        iRetrofit?.registCampaignByJsonApi(campaign)?.enqueue(object :Callback<ResponseCode>{
            override fun onResponse(call: Call<ResponseCode>, response: Response<ResponseCode>) {

                if(response.isSuccessful){
                    Log.d(TAG,"response.body(): "+response.body().toString())
                    code?.code=response.code()
                    code?.message=response.message()

                }
                else{
                    Log.d(TAG,"통신 실패--> response is not successful")
                }
            }

            override fun onFailure(call: Call<ResponseCode>, t: Throwable) {
                Log.d(TAG,"registCampaign onFailure")
                Log.d(TAG,t.message.toString())
            }
        })
        return code
    }



}