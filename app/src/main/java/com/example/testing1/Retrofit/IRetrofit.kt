package com.example.testing1.Retrofit

import androidx.lifecycle.LiveData
import com.example.testing1.*
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


// retrofit 관련 통신 api 인터페이스
//대부분 반환형은 Call 객체의 json 형태로
interface IRetrofit {
    @POST( "api/new/login") //로그인 요청
    fun loginApi(@Body() idAndPwd:LoginData):Call<ResponseCode>


    @POST("api/new") //회원가입 요청
    fun registApi(@Body() data:MemberData):Call<ResponseCode>

    @GET("api/new/campaign") //캠페인 리스트 요청
    fun campListApi():Call<CampaignList>

    @GET("api/new/campaign/{camapaignId}") //url 체크
    fun detailCampApi():Call<Campaign>

    @GET("api/new/campaign") //url 체크
    fun campTransactionApi(data:String):Call<JsonElement>


}