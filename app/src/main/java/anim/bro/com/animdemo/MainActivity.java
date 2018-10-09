package anim.bro.com.animdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by zhangshan on 2018/10/9 11:27.
 */
public class MainActi extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red_fly);
        ButterKnife.bind(this);
    }
}
