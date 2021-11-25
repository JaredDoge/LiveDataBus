package com.jared.livedatabus.core

import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner

fun LifecycleOwner.toViewLife():LifecycleOwner = if (this is Fragment) this.viewLifecycleOwner else this