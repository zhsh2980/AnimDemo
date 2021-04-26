package anim.bro.com.animdemo.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;

import anim.bro.com.animdemo.view.ProgressImageView;
import anim.bro.com.animdemo.R;
import anim.bro.com.animdemo.view.UpSeeMoreView;
import anim.bro.com.animdemo.util.RedPacketAnimView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RedFlyActivity extends AppCompatActivity {

    @BindView(R.id.rela_activi_root)
    RelativeLayout rela_activi_root;
    @BindView(R.id.iv_left_re)
    RelativeLayout iv_left_re;
    @BindView(R.id.iv_left)
    RedPacketAnimView redpacketView;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.btn_guide_first)
    Button btn_guide_first;
    @BindView(R.id.iv_guide_content)
    ImageView ivGuideView;
    @BindView(R.id.iv_guide_middle_content)
    ImageView ivGuideMiddleView;
    @BindView(R.id.tv_price_bottom_view)
    TextView tvPriceBottomView;
    @BindView(R.id.progressImg)
    ProgressImageView progressImg;
    @BindView(R.id.btn_normal_out)
    Button btn_normal_out;
    @BindView(R.id.lv)
    ListView lv;
    @BindView(R.id.btn_guide)
    Button btnGuide;
    @BindView(R.id.btn_price)
    Button btnPrice;
//    @BindView(R.id.iv_bg_red)
//    UpSeeMoreView ivBgRed;

    private float progress;
    View redBackground;

    private int mTouchShop;//最小滑动距离
    protected float mFirstY;//触摸下去的位置
    protected float mCurrentY;//滑动时Y的位置
    protected int direction;//判断是否上滑或者下滑的标志
    protected boolean mShow;//判断是否执行了上滑动画

    private static final String[] strs = new String[]{
            "first", "second", "third", "fourth", "fifth",
            "first", "second", "third", "fourth", "fifth",
            "first", "second", "third", "fourth", "fifth",
            "first", "second", "third", "fourth", "fifth",
            "first", "second", "third", "fourth", "fifth",
            "first", "second", "third", "fourth", "fifth",
            "first", "second", "third", "fourth", "fifth",
            "first", "second", "third", "fourth", "fifth"
    };//定义一个String数组用来显示ListView的内容

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red_fly);
        ButterKnife.bind(this);

        initListView();

        initRedBackground();

        redpacketView.setImageResource(R.drawable.red_big_middle);
        redpacketView.setEndView(ivRight);
        redpacketView.setGuideView(ivGuideView, ivGuideMiddleView, tvPriceBottomView);
        //首次进页面或者下拉刷新调用
