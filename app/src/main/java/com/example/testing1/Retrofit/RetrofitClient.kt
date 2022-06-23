package com.example.testing1.Retrofit

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.testing1.AddCookiesInterceptor
import com.example.testing1.ReceivedCookiesInterceptor
import com.google.gson.*
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.net.CookieManager
import java.time.LocalDate
import java.time.format.DateTimeFormatter


//retrofit build 후 리턴 해준다  v
object RetrofitClient {

    private var retrofitClient:Retrofit?=null

    @RequiresApi(Build.VERSION_CODES.O)
    fun getClient(baseUrl: String):Retrofit?{
        if(retrofitClient ==null){


            //val httpLoggingInterceptor=HttpLoggingInterceptor()
           //httpLoggingInterceptor.level=HttpLoggingInterceptor.Level.BODY
            val client=OkHttpClient.Builder()
                .cookieJar(JavaNetCookieJar(CookieManager()))
                .addInterceptor(AddCookiesInterceptor())
                .addInterceptor(ReceivedCookiesInterceptor())
                //.addInterceptor(httpLoggingInterceptor)
                .build()

            val gson = GsonBuilder()
                    .registerTypeAdapter(LocalDate::class.java, JsonDeserializer<LocalDate> { json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?
                        -> LocalDate.parse(json.asString, DateTimeFormatter.ofPattern("yyyy-MM-dd")) } as JsonDeserializer<LocalDate>?)
                    .setLenient()
                    .create()




            retrofitClient =Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }
        return retrofitClient
    }

}

object RetrofitClient2 {

    private var retrofitClient2:Retrofit?=null

    @RequiresApi(Build.VERSION_CODES.O)
    fun getClient(baseUrl: String):Retrofit?{
        if(retrofitClient2 ==null){


            //val httpLoggingInterceptor=HttpLoggingInterceptor()
            //httpLoggingInterceptor.level=HttpLoggingInterceptor.Level.BODY
            val client=OkHttpClient.Builder()

                    //.addInterceptor(httpLoggingInterceptor)
                    .build()

            val gson = GsonBuilder()
                    .registerTypeAdapter(LocalDate::class.java, JsonDeserializer<LocalDate> { json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?
                        -> LocalDate.parse(json.asString, DateTimeFormatter.ofPattern("yyyy-MM-dd")) } as JsonDeserializer<LocalDate>?)
                    .setLenient()
                    .create()




            retrofitClient2 =Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
        }
        return retrofitClient2
    }

}

