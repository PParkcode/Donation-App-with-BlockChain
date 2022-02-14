package com.example.testing1

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST

interface ConnectApi {

    @POST( "/api/user/login") //로그인 요청
    fun loginFunc(idAndPwd:String):Call<String>

}