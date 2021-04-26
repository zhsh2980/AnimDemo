package anim.bro.com.animdemo.rv;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import anim.bro.com.animdemo.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RVActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_r_v);
        ButterKnife.bind(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this , DividerItemDecoration.HORIZONTAL));
        recyclerView.setAdapter(new RvAdapter());

    }
}