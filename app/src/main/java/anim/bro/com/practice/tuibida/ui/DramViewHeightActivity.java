package anim.bro.com.practice.tuibida.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.ScreenUtils;

import anim.bro.com.practice.R;
import anim.bro.com.practice.util.UIUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DramViewHeightActivity extends AppCompatActivity {

    @BindView(R.id.btn_origin)
    TextView mBtnOrigin;
    @BindView(R.id.tv_move)
    TextView mTvMove;

    private final String TAG = "DramViewHeightActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dram_view_height);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
//        int[] location = new int[2];
////        mBtnOrigin.getLocationInWindow(location); //获取在当前窗口内的绝对坐标
//        mBtnOrigin.getLocationOnScreen(location);//获取在整个屏幕内的绝对坐标
//        Log.i(TAG, location[0]+" : "+location[1]);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
//        int[] location1 = new int[2] ;
//        mBtnOrigin.getLocationInWindow(location1); //获取在当前窗口内的绝对坐标
//        int[] location2 = new int[2] ;
//        mBtnOrigin.getLocationOnScreen(location2);//获取在整个屏幕内的绝对坐标
        //do something

        int[] location = new int[2];
//        mBtnOrigin.getLocationInWindow(location); //获取在当前窗口内的绝对坐标
        mBtnOrigin.getLocationInWindow(location);//获取在整个屏幕内的绝对坐标
        Log.i(TAG, location[0] + " : " + location[1]);
        Log.i(TAG, "getScreenHeight: " + ScreenUtils.getScreenHeight());
        int height = mBtnOrigin.getHeight();
        Log.i(TAG, "height: " + height);

        FrameLayout.LayoutParams mBtnOriginParam = (FrameLayout.LayoutParams) mBtnOrigin.getLayoutParams();
        int margin = mBtnOriginParam.bottomMargin;

        FrameLayout.LayoutParams layoutParams1 = (FrameLayout.LayoutParams) mTvMove.getLayoutParams();
//        layoutParams1.setMargins(location[0], 0, 0, UIUtils.dip2px(10) + (ScreenUtils.getScreenHeight() - location[1]) );
        layoutParams1.setMargins(location[0], 0, 0, mBtnOriginParam.bottomMargin + UIUtils.dip2px(10) + mBtnOrigin.getHeight());
        mTvMove.setLayoutParams(layoutParams1);

//
//
//        ViewGroup.LayoutParams param = (ViewGroup.LayoutParams) mBtnOrigin.getLayoutParams();
//        mBtnOrigin.getParent().getParent()


    }

}
