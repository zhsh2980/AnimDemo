package anim.bro.com.practice.kt

import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import anim.bro.com.practice.R
import anim.bro.com.practice.annotation.MustDeleteClass
import anim.bro.com.practice.annotation.MustDeleteMethod
import anim.bro.com.practice.ui.BaseActivity
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.Glide

/**
 * Created by zhangshan on 2022/3/9 20:42.
 * Description：kotlin 测试类
 */
class DemoKtActivity : BaseActivity() {

    private val btnClickTest: Button by lazy { findViewById(R.id.btn_click_test) }

    override fun getResourceID(): Int {
        return R.layout.activity_kt
    }

    override fun getTitleName(): String {
        return "kotlin demo"
    }

    override fun initView() {

    }

    override fun initData() {
        doClick()
        testLint()
    }

//    @MustDeleteMethod
    private fun testLint() {
//        Log.i("", "")
//        Glide.with(this).load("").into(ImageView(this))
//        Thread {
//        }
    }

    private fun toastMessage(view: View) {
        ToastUtils.showShort("收到点击消息~")
    }

    private fun doClick() {

        //第1种写法
        btnClickTest.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                toastMessage(v)
            }
        })

        //第2种写法
        btnClickTest.setOnClickListener(View.OnClickListener { v: View -> toastMessage(v) })

        btnClickTest.setOnClickListener({ v: View -> toastMessage(v) })

        btnClickTest.setOnClickListener({ v -> toastMessage(v) })

        btnClickTest.setOnClickListener({ it -> toastMessage(it) })

        btnClickTest.setOnClickListener({ toastMessage(it) })

        btnClickTest.setOnClickListener { toastMessage(it) }


    }
}