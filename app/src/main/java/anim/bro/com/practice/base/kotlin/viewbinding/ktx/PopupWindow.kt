@file:Suppress("unused")

package com.dangdang.kotlin.viewbinding.ktx

import android.app.Activity
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import anim.bro.com.practice.base.kotlin.viewbinding.ktx.inflateBinding

inline fun <reified VB : ViewBinding> Activity.popupWindow(
    width: Int = ViewGroup.LayoutParams.WRAP_CONTENT,
    height: Int = ViewGroup.LayoutParams.WRAP_CONTENT,
    focusable: Boolean = false,
    crossinline block: VB.() -> Unit
) =
    lazy {
        PopupWindow(inflateBinding<VB>(layoutInflater).apply(block).root, width, height, focusable)
    }

inline fun <reified VB : ViewBinding> Fragment.popupWindow(
    width: Int = ViewGroup.LayoutParams.WRAP_CONTENT,
    height: Int = ViewGroup.LayoutParams.WRAP_CONTENT,
    focusable: Boolean = false,
    crossinline block: VB.() -> Unit
) =
    lazy {
        PopupWindow(inflateBinding<VB>(layoutInflater).apply(block).root, width, height, focusable)
    }