//        redpacketView.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                redpacketView.setStartViewPosition();
//            }
//        }, 500);

    }

    private void initRedBackground() {
        redBackground = getRedBackground(this);
    }

    private void initListView() {
        /*为ListView设置Adapter来绑定数据*/
        lv.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, strs));

        lv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // 触摸按下时的操作
                        mFirstY = event.getY();//按下时获取位置
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mCurrentY = event.getY();//得到滑动的位置
                        if (mCurrentY - mFirstY > mTouchShop) {//滑动的位置减去按下的位置大于最小滑动距离 则表示向下滑动
                            direction = 0;//down
                        } else if (mFirstY - mCurrentY > mTouchShop) {//反之向上滑动
                            direction = 1;//up
                        }
                        if (direction == 1) {//判断如果是向上滑动 则执行向上滑动的动画
//                            ivMiddle.setVisibility(View.GONE);
//                            ivBgRed.setVisibility(View.GONE);

                            if (mShow) {//判断动画是否执行了 执行了则改变状态
                                //执行往上滑动的动画
//                                tolbarAnim(1);

                                mShow = false;
                            }
                        } else if (direction == 0) {//判断如果是向下滑动 则执行向下滑动的动画
//                            ivMiddle.setVisibility(View.VISIBLE);
//                            ivBgRed.setVisibility(View.VISIBLE);
                            if (!mShow) {//判断动画是否执行了 执行了则改变状态
                                //执行往下滑动的动画
//                                tolbarAnim(0);
                                mShow = true;

                            }
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
                return false;
            }
        });
    }

    @OnClick({R.id.btn_guide_first, R.id.btn_guide, R.id.btn_price, R.id.btn_normal_out, R.id.btn_normal_restart, R.id.btn_red_fly})
    public void onViewClicked2(View view) {
        switch (view.getId()) {
            case R.id.btn_guide_first:
//                redpacketView.cancleAnim();
                progressImg.setProgress(new ProgressImageView.EndListener() {

                    @Override
                    public void endListener() {

                    }

                    @Override
                    public void startFromZeroListener() {
                        redpacketView.cancleAnim();
                        ivGuideView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void startPercentListener() {
                        redpacketView.startGuideFirstAnim();
                    }
                });
                break;
            case R.id.btn_guide:
//                redpacketView.cancleAnim();
                progressImg.setProgress(new ProgressImageView.EndListener() {

                    @Override
                    public void endListener() {
                        redpacketView.startGuideLoginAnim();
                    }

                    @Override
                    public void startFromZeroListener() {
                        redpacketView.cancleAnim();
                    }

                    @Override
                    public void startPercentListener() {

                    }
                });
                break;
            case R.id.btn_price:
//                redpacketView.cancleAnim();
                redpacketView.startMoneyTextAnim(3.8 + "");
//                progressImg.setProgress(new ProgressImageView.EndListener() {
//
//                    @Override
//                    public void endListener() {
//                        redpacketView.startNormalAnim();
//                    }
//
//                    @Override
//                    public void startFromZeroListener() {
//                        redpacketView.cancleAnim();
//                    }
//
//                    @Override
//                    public void startPercentListener() {
//
//                    }
//                });
                break;
            case R.id.btn_normal_out:
                progress += 10;
                progressImg.setProgress(progress, true, new ProgressImageView.EndListener() {

                    @Override
                    public void endListener() {
                        ToastUtils.showShort("走完进度了");
                        redpacketView.startNormalAnim();
                        progress = 0;
                    }

                    @Override
                    public void startFromZeroListener() {
                        redpacketView.cancleAnim();
                    }

                    @Override
                    public void startPercentListener() {

                    }
                });
                break;
            case R.id.btn_normal_restart:
                progress = 0;
                break;
            case R.id.btn_red_fly:

                redpacketView.startNormalAnim();

                break;
        }

    }


    private View getRedBackground(Activity activity) {
        UpSeeMoreView upSeeMoreView = new UpSeeMoreView(this);
        upSeeMoreView.setId(R.id.rela_root);
//        View view = LayoutInflater.from(activity).inflate(R.treasure_anim.sv_video_tip_to_login, null);
        FrameLayout contentView = (FrameLayout) activity.findViewById(android.R.id.content);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        contentView.addView(upSeeMoreView, layoutParams);
        upSeeMoreView.setVisibility(View.INVISIBLE);
        return upSeeMoreView;
    }

    @OnClick({R.id.btn_bg_show, R.id.btn_bg_hide, R.id.btn_id_exist,R.id.btn_glide_invisible})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_bg_show:
                redBackground.setVisibility(View.VISIBLE);

//                rela_activi_root.bringChildToFront(iv_left_re);
//                rela_activi_root.updateViewLayout(iv_left_re, iv_left_re.getLayoutParams());
//                iv_left_re.bringChildToFront(redpacketView);
//                iv_left_re.updateViewLayout(redpacketView, redpacketView.getLayoutParams());

                break;
            case R.id.btn_bg_hide:
                redBackground.setVisibility(View.GONE);
                break;
            case R.id.btn_id_exist:
                findIdExist();
                redBackground.setVisibility(View.GONE);
                break;
            case R.id.btn_glide_invisible:
//                Glide.with(this).load("http://mp5.jmstatic.com/mobile/api/other/icon/red_bubble.png").listener(new RequestListener<String, GlideDrawable>() {
//                    @Override
//                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                        ivGuideMiddleView.setVisibility(View.INVISIBLE);
//                        return false;
//                    }
//                }).into(ivGuideMiddleView);
                Glide.with(this).load("http://mp5.jmstatic.com/mobile/api/other/icon/red_bubble.png").into(ivGuideMiddleView);
                break;
        }
    }

    private boolean findIdExist() {
        View view = findViewById(R.id.rela_root);
        if (view != null) {
            ToastUtils.showShort("id 存在");
            return true;
        }
        ToastUtils.showShort("id 不存在");
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("RedFlyActivity", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("RedFlyActivity", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("RedFlyActivity", "pauseVideo");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("RedFlyActivity", "onDestroy");
    }
}
