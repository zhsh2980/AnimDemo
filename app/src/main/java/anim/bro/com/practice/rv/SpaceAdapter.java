package anim.bro.com.practice.rv;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import anim.bro.com.practice.R;

/**
 * Created by zhangshan on 4/21/21 7:16 PM.
 */
public class SpaceAdapter extends RecyclerView.Adapter<SpaceAdapter.DemoViewHolder> {

    private List<RvSpaceBean> list;

    public SpaceAdapter() {
        list = new ArrayList();
        for (int i = 0; i <= 100; i++) {
            RvSpaceBean rvSpaceBean = new RvSpaceBean();
            Random randNum = new Random();
            boolean isLongText = randNum.nextBoolean();
            if (isLongText) {
                rvSpaceBean.setBottomText("我是一个很长的文字北京回龙观西大街国美店\n" +
                        "我是一个很长的文字北京回龙观西大街国美店\n" +
                        "我是一个很长的文字北京回龙观西大街国美店\n" +
                        "我是一个很长的文字北京回龙观西大街国美店\n" +
                        "我是一个很长的文字北京回龙观西大街国美店\"");

            } else {
                rvSpaceBean.setBottomText("我是一个短文字");
            }
            rvSpaceBean.setTitle("空调" + i);
            list.add(rvSpaceBean);
        }
    }

    @NonNull
    @Override
    public DemoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_rv_item, parent, false);
        return new DemoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DemoViewHolder viewHolder, int position) {
        viewHolder.bindTo(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class DemoViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_left;
        LinearLayout ll_right;
        TextView tvTitle;
        Space space;
        TextView tvBottomMaybeLong;

        public DemoViewHolder(@NonNull final View itemView) {
            super(itemView);
            iv_left = itemView.findViewById(R.id.iv_left);
            ll_right = itemView.findViewById(R.id.ll_right);
            tvTitle = itemView.findViewById(R.id.tv_title);
            space = itemView.findViewById(R.id.space);
            tvBottomMaybeLong = itemView.findViewById(R.id.tv_bottom_maybe_long);
        }

        public void bindTo(RvSpaceBean rvBean) {

            //恢复默认布局: 右边大于左边
            ViewGroup.LayoutParams params = ll_right.getLayoutParams();
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            ll_right.setLayoutParams(params);

            LinearLayout.LayoutParams spaceLayoutParams = (LinearLayout.LayoutParams) space.getLayoutParams();
            spaceLayoutParams.weight = 0;
            space.setLayoutParams(spaceLayoutParams);

            tvTitle.setText(rvBean.title);
            tvBottomMaybeLong.setText(rvBean.bottomText);

            //测量右侧高度
            ll_right.post(() -> {
                int ivLeftHeight = iv_left.getHeight();
                int llRightHeight = ll_right.getHeight();
                Log.i("bro", "ivLeftHeight: " + ivLeftHeight + "---llRightHeight: " + llRightHeight);
                if (llRightHeight < ivLeftHeight) {
                    //右侧 < 图片高度
                    ViewGroup.LayoutParams llRightLayoutParams = ll_right.getLayoutParams();
                    llRightLayoutParams.height = ivLeftHeight;
                    ll_right.setLayoutParams(llRightLayoutParams);

                    LinearLayout.LayoutParams spaceLayoutParams1 = (LinearLayout.LayoutParams) space.getLayoutParams();
                    spaceLayoutParams1.weight = 1;
                    space.setLayoutParams(spaceLayoutParams1);
                }
            });


        }
    }

}
