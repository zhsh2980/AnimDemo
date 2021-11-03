package anim.bro.com.practice.tuibida.ui;

import android.os.Build;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import anim.bro.com.practice.R;
import anim.bro.com.practice.tuibida.utils.StatusBarImmersiveUtils;
import anim.bro.com.practice.tuibida.view.TaskRedGuideView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DialogTransparentActivity extends AppCompatActivity {

    @BindView(R.id.task_guide_view)
    TaskRedGuideView mTaskGuideView;
    @BindView(R.id.view_root)
    FrameLayout mViewRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_transparent);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //设置全屏 避免预加载 启动splashActivity时，状态栏一闪的问题
            StatusBarImmersiveUtils.setFullScreen(this);
            //设置沉浸式的view
            StatusBarImmersiveUtils.immersiveView(mViewRoot);
        }
        initGuideView();


    }

    private void initGuideView() {

        mTaskGuideView.setOnClickListener(new TaskRedGuideView.OnGuideClickListener() {
            @Override
            public void onGuideEndListener() {
                setResult(1002);
                finish();
            }
        });

    }

}
