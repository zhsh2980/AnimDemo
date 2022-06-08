package anim.bro.com.practice.rv;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import anim.bro.com.practice.R;
import anim.bro.com.practice.tuibida.multidrag.SpacesItemDecoration;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RVActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.tv_search)
    TextView tv_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_r_v);
        ButterKnife.bind(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new SpacesItemDecoration(12));
        recyclerView.setAdapter(new SpaceRelaAdapter());

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE){
                    Log.i("bro", "newState: SCROLL_STATE_IDLE   " + newState);
                }else if (newState == RecyclerView.SCROLL_STATE_DRAGGING){
                    Log.i("bro", "newState: SCROLL_STATE_DRAGGING  " + newState);
                }else if (newState == RecyclerView.SCROLL_STATE_SETTLING){
                    Log.i("bro", "newState: SCROLL_STATE_SETTLING  " + newState);
                }
//                Log.i("bro", "newState: " + newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.i("bro", "dx: " + dx + "---dy: " + dy);
                if (dy > 0) {
                    tv_search.setVisibility(View.VISIBLE);
                } else {
                    tv_search.setVisibility(View.GONE);
                }
            }
        });


    }
}