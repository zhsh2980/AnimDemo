package anim.bro.com.animdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.blankj.utilcode.util.ActivityUtils;

import anim.bro.com.animdemo.bean.TestBean;
import anim.bro.com.animdemo.bean.TestBean2;
import anim.bro.com.animdemo.bean.TestBean3;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_red_fly, R.id.btn_praise, R.id.btn_red_new})
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
        }
    }

    @OnClick(R.id.btn_null)
    public void onViewClicked() {
        nullClick();
    }

    private void nullClick() {

        TestBean3 testBean3 = new TestBean3();
        testBean3.setStr("test3");

        TestBean2 testBean2 = new TestBean2();
        testBean2.setStr("test2");
        testBean2.setGetTestBean3(testBean3);

        TestBean testBean = new TestBean();
        testBean.setStr("test1");
        testBean.setGetTestBean2(testBean2);

        String str = testBean.getGetTestBean2().getGetTestBean3().getStr();
         Log.i(TAG , str);


    }
    
}
