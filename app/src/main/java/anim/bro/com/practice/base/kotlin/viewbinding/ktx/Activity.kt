@file:Suppress("unused")

package anim.bro.com.practice.base.kotlin.viewbinding.ktx

import androidx.core.app.ComponentActivity
import androidx.viewbinding.ViewBinding

inline fun <reified VB : ViewBinding> ComponentActivity.binding(setContentView: Boolean = true) =
    lazy {
        inflateBinding<VB>(layoutInflater).also { binding ->
            if (setContentView) setContentView(binding.root)
//    if (binding is ViewDataBinding) binding.lifecycleOwner = this
        }
    }
