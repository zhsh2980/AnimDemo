package anim.bro.com.practice.fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import anim.bro.com.practice.R;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RvFragmentActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv_fragment);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.rv_fragment);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this , DividerItemDecoration.HORIZONTAL));
        recyclerView.setAdapter(new RvOuterViewAdapter(this));
    }

    @OnClick({R.id.btn_top_1, R.id.btn_top_2
            , R.id.btn_top_3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_top_1:
                smoothScrolllTo(0);
                break;
            case R.id.btn_top_2:
                smoothScrolllTo(1);
                break;
            case R.id.btn_top_3:
                smoothScrolllTo(2);
                break;
            default:
        }
    }

    private void smoothScrolllTo(int position) {
//        recyclerView.smoothScrollToPosition(position);
        recyclerView.scrollToPosition(position);
    }

}