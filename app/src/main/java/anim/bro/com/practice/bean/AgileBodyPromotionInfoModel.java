package anim.bro.com.practice.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import anim.bro.com.practice.network.GsonBaseModel;

/**
 * 我的大促-新 body 数据结构
 */
public class AgileBodyPromotionInfoModel extends GsonBaseModel {

    /**
     * more_link :
     * anim_interval : 5
     * items : [{"items_inside":[{"pic":"https://img63.ddimg.cn/upload_img/00714/mobile/daifukuan-1533785892.png","pic_link":"coupon://","title":"图书会场"},{"pic":"https://img63.ddimg.cn/upload_img/00714/mobile/daifukuan-1533785892.png","pic_link":"coupon://","title":"图书会场2"}]},{"items_inside":[{"pic":"https://img63.ddimg.cn/upload_img/00714/mobile/daifukuan-1533785892.png","pic_link":"coupon://","title":"服百会场"},{"pic":"https://img63.ddimg.cn/upload_img/00714/mobile/daifukuan-1533785892.png","pic_link":"coupon://","title":"服百会场2"}]},{"items_inside":[{"pic":"https://img63.ddimg.cn/upload_img/00714/mobile/daifukuan-1533785892.png","pic_link":"coupon://","title":"今日抢购"},{"pic":"https://img63.ddimg.cn/upload_img/00714/mobile/daifukuan-1533785892.png","pic_link":"coupon://","title":"今日抢购2"}]},{"items_inside":[{"pic":"https://img63.ddimg.cn/upload_img/00714/mobile/daifukuan-1533785892.png","pic_link":"coupon://","title":"电子书会场"},{"pic":"https://img63.ddimg.cn/upload_img/00714/mobile/daifukuan-1533785892.png","pic_link":"coupon://","title":"电子书会场2"}]},{"items_inside":[{"pic":"https://img63.ddimg.cn/upload_img/00714/mobile/daifukuan-1533785892.png","pic_link":"coupon://","title":"玩具会场"},{"pic":"https://img63.ddimg.cn/upload_img/00714/mobile/daifukuan-1533785892.png","pic_link":"coupon://","title":"玩具会场2"}]}]
     */

    @SerializedName("more_link")
    public String moreLink;
    @SerializedName("anim_interval")
    public String animInterval;
    @SerializedName("items")
    public List<Items> items;

    public static class Items {
        @SerializedName("items_inside")
        public List<ItemsInside> itemsInside;

        public static class ItemsInside {
            /**
             * pic : https://img63.ddimg.cn/upload_img/00714/mobile/daifukuan-1533785892.png
             * pic_link : coupon://
             * title : 图书会场
             */

            @SerializedName("pic")
            public String pic;
            @SerializedName("pic_link")
            public String picLink;
            @SerializedName("title")
            public String title;

            @Override
            public String toString() {
                return "ItemsInside{" +
                        "pic='" + pic + '\'' +
                        ", picLink='" + picLink + '\'' +
                        ", title='" + title + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "Items{" +
                    "itemsInside=" + itemsInside +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "AgileBodyPromotionInfoModel{" +
                "moreLink='" + moreLink + '\'' +
                ", animInterval='" + animInterval + '\'' +
                ", items=" + items +
                '}';
    }
}
