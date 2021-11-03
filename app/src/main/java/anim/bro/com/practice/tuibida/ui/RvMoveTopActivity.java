package anim.bro.com.practice.tuibida.ui;

import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import anim.bro.com.practice.R;
import anim.bro.com.practice.tuibida.adapter.LiveGoodsAdapter;
import anim.bro.com.practice.tuibida.entity.LiveGoodsAnchorEntity;
import anim.bro.com.practice.tuibida.utils.HideShopAnimUtil;
import butterknife.BindView;
import butterknife.OnClick;

public class RvMoveTopActivity extends BaseActivity {

    @BindView(R.id.rv)
    RecyclerView rv_live_goods;
    @BindView(R.id.iv_goods)
    ImageView iv_goods;


    @Override
    public int getResourceID() {
        return R.layout.activity_rv_move_top;
    }

    @Override
    public String getTitleName() {
        return "置顶操作";
    }

    @Override
    public void initView() {


    }

    @Override
    public void initData() {
        initAdapter();
        setData();
    }

    private void setData() {
        List<LiveGoodsAnchorEntity.LiveGoodsAnchorListBean> listShows = new ArrayList<>();
        LiveGoodsAnchorEntity.LiveGoodsAnchorListBean bean = new LiveGoodsAnchorEntity.LiveGoodsAnchorListBean();
        bean.goodsName = "商品 A";
        bean.goodsDesc = "备注";
        bean.currentPrice = "100";
        bean.originalPrice = "200";
        bean.goodsLink = "www.good1.com";
        bean.sequence = "1";
        LiveGoodsAnchorEntity.LiveGoodsAnchorListBean bean2 = new LiveGoodsAnchorEntity.LiveGoodsAnchorListBean();
        bean2.goodsName = "商品 B";
        bean2.goodsDesc = "备注 B";
        bean2.currentPrice = "200";
        bean2.originalPrice = "400";
        bean2.goodsLink = "www.good2.com";
        bean2.sequence = "2";
        LiveGoodsAnchorEntity.LiveGoodsAnchorListBean bean3 = new LiveGoodsAnchorEntity.LiveGoodsAnchorListBean();
        bean3.goodsName = "商品 C";
        bean3.goodsDesc = "备注 C";
        bean3.currentPrice = "300";
        bean3.originalPrice = "600";
        bean3.goodsLink = "www.good3.com";
        bean3.sequence = "3";
        listShows.add(bean);
        listShows.add(bean2);
        listShows.add(bean3);
        adapter.addAll(listShows);

    }

    String goodsLink = "www.good2.com";

    private LiveGoodsAdapter adapter;
    private HideShopAnimUtil mShopAnimUtil;

    public void initAdapter() {
        //隐藏购物车
        mShopAnimUtil = new HideShopAnimUtil(iv_goods);
        adapter = new LiveGoodsAdapter(this, "", "", true, mShopAnimUtil);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv_live_goods.setLayoutManager(layoutManager);
        rv_live_goods.setAdapter(adapter);
    }

    @OnClick({R.id.btn_move_top, R.id.btn_cancel_move_top, R.id.btn_recommend, R.id.btn_cancel_recommend})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_move_top:
               adapter.doMoveTopById(goodsLink);
                break;
            case R.id.btn_cancel_move_top:
                adapter.doCancelMoveTopById(goodsLink);
                break;
            case R.id.btn_recommend:
                adapter.doRecommendById(goodsLink);
                break;
            case R.id.btn_cancel_recommend:
                adapter.doCancelRecommendById(goodsLink);
                break;
        }
    }



}
