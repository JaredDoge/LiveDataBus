package com.jared.livedatabus

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.jared.livedatabus.core.toViewLife
import java.util.*
import java.util.concurrent.atomic.AtomicInteger


open class BusLiveData<T> : LiveData<T>() {

    private val mCurrentVersion = AtomicInteger(START_VERSION)


    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner.toViewLife(), createObserverWrapper(observer, mCurrentVersion.get()))
    }

    override fun observeForever(observer: Observer<in T>) {
        super.observeForever(createObserverWrapper(observer, mCurrentVersion.get()))
    }


    fun observeSticky(owner: LifecycleOwner, observer: Observer<T>) {
        super.observe(owner.toViewLife(), createObserverWrapper(observer, START_VERSION))
    }


    fun observeStickyForever(observer: Observer<in T>) {
        super.observeForever(createObserverWrapper(observer, START_VERSION))
    }


    public override fun setValue(value: T) {
        mCurrentVersion.getAndIncrement()
        super.setValue(value)
    }



    @Suppress("UNCHECKED_CAST")
    internal inner class ObserverWrapper(observer: Observer<in T>, version: Int) : Observer<T> {

        private val mObserver: Observer<in T> = observer
        private var mVersion:Int = version



        override fun onChanged(t: T) {
            if (mCurrentVersion.get() > mVersion) {
                mObserver.onChanged(t)
            }
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) {
                return true
            }
            if (other == null || javaClass != other.javaClass) {
                return false
            }
            val that: ObserverWrapper = other as BusLiveData<T>.ObserverWrapper

            return mObserver == that.mObserver
        }

        override fun hashCode(): Int {
            return Objects.hash(mObserver)
        }


    }


    override fun removeObserver(observer: Observer<in T>) {
        if (observer is ObserverWrapper) {
            super.removeObserver(observer)
        } else {
            super.removeObserver(createObserverWrapper(observer, START_VERSION))
        }
    }


    private fun createObserverWrapper(observer: Observer<in T>, version: Int): ObserverWrapper {
        return ObserverWrapper(observer, version)
    }


    fun clear() {
        super.setValue(null)
    }

    companion object {
        private const val START_VERSION = -1
    }
}