package anim.bro.com.practice.tuibida.ui;

import androidx.annotation.Nullable;

import com.airbnb.lottie.Cancellable;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.OnCompositionLoadedListener;

import anim.bro.com.practice.R;
import butterknife.BindView;

public class LottieActivity extends BaseActivity {

    @BindView(R.id.animation_view_1)
    LottieAnimationView animationView1;
    @BindView(R.id.animation_view_2)
    LottieAnimationView animationView2;

    @Override
    public int getResourceID() {
        return R.layout.activity_lottie;
    }

    @Override
    public String getTitleName() {
        return "LottieActivity";
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    private void loadUrl(String url) {
        Cancellable compositionCancellable = LottieComposition.Factory.fromJsonString("", new OnCompositionLoadedListener() {
            @Override
            public void onCompositionLoaded(@Nullable LottieComposition composition) {
                setComposition(composition);
            }
        });

        // Cancel to stop asynchronous loading of composition
        // compositionCancellable.cancel();
    }

    private void setComposition(LottieComposition composition) {
        animationView1.setProgress(0);
        animationView1.loop(true);
        animationView1.setComposition(composition);
        animationView1.playAnimation();
    }

}
