@file:Suppress("unused")

package com.dangdang.kotlin.viewbinding.ktx

import androidx.viewbinding.ViewBinding
import com.google.android.material.navigation.NavigationView

inline fun <reified VB : ViewBinding> NavigationView.setHeaderView(
    index: Int = 0,
    block: VB.() -> Unit
) =
    getHeaderView(index)?.getBinding<VB>()?.run(block)