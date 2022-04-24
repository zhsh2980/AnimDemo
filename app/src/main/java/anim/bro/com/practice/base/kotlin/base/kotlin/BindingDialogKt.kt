@file:Suppress("unused")

package com.dangdang.kotlin.base.kotlin

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.viewbinding.ViewBinding
import anim.bro.com.practice.base.kotlin.viewbinding.base.ViewBindingUtil

/**
 * How to modify the base class to use view binding, you need the following steps:
 * 1. Adds a generic of view binding to the base class.
 * 2. Declares a binding object.
 * 3. Uses [inflateBindingWithGeneric] method to create the binding object.
 * 4. Uses the root of the binding object instead of layout id to set content view.
 *
 * Here is the core code.
 *
 * @author Dylan Cai
 */
abstract class BindingDialogKt<VB : ViewBinding>(context: Context, themeResId: Int = 0) :
    Dialog(context, themeResId) {

    lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ViewBindingUtil.inflateWithGeneric(this, layoutInflater)
        setContentView(binding.root)
    }
}