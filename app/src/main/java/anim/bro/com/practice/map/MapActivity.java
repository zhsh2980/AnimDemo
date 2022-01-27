package anim.bro.com.practice.map;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.SizeUtils;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdateFactory;
import com.tencent.tencentmap.mapsdk.maps.MapView;
import com.tencent.tencentmap.mapsdk.maps.TencentMap;
import com.tencent.tencentmap.mapsdk.maps.TencentMapOptions;
import com.tencent.tencentmap.mapsdk.maps.UiSettings;
import com.tencent.tencentmap.mapsdk.maps.model.LatLng;
import com.tencent.tencentmap.mapsdk.maps.model.LatLngBounds;

import java.util.ArrayList;
import java.util.List;

import anim.bro.com.practice.R;
import anim.bro.com.practice.tuibida.adapter.RecycleViewAdapter;

/**
 * Created by zhangshan on 2022/1/26 19:00
 * Description：腾讯地图
 */

public class MapActivity extends AppCompatActivity {
    private final String TAG = "MapActivity";
    private MapView mapView;
    private TencentMap tMap;
    private RelativeLayout rlTitleLayout;
    private TextView etvBack;
    private RecyclerView rcyLogistics;
    private List<String> dataList = new ArrayList<>();
    private RecycleViewAdapter recycleViewAdapter;
    private BottomSheetBehavior<RecyclerView> mBehavior;
    private static final int BOTTOM = 1;
    private static final int MIDDLE = 2;
    private static final int TOP = 3;
    private int POSITION = 2;
    private int BOTTOM_PICK_HEIGHT;
    private int MIDDLE_PICK_HEIGHT;
    private float ratio;
    private boolean isOnce;
//    private TencentMapRelativeLayout mBottomSheetContent;

