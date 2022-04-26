package com.example.testing1.adapter

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.example.testing1.MySharedPreferences
import com.example.testing1.R
import com.example.testing1.view.MainActivity


object ImageBindingAdapter {
    @BindingAdapter("imageUrl")
    @JvmStatic
    fun loadImage(imageView: ImageView, path: String?){
        Log.d("tag1", "imageBindingAdapter 실행")
        Log.d("tag1","파리마터 url: "+path)
        var preferences: String? = MainActivity?.getInstance()?.let { MySharedPreferences.getUserCookie(it) }
        val builder = LazyHeaders.Builder()
                //.addHeader("User-Agent", "your UA string")
                //.addHeader("Authorization", "your basic authentication string.")
                .addHeader("Cookie", preferences.toString())


        val baseUrl = "http://10.0.2.2:8080"
        val glideUrl = GlideUrl(baseUrl+path, builder.build())
        Glide.with(imageView.context).load(glideUrl).error(R.drawable.test9).into(imageView)
    }
}

