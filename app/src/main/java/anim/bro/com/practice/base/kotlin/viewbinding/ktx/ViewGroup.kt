@file:Suppress("unused")

package com.dangdang.kotlin.viewbinding.ktx

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import anim.bro.com.practice.base.kotlin.viewbinding.ktx.inflateBinding

inline fun <reified VB : ViewBinding> ViewGroup.inflate() =
    inflateBinding<VB>(LayoutInflater.from(context), this, true)

inline fun <reified VB : ViewBinding> ViewGroup.binding(attachToParent: Boolean = false) = lazy {
    inflateBinding<VB>(
        LayoutInflater.from(context),
        if (attachToParent) this else null,
        attachToParent
    )
}
