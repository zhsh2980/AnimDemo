package anim.bro.com.practice.tuibida.ui;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.List;

import anim.bro.com.practice.R;
import anim.bro.com.practice.tuibida.adapter.RecycleViewAdapter;
import anim.bro.com.practice.tuibida.utils.BlurUtil;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CoordinatorActivity2 extends AppCompatActivity {

    @BindView(R.id.collapsingToolbar)
    CollapsingToolbarLayout mCollapsingToolbar;
    @BindView(R.id.appbarlayout)
    AppBarLayout mAppbarlayout;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private List<String> dataList = new ArrayList<String>();
    private RecycleViewAdapter recycleViewAdapter;
    boolean isAppBarFold;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_2);
        ButterKnife.bind(this);
        initData(1);
        initView();
    }

    private void initData(int page) {
        for (int i = 0; i < 50; i++) {
            dataList.add("第" + page + "页第" + (i + 1) + "条数据");
        }
    }

    private void initView() {
        initAppBarLayoutListener();
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolabar替换actionBar,需在清单文件中设置style = noActionBar
//        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        //设置布局
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        //设置动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //设置适配器
        recycleViewAdapter = new RecycleViewAdapter(this, dataList);
        recyclerView.setAdapter(recycleViewAdapter);
//        Glide.with(this).asBitmap().load(R.drawable.zhiwu).into(new SimpleTarget<Bitmap>() {
//            @Override
//            public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
//                Bitmap doBlurBitmap = BlurUtil.doBlur(bitmap, 15, 10);
//                mCollapsingToolbar.setContentScrim(new BitmapDrawable(doBlurBitmap));
//            }
//        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.i("bro", "newState: " + newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.i("bro", "dx: " + dx + "---dy: " + dy);
            }
        });


//        ImageLoaderCompact.getInstance().asyncFetchBitmapByUrl(postImage.url, new OnFetchBitmapListener() {
//            @Override
//            public void onFetchBitmapSuccess(String s, Bitmap bitmap) {
//
//                Bitmap doBlurBitmap = BlurUtil.doBlur(bitmap, 15, 10);
//                blurBitmapList[position] = doBlurBitmap;
//
//                if (position == 0 && collapsing_toolbar != null) {
//                    collapsing_toolbar.setContentScrim(new BitmapDrawable(doBlurBitmap));
//                }
//            }
//
//            @Override
//            public void onFetchBitmapFailure(String s) {
//
//            }
//        });
    }

    //	/**
//	 * 初始化APPBarLayout监听
//	 */
    private void initAppBarLayoutListener() {
        mAppbarlayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if (verticalOffset == 0) {
                    Log.i("bro", "不折叠 1");
						isAppBarFold = false;
                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                    //此时为折叠状态
                    Log.i("bro", "折叠");
						isAppBarFold = true;
                } else {
                    //折叠中间状态
                    Log.i("bro", "不折叠中间状态");
						isAppBarFold = false;
                }
            }
        });

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mAppbarlayout.setExpanded(true, true);
//            }
//        }, 5000);

    }

}
