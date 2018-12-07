package anim.bro.com.animdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red_new);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        mIvClipView.set2ndIvView(mIvClipView2);
    }

    @OnClick({R.id.btn_red_translate, R.id.btn_cicle_expand,
            R.id.btn_pop, R.id.btn_coin, R.id.btn_3d, R.id.btn_clip,
            R.id.btn_progress, R.id.btn_radar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_red_translate:
//                mIvRedMove.setAnimViewReal(mIvRedOrigin);
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
                mIvRoundPoint1.startTranslateAnim(200, 100, 1000);
                mIvRoundPoint2.startTranslateAnim(100, 200, 2000);
                mIvRoundPoint3.startTranslateAnim(300, 300, 3000);
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
                mIvProgressClipView.setClipProgressAnimView(12, 20);
                break;
            case R.id.btn_radar:
                mIvRedOrigin.startAnim();
                break;
            default:
        }
    }

    @OnClick(R.id.iv_red_origin)
    public void onViewClicked() {
        Toast.makeText(this, "点我了", Toast.LENGTH_SHORT).show();
    }
}