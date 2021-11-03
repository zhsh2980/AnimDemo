package anim.bro.com.practice.tuibida.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import anim.bro.com.practice.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhangshan on 2018/11/8 15:30.
 */
public class TaskRedGuideView extends FrameLayout {

    @BindView(R.id.iv_1)
    ImageView mIv1;
    @BindView(R.id.iv_2)
    ImageView mIv2;

    @OnClick({R.id.iv_1, R.id.iv_2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_1:
                mIv1.setVisibility(GONE);
                mIv2.setVisibility(VISIBLE);
                break;
            case R.id.iv_2:
                mIv1.setVisibility(GONE);
                mIv2.setVisibility(GONE);
                if (onGuideClickListener != null) {
                    onGuideClickListener.onGuideEndListener();
                }
                break;
        }
    }

    private OnGuideClickListener onGuideClickListener;

    public void setOnClickListener(OnGuideClickListener onGuideClickListener) {
        this.onGuideClickListener = onGuideClickListener;
    }

    //自定义接口
    public interface OnGuideClickListener {
        //补差价
        void onGuideEndListener();
    }


    public TaskRedGuideView(Context context) {
        this(context, null);
    }

    public TaskRedGuideView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TaskRedGuideView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        Log.i("bro", "init");
        LayoutInflater.from(context).inflate(R.layout.view_task_red_guide, this);
        ButterKnife.bind(this);
        mIv1.setVisibility(VISIBLE);
        mIv2.setVisibility(GONE);
    }

}
