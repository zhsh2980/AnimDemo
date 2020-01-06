package anim.bro.com.animdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import butterknife.ButterKnife;

/**
 * Created by zhangshan on 2019-05-23 18:06.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null && !TextUtils.isEmpty(getTitleName())) {
            actionBar.setTitle(getTitleName());
        }
        setContentView(getResourceID());
        ButterKnife.bind(this);
        initView();
        initData();
    }

    public abstract int getResourceID();

    public abstract String getTitleName();

    public abstract void initView();

    public abstract void initData();
}
