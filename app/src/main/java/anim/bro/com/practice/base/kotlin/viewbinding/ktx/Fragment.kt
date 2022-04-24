@file:Suppress("unused")

package anim.bro.com.practice.base.kotlin.viewbinding.ktx

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.dangdang.kotlin.viewbinding.ktx.getBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

enum class Method { BIND, INFLATE }

inline fun <reified VB : ViewBinding> Fragment.binding() =
    FragmentBindingProperty(VB::class.java)

inline fun <reified VB : ViewBinding> Fragment.binding(method: Method) =
    if (method == Method.BIND) FragmentBindingProperty(VB::class.java) else FragmentInflateBindingProperty(
        VB::class.java
    )

class FragmentBindingProperty<VB : ViewBinding>(private val clazz: Class<VB>) :
    ReadOnlyProperty<Fragment, VB> {

    override fun getValue(thisRef: Fragment, property: KProperty<*>): VB =
        try {
            thisRef.requireView().getBinding(clazz).also { binding ->
//        if (binding is ViewDataBinding) binding.lifecycleOwner = thisRef.viewLifecycleOwner
            }
        } catch (e: IllegalStateException) {
            throw IllegalStateException("The property of ${property.name} has been destroyed.")
        }
}

class FragmentInflateBindingProperty<VB : ViewBinding>(private val clazz: Class<VB>) :
    ReadOnlyProperty<Fragment, VB> {
    private var binding: VB? = null
    private val handler by lazy { Handler(Looper.getMainLooper()) }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): VB {
        if (binding == null) {
            try {
                @Suppress("UNCHECKED_CAST")
                binding = (clazz.getMethod("inflate", LayoutInflater::class.java)
                    .invoke(null, thisRef.layoutInflater) as VB)
                    .also {
//              binding -> if (binding is ViewDataBinding) binding.lifecycleOwner = thisRef.viewLifecycleOwner
                    }
            } catch (e: IllegalStateException) {
                throw IllegalStateException("The property of ${property.name} has been destroyed.")
            }
//            thisRef.viewLifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
//                override fun onDestroy(owner: LifecycleOwner) {
//                    handler.post { binding = null }
//                }
//            })
        }
        return binding!!
    }
}
