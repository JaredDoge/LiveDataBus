package com.jared.livedatabus.core

import com.jared.livedatabus.BusLiveData

internal class LiveDataBusCore {

    companion object{

        @JvmStatic
        private val defaultBus = LiveDataBusCore()

        @JvmStatic
        fun getInstance() = defaultBus
    }

    private val mBusMap : MutableMap<String, BusLiveData<*>> by lazy {
        mutableMapOf()
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> getChannel(key: String) : BusLiveData<T> {

        return mBusMap.getOrPut(key){
            BusLiveData<T>()
        } as BusLiveData<T>
    }
}