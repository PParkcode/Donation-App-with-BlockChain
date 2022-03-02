package com.example.testing1.Retrofit

import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST

interface IRetrofit {

    @POST( "/api/new/login") //로그인 요청
    fun loginApi(idAndPwd:String):Call<JsonElement> //JsonElement or String ??

    @POST("/api/new")
    fun registApi(data:String):Call<JsonElement>
    @POST("/api/new/campaign")
    fun campListApi(data:String):Call<JsonElement>
    @GET("/api/new/campaign/{camapaignId}") //url 체크
    fun detailCampApi():Call<JsonElement>
    @GET("/api/new/campaign") //url 체크
    fun campTransactionApi(data:String):Call<JsonElement>
}