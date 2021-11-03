package anim.bro.com.practice.banner;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import anim.bro.com.practice.R;
import anim.bro.com.practice.banner.bannerview.BannerViewPager;
import anim.bro.com.practice.banner.bannerview.constants.PageStyle;
import anim.bro.com.practice.util.GetJsonDataUtil;

public class BannerActivity extends AppCompatActivity {

    private BannerViewPager<BannerBean.PitContent> mViewPager;
    private BannerVerticalAdapter adapter;
    private TextView tvBannerIndicator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        initView();
    }

    private void initView() {
        mViewPager = findViewById(R.id.banner_view);
        tvBannerIndicator = findViewById(R.id.tv_banner_indicator);
//        mViewPager.setLifecycleRegistry(getLifecycle());
        adapter = new BannerVerticalAdapter(SizeUtils.dp2px(8));
        mViewPager.setAdapter(adapter);
        mViewPager.setOnPageClickListener(new BannerViewPager.OnPageClickListener() {
            @Override
            public void onPageClick(View clickedView, int position) {
                itemClick(position);
            }
        });

//        int screenWidth = ScreenUtils.getScreenWidth();
//        int i = ScreenUtils.getScreenWidth() * 141 / 375;

        mViewPager.setInterval(3000);
        mViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                int size = mViewPager.getData().size();
                if (size == 1) {
                    tvBannerIndicator.setVisibility(View.GONE);
                } else {
                    tvBannerIndicator.setVisibility(View.VISIBLE);
                    tvBannerIndicator.setText((position + 1) + "/" + size);
                }
                Log.i("bro", "onPageSelected: " + (position + 1) + "/" + size);
            }
        });
        mViewPager.setAutoPlay(true).setCanLoop(true)
                .setPageMargin(getResources().getDimensionPixelOffset(R.dimen.dp_15))
                .setScrollDuration(180)
                .setRevealWidth(SizeUtils.dp2px(0), SizeUtils.dp2px(141))
                .setPageStyle(PageStyle.MULTI_PAGE_SCALE)
                .setPageMargin(SizeUtils.dp2px(12))
                .setIndicatorVisibility(View.GONE)
//                .setPageTransformer(new ScaleInTransformer())
//                .setIndicatorView(getIndicatorView())
//                .setIndicatorGravity(IndicatorGravity.END)
//                .setIndicatorMargin(0, 0, 0, SizeUtils.dp2px(12))
                .create();


        mViewPager.refreshData(getPicList());


    }


    private FigureIndicatorView getIndicatorView() {
        FigureIndicatorView indicatorView = new FigureIndicatorView(this);
        indicatorView.setRadius(getResources().getDimensionPixelOffset(R.dimen.dp_18));
        indicatorView.setTextSize(getResources().getDimensionPixelSize(R.dimen.sp_13));
        indicatorView.setBackgroundColor(Color.parseColor("#aa118EEA"));
        return indicatorView;
    }

    private List<BannerBean.PitContent> getPicList() {
        List<Integer> drawList = new ArrayList<>();
//        drawList.add(R.drawable.anim_box_1);
//        drawList.add(R.drawable.anim_box_10);
//        drawList.add(R.drawable.anim_box_20);
//        drawList.add(R.drawable.anim_box_30);
//        drawList.add(R.drawable.anim_box_40);
//        drawList.add(R.drawable.anim_box_50);

        String beanJson = new GetJsonDataUtil().getJson(this, "banner.json");//获取assets目录下的json文件数据
        Gson gson = new Gson();
        BannerBean bannerBean = gson.fromJson(beanJson, BannerBean.class);
        List<BannerBean.PitContent> pitContent = bannerBean.getPitContent();

        return pitContent;
    }

    private void itemClick(int position) {
        if (position != mViewPager.getCurrentItem()) {
            mViewPager.setCurrentItem(position, true);
        }
        ToastUtils.showShort("position:$position");
    }


}
