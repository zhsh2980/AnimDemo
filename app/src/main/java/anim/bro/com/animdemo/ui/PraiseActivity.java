package anim.bro.com.animdemo.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import anim.bro.com.animdemo.R;
import anim.bro.com.animdemo.util.PraiseAnimUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PraiseActivity extends AppCompatActivity {

    @BindView(R.id.iv_praise)
    ImageView ivPraise;

    boolean mIsSetPraise = true;// true 点赞  false 取消赞
    @BindView(R.id.iv_heart_left_top)
    ImageView ivHeartLeftTop;
    @BindView(R.id.iv_heart_left_bottom)
    ImageView ivHeartLeftBottom;
    @BindView(R.id.iv_heart_right_top)
    ImageView ivHeartRightTop;
    @BindView(R.id.iv_heart_right_bottom)
    ImageView ivHeartRightBottom;

    PraiseAnimUtil praiseAnimUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_praise);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        praiseAnimUtil = new PraiseAnimUtil();
        praiseAnimUtil.setGuideView(this , ivPraise, ivHeartLeftTop, ivHeartLeftBottom, ivHeartRightTop, ivHeartRightBottom);
    }

    @OnClick(R.id.iv_praise)
    public void onViewClicked() {
       setPraise(mIsSetPraise);
    }

    public void setPraise(boolean isSetPraise) {
//        ivPraise.setImageDrawable(mIsSetPraise ? getResources().getDrawable(R.drawable.video_praise) : getResources().getDrawable(R.drawable.video_not_praise));
        //点赞
        if (isSetPraise) {
            praiseAnimUtil.setPraise();
        } else {
            //取消赞
            praiseAnimUtil.setUnPraise();
        }
        mIsSetPraise = !mIsSetPraise;
    }

}
