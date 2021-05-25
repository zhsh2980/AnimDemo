package anim.bro.com.animdemo;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;

import anim.bro.com.animdemo.focus.FocusNewActivity;
import anim.bro.com.animdemo.goods.GlideActivity;
import anim.bro.com.animdemo.goods.GoodsBuyActivity;
import anim.bro.com.animdemo.goods.GoodsSaleActivity;
import anim.bro.com.animdemo.rv.RVActivity;
import anim.bro.com.animdemo.ui.BlurActivity;
import anim.bro.com.animdemo.ui.CycleBoxActivity;
import anim.bro.com.animdemo.ui.DuoDianActivity;
import anim.bro.com.animdemo.ui.PraiseActivity;
import anim.bro.com.animdemo.ui.RedFlyActivity;
import anim.bro.com.animdemo.ui.RedNewActivity;
import anim.bro.com.animdemo.ui.RedRainActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhangshan on 2018/10/9 11:27.
 */
public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";
    @BindView(R.id.btn_red_fly)
    Button btnRedFly;
    @BindView(R.id.btn_praise)
    Button btnPraise;
    @BindView(R.id.text_num_new_gold)
    TextView textNumNewGold;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_red_fly, R.id.btn_praise
            , R.id.btn_red_new, R.id.btn_box_dialog
            , R.id.btn_blur,R.id.btn_cycle_box
            , R.id.btn_goods_sale,R.id.btn_goods_buy
            , R.id.btn_goods_glide
            ,R.id.btn_duodian,R.id.btn_rv
            ,R.id.btn_focus})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_red_fly:
                ActivityUtils.startActivity(RedFlyActivity.class);
                break;
            case R.id.btn_praise:
                ActivityUtils.startActivity(PraiseActivity.class);
                break;
            case R.id.btn_red_new:
                ActivityUtils.startActivity(RedNewActivity.class);
                break;
            case R.id.btn_box_dialog:
                TreasureOpenDialog dialog = new TreasureOpenDialog(this);
                dialog.show();
                dialog.setData();
                break;
            case R.id.btn_blur:
                ActivityUtils.startActivity(BlurActivity.class);
                break;
            case R.id.btn_cycle_box:
                ActivityUtils.startActivity(CycleBoxActivity.class);
                break;
            case R.id.btn_duodian:
                ActivityUtils.startActivity(DuoDianActivity.class);
                break;
            case R.id.btn_goods_sale:
                ActivityUtils.startActivity(GoodsSaleActivity.class);
                break;
            case R.id.btn_goods_buy:
                ActivityUtils.startActivity(GoodsBuyActivity.class);
                break;
            case R.id.btn_goods_glide:
                ActivityUtils.startActivity(GlideActivity.class);
                break;
                case R.id.btn_rv:
                ActivityUtils.startActivity(RVActivity.class);
                break;
                case R.id.btn_focus:
                ActivityUtils.startActivity(FocusNewActivity.class);
                break;
        }
    }

    @OnClick({R.id.btn_red_rain})
    public void onViewClicked2(View view) {
        switch (view.getId()) {
            case R.id.btn_red_rain:
                ActivityUtils.startActivity(RedRainActivity.class);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("MainActivity", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("MainActivity", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("MainActivity", "pauseVideo");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("MainActivity", "onDestroy");
    }
}
