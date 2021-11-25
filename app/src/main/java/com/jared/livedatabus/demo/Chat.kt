package com.jared.livedatabus.demo

import com.jared.livedatabus.BusLiveData

interface Chat {

    fun message(): BusLiveData<Int>

}