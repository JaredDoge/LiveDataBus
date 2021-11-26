package com.jared.livedatabus.core

import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.jared.livedatabus.SingleLiveData


fun LifecycleOwner.toViewLife(): LifecycleOwner =
    if (this is Fragment) this.viewLifecycleOwner else this

