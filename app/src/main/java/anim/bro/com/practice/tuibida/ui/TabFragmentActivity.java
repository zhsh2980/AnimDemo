package anim.bro.com.practice.tuibida.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import anim.bro.com.practice.R;
import anim.bro.com.practice.tuibida.view.NoScrollViewPager;
import anim.bro.com.practice.tuibida.view.entity.TabEntity;
import butterknife.BindView;
import butterknife.ButterKnife;

import java.util.ArrayList;

public class TabFragmentActivity extends AppCompatActivity {

    @BindView(R.id.view_pager)
    NoScrollViewPager mViewPager;
    @BindView(R.id.tab_layout)
    CommonTabLayout tabLayout;

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"发布", "喜欢", "推广"};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private int[] mIconUnselectIds = {
            R.drawable.tab_home_unselect, R.drawable.tab_speech_unselect,
            R.drawable.tab_contact_unselect, R.drawable.tab_more_unselect};
    private int[] mIconSelectIds = {
            R.drawable.tab_home_select, R.drawable.tab_speech_select,
            R.drawable.tab_contact_select, R.drawable.tab_more_select};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_fragment);
        ButterKnife.bind(this);

        mTabEntities.clear();
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }

        mFragments.clear();
        for (String title : mTitles) {
            mFragments.add(SimpleCardFragment.getInstance("Switch ViewPager " + title));
        }
        mViewPager.setScroll(false);
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
//        mViewPager.setCurrentItem(0);
        tabLayout.setTabData(mTabEntities);

        initView();

    }

    private void initView() {
        mViewPager.setCurrentItem(1);
        tabLayout.setCurrentTab(1);
        tabLayout.notifyDataSetChanged();

        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }


}
