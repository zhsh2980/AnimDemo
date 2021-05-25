package anim.bro.com.animdemo.goods;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.RadioButton;

import com.blankj.utilcode.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import anim.bro.com.animdemo.R;
import anim.bro.com.animdemo.goods.adapter.GoodsSaleAdapter;
import anim.bro.com.animdemo.util.UIUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lihongxin on 2019/1/21
 */
public class GoodsSaleActivity extends AppCompatActivity {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.goods_label_price)
    RadioButton goodsLabelPrice;
    private List<String> list;
    private GoodsSaleAdapter goodsSaleAdapter;
    private LinearLayoutManager linearLayoutManager;

    //默认价格递增
    private boolean isPriceAdd = false;

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, GoodsSaleActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_sale);
        ButterKnife.bind(this);
        initList();
        initView();

    }

    private void initList() {
        list = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            list.add(i + "");
        }
    }

    private void initView() {

        linearLayoutManager = new GridLayoutManager(this ,2);
        goodsSaleAdapter = new GoodsSaleAdapter(list);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.addItemDecoration(new AverageGapItemDecoration(UIUtils.dip2px(5) , UIUtils.dip2px(5),UIUtils.dip2px(7)));

        recyclerView.setAdapter(goodsSaleAdapter);
    }

    @OnClick({R.id.goods_label_complex, R.id.goods_label_sales, R.id.goods_label_price})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.goods_label_complex:
                ToastUtils.showShort("综合排序");
                break;
            case R.id.goods_label_sales:
                ToastUtils.showShort("销量排序");
                break;
            case R.id.goods_label_price:
                if (isPriceAdd) {
                    ToastUtils.showShort("价格递减");
                    goodsLabelPrice.setSelected(true);
                } else {
                    ToastUtils.showShort("价格递增");
                    goodsLabelPrice.setSelected(false);
                }
                isPriceAdd = !isPriceAdd;
                break;
        }
    }
}
