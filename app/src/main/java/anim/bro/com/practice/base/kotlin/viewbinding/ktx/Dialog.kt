@file:Suppress("unused")

package anim.bro.com.practice.base.kotlin.viewbinding.ktx

import android.app.Dialog
import androidx.viewbinding.ViewBinding

inline fun <reified VB : ViewBinding> Dialog.binding() = lazy {
    inflateBinding<VB>(layoutInflater).also { setContentView(it.root) }
}
