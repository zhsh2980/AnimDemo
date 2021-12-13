package anim.bro.com.practice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.constant.TimeConstants;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import anim.bro.com.practice.dialog.TreasureOpenDialog;
import anim.bro.com.practice.focus.FocusNewActivity;
import anim.bro.com.practice.fragment.RvFragmentActivity;
import anim.bro.com.practice.goods.GlideActivity;
import anim.bro.com.practice.goods.GoodsBuyActivity;
import anim.bro.com.practice.goods.GoodsSaleActivity;
import anim.bro.com.practice.rv.RVActivity;
import anim.bro.com.practice.rvcategory.RvCategoryActivity;
import anim.bro.com.practice.banner.BannerActivity;
import anim.bro.com.practice.tuibida.MainTuiBiDaActivity;
import anim.bro.com.practice.ui.BlurActivity;
import anim.bro.com.practice.ui.CycleBoxActivity;
import anim.bro.com.practice.ui.DuoDianActivity;
import anim.bro.com.practice.ui.PraiseActivity;
import anim.bro.com.practice.ui.RedFlyActivity;
import anim.bro.com.practice.ui.RedNewActivity;
import anim.bro.com.practice.ui.RedRainActivity;
import anim.bro.com.practice.ui.TestInputActivity;
import anim.bro.com.practice.util.EnterDebugUtils;
import anim.bro.com.practice.util.GetJsonDataUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhangshan on 2018/10/9 11:27.
 */
public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";
    @BindView(R.id.btn_red_fly)
    Button btnRedFly;
    @BindView(R.id.btn_praise)
    Button btnPraise;
    @BindView(R.id.text_num_new_gold)
    TextView textNumNewGold;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initScreenListener();

        //测试日期
        testData();

        //正式环境打开 debug 界面
        testQrCodeEndterDebug();

