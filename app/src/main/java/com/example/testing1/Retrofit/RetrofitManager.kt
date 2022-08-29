package com.example.testing1.Retrofit

import android.content.ContentValues.TAG
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.testing1.*
import com.example.testing1.model.*
import com.example.testing1.view.ExchangeActivity2
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
const val NH_URL="https://developers.nonghyup.com/"
//repository와 같은 역할을 한다
class RetrofitManager {

    companion object {
        val instance = RetrofitManager()
    }


    //http콜 만들기
    //레트로핏 인터페이스 가져오기
    @RequiresApi(Build.VERSION_CODES.O)
    private val iRetrofit: IRetrofit? = RetrofitClient.getClient(com.example.testing1.Retrofit.BASE_URL)?.create(IRetrofit::class.java)
    @RequiresApi(Build.VERSION_CODES.O)
    private val iRetrofit2:IRetrofit? = RetrofitClient2.getClient(com.example.testing1.Retrofit.NH_URL)?.create(IRetrofit::class.java)


    @RequiresApi(Build.VERSION_CODES.O)
    fun loginCall(idAndPwd: LoginData): MutableLiveData<ResponseCode>? {
        val data = MutableLiveData<ResponseCode>()
        Log.d(TAG, "loginCall()함수 실행")

        iRetrofit?.loginApi(idAndPwd)?.enqueue(object : Callback<ResponseCode> {

            override fun onResponse(call: Call<ResponseCode>, response: Response<ResponseCode>) {
                Log.d(TAG, "login onResponse 진입")
                if (response.isSuccessful) {
                    Log.d(TAG, "ReuestBody: ${idAndPwd}")
                    Log.d(TAG, "isSuccesful entered")
                    Log.d(TAG, "response.body(): ${response.body()}")
                    Log.d(TAG, "response.headers(): ${response.headers()}")

                    val statusData = response.body()!!.code
                    var msg: String? = response.body()!!.message
                    val tmpCookie = response.headers().get("Set-Cookie")

                    Log.d(TAG, "tempCookie: ${tmpCookie}")

                    //var cookie=tmpCookie?.split(";")!!.get(0)
                    //var cookie:String=cookieArr!!.get(0)

                    Log.d(TAG, "Header.(): " + tmpCookie.toString())
                    //Log.d(TAG,"cookie: ${cookie}")
                    Log.d(TAG, "code:${statusData}")
                    Log.d(TAG, msg.toString())
                    var codeCookie: ResponseCode? = null
                    if (tmpCookie == null) {
                        codeCookie = ResponseCode(statusData, "")
                    } else {

                        codeCookie = ResponseCode(statusData, tmpCookie)
                    }

                    data.value = codeCookie!!
                    Log.d(TAG, "data.value: ${data.value}")
                } else {
                    val errResponse: ResponseCode = ResponseCode(response.code(), "error")
                    data.value = errResponse
                    Log.d(TAG, response.code().toString())
                    Log.d(TAG, "response is not successful")
                }
            }

            override fun onFailure(call: Call<ResponseCode>, t: Throwable) {
                Log.d(TAG, "login함수 onFailure 진입\n t:${t}")

                t.stackTrace
            }
        })

        return data
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun registCall(registData: MemberData): MutableLiveData<Int>? {
        val data = MutableLiveData<Int>()
        Log.d(TAG, "registCall()함수 실행")

        iRetrofit?.registApi(registData)?.enqueue(object : Callback<ResponseCode> {

            override fun onResponse(call: Call<ResponseCode>, response: Response<ResponseCode>) {
                Log.d(TAG, "regist onResponse 진입")
                if (response.isSuccessful) {
                    val statusData = response.body()!!.code
                    val msg = response.body()!!.message
                    val tmpCookie = response.headers().get("Set-Cookie")
                    var cookieArr = tmpCookie?.split(";")
                    var cookie: String = cookieArr!!.get(0)

                    data.value = statusData
                    Log.d(TAG, "data.value: ${data.value}")
                } else {
                    data.value = response.code()
                    Log.d(TAG, response.code().toString())
                    Log.d(TAG, "response is not successful")
                }
            }

            override fun onFailure(call: Call<ResponseCode>, t: Throwable) {
                Log.d(TAG, "regist함수 onFailure 진입\n t:${t}")

                t.stackTrace
            }
        })
        Log.d(TAG, "리턴 전 마지막 출력 : ${data.value}")
        return data
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun getCampaignList(): MutableLiveData<List<Campaign>> {
        Log.d(TAG, "getCampaignList() 실행")
        val data = MutableLiveData<List<Campaign>>()

        iRetrofit?.campListApi(CampaignSearchConditionDto())?.enqueue(object : Callback<List<Campaign>> {
            override fun onResponse(call: Call<List<Campaign>>, response: Response<List<Campaign>>) {
                Log.d(TAG, "getRecommList() onResponse 진입")
                if (response.isSuccessful) {
                    Log.d(TAG, "response is Successful")
                    data.value = response.body()!!
                    Log.d(TAG, "campaignList on: ${response.body()}")
                }
                Log.d(TAG, response.code().toString())
            }

            override fun onFailure(call: Call<List<Campaign>>, t: Throwable) {
                Log.d(TAG, "getRecommList() onFailure()")
                Log.d(TAG, t.stackTraceToString())
                t.stackTrace
            }
        })
        return data
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getMyData(): MutableLiveData<MemberData> {
        Log.d(TAG, "getMyData() 실행")
        val data = MutableLiveData<MemberData>()

        iRetrofit?.getUserApi()?.enqueue(object : Callback<MemberData> {
            override fun onResponse(call: Call<MemberData>, response: Response<MemberData>) {
                Log.d(TAG, "getMyData() onResponse 진입")
                if (response.isSuccessful) {
                    Log.d(TAG, "response is Successful")
                    data.value = response.body()!!
                    Log.d(TAG, "campaignList on: ${response.body()}")
                    MainActivity.getInstance()?.let { MySharedPreferences.setUserMode(it, response.body()!!.memberType) }

                }
                Log.d(TAG, response.code().toString())

            }

            override fun onFailure(call: Call<MemberData>, t: Throwable) {
                Log.d(TAG, "getMyData() onFailure 진입")
            }
        })

        return data
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun getCampaignDetail(campaignId: String): MutableLiveData<Campaign> {
        Log.d(TAG, "getCampaignDetail 실행")
        val data = MutableLiveData<Campaign>()

        iRetrofit?.detailCampApi(campaignId = campaignId)?.enqueue(object : Callback<Campaign> {
            override fun onResponse(call: Call<Campaign>, response: Response<Campaign>) {
                Log.d(TAG, "detailCamapign onResponse 진입")
                if (response.isSuccessful) {
                    Log.d(TAG, "response is successful")
                    data.value = response.body()!!
                    Log.d(TAG, "DetailCamapin Response: " + response.body())
                }
                Log.d(TAG, response.code().toString())
            }

            override fun onFailure(call: Call<Campaign>, t: Throwable) {
                Log.d(TAG, "getCampaignDetail onFailure 진입")
            }
        })
        return data

    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun registCampaginByJson(campaign: Campaign): ResponseCode? {
        var code: ResponseCode? = null
        iRetrofit?.registCampaignByJsonApi(campaign)?.enqueue(object : Callback<ResponseCode> {
            override fun onResponse(call: Call<ResponseCode>, response: Response<ResponseCode>) {
                Log.d("registCampaign", "response.body(): " + response.body().toString())
                Log.d("registCampaign", "response.code(): " + response.code().toString())

                if (response.isSuccessful) {

                    code?.code = response.code()
                    code?.message = response.message()

                } else {
                    Log.d("registCampaign", "캠페인 등록 통신 실패--> response is not successful")
                }
            }

            override fun onFailure(call: Call<ResponseCode>, t: Throwable) {
                Log.d("registCampaign", "registCampaign onFailure")
                Log.d("registCampaign", t.message.toString())
            }
        })
        return code
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun donate(amount: String,completion: (Int)-> Unit): ResponseCode? {
        var code: ResponseCode? = null
        iRetrofit?.donateApi(amount)?.enqueue(object : Callback<ResponseCode> {
            override fun onResponse(call: Call<ResponseCode>, response: Response<ResponseCode>) {

                if (response.isSuccessful) {
                    Log.d(TAG, "response.body(): " + response.body().toString())
                    code?.code = response.code()
                    code?.message = response.message()
                    completion(response.code())

                } else {
                    Log.d(TAG, "통신 실패--> response is not successful")
                    Log.d(TAG,code?.message.toString())
                    completion(response.code())
                }
            }

            override fun onFailure(call: Call<ResponseCode>, t: Throwable) {

                Log.d(TAG, t.message.toString())
            }
        })
        return code


    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun charge(amount: String,completion: (Int) -> Unit): ResponseCode? {
        var code: ResponseCode? = null
        iRetrofit?.chargeApi(amount)?.enqueue(object : Callback<ResponseCode> {
            override fun onResponse(call: Call<ResponseCode>, response: Response<ResponseCode>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "response.body(): " + response.body().toString())
                    code?.code = response.code()
                    code?.message = response.message()
                    completion(response.code())

                } else {
                    Log.d(TAG, "통신 실패--> response is not successful")
                    Log.d(TAG,code?.message.toString())
                }
            }

            override fun onFailure(call: Call<ResponseCode>, t: Throwable) {

                Log.d(TAG, t.message.toString())
            }
        })
        return code


    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun logout(): ResponseCode? {
        var code: ResponseCode? = null
        iRetrofit?.logoutApi()?.enqueue(object : Callback<ResponseCode> {
            override fun onResponse(call: Call<ResponseCode>, response: Response<ResponseCode>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "response.body(): " + response.body().toString())
                    code?.code = response.code()
                    code?.message = response.message()


                } else {
                    Log.d(TAG, "통신 실패--> response is not successful")
                    Log.d(TAG,code?.message.toString())
                }
            }

            override fun onFailure(call: Call<ResponseCode>, t: Throwable) {

                Log.d(TAG, t.message.toString())
            }
        })
        return code


    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun imageUpload(file: MultipartBody.Part): LiveData<String>? {
        val imageLiveData:MutableLiveData<String> = MutableLiveData()
        Log.d(TAG, "RequestBody(): " + RequestBody.toString())
        iRetrofit?.imageUploadApi(file)?.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                Log.d("img", "response.body(): " + response.body().toString())
                Log.d("img","response.code()"+response.code())
                if (response.isSuccessful) {
                   imageLiveData.value=response.body()

                } else {

                    Log.d("img", "이미지 통신 실패")
                }

            }
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("img", "이미지통신 OnFailure")
                Log.d("img", t.message.toString())
                Log.d("img",t.stackTraceToString())
            }
        })
        return imageLiveData
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun withDraw(campaignId:String, data:WithDrawData,completion: (Int) -> Unit):MutableLiveData<ResponseCode>?{
        val responseLiveData:MutableLiveData<ResponseCode> = MutableLiveData()

        iRetrofit?.withDrawApi(campaignId = campaignId,data)?.enqueue(object :Callback<ResponseCode>{
            override fun onResponse(call: Call<ResponseCode>, response: Response<ResponseCode>) {
                Log.d(TAG,"withDrawApi: "+response.code().toString())
                Log.d(TAG,"withDrawApi: "+response.body().toString())
                if(response.isSuccessful){
                    responseLiveData.value=response.body()
                    Log.d(TAG,"withDrawApi is Success ")
                    completion(200)
                }
                else{
                    Log.d(TAG,"withDrawApi is NOT Success ")
                }
            }

            override fun onFailure(call: Call<ResponseCode>, t: Throwable) {
                Log.d(TAG,"withDrawApi is onFailure ")
                t.stackTrace
            }
        })
        return responseLiveData

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun exchange(data:ExchangeData):MutableLiveData<ExchangeResponseData>?{
        var responseLivedData:MutableLiveData<ExchangeResponseData> = MutableLiveData()

        iRetrofit2?.exchangeApi(data)?.enqueue(object :Callback<ExchangeResponseData>{
            override fun onResponse(call: Call<ExchangeResponseData>, response: Response<ExchangeResponseData>) {
                responseLivedData.value=response.body()
                Log.d("NHResponse","onResponse 진입")
                Log.d("NHResponse",response.body().toString())
                Log.d("NHResponse",response.code().toString())
                
            }

            override fun onFailure(call: Call<ExchangeResponseData>, t: Throwable) {
                Log.d("NHResponse","onFailure 진입")
                t.stackTrace
            }
        })
        return responseLivedData
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun payBack(amount: String,completion:(Int) -> Unit):MutableLiveData<ResponseCode>{
        var responseLiveData:MutableLiveData<ResponseCode> = MutableLiveData()
        iRetrofit?.payBackApi(amount)?.enqueue(object : Callback<ResponseCode>{
            override fun onResponse(call: Call<ResponseCode>, response: Response<ResponseCode>) {

                Log.d("tag1","payback ON Response")
                responseLiveData.value=response.body()
                completion(response.code())
            }

            override fun onFailure(call: Call<ResponseCode>, t: Throwable) {
                t.stackTrace
            }
        })
        return responseLiveData

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getTxList(): MutableLiveData<List<TransactionForm>> {

        val data = MutableLiveData<List<TransactionForm>>()

        iRetrofit?.txApi()?.enqueue(object : Callback<List<TransactionForm>> {
            override fun onResponse(call: Call<List<TransactionForm>>, response: Response<List<TransactionForm>>) {
                Log.d(TAG, "getRecommList() onResponse 진입")
                if (response.isSuccessful) {
                    data.value = response.body()!!
                }
                Log.d(TAG, response.code().toString())
            }

            override fun onFailure(call: Call<List<TransactionForm>>, t: Throwable) {
                Log.d(TAG, "getRecommList() onFailure()")
                Log.d(TAG, t.stackTraceToString())
                t.stackTrace
            }
        })
        return data
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getMyCampaign():MutableLiveData<List<Campaign>>{
        val data=MutableLiveData<List<Campaign>>()

        iRetrofit?.getMyCampaignApi()?.enqueue(object : Callback<List<Campaign>>{
            override fun onResponse(call: Call<List<Campaign>>, response: Response<List<Campaign>>) {
                if (response.isSuccessful) {
                    data.value = response.body()!!
                }
                Log.d(TAG, response.code().toString())
            }

            override fun onFailure(call: Call<List<Campaign>>, t: Throwable) {
                Log.d(TAG, "getMyCampList() onFailure()")
                Log.d(TAG, t.stackTraceToString())
                t.stackTrace
            }
        })

        return data
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getHistory(campaignId: String):MutableLiveData<List<WithDrawData>>{
        val data=MutableLiveData<List<WithDrawData>>()

        iRetrofit?.getHistoryApi(campaignId)?.enqueue(object: Callback<List<WithDrawData>>{

            override fun onResponse(call: Call<List<WithDrawData>>, response: Response<List<WithDrawData>>) {
                if (response.isSuccessful) {
                    data.value = response.body()!!
                }
                Log.d(TAG, response.code().toString())
                Log.d("history1",response.body().toString())
            }

            override fun onFailure(call: Call<List<WithDrawData>>, t: Throwable) {
                Log.d(TAG, "getHistory onFailure()")
                Log.d(TAG, t.stackTraceToString())
                t.stackTrace
            }
        })
        return data
    }



}