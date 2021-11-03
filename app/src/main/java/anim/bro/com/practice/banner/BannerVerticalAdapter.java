package anim.bro.com.practice.banner;

import android.widget.ImageView;

import com.blankj.utilcode.util.SizeUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import anim.bro.com.practice.R;
import anim.bro.com.practice.banner.bannerview.BaseBannerAdapter;
import anim.bro.com.practice.banner.bannerview.BaseViewHolder;

/**
 * Created by zhangshan on 2021/10/15 10:18.
 * Description：请添加类注释
 */
class BannerVerticalAdapter extends BaseBannerAdapter<BannerBean.PitContent> {

    private final int mRoundCorner;

    public BannerVerticalAdapter(int roundCorner) {
        mRoundCorner = roundCorner;
    }

    @Override
    protected void bindData(BaseViewHolder<BannerBean.PitContent> holder, BannerBean.PitContent data, int position, int pageSize) {
//        holder.setImageResource(R.id.banner_image, data);
        //Glide设置图片圆角角度
        RoundedCorners roundedCorners = new RoundedCorners(SizeUtils.dp2px(10));
        //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
        // RequestOptions options = RequestOptions.bitmapTransform(roundedCorners).override(20, 20);
        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners);

        Glide.with(holder.itemView).load(data.getImgUrl())
                .apply(options)
                .into((ImageView) holder.findViewById(R.id.banner_image));
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.banner_vertical_item;
    }
}
