package anim.bro.com.practice.tuibida.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import anim.bro.com.practice.R;
import anim.bro.com.practice.tuibida.utils.HiddenAnimUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HideViewActivity extends AppCompatActivity {

    @BindView(R.id.iv_arrow_expand)
    ImageView mIvArrowExpand;
    @BindView(R.id.rela_root_expand)
    RelativeLayout mRelaRootExpand;

    boolean isExpand = false;

    @BindView(R.id.tv_content)
    TextView mTvContent;
    @BindView(R.id.rela_content_2)
    RelativeLayout mRelaContent2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hide_view);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.iv_arrow_expand)
    public void onViewClicked() {
        if (isExpand) {
            mTvContent.setVisibility(View.VISIBLE);
            mRelaContent2.setVisibility(View.GONE);
            HiddenAnimUtils.newInstance(HideViewActivity.this, mRelaRootExpand, mIvArrowExpand, 33, 100, false).toggle();
            isExpand = !isExpand;
        } else {
            mTvContent.setVisibility(View.GONE);
            mRelaContent2.setVisibility(View.VISIBLE);
            HiddenAnimUtils.newInstance(HideViewActivity.this, mRelaRootExpand, mIvArrowExpand, 77, 200, true).toggle();
            isExpand = !isExpand;
        }
    }
}
