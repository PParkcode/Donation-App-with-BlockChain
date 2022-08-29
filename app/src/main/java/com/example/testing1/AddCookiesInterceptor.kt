package com.example.testing1

import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.testing1.view.MainActivity
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

private val TAG="tag1"
class AddCookiesInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var builder: Request.Builder= chain.request().newBuilder()

        var preferences: String? = MainActivity?.getInstance()?.let { MySharedPreferences.getUserCookie(it) }
       // Log.d(TAG,"cookie_value: ${preferences}")

        if (preferences != null) {
           // Log.d(TAG,"preferences가 null이 아니므로 addHeader()실행")
           // Log.d(TAG,"쿠키 값: "+preferences)
            builder.addHeader("Cookie",preferences)

        };

        //Web,Android,IOS 구분을 위한 User-Agent 세팅?
        //builder.removeHeader("User-Agent").addHeader("User-Agent","Android")


        return chain.proceed(builder.build())

    }
}