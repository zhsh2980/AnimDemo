package anim.bro.com.practice.tuibida.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;


import com.makeramen.roundedimageview.RoundedImageView;

import anim.bro.com.practice.R;
import anim.bro.com.practice.tuibida.utils.TaskRedAnimUtils;
import anim.bro.com.practice.util.UIUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhangshan on 2018/11/8 15:30.
 */
public class TaskRedView extends FrameLayout {

    private String TAG = "TaskRedView";

    //    public static int TASK_OLD = -1;//前一个的状态
    public static int TASK_CURRENT = -1;//当前的状态

    public final static int TASK_DEFAULT = 0;
    public final static int TASK_EXPAND = 1;
    public final static int TASK_PAY_PART = 2;
    public final static int TASK_FOR_FREE = 3;
    public final static int TASK_WAIT_DELETE = 4;//待删除的状态

    //是否可以抖动
    private boolean canShake = true;

    @BindView(R.id.iv_left)
    RoundedImageView mIvLeft;
    @BindView(R.id.tv_good_name)
    TextView mTvGoodName;
    @BindView(R.id.frame_left)
    FrameLayout mFrameLeft;
    @BindView(R.id.tv_has_finished_part)
    TextView mTvMiddle;
    @BindView(R.id.iv_progress)
    ZzHorizontalProgressBar mIvProgress;
    @BindView(R.id.frame_middle)
    FrameLayout mFrameMiddle;
    @BindView(R.id.iv_arrow_right)
    ImageView mIvArrowRight;
    @BindView(R.id.frame_right)
    FrameLayout mFrameRight;
    @BindView(R.id.ll_task_red_root)
    LinearLayout mLlTaskRedRoot;

    boolean isExpand = false;
    @BindView(R.id.iv_close_right)
    ImageView mIvCloseRight;
    @BindView(R.id.btn_middle)
    Button mBtnMiddle;
    @BindView(R.id.frame_arrow_right)
    FrameLayout mFrameArrowRight;
    @BindView(R.id.frame_close_right)
    FrameLayout mFrameCloseRight;

    private OnClickListener onClickListener;

    int mTouchSlop; //最短滑动距离
    Activity mActivity;

    Animation shake;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    //自定义接口
    public interface OnClickListener {
        //补差价
        void onPayPartListener();

        //返现免单
        void onForFreeListener();

        //关闭
        void onCloseListener();
    }


    public TaskRedView(Context context) {
        this(context, null);
    }

