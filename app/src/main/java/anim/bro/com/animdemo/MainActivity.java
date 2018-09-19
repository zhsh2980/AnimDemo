package anim.bro.com.animdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import anim.bro.com.animdemo.util.RedPacketAnimView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        redpacketView.setBackgroundResource(R.drawable.red_big_middle);
        redpacketView.setEndView(ivRight);
        redpacketView.setGuideView(ivGuideView, ivGuideMiddleView, tvPriceBottomView);
    }

    @OnClick({R.id.btn_guide_first, R.id.btn_guide, R.id.btn_normal})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_guide_first:
                redpacketView.setStartViewPosition();
                redpacketView.startGuideFirstAnim();
                break;
            case R.id.btn_guide:
                redpacketView.setStartViewPosition();
                redpacketView.startGuideLoginAnim();

                break;
            case R.id.btn_normal:
                redpacketView.setStartViewPosition();
                redpacketView.startNormalAnim();

                break;
        }
    }
}
