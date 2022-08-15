package com.example.testing1.viewModel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.testing1.Retrofit.RetrofitManager.Companion.instance
import com.example.testing1.model.WithDrawData
@RequiresApi(Build.VERSION_CODES.O)
class WithDrawViewModel(val param:String):ViewModel() {

    private val _withDraws:MutableLiveData<List<WithDrawData>> = instance.getHistory(param)
    val withDraws:LiveData<List<WithDrawData>>
    get() = _withDraws




}

/*
class ParamViewModelFactory(private val param: String) : ViewModelProvider.Factory{
    @RequiresApi(Build.VERSION_CODES.O)
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(WithDrawViewModel::class.java)) {
            WithDrawViewModel(param) as T
        } else{
            throw IllegalArgumentException()
        }
    }
}

 */