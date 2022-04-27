@file:Suppress("unused")

package anim.bro.com.practice.base.kotlin.viewbinding.base

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

inline fun <reified VB : ViewBinding> simpleIntListAdapter(crossinline onBindViewHolder: VB.(Int) -> Unit) =
    simpleListAdapter(IntDiffCallback(), onBindViewHolder)

inline fun <reified VB : ViewBinding> simpleLongListAdapter(crossinline onBindViewHolder: VB.(Long) -> Unit) =
    simpleListAdapter(LongDiffCallback(), onBindViewHolder)

inline fun <reified VB : ViewBinding> simpleBooleanListAdapter(crossinline onBindViewHolder: VB.(Boolean) -> Unit) =
    simpleListAdapter(BooleanDiffCallback(), onBindViewHolder)

inline fun <reified VB : ViewBinding> simpleFloatListAdapter(crossinline onBindViewHolder: VB.(Float) -> Unit) =
    simpleListAdapter(FloatDiffCallback(), onBindViewHolder)

inline fun <reified VB : ViewBinding> simpleDoubleListAdapter(crossinline onBindViewHolder: VB.(Double) -> Unit) =
    simpleListAdapter(DoubleDiffCallback(), onBindViewHolder)

inline fun <reified VB : ViewBinding> simpleStringListAdapter(crossinline onBindViewHolder: VB.(String) -> Unit) =
    simpleListAdapter(StringDiffCallback(), onBindViewHolder)

inline fun <T, reified VB : ViewBinding> simpleListAdapter(
    diffCallback: DiffUtil.ItemCallback<T>,
    crossinline onBindViewHolder: VB.(T) -> Unit
) = object : SimpleListAdapter<T, VB>(diffCallback) {

    override fun onBindViewHolder(binding: VB, item: T, position: Int) {
        onBindViewHolder(binding, item)
    }
}

abstract class SimpleListAdapter<T, VB : ViewBinding>(
    diffCallback: DiffUtil.ItemCallback<T>
) : ListAdapter<T, SimpleListAdapter.BindingViewHolder<VB>>(diffCallback) {

    private var onItemClickListener: ((T, Int) -> Unit)? = null
    private var onItemLongClickListener: ((T, Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder<VB> =
        BindingViewHolder(ViewBindingUtil.inflateWithGeneric<VB>(this, parent)).apply {
            itemView.setOnClickListener {
                onItemClickListener?.invoke(getItem(adapterPosition), adapterPosition)
            }
            itemView.setOnLongClickListener {
                onItemLongClickListener?.invoke(getItem(adapterPosition), adapterPosition)
                onItemLongClickListener != null
            }
        }

    override fun onBindViewHolder(holder: BindingViewHolder<VB>, position: Int) {
        onBindViewHolder(holder.binding, getItem(position), position)
    }

    fun doOnItemClick(block: (T, Int) -> Unit) {
        onItemClickListener = block
    }

    fun doOnItemLongClick(block: (T, Int) -> Unit) {
        onItemLongClickListener = block
    }

    abstract fun onBindViewHolder(binding: VB, item: T, position: Int)

    class BindingViewHolder<VB : ViewBinding>(val binding: VB) :
        RecyclerView.ViewHolder(binding.root)
}

abstract class SimpleIntListAdapter<VB : ViewBinding> :
    SimpleListAdapter<Int, VB>(IntDiffCallback())

abstract class SimpleLongListAdapter<VB : ViewBinding> : SimpleListAdapter<Long, VB>(
    LongDiffCallback()
)

abstract class SimpleBooleanListAdapter<VB : ViewBinding> : SimpleListAdapter<Boolean, VB>(
    BooleanDiffCallback()
)

abstract class SimpleFloatListAdapter<VB : ViewBinding> : SimpleListAdapter<Float, VB>(
    FloatDiffCallback()
)

abstract class SimpleDoubleListAdapter<VB : ViewBinding> : SimpleListAdapter<Double, VB>(
    DoubleDiffCallback()
)

abstract class SimpleStringListAdapter<VB : ViewBinding> : SimpleListAdapter<String, VB>(
    StringDiffCallback()
)

class IntDiffCallback : DiffUtil.ItemCallback<Int>() {
    override fun areItemsTheSame(oldItem: Int, newItem: Int) = oldItem == newItem
    override fun areContentsTheSame(oldItem: Int, newItem: Int) = oldItem == newItem
}

class LongDiffCallback : DiffUtil.ItemCallback<Long>() {
    override fun areItemsTheSame(oldItem: Long, newItem: Long) = oldItem == newItem
    override fun areContentsTheSame(oldItem: Long, newItem: Long) = oldItem == newItem
}

class BooleanDiffCallback : DiffUtil.ItemCallback<Boolean>() {
    override fun areItemsTheSame(oldItem: Boolean, newItem: Boolean) = oldItem == newItem
    override fun areContentsTheSame(oldItem: Boolean, newItem: Boolean) = oldItem == newItem
}

class FloatDiffCallback : DiffUtil.ItemCallback<Float>() {
    override fun areItemsTheSame(oldItem: Float, newItem: Float) = oldItem == newItem
    override fun areContentsTheSame(oldItem: Float, newItem: Float) = oldItem == newItem
}

class DoubleDiffCallback : DiffUtil.ItemCallback<Double>() {
    override fun areItemsTheSame(oldItem: Double, newItem: Double) = oldItem == newItem
    override fun areContentsTheSame(oldItem: Double, newItem: Double) = oldItem == newItem
}

class StringDiffCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String) = oldItem == newItem
    override fun areContentsTheSame(oldItem: String, newItem: String) = oldItem == newItem
}