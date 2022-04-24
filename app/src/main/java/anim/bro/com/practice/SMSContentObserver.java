package anim.bro.com.practice;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

/**
 * Created by zhangshan on 2022/4/19 15:24.
 * Description：请添加类注释
 */
//（2）观察系统里短消息的数据库变化的ContentObserver派生类

public class SMSContentObserver extends ContentObserver {
    private static String TAG = "SMSContentObserver";

    private int MSG_OUTBOXCONTENT = 2;

    private Context mContext;
    private Handler mHandler;   //更新UI线程

    public SMSContentObserver(Context context, Handler handler) {
        super(handler);
        mContext = context;
        mHandler = handler;
    }

    /**
     * 当所监听的Uri发生改变时，就会回调此方法
     *
     * @param selfChange 此值意义不大 一般情况下该回调值false
     */
    @Override
    public void onChange(boolean selfChange) {
        Log.i(TAG, "the sms table has changed");

        //查询发件箱里的内容
        Uri outSMSUri = Uri.parse("content://sms/sent");
        Cursor c = mContext.getContentResolver().query(outSMSUri, null, null, null, "date desc");
        if (c != null) {
            Log.i(TAG, "the number of send is" + c.getCount());
            StringBuilder sb = new StringBuilder();
            //循环遍历
            while (c.moveToNext()) {
                sb.append("发件人手机号码: " + c.getInt(c.getColumnIndex("address")))
                        .append("信息内容: " + c.getString(c.getColumnIndex("body")))
                        .append("\n");
            }
            c.close();
            mHandler.obtainMessage(MSG_OUTBOXCONTENT, sb.toString()).sendToTarget();
        }
    }

    public void onDestroy() {
        mContext = null;
        mHandler = null;
    }

}
