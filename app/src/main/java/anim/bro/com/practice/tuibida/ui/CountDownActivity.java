package anim.bro.com.practice.tuibida.ui;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import anim.bro.com.practice.R;
import anim.bro.com.practice.tuibida.utils.BoxPopManage;
import anim.bro.com.practice.tuibida.utils.CustomPopWindow;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import java.util.Set;

/**
 * 倒计时
 */
public class CountDownActivity extends AppCompatActivity {

    @BindView(R.id.tv_time_left)
    TextView mTvTimeLeft;
    @BindView(R.id.btn_start)
    Button mBtnStart;
    @BindView(R.id.btn_stop)
    Button mBtnStop;
    @BindView(R.id.frame_tv_parent)
    FrameLayout mFrameTvParent;


    private CountDownTimer mTimer;
    private TimeCount time;
    /*24小时时间戳*/
    private long timeStamp = 186400000;
    private CustomPopWindow popWindow;

    String popText = "转发后获得宝箱\n开启宝箱拿元宝大奖\n转发后获得宝箱\n开启宝箱拿元宝大奖";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_down);
        ButterKnife.bind(this);
        Log.d("CountDownActivity", "oncreate");

        time = new TimeCount(timeStamp + 500, 1000);

        Uri url = Uri.parse("");
        Set<String> queryParameterNames = url.getQueryParameterNames();

        mBtnStop.post(new Runnable() {
            @Override
            public void run() {
                //调用上面的创建pop函数
                BoxPopManage.getInstance().showPopWindow(mBtnStop, popText);
            }
        });


    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d("CountDownActivity", "onNewIntent");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("CountDownActivity", "onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("CountDownActivity", "onRestart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("CountDownActivity", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("CountDownActivity", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("CountDownActivity", "onDestroy");
    }

    public boolean onKeyDown(int keycode, KeyEvent event) {
//        Log.i(TAG, "Key_Stuta = " + event.getAction());
        if (keycode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Log.d("CountDownActivity", "onKeyDown");
            // 右键处理
            moveTaskToBack(true);
        }
        return true;
    }

    @OnClick({R.id.btn_start, R.id.btn_stop, R.id.frame_tv_parent})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_start:
//                startSecondsCountDown();
//                if (time != null) {
//                    time.cancel();
//                }
//                time.start();

                startCountDown();
                break;
            case R.id.btn_stop:
                stopCoundDown();
                break;
            case R.id.frame_tv_parent:
                Log.d("TaskTextSpanUtil", "点击了文字");
                break;
        }
    }

    private void stopCoundDown() {
        if (mTimer != null) {
            mTimer.cancel();
        }
        if (time != null) {
            time.cancel();
        }
    }

    private void pauseCoundDown() {


    }

    private void startCountDown() {
        if (mTimer != null) {
            mTimer.cancel();
        }
        mTvTimeLeft.setVisibility(View.VISIBLE);
        mTimer = new CountDownTimer(timeStamp + 500, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
//                long day = millisUntilFinished / (1000 * 60 * 60 * 24);
//                long hour = (millisUntilFinished % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
                long hour = millisUntilFinished / (1000 * 60 * 60);
                long minute = (millisUntilFinished % (1000 * 60 * 60)) / (1000 * 60);
                long second = (millisUntilFinished % (1000 * 60)) / 1000;

                String str = "<font color='#FE4070'>订单中心</font>";
//
//                String strAll = "可在${jump_text}选择继续之前的任务哦";
//                String strPart = "订单中心";
//
                Log.i("bro", millisUntilFinished + "");

//                mTvTimeLeft.setText(Html.fromHtml(str + "剩余:" + day + "天" + hour + "时" + minute + "分" + second + "秒"));
                mTvTimeLeft.setText(Html.fromHtml(str + "剩余:" + hour + "时" + minute + "分" + second + "秒"));
            }

            @Override
            public void onFinish() {
                //倒计时结束
                mTvTimeLeft.setVisibility(View.GONE);
            }
        };
        mTimer.start();
    }

    private void startSecondsCountDown() {
        if (mTimer != null) {
            mTimer.cancel();
        }
        mTvTimeLeft.setVisibility(View.VISIBLE);
        if (mTimer == null) {
            mTimer = new CountDownTimer(5000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    long second = (millisUntilFinished % (1000 * 60)) / 1000;
                    mTvTimeLeft.setText(Html.fromHtml("剩余:" + second + "秒"));
                }

                @Override
                public void onFinish() {
                    //倒计时结束
                    mTvTimeLeft.setVisibility(View.GONE);
                }
            };
        }

        mTimer.start();
    }

    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {

            Log.d("TimeCount", "onTick : " + millisUntilFinished / 1000);

            mTvTimeLeft.setBackgroundColor(Color.parseColor("#B6B6D8"));
            mTvTimeLeft.setClickable(false);
            mTvTimeLeft.setText("(" + millisUntilFinished / 1000 + ") 秒后可重新发送");
        }

        @Override
        public void onFinish() {
            Log.d("TimeCount", "onFinish");
            mTvTimeLeft.setText("重新获取验证码");
            mTvTimeLeft.setClickable(true);
            mTvTimeLeft.setBackgroundColor(Color.parseColor("#4EB84A"));

        }
    }

}
