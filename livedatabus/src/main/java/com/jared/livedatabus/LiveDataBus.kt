package com.jared.livedatabus

import com.jared.livedatabus.core.LiveDataBusCore
import java.lang.reflect.InvocationHandler
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Proxy


class LiveDataBus {

    companion object {

        @JvmStatic
        @Synchronized
        fun <T> getSyn(key: String): SingleLiveData<T> {
            require(key.isNotEmpty()) { "Bus Key value cannot be empty" }
            return get(key)
        }

        @JvmStatic
        fun <T> get(key: String): SingleLiveData<T> {
            require(key.isNotEmpty()) { "Bus Key value cannot be empty" }
            return LiveDataBusCore.getInstance().getChannel(key)
        }

        private fun <T> get(key: String, type: Class<T>): SingleLiveData<T> {
            require(key.isNotEmpty()) { "Bus Key value cannot be empty" }
            return LiveDataBusCore.getInstance().getChannel(key)
        }


        inline fun <reified E> of(): E
            = of(E::class.java)

        @Suppress("UNCHECKED_CAST")
        @JvmStatic
        fun <E> of(clz: Class<E>): E {
            require(clz.isInterface) { "API declarations must be interfaces." }
            require(clz.interfaces.isEmpty()) { "API interfaces must not extend other interfaces." }
            return Proxy.newProxyInstance(
                clz.classLoader,
                arrayOf(clz),
                InvocationHandler { _, method, _ ->
                    return@InvocationHandler get(
                        // 事件名以集合類名_事件方法名定義
                        // 以此保證事件的唯一性
                        "${clz.canonicalName}_${method.name}",
                        (method.genericReturnType as ParameterizedType).actualTypeArguments[0].javaClass
                    )
                }) as E
        }
    }


}