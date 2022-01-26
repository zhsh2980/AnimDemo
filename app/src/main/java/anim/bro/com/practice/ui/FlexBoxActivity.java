package anim.bro.com.practice.ui;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import anim.bro.com.practice.R;
import anim.bro.com.practice.bean.AgileBodyPromotionInfoModel;
import anim.bro.com.practice.util.ETCheckNumLengthWatcher;
import anim.bro.com.practice.util.EnterDebugUtils;
import anim.bro.com.practice.util.GetJsonDataUtil;
import anim.bro.com.practice.util.NotificationUtil;
import anim.bro.com.practice.util.QrCodeUtil;
import anim.bro.com.practice.util.TextLabelUtil;
import anim.bro.com.practice.view.AdvertThreeTextView;
import anim.bro.com.practice.view.AppendViewAfterTextView;

public class FlexBoxActivity extends BaseActivity {

    //    参考文档: https://www.jianshu.com/p/b3a9c4a99053
    @Override
    public int getResourceID() {
        return R.layout.activity_flexbox;
    }

    @Override
    public String getTitleName() {
        return "FlexBox布局";
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void initView() {
//        tv_test = findViewById(R.id.tv_test);
    }

    @Override
    public void initData() {

        String action = "%城市";

        String tempAction = null;
        try {
            action = action.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
            action = action.replaceAll("\\+", "%2B");
            tempAction = URLDecoder.decode(action, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("bro", "error Exception");
        }
        Log.i("bro", "tempAction: " + tempAction);

    }

}
