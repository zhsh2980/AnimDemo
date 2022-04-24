package anim.bro.com.practice.ui

import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import anim.bro.com.practice.SMSContentObserver
import anim.bro.com.practice.databinding.ActivityOkHttpTestBinding
import anim.bro.com.practice.fragment.reflection.java.BaseBindingActivity
import okhttp3.*
import java.io.IOException

class OkHttpTestActivity(handler: Handler?) : BaseBindingActivity<ActivityOkHttpTestBinding>() {
    var url = "https://api.github.com/users/zhsh2980/repos"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
    }

    private fun initData() {

        val client = OkHttpClient()

        val request: Request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                val string = response.body?.string()
                Log.i("bro", "string: $string")
                val code = response.code
                Log.i("bro", "code: $code")
            }
        })

        val handler = Handler(Looper.getMainLooper())
        val smsContentObserver = SMSContentObserver(this, handler)

        //注册
        val smsUri: Uri = Uri.parse("content://sms")
        contentResolver.registerContentObserver(smsUri, true, smsContentObserver)

        //onDestroy 取消注册
        contentResolver.unregisterContentObserver(smsContentObserver)
        smsContentObserver.onDestroy()

//        OkHttpClient.Builder()
//            .authenticator()

//        val editText = EditText(this)
//        editText.addTextChangedListener()


    }
}