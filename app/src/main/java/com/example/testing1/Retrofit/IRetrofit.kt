package com.example.testing1.Retrofit

import com.example.testing1.*
import com.example.testing1.model.*
import com.google.gson.JsonElement
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import java.time.LocalDate


// retrofit 관련 통신 api 인터페이스
//대부분 반환형은 Call 객체의 json 형태로
interface IRetrofit {
    @POST( "api/new/login") //로그인 요청
    fun loginApi(@Body() idAndPwd: LoginData):Call<ResponseCode>


    @POST("api/new") //회원가입 요청
    fun registApi(@Body() data: MemberData):Call<ResponseCode>

    @POST("api/new/campaign") //캠페인 리스트 요청
    fun campListApi(@Body() searchKey:CampaignSearchConditionDto):Call<List<Campaign>>

    @GET("api/new/campaign/{camapaignId}") //url 체크
    fun detailCampApi(@Path("camapaignId") campaignId:String):Call<Campaign>



    @GET("api/user")
    fun getUserApi():Call<MemberData>


    @Multipart
    @POST("api/charity")
    fun registCampaignApi(
        /*
            @Part("campaignName") campaignName: RequestBody,
            @Part("charityName") charityName:RequestBody,
            @Part("deadline") deadline: RequestBody,
            @Part("goalAmount") goalAmount:RequestBody,
            @Part("categories") categories:RequestBody,
         */
        @PartMap map:HashMap<String,RequestBody> ,

            @Part coverImagePath:MultipartBody.Part?,
            @Part detailImagePath:MultipartBody.Part?,
   ):Call<ResponseCode>

    @POST("api/charity")
    fun registCampaignByJsonApi(@Body() campaign:Campaign):Call<ResponseCode>

    @GET("api/user/give")
    fun donateApi(@Query("amount") amount:String):Call<ResponseCode>

    @POST("api/user/pay")
    fun chargeApi(@Query("amount") amount:String):Call<ResponseCode>

    @GET("api/user/logout")
    fun logoutApi():Call<ResponseCode>

    @Multipart
    @POST("upload")
    fun imageUploadApi(@Part file: MultipartBody.Part):Call<String>

    @POST("api/charity/withdraw/{campaignId}")
    fun withDrawApi(@Path("campaignId") campaignId:String, @Body() data: WithDrawData):Call<ResponseCode>

    @POST("ReceivedTransferOtherBank.nh")
    fun exchangeApi(@Body() data:ExchangeData):Call<ExchangeResponseData>

    @GET("api/user/payback")
    fun payBackApi(@Query("amount") amount:String):Call<ResponseCode>

    @GET("api/user/tx")
    fun txApi():Call<List<TransactionForm>>
}
