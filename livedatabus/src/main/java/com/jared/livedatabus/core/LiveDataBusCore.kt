package com.jared.livedatabus.core

import com.jared.livedatabus.SingleLiveData

internal class LiveDataBusCore {

    companion object{

        @JvmStatic
        private val defaultBus = LiveDataBusCore()

        @JvmStatic
        fun getInstance() = defaultBus
    }

    private val mBusMap : MutableMap<String, SingleLiveData<*>> by lazy {
        mutableMapOf()
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> getChannel(key: String) : SingleLiveData<T> {

        return mBusMap.getOrPut(key){
            SingleLiveData<T>()
        } as SingleLiveData<T>
    }
}