//        JSONObject object = testJsonMap();
        String shuabaoJson = new GetJsonDataUtil().getJson(this, "shuabao.json");//获取assets目录下的json文件数据
        try {
            JSONObject jsonObject = new JSONObject(shuabaoJson);
            resolveJsonMap(jsonObject.optJSONObject("alarmMsg"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        Log.i("bro", "value: " + shuabaoJson);
//        resolveJsonMap(object.optJSONObject("alarmMsg"));
        //9b17 ab78 408f 8f97 f460 a337 f0e5 3af5
    }

    private void testQrCodeEndterDebug() {
        String qrCodeEnterDebug = EnterDebugUtils.getInstance(this).getQrCodeEnterDebug();
        Log.i("bro", "qrCodeEnterDebug: " + qrCodeEnterDebug);
    }

    private void resolveJsonMap(JSONObject data) {
        if (isNullJSONObject(data)) {
            return;
        }
//        HashMap<String, String> map = new HashMap<>();
        Iterator iterator = data.keys();
        while (iterator.hasNext()) {
            String key = iterator.next().toString();
            if (TextUtils.equals(key, "240")) {
                String value = data.optString(key);
//                ToastUtils.showLong(value);
//                Log.i("bro", "value: " + value);
            }
        }
    }

    private JSONObject testJsonMap() {
        Map<String, String> map = new HashMap<>();
        map.put("240", "我是一句话");

        JSONObject object = new JSONObject();
        try {
            object.put("alarmMsg", map);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i("bro", "jsonMap: " + object.toString());

        return object;

    }


    private void initScreenListener() {

        BroadcastReceiver screenReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent == null) {
                    return;
                }
                String action = intent.getAction();
                switch (action) {
                    case Intent.ACTION_SCREEN_ON:
                        Log.i("bro", "屏幕点亮");
                        Toast.makeText(MainActivity.this, "屏幕点亮", Toast.LENGTH_LONG).show();
                        break;

                    case Intent.ACTION_SCREEN_OFF:
                        Log.i("bro", "屏幕关闭");
                        Toast.makeText(MainActivity.this, "屏幕点亮", Toast.LENGTH_LONG).show();
                        break;
                    default:
                        break;
                }

            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        registerReceiver(screenReceiver, filter);


    }

    @OnClick({R.id.btn_red_fly, R.id.btn_praise
            , R.id.btn_red_new, R.id.btn_box_dialog
            , R.id.btn_blur, R.id.btn_cycle_box
            , R.id.btn_goods_sale, R.id.btn_goods_buy
            , R.id.btn_goods_glide
            , R.id.btn_duodian, R.id.btn_rv
            , R.id.btn_focus, R.id.btn_category
            , R.id.btn_rv_add_frag
            , R.id.btn_banner
            , R.id.btn_tuibida
            , R.id.btn_keyboard
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_red_fly:
                ActivityUtils.startActivity(RedFlyActivity.class);
                break;
            case R.id.btn_praise:
                ActivityUtils.startActivity(PraiseActivity.class);
                break;
            case R.id.btn_red_new:
                ActivityUtils.startActivity(RedNewActivity.class);
                break;
            case R.id.btn_box_dialog:
                TreasureOpenDialog dialog = new TreasureOpenDialog(this);
                dialog.show();
                dialog.setData();
                break;
            case R.id.btn_blur:
                ActivityUtils.startActivity(BlurActivity.class);
                break;
            case R.id.btn_cycle_box:
                ActivityUtils.startActivity(CycleBoxActivity.class);
                break;
            case R.id.btn_duodian:
                ActivityUtils.startActivity(DuoDianActivity.class);
                break;
            case R.id.btn_goods_sale:
                ActivityUtils.startActivity(GoodsSaleActivity.class);
                break;
            case R.id.btn_goods_buy:
                ActivityUtils.startActivity(GoodsBuyActivity.class);
                break;
            case R.id.btn_goods_glide:
                ActivityUtils.startActivity(GlideActivity.class);
                break;
            case R.id.btn_rv:
                ActivityUtils.startActivity(RVActivity.class);
                break;
            case R.id.btn_focus:
                ActivityUtils.startActivity(FocusNewActivity.class);
                break;
            case R.id.btn_category:
                ActivityUtils.startActivity(RvCategoryActivity.class);
                break;
            case R.id.btn_rv_add_frag:
                ActivityUtils.startActivity(RvFragmentActivity.class);
                break;
            case R.id.btn_banner:
                ActivityUtils.startActivity(BannerActivity.class);
                break;
            case R.id.btn_tuibida:
                ActivityUtils.startActivity(MainTuiBiDaActivity.class);
                break;
            case R.id.btn_keyboard:
                ActivityUtils.startActivity(TestInputActivity.class);
                break;
            default:
        }
    }

    @OnClick({R.id.btn_red_rain})
    public void onViewClicked2(View view) {
        switch (view.getId()) {
            case R.id.btn_red_rain:
                ActivityUtils.startActivity(RedRainActivity.class);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("MainActivity", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("MainActivity", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("MainActivity", "pauseVideo");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("MainActivity", "onDestroy");
    }

    public String caseData(String dt) {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(dt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String now = new SimpleDateFormat("MM月dd日 HH:mm:ss").format(date);
//        return now.split(" ")[0];//年月日
        return now;//年月日
    }

    private void testData() {
        String dueStartDate = "2021-08-25 15:00:00";//尾款开始时间(yyyy-MM-dd HH:mm:ss)
        String dueEndDate = "2021-08-26 14:16:18";//尾款结束时间(yyyy-MM-dd HH:mm:ss)
        long serverTime = 1629872177000L;//返回时间戳,毫秒转换后变为 2021-08-25 14:16:17
        long longTimeStart = TimeUtils.string2Millis(dueStartDate);//1629874800000
        long longTimeEnd = TimeUtils.string2Millis(dueEndDate);//1630324800000

        String dataFormat = caseData(dueStartDate);

        long timeSpanMSEC = TimeUtils.getTimeSpan(longTimeEnd, serverTime, TimeConstants.MSEC);

        long MSEC_24 = 24 * 60 * 60 * 1000;

        if (serverTime < longTimeStart) {
            //未开始
        } else if (serverTime < longTimeEnd) {
            if (timeSpanMSEC > MSEC_24) {

            } else {

            }
        }

        Log.d("bro", "longTimeStart---" + longTimeStart + "---longTimeEnd---" + longTimeEnd
                + "---timeSpanMSEC---" + timeSpanMSEC + "---MSEC_24---" + MSEC_24
                + "---dataFormat---" + dataFormat);

        Date date = TimeUtils.millis2Date(10872 * 1000);
        String now = new SimpleDateFormat("HH:mm:ss").format(date);
        Log.d("bro", "now--" + now);


    }

    protected boolean isNullJSONObject(JSONObject jsonObject) {
        return jsonObject == null || JSONObject.NULL.equals(jsonObject);
    }

}
