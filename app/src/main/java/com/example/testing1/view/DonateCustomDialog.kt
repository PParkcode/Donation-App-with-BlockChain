package com.example.testing1.view

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import com.airbnb.lottie.LottieAnimationView
import com.example.testing1.R

class DonateCustomDialog(context:Context):Dialog(context) {

    lateinit var loading_animation: LottieAnimationView
    //dialog가 메모리에 올라갔을 때
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.donate_custom_dialog)

        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

    }

    override fun onStop() {
        super.onStop()
        Log.d("tag2","dialog on stop")
    }

    override fun cancel() {
        super.cancel()
        Log.d("tag2","dialog cancel")
    }

    override fun closeOptionsMenu() {
        super.closeOptionsMenu()
        Log.d("tag2","close options menu")
    }

}