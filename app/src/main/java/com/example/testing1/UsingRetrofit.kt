package com.example.testing1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UsingRetrofit {
    private val connectApi: ConnectApi

    init{
        val retrofit = Retrofit.Builder()
            .baseUrl("")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        connectApi=retrofit.create(ConnectApi::class.java)
    }

}