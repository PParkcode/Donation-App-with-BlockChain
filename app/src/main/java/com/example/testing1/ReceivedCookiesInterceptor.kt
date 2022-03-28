package com.example.testing1

import android.content.Context
import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
private val TAG="tag1"
class ReceivedCookiesInterceptor:Interceptor {


    override fun intercept(chain: Interceptor.Chain): Response {
        Log.d(TAG,"ReivedCookiesInterceptor intercept() ON")
        var originalResponse: Response=chain.proceed(chain.request())

        if(!originalResponse.headers("Set-Cookie").isEmpty()){
            var tmpCookie:String = originalResponse.headers("Set-Cookie").toString()
            var cookie=tmpCookie.split(";").get(0)


            MainActivity.getInstance()?.let {
                MySharedPreferences.setUserCookie(it,cookie)
                Log.d(TAG,"recievedinterceptor : ${MySharedPreferences.getUserCookie(it)} ")}

        }
        return originalResponse

    }
}