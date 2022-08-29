package com.example.testing1

import android.util.Log
import com.example.testing1.view.MainActivity
import okhttp3.Interceptor
import okhttp3.Response


private val TAG="tag3"
class ReceivedCookiesInterceptor:Interceptor {



    override fun intercept(chain: Interceptor.Chain): Response {
        Log.d(TAG, "ReivedCookiesInterceptor intercept() ON")
        var originalResponse: Response=chain.proceed(
                chain.request()
        )

        if(!originalResponse.headers("Set-Cookie").isEmpty()){ //만약 원래의 요청 Set-Cookie 헤더가 비지 않았다면,
            var tmpCookie:String = originalResponse.headers("Set-Cookie").toString()
            Log.d(TAG, "set-Cookie의 값: " + tmpCookie)
            var cookie=tmpCookie.split(";").get(0)
            cookie = cookie.replace("[", "")




            MainActivity.getInstance()?.let {
                MySharedPreferences.setUserCookie(it, cookie)
               // Log.d(TAG,"recievedinterceptor :${MySharedPreferences.getUserCookie(it)}~")
            }

        }
        else{

        }

        return originalResponse

    }






}