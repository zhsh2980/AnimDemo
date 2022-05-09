//package anim.bro.com.practice.tuibida.ui;
//
//import android.content.Intent;
//import android.graphics.Color;
//import android.graphics.Typeface;
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.view.KeyEvent;
//import android.view.LayoutInflater;
//import android.view.MotionEvent;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.fragment.app.Fragment;
//import androidx.viewpager.widget.ViewPager;
//
//import com.gome.core.GBuildConfig;
//import com.gome.ecmall.business.bridge.product.ProductBridge;
//import com.gome.ecmall.business.bridge.search.constant.SearchSchemeConstant;
//import com.gome.ecmall.business.customerservice.CustomerServiceHelper;
//import com.gome.ecmall.business.customerservice.bean.request.VideoGuideServiceRequest;
//import com.gome.ecmall.business.search.service.SearchServer;
//import com.gome.ecmall.business.search.utils.SearchConstant;
//import com.gome.ecmall.search.R;
//import com.gome.ecmall.search.base.SearchBaseActivity;
//import com.gome.ecmall.search.listener.OnMultipleContentInterface;
//import com.gome.ecmall.search.ui.adapter.SearchLayoutAdapter;
//import com.gome.ecmall.search.ui.adapter.SearchMultipleViewAdapter;
//import com.gome.ecmall.search.ui.fragment.ProductSearchListFragment;
//import com.gome.ecmall.search.ui.fragment.SearchGameListFragment;
//import com.gome.ecmall.search.ui.fragment.SearchMultipleListFragment;
//import com.gome.ecmall.search.ui.fragment.SearchUserListFragment;
//import com.gome.ecmall.search.ui.holder.SearchLayoutHolder;
//import com.gome.ecmall.search.ui.model.SearchModel;
//import com.gome.ecmall.search.ui.presenter.SearchMultiplePresenter;
//import com.gome.ecmall.search.ui.view.SearchMultipleView;
//import com.gome.ecmall.search.widgets.array.holder.BaseArrayHolder;
//import com.gome.mobile.frame.router.annotation.IActivity;
//import com.gome.mobile.widget.view.statusbar.PlusStatusBarUtil;
//import com.google.android.material.appbar.AppBarLayout;
//import com.google.android.material.tabs.TabLayout;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * 搜索综合入口 Activity
// * @author zhaofusen
// */
//@IActivity(SearchSchemeConstant.SEARCH_MULTIPLE_ENTRY_ACTIVITY)
//public class SearchMultipleEntryActivity extends SearchBaseActivity<SearchMultipleView, SearchMultiplePresenter> implements OnMultipleContentInterface {
//	/**
//	 * tab切换对应相应
//	 */
//	public static final int SEARCHTAB_CONTENT = 100002; //内容
//	public static final int SEARCHTAB_BUY = 100003;//商品
//	public static final int SEARCHTAB_USER = 100004;//用户
//	public static final int SEARCHTAB_GAME = 100005;//赛事
//
//	private ProductSearchListFragment mSearchListFragment;
//	private SearchMultipleListFragment mMultipleListFragment;
//	private SearchUserListFragment mSearchUserListFragment;
//	private SearchGameListFragment mSearchGameListFragment;
//	private List<String> mTabList;
//	private SearchLayoutHolder mSearchHolder;
//	private ViewPager mViewPager;
//	private AppBarLayout mAppBarLayout;
//	private View mTopRightView;
//	/**
//	 * APP Bar 是否是折叠状态
//	 */
//	private boolean isAppBarFold = false;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.psearch_activity_multiple_entry_view);
//		initView();
//		initSearchView();
//		initAppBarLayoutListener();
//	}
//
//	private void initView() {
//		mSearchHolder = new SearchLayoutHolder(findViewById(R.id.home_top_searchlayout_layout), inputParam, null);
//		if (mSearchHolder != null) {
//			mSearchHolder.setContext(this);
//			mSearchHolder.setData(!TextUtils.isEmpty(keyword) ? keyword : "");
//			mSearchHolder.setOnDeleteListener(new SearchLayoutAdapter.OnDeleteListener() {
//				@Override
//				public void onDeleteListener(BaseArrayHolder<SearchModel> holder, boolean isOnlyOneAndFrist) {
//					if (isOnlyOneAndFrist) {
//						inputParam.isFromSearchList = true;
//						inputParam.keyword = "";
//						SearchServer.jumpNewSearchActivity(SearchMultipleEntryActivity.this, inputParam, SearchConstant.REQUEST_JUMP_INPUT_PAGE_CODE);
//					}
//				}
//			});
//		}
//		TabLayout tabLayout = findViewById(R.id.psearch_tab_layout);
//		mAppBarLayout = findViewById(R.id.psearch_multiple_entry_appbar_layout);
//		mViewPager = findViewById(R.id.psearch_view_pager);
//		postRun(tabLayout);
//	}
//
//	private void postRun(final TabLayout tabLayout) {
//		mViewPager.postDelayed(new Runnable() {
//			@Override
//			public void run() {
//				if(GBuildConfig.getInstance().mini){
//					List<Fragment> mFragments = new ArrayList<>();
//					Bundle mBundle = new Bundle();
//					mBundle.putParcelable(SearchConstant.REQUEST_PARAM_KEY, getIntent());
//					//从综合搜索页面创建特此标识
//					mBundle.putBoolean(SearchConstant.SEARCH_MULTIPLE_CONTENT_KEY, true);
//					mMultipleListFragment =  SearchMultipleListFragment.newInstance(mBundle);
//					mMultipleListFragment.setOnMultipleContentInterface(SearchMultipleEntryActivity.this);
//					mSearchListFragment =  ProductSearchListFragment.newInstance(mBundle);
//					mSearchListFragment.setOnMultipleContentInterface(SearchMultipleEntryActivity.this);
//					mSearchGameListFragment  = SearchGameListFragment.newInstance(mBundle);
//					mSearchGameListFragment.setOnMultipleContentInterface(SearchMultipleEntryActivity.this);
//					mFragments.add(mMultipleListFragment);
//					mFragments.add(mSearchGameListFragment);
//					mFragments.add(mSearchListFragment);
//					mTabList = new ArrayList<>();
//					mTabList.add("内容");
//					mTabList.add("赛事");
//					mTabList.add("商品");
//					SearchMultipleViewAdapter searchMultipleAdapter = new SearchMultipleViewAdapter(getSupportFragmentManager(), mTabList, mFragments);
//					mViewPager.setAdapter(searchMultipleAdapter);
//
//					setTabLayout(tabLayout);
//				}else{
//					List<Fragment> mFragments = new ArrayList<>();
//					Bundle mBundle = new Bundle();
//					mBundle.putParcelable(SearchConstant.REQUEST_PARAM_KEY, getIntent());
//					//从综合搜索页面创建特此标识
//					mBundle.putBoolean(SearchConstant.SEARCH_MULTIPLE_CONTENT_KEY, true);
//					mMultipleListFragment =  SearchMultipleListFragment.newInstance(mBundle);
//					mMultipleListFragment.setOnMultipleContentInterface(SearchMultipleEntryActivity.this);
//					mSearchListFragment =  ProductSearchListFragment.newInstance(mBundle);
//					mSearchListFragment.setOnMultipleContentInterface(SearchMultipleEntryActivity.this);
//					mSearchUserListFragment  = SearchUserListFragment.newInstance(mBundle);
//					mSearchUserListFragment.setOnMultipleContentInterface(SearchMultipleEntryActivity.this);
//					mSearchGameListFragment  = SearchGameListFragment.newInstance(mBundle);
//					mSearchGameListFragment.setOnMultipleContentInterface(SearchMultipleEntryActivity.this);
//					mFragments.add(mMultipleListFragment);
//					mFragments.add(mSearchUserListFragment);
//					mFragments.add(mSearchGameListFragment);
//					mFragments.add(mSearchListFragment);
//					mTabList = new ArrayList<>();
//					mTabList.add("内容");
//					mTabList.add("用户");
//					mTabList.add("赛事");
//					mTabList.add("商品");
//					SearchMultipleViewAdapter searchMultipleAdapter = new SearchMultipleViewAdapter(getSupportFragmentManager(), mTabList, mFragments);
//					mViewPager.setAdapter(searchMultipleAdapter);
//
//					setTabLayout(tabLayout);
//				}
//
//			}
//		}, 10);
//	}
//
//	/**
//	 * 初始化APPBarLayout监听
//	 */
//	private void initAppBarLayoutListener() {
//		if (mAppBarLayout != null) {
//			mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
//				@Override
//				public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//					if (verticalOffset == 0) {
//						isAppBarFold = false;
//					} else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
//						//此时为折叠状态
//						isAppBarFold = true;
//					} else {
//						//折叠中间状态
//						isAppBarFold = false;
//					}
//				}
//			});
//		}
//	}
//
//	/**
//	 * 设置Tab Layout并初始化数据
//	 * @param tabLayout
//	 */
//	private void setTabLayout(TabLayout tabLayout) {
//		if(GBuildConfig.getInstance().mini){
//			int selectId = 2;
//			int searchTab = 0;
//			if(inputParam != null){
//				searchTab = inputParam.searchTab;
//			}
//			if(searchTab!=0){ //说明切换选项卡后再次搜索
//				if(searchTab==SEARCHTAB_BUY){
//					selectId = 2;
//				}else if(searchTab==SEARCHTAB_CONTENT){
//					selectId = 0;
//				}else if(searchTab==SEARCHTAB_GAME){
//					selectId = 1;
//				}
//			}else{ //直接从乐或者购点击搜索进来的
//				//从乐页面进入的标识，如果是从购页面则选中商品标签
//				int isMultipleSearch = ProductBridge.FLAG_SEARCH_BUY_SEARCH;
//				if(inputParam != null){
//					isMultipleSearch = inputParam.flag;
//				}
//				if(isMultipleSearch==ProductBridge.FLAG_SEARCH_BUY_SEARCH){
//					selectId = 2;
//				}else if(isMultipleSearch==ProductBridge.FLAG_MULTIPLE_CONTENT_SEARCH){
//					selectId = 0;
//				}else{
//					selectId = 2;
//				}
//			}
//			firstupdateParamSearchTab(selectId);
//			mViewPager.setOffscreenPageLimit(mTabList.size());
//			tabLayout.setupWithViewPager(mViewPager);
//			tabLayout.getTabAt(selectId).select();//设置选中的ID
//			tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//				@Override
//				public void onTabSelected(TabLayout.Tab tab) {
//					int tabPos = tab.getPosition();
//					updateViewStatus(tabPos);
//					updateTabTextView(tab, true);
//					updateParamSearchTab(tab.getText().toString().trim());
//				}
//
//				@Override
//				public void onTabUnselected(TabLayout.Tab tab) {
//					updateTabTextView(tab, false);
//				}
//
//				@Override
//				public void onTabReselected(TabLayout.Tab tab) {
//
//				}
//			});
//			for (int i = 0; i < tabLayout.getTabCount(); i++) {
//				TabLayout.Tab tab = tabLayout.getTabAt(i);
//				if (tab != null) {
//					tab.setCustomView(getTabView(i));
//				}
//			}
//			//刷新View状态
//			updateViewStatus(selectId);
//			updateTabTextView(tabLayout.getTabAt(tabLayout.getSelectedTabPosition()), true);
//		}else{
//			int selectId = 3;
//			int searchTab = 0;
//			if(inputParam != null){
//				searchTab = inputParam.searchTab;
//			}
//			if(searchTab!=0){ //说明切换选项卡后再次搜索
//				if(searchTab==SEARCHTAB_BUY){
//					selectId = 3;
//				}else if(searchTab==SEARCHTAB_CONTENT){
//					selectId = 0;
//				}else if(searchTab==SEARCHTAB_USER){
//					selectId = 1;
//				}else if(searchTab==SEARCHTAB_GAME){
//					selectId = 2;
//				}
//			}else{ //直接从乐或者购点击搜索进来的
//				//从乐页面进入的标识，如果是从购页面则选中商品标签
//				int isMultipleSearch = ProductBridge.FLAG_SEARCH_BUY_SEARCH;
//				if(inputParam != null){
//					isMultipleSearch = inputParam.flag;
//				}
//				if(isMultipleSearch==ProductBridge.FLAG_SEARCH_BUY_SEARCH){
//					selectId = 3;
//				}else if(isMultipleSearch==ProductBridge.FLAG_MULTIPLE_CONTENT_SEARCH){
//					selectId = 0;
//				}else{
//					selectId = 3;
//				}
//			}
//			firstupdateParamSearchTab(selectId);
//			mViewPager.setOffscreenPageLimit(mTabList.size());
//			tabLayout.setupWithViewPager(mViewPager);
//			tabLayout.getTabAt(selectId).select();//设置选中的ID
//			tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//				@Override
//				public void onTabSelected(TabLayout.Tab tab) {
//					int tabPos = tab.getPosition();
//					updateViewStatus(tabPos);
//					updateTabTextView(tab, true);
//					updateParamSearchTab(tab.getText().toString().trim());
//				}
//
//				@Override
//				public void onTabUnselected(TabLayout.Tab tab) {
//					updateTabTextView(tab, false);
//				}
//
//				@Override
//				public void onTabReselected(TabLayout.Tab tab) {
//
//				}
//			});
//			for (int i = 0; i < tabLayout.getTabCount(); i++) {
//				TabLayout.Tab tab = tabLayout.getTabAt(i);
//				if (tab != null) {
//					tab.setCustomView(getTabView(i));
//				}
//			}
//			//刷新View状态
//			updateViewStatus(selectId);
//			updateTabTextView(tabLayout.getTabAt(tabLayout.getSelectedTabPosition()), true);
//		}
//
//	}
//
//	/**
//	 * @param selectId
//	 */
//	private void firstupdateParamSearchTab(int selectId) {
//		if(GBuildConfig.getInstance().mini){
//			if (selectId==0) {
//				inputParam.searchTab = SEARCHTAB_CONTENT;
//			}
//			if (selectId==1) {
//				inputParam.searchTab = SEARCHTAB_GAME;
//			}
//			if (selectId==2) {
//				inputParam.searchTab = SEARCHTAB_BUY;
//			}
//		}else{
//			if (selectId==0) {
//				inputParam.searchTab = SEARCHTAB_CONTENT;
//			}
//			if (selectId==1) {
//				inputParam.searchTab = SEARCHTAB_USER;
//			}
//			if (selectId==2) {
//				inputParam.searchTab = SEARCHTAB_GAME;
//			}
//			if (selectId==3) {
//				inputParam.searchTab = SEARCHTAB_BUY;
//			}
//		}
//
//	}
//
//	/**
//	 * @param tabName
//	 */
//	private void updateParamSearchTab(String tabName) {
//		if(GBuildConfig.getInstance().mini){
//			if ("内容".equals(tabName)) {
//				inputParam.searchTab = SEARCHTAB_CONTENT;
//			}
//			if ("商品".equals(tabName)) {
//				inputParam.searchTab = SEARCHTAB_BUY;
//			}
//			if ("赛事".equals(tabName)) {
//				inputParam.searchTab = SEARCHTAB_GAME;
//			}
//		}else{
//			if ("内容".equals(tabName)) {
//				inputParam.searchTab = SEARCHTAB_CONTENT;
//			}
//			if ("商品".equals(tabName)) {
//				inputParam.searchTab = SEARCHTAB_BUY;
//			}
//			if ("用户".equals(tabName)) {
//				inputParam.searchTab = SEARCHTAB_USER;
//			}
//			if ("赛事".equals(tabName)) {
//				inputParam.searchTab = SEARCHTAB_GAME;
//			}
//		}
//
//	}
//
//	private void updateViewStatus(int tabPos) {
//		//TODO 暂时隐藏，后需要需要打开
//		if (mTopRightView != null) {
//			mTopRightView.setVisibility(tabPos == 0||tabPos == 1||tabPos == 2  ? View.GONE : View.VISIBLE);
//		}
//		//切换的时候判断值是否改变
//		if(GBuildConfig.getInstance().mini){
//			if (tabPos == 0 && mMultipleListFragment != null && mSearchHolder != null) {
//				mMultipleListFragment.changeKeywordRequest(mSearchHolder.getSearchString());
//			}
//			if (tabPos == 1 && mSearchGameListFragment != null&& mSearchHolder != null) {
//				mSearchGameListFragment.changeKeywordRequest(mSearchHolder.getSearchString());
//			}
//			if (tabPos == 2 && mSearchListFragment != null) {
//				mSearchListFragment.onSearchWorld(true);
//			}
//		}else{
//			if (tabPos == 0 && mMultipleListFragment != null && mSearchHolder != null) {
//				mMultipleListFragment.changeKeywordRequest(mSearchHolder.getSearchString());
//			}
//			if (tabPos == 1 && mSearchUserListFragment != null&& mSearchHolder != null) {
//				mSearchUserListFragment.changeKeywordRequest(mSearchHolder.getSearchString());
//			}
//			if (tabPos == 2 && mSearchGameListFragment != null&& mSearchHolder != null) {
//				mSearchGameListFragment.changeKeywordRequest(mSearchHolder.getSearchString());
//			}
//			if (tabPos == 3 && mSearchListFragment != null) {
//				mSearchListFragment.onSearchWorld(true);
//			}
//		}
//
//
//	}
//
//	private void initSearchView() {
//		findViewById(R.id.ll_common_title_btn_back).setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				finish();
//			}
//		});
//		boolean isMiniBangBang = GBuildConfig.getInstance().isMini() || GBuildConfig.getInstance().isAssist();
//		//综合搜索里面，单双列切换按钮暂时不展示
//		mTopRightView = findViewById(R.id.ll_common_title_btn_switch);
//		//TODO 暂时需要隐藏
//		mTopRightView.setVisibility(View.GONE);
//		ImageView guideTopImg = findViewById(R.id.search_list_top_guide_img);
//		guideTopImg.setVisibility(isMiniBangBang ? View.GONE : View.VISIBLE);
//		guideTopImg.setOnClickListener(isMiniBangBang ? null : new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				//点击顶部导购跳转
//				VideoGuideServiceRequest videoGuideRequest = new VideoGuideServiceRequest();
//				if (requestParam != null) {
//					videoGuideRequest.storeid = requestParam.shopId;
//				}
//				videoGuideRequest.entry =
//						com.gome.ecmall.business.customerservice.constant.Constant.CUSTOMER_SERVICE_ENTRY_VIDEOGUIDE_ALL;//全程导购
//				CustomerServiceHelper.getVideoGuideService(SearchMultipleEntryActivity.this, videoGuideRequest);
//			}
//		});
//	}
//	@NonNull
//	@Override
//	public SearchMultiplePresenter createPresenter() {
//		return new SearchMultiplePresenter(this);
//	}
//
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		super.onActivityResult(requestCode, resultCode, data);
//		if (mSearchListFragment != null) {
//			mSearchListFragment.onActivityResult(requestCode, resultCode, data);
//		}
//	}
//
//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		return mSearchListFragment != null && mSearchListFragment.onKeyDown(keyCode, super.onKeyDown(keyCode, event));
//	}
//
//	@Override
//	public boolean dispatchTouchEvent(MotionEvent ev) {
//		return mSearchListFragment != null && mSearchListFragment.dispatchTouchEvent(ev, super.dispatchTouchEvent(ev));
//	}
//
//	@Override
//	protected void onDestroy() {
//		super.onDestroy();
//		if (mSearchHolder != null) {
//			mSearchHolder.onDestory();
//		}
//	}
//
//	@Override
//	public SearchLayoutHolder getMultipleSearchLayoutHolder() {
//		return mSearchHolder;
//	}
//
//	@Override
//	public View getTopSwitchView() {
//		return mTopRightView;
//	}
//
//	private View getTabView(int currentPosition) {
//		View view = LayoutInflater.from(this).inflate(R.layout.psearch_multiple_tab_layout, null);
//		TextView textView = (TextView) view.findViewById(R.id.tab_item_tx);
//		textView.setText(mTabList.get(currentPosition));
//		return view;
//	}
//
//	private void updateTabTextView(TabLayout.Tab tab, boolean isSelect) {
//		if (isSelect) {
//			//选中加粗
//			TextView tabSelect = (TextView) tab.getCustomView().findViewById(R.id.tab_item_tx);
//			ImageView tabBg = tab.getCustomView().findViewById(R.id.iv_tab_bg);
//			tabBg.setVisibility(View.VISIBLE);
//			tabSelect.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
//			tabSelect.setTextSize(15f);
//			tabSelect.setTextColor(Color.parseColor("#262C32"));
//			tabSelect.setText(tab.getText());
//		} else {
//			TextView tabUnSelect = (TextView) tab.getCustomView().findViewById(R.id.tab_item_tx);
//			ImageView tabBg = tab.getCustomView().findViewById(R.id.iv_tab_bg);
//			tabBg.setVisibility(View.GONE);
//			tabUnSelect.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
//			tabUnSelect.setTextColor(Color.parseColor("#999999"));
//			tabUnSelect.setTextSize(14f);
//			tabUnSelect.setText(tab.getText());
//		}
//	}
//
//	/**
//	 * 更新  AppbarLayout Status
//	 * @param isExpanded
//	 */
//	@Override
//	public void updateAppbarLayoutStatus(boolean isExpanded){
//		if (mAppBarLayout != null) {
//			mAppBarLayout.setExpanded(isExpanded,true);
//		}
//	}
//
//	/**
//	 * AppBarLayout 是否展开
//	 * @return
//	 */
//	@Override
//	public boolean isAppBarLayoutFold(){
//		return isAppBarFold;
//	}
//}
