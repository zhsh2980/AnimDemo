package anim.bro.com.practice.tuibida.entity;


import java.util.List;

/**
 * Created by zhangshan on 2019/12/09.
 */
public class LiveGoodsAnchorEntity {

    public List<LiveGoodsAnchorListBean> goodsList;
    public int goodsNum;

    public static class LiveGoodsAnchorListBean implements Cloneable {

        public int uid;
        public String originalPrice;
        public String goodsId;
        public String imageUrl;
        public String currentPrice;
        public String sku;
        public String goodsLink;
        public String goodsName;
        public String goodsDesc;
        //序号
        public String sequence;

        //是否已经置顶
        public boolean isTop = false;
        //是否已经被推荐
        public boolean isRecommend = false;

        @Override
        public Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }

}
