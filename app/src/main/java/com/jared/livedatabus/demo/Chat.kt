package com.jared.livedatabus.demo

import com.jared.livedatabus.SingleLiveData

interface Chat {

    fun message(): SingleLiveData<Int>

}