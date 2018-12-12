package anim.bro.com.animdemo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import anim.bro.com.animdemo.util.HideShopAnimUtil;
import anim.bro.com.animdemo.util.RedNewAnimView;
import anim.bro.com.animdemo.view.ClipProgressView;
import anim.bro.com.animdemo.view.Rotate3dAnimation;
import anim.bro.com.animdemo.view.RoundView;
import anim.bro.com.animdemo.view.SwapAnimImageView;
import anim.bro.com.animdemo.view.clipImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RedNewActivity extends AppCompatActivity {

    private final String TAG = "RedNewActivity";

    @BindView(R.id.iv_red_origin)
    SwapAnimImageView mIvRedOrigin;
    @BindView(R.id.iv_red_move)
    RedNewAnimView mIvRedMove;
    @BindView(R.id.main_layout)
    RelativeLayout mMainLayout;
    @BindView(R.id.btn_red_translate)
    Button mBtnRedTranslate;
    @BindView(R.id.btn_cicle_expand)
    Button mBtnCicleExpand;
    @BindView(R.id.btn_pop)
    Button mBtnPop;
    @BindView(R.id.btn_coin)
    Button mBtnCoin;
    @BindView(R.id.btn_3d)
    Button mBtn3d;
    @BindView(R.id.frame_root)
    FrameLayout mFrameRoot;
    @BindView(R.id.round_view)
    RoundView mRoundView;
    @BindView(R.id.round_view_2)
    RoundView mRoundView2;
    @BindView(R.id.round_view_3)
    RoundView mRoundView3;
    @BindView(R.id.iv_3d)
    ImageView mIv3d;
    @BindView(R.id.iv_round_point_1)
    RoundView mIvRoundPoint1;
    @BindView(R.id.iv_round_point_2)
    RoundView mIvRoundPoint2;
    @BindView(R.id.iv_round_point_3)
    RoundView mIvRoundPoint3;
    @BindView(R.id.iv_clip_view)
    clipImageView mIvClipView;
    @BindView(R.id.btn_clip)
    Button mBtnClip;
    @BindView(R.id.iv_clip_view_2)
    clipImageView mIvClipView2;
    @BindView(R.id.iv_progress_clip_view)
    ClipProgressView mIvProgressClipView;
    @BindView(R.id.btn_progress)
    Button mBtnProgress;
    @BindView(R.id.btn_radar)
    Button mBtnRadar;

    private final static int WHAT_SEND = 1;
    @BindView(R.id.iv_round_point_4)
    RoundView mIvRoundPoint4;
    @BindView(R.id.iv_round_point_5)
    RoundView mIvRoundPoint5;
    @BindView(R.id.iv_round_point_6)
    RoundView mIvRoundPoint6;
    @BindView(R.id.iv_round_point_7)
    RoundView mIvRoundPoint7;
    @BindView(R.id.iv_shop_short)
    ImageView mIvShopShort;
    @BindView(R.id.iv_shop)
    ImageView mIvShop;
    @BindView(R.id.btn_anim_all)
    Button mBtnAnimAll;
    @BindView(R.id.btn_show_shop)
    Button mBtnShowShop;
    //从 0 开始
    private int mCountStart = 0;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case WHAT_SEND:
                    //蒙层转圈
                    if (mCountStart == 0) {
                        Calendar cal = Calendar.getInstance();
                        Date date = cal.getTime();
                        System.out.println();
                        Log.i(TAG, new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss:SSS").format(date));
                        //1. 先转圈  0.18秒
                        mIvRedOrigin.startAnim();
                    }
                    //红包上跳
                    if (mCountStart == 1900) {
                        Calendar cal = Calendar.getInstance();
                        Date date = cal.getTime();
                        System.out.println();
                        Log.i(TAG, new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss:SSS").format(date));
                        mIvRedMove.setAnimViewReal(mIvRedOrigin);
                        mIvRedMove.startRedNewAnim();
                    }
                    //圆环1扩散
                    if (mCountStart == 2600) {
                        mRoundView.startCircleAnim(10, 30);
                    }
                    //圆环2扩散
                    if (mCountStart == 2800) {
                        mRoundView2.startCircleAnim(8, 20);
                    }
                    //圆环3扩散
                    if (mCountStart == 2900) {
                        mRoundView3.startCircleAnim(5, 20);
                    }
                    // TODO 金币上飞
                    if (mCountStart == 1800) {

                    }
                    //3. 小点开始跑
                    if (mCountStart == 2300) {
                        startPopAnim1();
                    }
                    //3. 小点开始跑
                    if (mCountStart == 2500) {
                        startPopAnim2();
                    }
                    //3. 小点开始跑
                    if (mCountStart == 2700) {
                        startPopAnim3();
                    }
                    //3. 文案向上
                    if (mCountStart == 3800) {
                        mIvClipView.setClipAnim();
                    }
                    //3. 进度条
                    if (mCountStart == 3000) {
                        mIvProgressClipView.setClipProgressAnimView(5, 6, 20);
                    }
                    //4. 整个动画的最后金币翻转
                    if (mCountStart == 4000) {
                        // 整个动画的最后金币翻转
                        float centerX = mIv3d.getWidth() / 2.0f;
                        float centerY = mIv3d.getHeight() / 2.0f;
                        float centerZ = 0f;
                        Rotate3dAnimation rotate3dAnimationX = new Rotate3dAnimation(0, 360,
                                centerX, centerY, centerZ, Rotate3dAnimation.ROTATE_Y_AXIS, true);
                        rotate3dAnimationX.setDuration(1000);
                        mIv3d.startAnimation(rotate3dAnimationX);
                    }
                    if (mCountStart == 6500) {// 7000 是整个动画执行的时间(毫秒)
                        handler.removeMessages(WHAT_SEND);

                        mCountStart = 0;

                        return;
                    }

                    mCountStart += 100;
                    handler.sendEmptyMessageDelayed(WHAT_SEND, 100);
                    break;
            }
        }
    };
    private HideShopAnimUtil mShopAnimUtil;

    //小点位移动画
    private void startPopAnim1() {
        mIvRoundPoint1.startTranslateAnim(-150, 0, 1500);
        mIvRoundPoint2.startTranslateAnim(-200, 60, 1500);
    }

    //小点位移动画
    private void startPopAnim2() {
        mIvRoundPoint3.startTranslateAnim(200, -100, 1500);
        mIvRoundPoint4.startTranslateAnim(200, -100, 1500);
    }

    //小点位移动画
    private void startPopAnim3() {
        mIvRoundPoint5.startTranslateAnim(0, -150, 1500);
        mIvRoundPoint6.startTranslateAnim(-500, -150, 1500);
        mIvRoundPoint7.startTranslateAnim(-150, 100, 1500);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red_new);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {

        //向上翻的 view
        mIvClipView.set2ndIvView(mIvClipView2);

        //初始化进度条个数
        mIvProgressClipView.setText(5, 20);
        mIvProgressClipView.setLevel(5, 20);

        //隐藏购物车
        mShopAnimUtil = new HideShopAnimUtil(mIvShop, mIvShopShort);
    }

    @OnClick({R.id.btn_red_translate, R.id.btn_cicle_expand,
            R.id.btn_pop, R.id.btn_coin, R.id.btn_3d, R.id.btn_clip,
            R.id.btn_progress, R.id.btn_radar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_red_translate:
                mIvRedMove.setAnimViewReal(mIvRedOrigin);
                mIvRedMove.startRedNewAnim();
                break;
            case R.id.btn_cicle_expand:
//                mRoundView.setRadius(10);
//                mRoundView.setRingWidth(10);
                mRoundView.startCircleAnim(10, 30);
                mRoundView2.startCircleAnim(8, 20);
                mRoundView3.startCircleAnim(5, 20);
                break;
            case R.id.btn_pop:
                startPopAnim1();
                startPopAnim2();
                startPopAnim3();
                break;
            case R.id.btn_coin:
                Toast.makeText(this, "暂无", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_3d:
                float centerX = mIv3d.getWidth() / 2.0f;
                float centerY = mIv3d.getHeight() / 2.0f;
                float centerZ = 0f;
                Rotate3dAnimation rotate3dAnimationX = new Rotate3dAnimation(0, 360,
                        centerX, centerY, centerZ, Rotate3dAnimation.ROTATE_Y_AXIS, true);
                rotate3dAnimationX.setDuration(1000);
                mIv3d.startAnimation(rotate3dAnimationX);
                break;
            case R.id.btn_clip:
//                ClipDrawable drawable = (ClipDrawable) mIvClipView.getDrawable();
//                drawable.setLevel(5000);
                mIvClipView.setClipAnim();
                break;
            case R.id.btn_progress:
                mIvProgressClipView.setClipProgressAnimView(5, 6, 20);
                break;
            case R.id.btn_radar:
                mIvRedOrigin.startAnim();
                break;
            default:
        }
    }


    @OnClick(R.id.iv_red_origin)
    public void onRedViewClicked() {
        Toast.makeText(this, "点我了", Toast.LENGTH_SHORT).show();
    }

    //开始所有动画
    @OnClick(R.id.btn_anim_all)
    public void onBtnAllClicked() {
        //1. 移除消息
        handler.removeMessages(WHAT_SEND);
        //2. 计数器归零
        mCountStart = 0;
        //3. 结束所有动画
        // TODO: 2018/12/9
        finishAllAnim();
        //开始动画
//        handler.removeCallbacksAndMessages(null);
        handler.sendEmptyMessage(WHAT_SEND);

    }


    private void finishAllAnim() {
        mIvRedMove.resetAnim();
        mRoundView.resetAnim();
        mIvRoundPoint1.resetAnim();
        mIvRoundPoint2.resetAnim();
        mIvRoundPoint3.resetAnim();
        mIv3d.clearAnimation();
        mIvClipView.resetAnim();
//        mIvProgressClipView.resetAnim();
        mIvRedOrigin.resetAnim();
    }

    @OnClick({R.id.iv_shop_short, R.id.btn_show_shop , R.id.btn_cancel_pop})
    public void onShowViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_shop_short:
                //购物按钮显示
                mShopAnimUtil.resetAnim();
                mShopAnimUtil.showShopView();
                break;
            case R.id.btn_show_shop:
                //购物按钮显示
                mShopAnimUtil.resetAnim();
                mShopAnimUtil.showShopView();
                break;
            case R.id.btn_cancel_pop:
                //购物按钮显示
               mIvClipView.stopAnimSetAlpha();
                break;
        }
    }
}