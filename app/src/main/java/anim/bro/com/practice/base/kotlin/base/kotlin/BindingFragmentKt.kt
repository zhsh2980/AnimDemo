@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package com.dangdang.kotlin.base.kotlin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import anim.bro.com.practice.base.kotlin.viewbinding.base.ViewBindingUtil

/**
 * How to modify the base class to use view binding, you need the following steps:
 * 1. Adds a generic of view binding to the base class.
 * 2. Declares a binding object.
 * 3. Uses [inflateBindingWithGeneric] method to create the binding object.
 * 4. Uses the root of the binding object instead of view to return content view.
 *
 * Here is the core code.
 *
 * @author Dylan Cai
 */
abstract class BindingFragmentKt<VB : ViewBinding> : Fragment() {

    private var _binding: VB? = null
    val binding: VB get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ViewBindingUtil.inflateWithGeneric(this, inflater, container, false)
        return binding.root

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}