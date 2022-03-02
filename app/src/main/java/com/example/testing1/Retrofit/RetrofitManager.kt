package com.example.testing1.Retrofit

import android.content.ContentValues.TAG
import android.util.Log
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.Response

const val BASE_URL ="http://127.0.0.1:8080/"
class RetrofitManager {

    companion object{
        val instance= RetrofitManager()
    }

    //http콜 만들기
    //레트로핏 인터페이스 가져오기
    private val iRetrofit: IRetrofit?= RetrofitClient.getClient("http://127.0.0.1:8080/")?.create(IRetrofit::class.java)

    fun LoginCall(idAndPwd:String?,completion:(String)-> Unit){
        val key=idAndPwd.let{
            it
        }?: ""  //iaANdPwd가 비워 있으면 ""을 반환하라는뜻
        val call: Call<JsonElement> =iRetrofit?.loginApi(idAndPwd=key).let{
            it
        }?: return
        call.enqueue(object: retrofit2.Callback<JsonElement>{
            //응답 실패
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d(TAG,"Retrofit Manager - onFailure() called")
            }
            //응답 성공
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                Log.d(TAG,"Retrofit Manager - onResponse() called")
            }
        })
    }
}