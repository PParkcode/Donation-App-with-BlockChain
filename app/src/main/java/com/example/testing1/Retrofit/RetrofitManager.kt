package com.example.testing1.Retrofit

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.testing1.*
import com.google.gson.JsonElement
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
private const val TAG="tag1"
const val BASE_URL ="http://10.0.2.2:8080/"
//repository와 같은 역할을 한다
class RetrofitManager {

    companion object{
        val instance= RetrofitManager()
    }


    //http콜 만들기
    //레트로핏 인터페이스 가져오기
    private val iRetrofit: IRetrofit?= RetrofitClient.getClient(com.example.testing1.Retrofit.BASE_URL)?.create(IRetrofit::class.java)


    fun loginCall(idAndPwd:LoginData):MutableLiveData<ResponseCode>?{
        val data=MutableLiveData<ResponseCode>()
        Log.d(TAG,"loginCall()함수 실행")

        iRetrofit?.loginApi(idAndPwd)?.enqueue(object: Callback<ResponseCode>{

            override fun onResponse(call: Call<ResponseCode>, response: Response<ResponseCode>) {
                Log.d(TAG, "login onResponse 진입")
                if(response.isSuccessful)
                {
                    Log.d(TAG,"isSuccesful entered")
                    Log.d(TAG,"response.body(): ${response.body()}")
                    Log.d(TAG,"response.headers(): ${response.headers()}")

                    val statusData = response.body()!!.code
                    val msg = response.body()!!.message
                    val tmpCookie=response.headers().get("Set-Cookie")

                    Log.d(TAG,"tempCookie: ${tmpCookie}")

                    //var cookie=tmpCookie?.split(";")!!.get(0)
                    //var cookie:String=cookieArr!!.get(0)

                    Log.d(TAG,"Header.(): "+ tmpCookie.toString())
                    //Log.d(TAG,"cookie: ${cookie}")
                    Log.d(TAG,"code:${statusData}")
                    Log.d(TAG, msg)

                    val codeCookie:ResponseCode=ResponseCode(statusData,msg)

                    data.value=codeCookie
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


    fun registCall(registData:MemberData):MutableLiveData<Int>?{
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



    fun getCampaignList(): MutableLiveData<List<Campaign>>{
        val data=MutableLiveData<List<Campaign>>()

        iRetrofit?.campListApi()?.enqueue(object: Callback<CampaignList>{
            override fun onResponse(call: Call<CampaignList>, response: Response<CampaignList>) {
                Log.d(TAG,"getRecommList() onResponse 진입")
                if(response.isSuccessful){
                    Log.d(TAG,"response is Successful")
                    data.value=response.body()!!.campaignList
                }
            }
            override fun onFailure(call: Call<CampaignList>, t: Throwable) {
                Log.d(TAG,"getRecommList() onFailure()")
                t.stackTrace
            }
        })
        return data
    }
}