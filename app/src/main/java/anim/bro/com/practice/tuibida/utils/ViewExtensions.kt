package bro.tuibida.com.utils

import android.content.Context
import android.graphics.Bitmap
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import anim.bro.com.practice.tuibida.utils.SVUtil.isFastDoubleClick

fun View.click(filter: Boolean = true, block: () -> Unit) {
    this.setOnClickListener {
        if (filter) {
            if (isFastDoubleClick()) return@setOnClickListener
        }
        block()
    }
}

//fun ImageView.load(url: String, circle: Boolean = false) {
//    val glide = Glide.with(this.context).load(url)
//    if (circle) {
//        glide.circleCrop().into(this)
//    } else {
//        glide.into(this)
//    }
//}

fun TextView.addTextChangedListener(onTextChanged: (String) -> Unit = {}, afterChanged: (String) -> Unit = {}) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            s?.let {
                afterChanged(it.toString())
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            s?.let {
                onTextChanged(it.toString())
            }
        }
    })
}

fun View.touch(block: () -> Unit = {}, result: Boolean = false) {
    this.setOnTouchListener { v, event ->
        if (event.action == MotionEvent.ACTION_UP) {
            block()
        }
        result
    }
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.visible(visible: Boolean, needGone: Boolean = false) {
    if (visible) {
        visible()
    } else {
        if (needGone) gone() else invisible()
    }
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.flipVisible(): Boolean {
    return if (isVisible()) {
        this.visibility = View.INVISIBLE
        false
    } else {
        this.visibility = View.VISIBLE
        true
    }
}

fun View.isGone(): Boolean {
    return this.visibility == View.GONE
}

fun View.isVisible(): Boolean {
    return this.visibility == View.VISIBLE
}

fun View.isInvisible(): Boolean {
    return this.visibility == View.INVISIBLE
}

fun Context.inflate(res: Int, container: ViewGroup, attach: Boolean = false): View {
    return LayoutInflater.from(this).inflate(res, container, attach)
}

fun TextView.isValid(vararg edits: EditText): Boolean {
    edits.forEach {
        if (it == null || it.text.isNullOrBlank()) {
            this.isEnabled = false
            return false
        }
    }
    this.isEnabled = true
    return true
}

fun TextView.visibleText(content: String?, needGone: Boolean = true) {
    if (content.isNullOrBlank()) {
        visibility = if (needGone) View.GONE else View.INVISIBLE
        return
    }
    text = content
    visibility = View.VISIBLE
}

fun Context.toast(content: String?, needLong: Boolean = false) {
    content?.let {
        Toast.makeText(this, content, if (needLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT).show()
    }
}

fun EditText.moveSelect2End() {
    text?.let {
        requestFocus()
        setSelection(text.length)
    }
}

fun View.toBitmap(): Bitmap {
    measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
    layout(0, 0, measuredWidth, measuredHeight)
    buildDrawingCache()
    return drawingCache
}

