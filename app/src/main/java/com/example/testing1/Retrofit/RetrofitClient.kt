package com.example.testing1.Retrofit

import android.content.Context
import com.example.testing1.AddCookiesInterceptor
import com.example.testing1.MainActivity
import com.example.testing1.ReceivedCookiesInterceptor
import okhttp3.CookieJar
import okhttp3.Interceptor
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieManager


//retrofit build 후 리턴 해준다  v
object RetrofitClient {

    private var retrofitClient:Retrofit?=null

    fun getClient(baseUrl:String):Retrofit?{
        if(retrofitClient ==null){

            /*
            val headerInterceptor= Interceptor{
                val request=it.request()
                    .newBuilder()
                    .addHeader("Cookie","")
                    .build()
                return@Interceptor it.proceed(request)
            }

             */
            val client=OkHttpClient.Builder()
                .cookieJar(JavaNetCookieJar(CookieManager()))
               // .addInterceptor(AddCookiesInterceptor())
                    .addInterceptor(ReceivedCookiesInterceptor())
                .build()




            retrofitClient =Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofitClient
    }




}