    private int totalHeight;
    private int dp200;
    private View blank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_tencent);
        initView();
        initMap();

        initData();
    }

    private void initData() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                initMockData();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        recycleViewAdapter.notifyDataSetChanged();
                        rcyLogistics.post(() -> {
                            totalHeight = rcyLogistics.getHeight();
                            BOTTOM_PICK_HEIGHT = (int) (totalHeight * 0.2f);
                            MIDDLE_PICK_HEIGHT = (int) (totalHeight * 0.6f);
                            mBehavior.setPeekHeight(MIDDLE_PICK_HEIGHT);
                            adjustMapRatio(MIDDLE_PICK_HEIGHT);
                        });
                    }
                });
            }
        }, 1000);

    }

    private void initView() {
//        mBottomSheetContent = findViewById(R.id.mBottomSheetContent);
        mapView = (MapView) findViewById(R.id.map);
        tMap = mapView.getMap();
        rlTitleLayout = (RelativeLayout) findViewById(R.id.rl_title_layout);
        etvBack = (TextView) findViewById(R.id.etv_back);
        blank = (View) findViewById(R.id.blank);
        rcyLogistics = (RecyclerView) findViewById(R.id.rcy_logistics);
        //设置布局
        rcyLogistics.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        //设置动画
        rcyLogistics.setItemAnimator(new DefaultItemAnimator());
        //设置适配器
        recycleViewAdapter = new RecycleViewAdapter(this, dataList);
        rcyLogistics.setAdapter(recycleViewAdapter);

        mBehavior = BottomSheetBehavior.from(rcyLogistics);
        mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        mBehavior.setHideable(true);
//        mBottomSheetContent.setBehavior(mBehavior);
        mBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {

            @Override
            public void onStateChanged(@NonNull View view, int state) {

                Log.i(TAG, "onStateChanged state == BottomSheetBehavior: " + getState(state));

                if (state == BottomSheetBehavior.STATE_DRAGGING) {
                    isOnce = true;
                }
                if (isOnce && state == BottomSheetBehavior.STATE_SETTLING) {
                    isOnce = false;
                    if (POSITION == MIDDLE) {
                        if (ratio >= 0.2) {
                            POSITION = TOP;
                        } else if (ratio < -0.2) {
                            POSITION = BOTTOM;
                        }
                    } else if (POSITION == TOP) {
                        if (ratio >= 0.4 && ratio < 0.8) {
                            POSITION = MIDDLE;
                        } else if (ratio < 0.4) {
                            POSITION = BOTTOM;
                        }
                    } else if (POSITION == BOTTOM) {
                        if (ratio > 0.2 && ratio <= 0.5) {
                            POSITION = MIDDLE;
                        } else if (ratio > 0.5) {
                            POSITION = TOP;
                        }
                    }
                    Log.i(TAG, "onStateChanged POSITION: " + getPosition(POSITION));
                    setState();
                }

            }

            @Override
            public void onSlide(@NonNull View view, float v) {
                ratio = v;
//                slideOffset为底部的新偏移量，值在[-1,1]范围内。当BottomSheetBehavior处于折叠(STATE_COLLAPSED)和
//                展开(STATE_EXPANDED)状态之间时,它的值始终在[0,1]范围内，向上移动趋近于1，向下区间于0。[-1,0]处于
//                隐藏状态(STATE_HIDDEN)和折叠状态(STATE_COLLAPSED)之间。
                int top = view.getTop();
                if (top <= dp200) {
                    float alpha = 1 - (float) top / dp200;
                    //距离顶部 200dp 之内,给地图设置透明度
                    blank.setAlpha(alpha);
                } else {
                    blank.setAlpha(0);
                }
            }
        });
    }

    private String getState(int state) {
        switch (state) {
            case BottomSheetBehavior.STATE_DRAGGING:
                return "STATE_DRAGGING";
            case BottomSheetBehavior.STATE_SETTLING:
                return "STATE_SETTLING";
            case BottomSheetBehavior.STATE_EXPANDED:
                return "STATE_EXPANDED";
            case BottomSheetBehavior.STATE_COLLAPSED:
                return "STATE_COLLAPSED";
            case BottomSheetBehavior.STATE_HIDDEN:
                return "STATE_HIDDEN";
            case BottomSheetBehavior.STATE_HALF_EXPANDED:
                return "STATE_HALF_EXPANDED";
            default:
                return "null state";
        }

    }

    private String getPosition(int position) {
        switch (position) {
            case BOTTOM:
                return "BOTTOM";
            case MIDDLE:
                return "MIDDLE";
            case TOP:
                return "TOP";
            default:
                return "null state";
        }

    }

    private void setState() {
        if (POSITION == BOTTOM) {
            mBehavior.setHideable(false);
            mBehavior.setPeekHeight(BOTTOM_PICK_HEIGHT);
        } else {
            mBehavior.setHideable(true);
        }
        if (POSITION == MIDDLE || POSITION == TOP) {
            mBehavior.setPeekHeight(MIDDLE_PICK_HEIGHT);
        }
        if (POSITION == TOP) {
            mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        } else {
            mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            if (tMap != null) {
                if (POSITION == BOTTOM) {
                    adjustMapRatio(BOTTOM_PICK_HEIGHT);
                } else {
                    adjustMapRatio(MIDDLE_PICK_HEIGHT);
                }
            }
        }
    }

    private void adjustMapRatio(int bottomMargin) {
        LatLng startLatLng = new LatLng(36.954465, 117.147163);
        LatLng endLatLng = new LatLng(36.629983, 116.933417);
        LatLngBounds bounds = new LatLngBounds.Builder()
                .include(startLatLng)
                .include(endLatLng).build();
        tMap.moveCamera(CameraUpdateFactory.newLatLngBoundsRect(bounds,
                SizeUtils.dp2px(130),
                SizeUtils.dp2px(130),
                SizeUtils.dp2px(150),
                bottomMargin));

    }

    private void initMockData() {
        for (int i = 0; i < 50; i++) {
            dataList.add("第" + (i + 1) + "条数据");
        }
    }

    private void initMap() {
        if (tMap == null) {
            tMap = mapView.getMap();
        }
        if (tMap == null) return;
        UiSettings uiSettings = tMap.getUiSettings();
        uiSettings.setRotateGesturesEnabled(false);
        //logo放在右下角
        uiSettings.setLogoPosition(TencentMapOptions.LOGO_POSITION_BOTTOM_RIGHT);
//            uiSettings.setAllGesturesEnabled(false);
        //地图模式可选类型：MAP_TYPE_NORMAL:标准底图,MAP_TYPE_SATELLITE:卫星底图
        tMap.setMapType(TencentMap.MAP_TYPE_NORMAL);
        //缩放比例  地图的缩放级别一共分为 17 级，从 3 到 19。数字越大，展示的图面信息越精细。
//        aMap.moveCamera(CameraUpdateFactory.zoomTo(3f));


    }

    @Override
    public void onResume() {
        super.onResume();
        if (mapView != null) {
            mapView.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mapView != null) {
            mapView.onPause();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mapView != null) {
            mapView.onDestroy();
        }
    }


}