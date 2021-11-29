package com.jared.livedatabus.demo

import com.jared.livedatabus.SingleLiveData

interface SampleEvent {

    fun message(): SingleLiveData<Int>

}