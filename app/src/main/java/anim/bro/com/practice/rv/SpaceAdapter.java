package anim.bro.com.practice.rv;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
        RelativeLayout relaRight;
        LinearLayout ll_bottom;
        TextView tvTitle;
        Space space;
        TextView tvBottomMaybeLong;

        public DemoViewHolder(@NonNull final View itemView) {
            super(itemView);
            iv_left = itemView.findViewById(R.id.iv_left);
            relaRight = itemView.findViewById(R.id.rela_right);
            ll_bottom = itemView.findViewById(R.id.ll_bottom);
            tvTitle = itemView.findViewById(R.id.tv_title);
            space = itemView.findViewById(R.id.space);
            tvBottomMaybeLong = itemView.findViewById(R.id.tv_bottom_maybe_long);
        }

        public void setData() {
            int ivLeftHeight = iv_left.getHeight();
            int llRightHeight = relaRight.getHeight();
            Log.i("bro", "ivLeftHeight: " + ivLeftHeight + "---llRightHeight: " + llRightHeight);
            if (llRightHeight > ivLeftHeight) {
                //右侧 > 图片高度
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) relaRight.getLayoutParams();
                params.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
                params.removeRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                relaRight.setLayoutParams(params);

                RelativeLayout.LayoutParams params2 = (RelativeLayout.LayoutParams) ll_bottom.getLayoutParams();
                params2.removeRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                ll_bottom.setLayoutParams(params2);
            } else {
                //右侧 < 图片高度
                RelativeLayout.LayoutParams llRightLayoutParams = (RelativeLayout.LayoutParams) relaRight.getLayoutParams();
                llRightLayoutParams.height = ivLeftHeight;
                relaRight.setLayoutParams(llRightLayoutParams);

                RelativeLayout.LayoutParams params2 = (RelativeLayout.LayoutParams) ll_bottom.getLayoutParams();
                params2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                ll_bottom.setLayoutParams(params2);
            }
            itemView.invalidate();
        }

        public void bindTo(RvSpaceBean rvBean) {
            tvTitle.setText(rvBean.title);
            tvBottomMaybeLong.setText(rvBean.bottomText);
            itemView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
//                    测量右侧高度
                    int ivLeftHeight = iv_left.getHeight();
                    int llRightHeight = relaRight.getHeight();
                    if (ivLeftHeight == 0 || llRightHeight == 0) {
                        Log.i("bro", "ivLeftHeight == 0");
                        itemView.post(new Runnable() {
                            @Override
                            public void run() {
                                setData();
                            }
                        });
                    } else {
                        Log.i("bro", "ivLeftHeight != 0");
                        setData();
                    }
                }
            });
//
        }
    }

}
