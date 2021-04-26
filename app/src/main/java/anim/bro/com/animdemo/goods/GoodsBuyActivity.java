package anim.bro.com.animdemo.goods;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import anim.bro.com.animdemo.R;
import anim.bro.com.animdemo.goods.adapter.GoodsBuyAdapter;
import anim.bro.com.animdemo.goods.banner.BannerConfig;
import anim.bro.com.animdemo.goods.banner.BannerStyle;
import anim.bro.com.animdemo.goods.banner.HBanner;
import anim.bro.com.animdemo.goods.banner.listener.OnBannerListener;
import anim.bro.com.animdemo.goods.banner.loader.ViewItemBean;
import anim.bro.com.animdemo.goods.banner.transformer.DefaultTransformer;
import anim.bro.com.animdemo.util.UIUtils;
import butterknife.BindView;
import butterknife.ButterKnife;


public class GoodsBuyActivity extends AppCompatActivity {
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.tv_good_buy_now_index)
    TextView tvGoodBuyNowIndex;
    private HBanner banner;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] NEEDED_PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_buy);
        ButterKnife.bind(this);
        banner = findViewById(R.id.banner);
        verifyStoragePermissions(this);
    }

    public void verifyStoragePermissions(Activity activity) {
        try {
            //检测权限
            if (!checkPermissions(NEEDED_PERMISSIONS)) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, NEEDED_PERMISSIONS, REQUEST_EXTERNAL_STORAGE);
            } else {
                init();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() {

        initList();
        initView();

        /**
         * 视频图片混播
         */
        List<ViewItemBean> list = new ArrayList<>();
//        Uri path1 = Uri.parse("https://v-cdn.zjol.com.cn/123468.mp4");
//        Uri path2 = Uri.parse("http://zkteam.cc/1581669892013801.mp4");
        String path2 = "https://v-cdn.zjol.com.cn/276998.mp4";
//        Uri path2 = Uri.parse("https://raw.githubusercontent.com/zhsh2980/AnimDemo/master/video.mp4");
//        Uri path2 = Uri.parse("https://v-cdn.zjol.com.cn/276982.mp4");
//        String imageUrl = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1579170629919&di=fc03a214795a764b4094aba86775fb8f&imgtype=jpg&src=http%3A%2F%2Fimg4.imgtn.bdimg.com%2Fit%2Fu%3D4061015229%2C3374626956%26fm%3D214%26gp%3D0.jpg";
//        list.add(new ViewItemBean(BannerConfig.VIDEO, path1, 15 * 1000));
        list.add(new ViewItemBean(BannerConfig.VIDEO, path2, 15 * 1000));
//        list.add(new ViewItemBean(BannerConfig.IMAGE, imageUrl, 5 * 1000));
//        list.add(new ViewItemBean(VIDEO, path2, 15 * 1000));
        list.add(new ViewItemBean(BannerConfig.IMAGE, R.mipmap.b1, 5 * 1000));
        list.add(new ViewItemBean(BannerConfig.IMAGE, R.mipmap.b2, 5 * 1000));

        banner.setViews(list)
                .setBannerAnimation(DefaultTransformer.class)
                .setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position, boolean isVideo) {
                        ToastUtils.showShort("haha" + isVideo);
                    }
                })
                .setBannerStyle(BannerStyle.CIRCLE_INDICATOR)
                .setCache(true)//可以不用设置，默认为true
                .setOffscreenPageLimit(5)
                .isAutoPlay(false)
                .setViewPagerIsScroll(true)
                .start();
    }


    private List<String> list;
    private GoodsBuyAdapter goodsBuyAdapter;
    private LinearLayoutManager linearLayoutManager;

    private void initList() {
        list = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            list.add(i + "");
        }
    }

    private void initView() {

        linearLayoutManager = new GridLayoutManager(this, 2);
        goodsBuyAdapter = new GoodsBuyAdapter(list);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.addItemDecoration(new AverageGapItemDecoration(UIUtils.dip2px(1), UIUtils.dip2px(1), UIUtils.dip2px(0)));

        recyclerView.setAdapter(goodsBuyAdapter);

        appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                if (-i >= appBarLayout.getTotalScrollRange() - 220) {
                    tvGoodBuyNowIndex.setVisibility(View.VISIBLE);
                    if (isGoodBuyNowBottomShown) {
                        banner.pauseVideo();
                        LogUtils.i("隐藏啦");
                    }
                    isGoodBuyNowBottomShown = false;

//                    ToastUtils.showShort("隐藏啦");
                } else {
                    if (!isGoodBuyNowBottomShown) {
                        tvGoodBuyNowIndex.setVisibility(View.INVISIBLE);
                        banner.restartVideo();
                        LogUtils.i("显示啦");
                    }
//                    ToastUtils.showShort("显示啦");
                    isGoodBuyNowBottomShown = true;
                }
//                LogUtils.i("haha" + i);
//                LogUtils.i("haha" + appBarLayout.getTotalScrollRange());
            }
        });
    }

    boolean isGoodBuyNowBottomShown = false;


    private boolean checkPermissions(String[] neededPermissions) {
        if (neededPermissions == null || neededPermissions.length == 0) {
            return true;
        }
        boolean allGranted = true;
        for (String neededPermission : neededPermissions) {
            allGranted &= ContextCompat.checkSelfPermission(this.getApplicationContext(), neededPermission) == PackageManager.PERMISSION_GRANTED;
        }
        return allGranted;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean allGranted = true;
        for (int results : grantResults) {
            allGranted &= results == PackageManager.PERMISSION_GRANTED;
        }
        if (allGranted) {
            init();
        } else {
            Toast.makeText(this, "Maybe can not cache the video or play the local resource!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        banner.pauseVideo();
    }

    @Override
    protected void onResume() {
        super.onResume();
        banner.restartVideo();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        banner.destroyVideo();
    }

    //
//    @OnClick({R.id.btn_pause, R.id.btn_start})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.btn_pause:
//                banner.pauseVideo();
//                break;
//            case R.id.btn_start:
//                banner.restartVideo();
//                break;
//        }
//    }
}