package anim.bro.com.practice.tuibida.ui;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import anim.bro.com.practice.R;
import anim.bro.com.practice.util.UIUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.recyclerview.animators.SlideInDownAnimator;

import java.util.ArrayList;
import java.util.List;

public class GridActivity extends AppCompatActivity {

    @BindView(R.id.id_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.frame_root)
    FrameLayout frame_root;
    @BindView(R.id.btn_minus)
    Button btnMinus;
    @BindView(R.id.btn_plus)
    Button btnPlus;
    @BindView(R.id.iv_arrow_expand)
    ImageView ivArrowExpand;

    private List<String> mDatas = new ArrayList<String>();
    private HomeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);
        ButterKnife.bind(this);
        initData();
        init();

        addData();
    }

    private void addData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mDatas.clear();
                for (int i = 'A'; i < 'Z'; i++) {
                    mDatas.add("" + (char) i);
                }
            }
        }, 3000);
        mAdapter.notifyDataSetChanged();

    }

    private int lines;
    private int height;

    private void changeHeight(int size) {
        if (size % 4 == 0) {
            lines = size / 4;
        } else {
            lines = size / 4 + 1;
        }
        height = UIUtils.dip2px(lines * 83);
        ValueAnimator animatorHeight = createDropAnimator(mRecyclerView, mRecyclerView.getHeight(), height, true);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(animatorHeight);
        animatorSet.start();
    }


    private ValueAnimator createDropAnimator(final View v, int start, int end, final boolean isHeight) {
        final ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator arg0) {
                int value = (int) arg0.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
                if (isHeight) {
                    layoutParams.height = value;
                } else {
                    layoutParams.width = value;
                }
                v.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }


    private RotateAnimation animation;//旋转动画
    boolean mToExpand = true;

    /**
     * 开关旋转动画
     */
    private void startAnimation() {
        if (mToExpand) {
            if (mDatas.size() <= 4) {
                return;
            }
            changeHeight(mDatas.size());
            animation = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        } else {
            changeHeight(4);
            animation = new RotateAnimation(180, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        }
        mToExpand = !mToExpand;
        animation.setDuration(30);//设置动画持续时间
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatMode(Animation.REVERSE);//设置反方向执行
        animation.setFillAfter(true);//动画执行完后是否停留在执行完的状态
        ivArrowExpand.startAnimation(animation);
    }

    protected void initData() {
        mDatas.clear();
        for (int i = 'A'; i < 'E'; i++) {
            mDatas.add("" + (char) i);
        }
    }

    private void init() {
        mRecyclerView.setLayoutManager((new GridLayoutManager(this, 4)));
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new SlideInDownAnimator());
        mRecyclerView.setAdapter(mAdapter = new HomeAdapter());

    }

    @OnClick({R.id.btn_minus, R.id.btn_plus, R.id.iv_arrow_expand})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_minus:
                changeHeight(4);
                break;
            case R.id.btn_plus:
                changeHeight(mDatas.size());
                break;
            case R.id.iv_arrow_expand:
                startAnimation();
                break;
        }
    }

    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    GridActivity.this).inflate(R.layout.item_grid, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.tv.setText(position + "");
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            TextView tv;

            public MyViewHolder(View view) {
                super(view);
                tv = (TextView) view.findViewById(R.id.id_num);
            }
        }
    }
}
