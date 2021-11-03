package anim.bro.com.practice.focus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import anim.bro.com.practice.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FocusNewActivity extends AppCompatActivity {

    @BindView(R.id.cons_root)
    ConstraintLayout consRoot;
    @BindView(R.id.frame_root)
    FrameLayout frameLayout;

    LayoutInflater inflater;
    View adInfoLayout;
    private FrameLayout frame_root_inner;
    private RelativeLayout ad_info_container;
    private ImageView img_logo;
    private TextView text_title;
    private TextView text_desc;
    private TextView text_btn;

    private List<View> mClickableViews = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focus_new);
        ButterKnife.bind(this);

        setData();

    }

    private void setData() {
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        adInfoLayout = inflater.inflate(R.layout.stream_ad_info_layout, null);
        frame_root_inner = adInfoLayout.findViewById(R.id.frame_root_inner);
        ad_info_container = adInfoLayout.findViewById(R.id.ad_info_container);
        img_logo = adInfoLayout.findViewById(R.id.img_logo);
        text_title = adInfoLayout.findViewById(R.id.text_title);
        text_desc = adInfoLayout.findViewById(R.id.text_desc);
        text_btn = adInfoLayout.findViewById(R.id.text_btn);
        mClickableViews.add(frame_root_inner);
        mClickableViews.add(img_logo);
        mClickableViews.add(text_title);
        mClickableViews.add(text_desc);
        mClickableViews.add(text_btn);
        mClickableViews.add(adInfoLayout);

        frameLayout.removeAllViews();

        ad_info_container.setOnClickListener(onClickListener);
//        for (int i = 0; i < mClickableViews.size(); i++) {
//            mClickableViews.get(i).setOnClickListener(onClickListener);
//        }
        frame_root_inner.setOnClickListener(onFullClickListener);

//        frameLayout.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                ToastUtils.showLong("onFullClickListener setOnTouchListener");
//                return true;
//            }
//        });
        frameLayout.addView(adInfoLayout);

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ToastUtils.showLong("onClickListener");
        }
    };

    View.OnClickListener onFullClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ToastUtils.showLong("onFullClickListener");
        }
    };


}