package anim.bro.com.practice.focus;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import anim.bro.com.practice.R;

public class FocusNewActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private LinearLayout consRoot;
    private FrameLayout frame1;
    private FrameLayout frame2;
    private CheckBox colorSizeRb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focus_new);
        initView();
        setData();
    }

    private void initView() {
        consRoot = (LinearLayout) findViewById(R.id.cons_root);
        frame1 = (FrameLayout) findViewById(R.id.frame_1);
        frame2 = (FrameLayout) findViewById(R.id.frame_2);
        colorSizeRb = (CheckBox) findViewById(R.id.color_size_rb);
        colorSizeRb.setOnCheckedChangeListener(this);
    }

    private void setData() {
        frame1.bringToFront();


    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (colorSizeRb.isChecked()){
            colorSizeRb.setClickable(false);
        }else{
            colorSizeRb.setClickable(true);
        }
    }
}