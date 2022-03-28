package com.example.testing1

import android.content.Context
import android.net.Uri
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AddCookiesInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var builder: Request.Builder= chain.request().newBuilder()

        var preferences: String? = MainActivity?.getInstance()?.let { MySharedPreferences.getUserCookie(it) }

        if (preferences != null) {
            builder.addHeader("Cookie",preferences)
        };

        //Web,Android,IOS 구분을 위한 User-Agent 세팅?
        builder.removeHeader("User-Agent").addHeader("User-Agent","Android")

        return chain.proceed(builder.build())
    }
}