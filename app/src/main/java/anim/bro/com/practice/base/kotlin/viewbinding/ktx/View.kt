package com.dangdang.kotlin.viewbinding.ktx

import android.view.View
import androidx.viewbinding.ViewBinding

inline fun <reified VB : ViewBinding> View.getBinding() = getBinding(VB::class.java)

@Suppress("UNCHECKED_CAST")
fun <VB : ViewBinding> View.getBinding(clazz: Class<VB>) =
    bindingCache as? VB ?: (clazz.getMethod("bind", View::class.java)
        .invoke(null, this) as VB)
        .also { binding -> bindingCache = binding }

private var View.bindingCache: Any?
    get() = getTag(Int.MIN_VALUE)
    set(value) = setTag(Int.MIN_VALUE, value)
