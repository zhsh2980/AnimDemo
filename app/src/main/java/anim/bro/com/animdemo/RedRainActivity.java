package anim.bro.com.animdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import com.plattysoft.leonids.ParticleSystem;
import com.plattysoft.leonids.modifiers.AlphaModifier;
import com.plattysoft.leonids.modifiers.ScaleModifier;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RedRainActivity extends AppCompatActivity {

    @BindView(R.id.btn_red_anim)
    Button mBtnRedAnim;
    @BindView(R.id.iv_progress)
    ImageView mIvProgress;
    @BindView(R.id.view_anim)
    View mViewAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red_rain);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_red_anim)
    public void onViewClicked() {
        doRainAnim();
    }

    private void doRainAnim2() {

        ParticleSystem ps = new ParticleSystem(this, 10, R.drawable.red_new, 1000);
        ps.setScaleRange(0.7f, 1.3f);
        ps.setSpeedByComponentsRange(-0.025f, 0.025f, -0.06f, -0.08f);
//        ps.setSpeedModuleAndAngleRange(0.07f, 0.16f, 0, 180);
        ps.setRotationSpeedRange(90, 180);
        ps.setAcceleration(0.00013f, 90);
        ps.setFadeOut(200, new AccelerateInterpolator());
        ps.emit(mIvProgress, 100, 2000);
    }

    int animTotal = 2000;
    int animSingle = 800;

    private void doRainAnim() {

        new ParticleSystem(this, 100, R.drawable.star, animSingle)
                .setSpeedByComponentsRange(-0.025f, 0.025f, -0.06f, -0.08f)
                .setAcceleration(0.00001f, 30)
//                .setInitialRotationRange(-30, 30)
                .setSpeedModuleAndAngleRange(0.05f, 0.1f, -165, -15)
                .addModifier(new AlphaModifier(255, 0, animSingle * 6 / 10, animSingle))
                .addModifier(new ScaleModifier(0.2f, 0.5f, 0, animSingle))
                .emit(mViewAnim, 20, animTotal);
//                .oneShot(mIvRed, 2);

        new ParticleSystem(this, 25, R.drawable.red_new, animSingle)
                .setSpeedByComponentsRange(-0.025f, 0.025f, -0.06f, -0.08f)
                .setAcceleration(0.00001f, 30)
                .setInitialRotationRange(-30, -10)
                .setSpeedModuleAndAngleRange(0.08f, 0.15f, -120, -110)
                .addModifier(new AlphaModifier(255, 0, animSingle * 6 / 10, animSingle))
                .addModifier(new ScaleModifier(0.5f, 1.0f, 0, animSingle))
                .emit(mViewAnim, 5, animTotal);

        new ParticleSystem(this, 25, R.drawable.red_new, animSingle)
                .setSpeedByComponentsRange(-0.025f, 0.025f, -0.06f, -0.08f)
                .setAcceleration(0.00001f, 30)
                .setInitialRotationRange(10, 30)
                .setSpeedModuleAndAngleRange(0.08f, 0.15f, -80, -60)
                .addModifier(new AlphaModifier(255, 0, animSingle * 6 / 10, animSingle))
                .addModifier(new ScaleModifier(0.5f, 1.0f, 0, animSingle))
                .emit(mViewAnim, 5, animTotal);


//        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mIvProgress.getLayoutParams();
//        params.bottomMargin = 0;
//        mIvProgress.setLayoutParams(params);

    }
}
