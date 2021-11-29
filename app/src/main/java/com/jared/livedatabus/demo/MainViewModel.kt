package com.jared.livedatabus.demo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jared.livedatabus.SingleLiveData

class MainViewModel : ViewModel() {

    //用singleLiveData,避免當螢幕轉向時，所帶來的數據倒灌問題
    //可以參考
    // https://juejin.cn/post/6850037277574299656
    //https://github.com/KunMinX/UnPeek-LiveData
    val showToast: SingleLiveData<String> = SingleLiveData<String>()

    fun show(){
        showToast.setValue("testMsg")
    }
}