    public TaskRedView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TaskRedView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        //长按抖动?
        setOnLongClickLis();
    }

    private void init(Context context) {
        Log.i("bro", "init");
        LayoutInflater.from(context).inflate(R.layout.view_task_red, this);
        ButterKnife.bind(this);
        mActivity = (Activity) context;
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
//        setUiDefault();
//        view_white_line = findViewById(R.id.view_white_line);
    }

    private void setOnLongClickLis() {
        setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                if (!canShake) {
                    return false;
                }
                if (TASK_CURRENT == TASK_FOR_FREE) {
                    return false;
                }
                //哪几种状态支持抖动
                shake = AnimationUtils.loadAnimation(getContext(), R.anim.add_tab_shake_anim);
                shake.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        Log.i("AddCartAnimManager --> ", String.format("|||==>>  onAnimationStart([animation]):%s \n", "抖动动画开始"));
                        //抖动状态不能再次抖动
                        canShake = false;
                        mFrameArrowRight.setVisibility(GONE);
                        mFrameCloseRight.setVisibility(VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Log.i("AddCartAnimManager --> ", String.format("|||==>>  onAnimationStart([animation]):%s \n", "抖动动画结束"));
                        canShake = true;
//                        mFrameArrowRight.setVisibility(VISIBLE);
//                        mFrameCloseRight.setVisibility(GONE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                startAnimation(shake);

                return true;
            }
        });
    }

    @OnClick({R.id.frame_arrow_right, R.id.frame_close_right,
            R.id.btn_middle})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.frame_arrow_right) {
            if (isExpand) {
                //折叠
                setTaskDefault();
//                TASK_OLD = TASK_CURRENT;

            } else {
                if (TASK_CURRENT == -1) {
                    setTaskExpand();
                } else
                    //展开
                    if (TASK_CURRENT == TASK_EXPAND) {
                        setTaskExpand();
                    } else if (TASK_CURRENT == TASK_PAY_PART) {
                        setTaskPayPart();
                    } else if (TASK_CURRENT == TASK_FOR_FREE) {
                        setTaskForFree();
                    }
            }
        } else if (i == R.id.frame_close_right) {
            if (shake != null) {
                shake.cancel();
            }
            //关闭
            if (onClickListener != null) {
                onClickListener.onCloseListener();
            }
        } else if (i == R.id.btn_middle) {
            Log.d("TaskRedView", "点按了");
            //中间按钮的点击
            if (TASK_CURRENT == TASK_PAY_PART) {
                if (onClickListener != null) {
                    onClickListener.onPayPartListener();
                }
            } else if (TASK_CURRENT == TASK_FOR_FREE) {
                if (onClickListener != null) {
                    onClickListener.onForFreeListener();
                }
            }
        }
    }

    /**
     * 变为默认的状态 (动画 + UI)
     */
    private void setTaskDefault() {
//        TASK_CURRENT = TASK_DEFAULT;
//        if (isExpand) {
//        TASK_CURRENT = TASK_EXPAND;
        if (isExpand) {
            TaskRedAnimUtils.getInstance().init(getContext(), mLlTaskRedRoot, mIvArrowRight, 30, 210, TaskRedAnimUtils.tog_180_0).toggle();
        }
        setUiDefault();
        isExpand = false;
//        }
    }

    /**
     * 变为展开的状态 (动画 + UI)
     */
    private void setTaskExpand() {
//        if (!isExpand) {
        setUiExpand();
        if ((TASK_CURRENT == TASK_EXPAND && isExpand) || TASK_CURRENT == TASK_PAY_PART || TASK_CURRENT == TASK_FOR_FREE) {
            TaskRedAnimUtils.getInstance().init(getContext(), mLlTaskRedRoot, mIvArrowRight, 70, 210, TaskRedAnimUtils.tog_no).startChangeSize();
        } else {
            TaskRedAnimUtils.getInstance().init(getContext(), mLlTaskRedRoot, mIvArrowRight, 70, 210, TaskRedAnimUtils.tog_0_180).toggle();
        }
        isExpand = true;
        TASK_CURRENT = TASK_EXPAND;
//        }
    }

    /**
     * 变为支付剩余的状态 (动画 + UI)
     */
    private void setTaskPayPart() {
        setUiPayPart();
        if (TASK_CURRENT == TASK_EXPAND || (TASK_CURRENT == TASK_PAY_PART && isExpand) || TASK_CURRENT == TASK_FOR_FREE) {
            TaskRedAnimUtils.getInstance().init(getContext(), mLlTaskRedRoot, mIvArrowRight, 70, 225, TaskRedAnimUtils.tog_no).startChangeSize();
        } else {
            TaskRedAnimUtils.getInstance().init(getContext(), mLlTaskRedRoot, mIvArrowRight, 70, 225, TaskRedAnimUtils.tog_0_180).toggle();
        }
        TASK_CURRENT = TASK_PAY_PART;
//        if (!isExpand) {
        isExpand = true;
    }

    /**
     * 变为返现免单的状态 (动画 + UI)
     */
    private void setTaskForFree() {
        setUiForFree();
        //叉号隐藏,所以,宽度减去32
        TaskRedAnimUtils.getInstance().init(getContext(), mLlTaskRedRoot, mIvArrowRight, 70, 225 - 32, TaskRedAnimUtils.tog_no).startChangeSize();
        TASK_CURRENT = TASK_FOR_FREE;
//        if (!isExpand) {
        isExpand = true;
    }

    /**
     * 变为等待删除的状态 (动画 + UI)
     */
    private void setTaskWaitDelete() {
//        if (isExpand) {
        isExpand = false;
        setUiWaitDelete();
        TaskRedAnimUtils.getInstance().init(getContext(), mLlTaskRedRoot, mIvArrowRight, 30, 210, TaskRedAnimUtils.tog_no).startChangeSize();
//        }
    }

    /**
     * 变为默认的状态 (UI)
     */
    private void setUiDefault() {
        setVisibility(VISIBLE);
        setViewWidthHeight(mLlTaskRedRoot, 210, 30);
        setViewWidthHeight(mIvProgress, 60, 7);
        hideRelatedUi();
        mTvGoodName.setVisibility(VISIBLE);
        mIvProgress.setVisibility(VISIBLE);
        mFrameArrowRight.setVisibility(VISIBLE);
    }

    /**
     * 变为展开的状态 (UI)
     */
    private void setUiExpand() {
        setVisibility(VISIBLE);
        setViewWidthHeight(mIvProgress, 85, 7);
        hideRelatedUi();
        mIvLeft.setVisibility(VISIBLE);
        mTvMiddle.setVisibility(VISIBLE);
        mIvProgress.setVisibility(VISIBLE);
        mFrameArrowRight.setVisibility(VISIBLE);
    }

    /**
     * 变为支付剩余的状态 (UI)
     */
    private void setUiPayPart() {
        setVisibility(VISIBLE);
        hideRelatedUi();
        mIvLeft.setVisibility(VISIBLE);
        mTvMiddle.setVisibility(VISIBLE);
        mBtnMiddle.setVisibility(VISIBLE);
        mBtnMiddle.setText("补差价购买");
        mFrameArrowRight.setVisibility(VISIBLE);
    }

    /**
     * 变为返现免单的状态 (UI)
     */
    private void setUiForFree() {
        setVisibility(VISIBLE);
        hideRelatedUi();
        mIvLeft.setVisibility(VISIBLE);
        mTvMiddle.setVisibility(VISIBLE);
        mBtnMiddle.setVisibility(VISIBLE);
        mBtnMiddle.setText("点击返现免单");
//        mFrameCloseRight.setVisibility(VISIBLE);
    }

    /**
     * 变为等待删除的状态 (UI)
     */
    private void setUiWaitDelete() {
        setVisibility(VISIBLE);
        setViewWidthHeight(mIvProgress, 60, 7);
        hideRelatedUi();
        mTvGoodName.setVisibility(VISIBLE);
        mIvProgress.setVisibility(VISIBLE);
        mFrameCloseRight.setVisibility(VISIBLE);
    }

    //每次变化前,先把共用的 UI 都隐藏了,每种里面单独显示
    private void hideRelatedUi() {
        mIvLeft.setVisibility(GONE);
        mTvGoodName.setVisibility(GONE);
        mTvMiddle.setVisibility(GONE);
        mIvProgress.setVisibility(GONE);
        mFrameCloseRight.setVisibility(GONE);
        mBtnMiddle.setVisibility(GONE);
//        mIvArrowRight.clearAnimation();
        mFrameArrowRight.setVisibility(GONE);

//        mIvArrowRight.clearAnimation();

    }

    private void setViewWidthHeight(View view, int width, int height) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = UIUtils.dip2px(width);
        params.height = UIUtils.dip2px(height);
        view.setLayoutParams(params);
    }

    //供外界调用,改变形态
    public void setTask(int taskType) {
        //记录变换前的状态,点击再次回去
//        TASK_OLD = TASK_CURRENT;
//        TASK_CURRENT = taskType;
        switch (taskType) {
            case TASK_DEFAULT:
                setTaskDefault();
                break;
            case TASK_EXPAND:
                setTaskExpand();
                break;
            case TASK_PAY_PART:
                setTaskPayPart();
                break;
            case TASK_FOR_FREE:
                setTaskForFree();
                break;
            default:
                break;
        }

    }

//    //设置中间的文字
//    public void setTvMiddle(String strAll, String strPart) {
//        TaskTextSpanUtil.getInstance().setTvMiddle(mTvMiddle, strAll, strPart);
//    }

    //设置中间按钮的文字
    public void setBtnTextMiddle(String str) {
        if (mBtnMiddle != null) {
            mBtnMiddle.setText(str + "");
        }
    }

    //设置左边文字
    public void setTvLeft(String str) {
        mTvGoodName.setText(str + "");
    }

    //设置中间按钮的文字
    public void setProgress(String progress) {
        try {
            mIvProgress.setProgress(Integer.parseInt(progress));
        } catch (Exception e) {
            Log.i(TAG, " -- setProgress Exception --");
        }
    }

    //显示箭头,隐藏关闭按钮
    public void restoreIvRight() {
        mFrameArrowRight.setVisibility(VISIBLE);
        mFrameCloseRight.setVisibility(GONE);
        canShake = true;
    }

}
