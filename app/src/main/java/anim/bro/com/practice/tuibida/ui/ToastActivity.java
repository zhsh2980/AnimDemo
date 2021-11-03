package anim.bro.com.practice.tuibida.ui;

import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.ScreenUtils;

import anim.bro.com.practice.R;


public class ToastActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toast);
    }

    public void onClickDefaultToast(View v) {
        Toast.makeText(this, "默认位置的Toast", Toast.LENGTH_LONG).show();
    }

    public void onClickBottomToast(View v) {
        Toast toast = Toast.makeText(this, "居下位置的Toast", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM, 0, ScreenUtils.getScreenHeight() / 4);
        toast.show();
    }

    public void onClickCenterToast(View v) {
        Toast toast = Toast.makeText(this, "居中位置的Toast", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public void onClickTopToast(View v) {
        Display display = getWindowManager().getDefaultDisplay();
        // 获取屏幕高度
        int height = display.getHeight();
        Toast toast = Toast.makeText(this, "居中上部位置的Toast", Toast.LENGTH_LONG);
        // 这里给了一个1/4屏幕高度的y轴偏移量
        toast.setGravity(Gravity.TOP, 0, height / 4);
        toast.show();
    }
}