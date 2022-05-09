package anim.bro.com.practice.tuibida;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.blankj.utilcode.util.ActivityUtils;

import anim.bro.com.practice.R;
import anim.bro.com.practice.tuibida.multidrag.MultiDragActivity;
import anim.bro.com.practice.tuibida.ui.BaseActivity;
import anim.bro.com.practice.tuibida.ui.CoordinatorActivity;
import anim.bro.com.practice.tuibida.ui.CoordinatorActivity2;
import anim.bro.com.practice.tuibida.ui.CountDownActivity;
import anim.bro.com.practice.tuibida.ui.DialogActivity;
import anim.bro.com.practice.tuibida.ui.DramViewHeightActivity;
import anim.bro.com.practice.tuibida.ui.GridActivity;
import anim.bro.com.practice.tuibida.ui.HideViewActivity;
import anim.bro.com.practice.tuibida.ui.LottieActivity;
import anim.bro.com.practice.tuibida.ui.RedTaskActivity;
import anim.bro.com.practice.tuibida.ui.RvMoveTopActivity;
import anim.bro.com.practice.tuibida.ui.SwitchActivity;
import anim.bro.com.practice.tuibida.ui.TabFragmentActivity;
import anim.bro.com.practice.tuibida.ui.ToastActivity;
import anim.bro.com.practice.tuibida.ui.ViewModelActivity;
import butterknife.BindView;
import butterknife.OnClick;

public class MainTuiBiDaActivity extends BaseActivity {

    @BindView(R.id.et_url)
    EditText mEtUrl;

    @Override
    public int getResourceID() {
        return R.layout.activity_main_tuibida;
    }

    @Override
    public String getTitleName() {
        return "MainActivity";
    }
    @Override
    public void initView() {

        mEtUrl.setText("https://m.cnbeta.com/");

    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.btn_task_red, R.id.btn_expand
            , R.id.btn_coordinator, R.id.btn_dram
            , R.id.btn_toast, R.id.btn_multi_drag
            , R.id.btn_count_down, R.id.btn_grid
            , R.id.btn_switch, R.id.btn_tab_fragment
            ,R.id.btn_view_model,R.id.btn_lottie
            ,R.id.btn_move_top
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_task_red:
                ActivityUtils.startActivity(RedTaskActivity.class);
                break;
            case R.id.btn_expand:
                ActivityUtils.startActivity(HideViewActivity.class);
                break;
            case R.id.btn_coordinator:
                ActivityUtils.startActivity(CoordinatorActivity2.class);
                break;
            case R.id.btn_dram:
                ActivityUtils.startActivity(DramViewHeightActivity.class);
                break;
            case R.id.btn_toast:
                ActivityUtils.startActivity(ToastActivity.class);
                break;
            case R.id.btn_multi_drag:
                ActivityUtils.startActivity(MultiDragActivity.class);
                break;
            case R.id.btn_count_down:
                ActivityUtils.startActivity(CountDownActivity.class);
                break;
            case R.id.btn_grid:
                ActivityUtils.startActivity(GridActivity.class);
                break;
            case R.id.btn_switch:
                ActivityUtils.startActivity(SwitchActivity.class);
                break;
            case R.id.btn_tab_fragment:
                ActivityUtils.startActivity(TabFragmentActivity.class);
                break;
            case R.id.btn_view_model:
                ActivityUtils.startActivity(ViewModelActivity.class);
                break;
            case R.id.btn_lottie:
                ActivityUtils.startActivity(LottieActivity.class);
                break;
            case R.id.btn_move_top:
                ActivityUtils.startActivity(RvMoveTopActivity.class);
                break;
        }
    }

    @OnClick(R.id.btn_load_url)
    public void onViewClicked() {
        String url = mEtUrl.getText().toString().trim();
        if (TextUtils.isEmpty(url)) {
            Toast.makeText(this, "url 不能为空", Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent(MainTuiBiDaActivity.this, DialogActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }
}
