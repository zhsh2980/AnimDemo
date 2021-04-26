package anim.bro.com.animdemo.rv;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import anim.bro.com.animdemo.R;

/**
 * Created by zhangshan on 4/21/21 7:16 PM.
 */
class RvAdapter extends RecyclerView.Adapter<RvAdapter.DemoViewHolder> {

    private List<RvBean> list;

    RvAdapter() {
        list = new ArrayList();
        for (int i = 0; i <= 100; i++) {
            list.add(new RvBean("haha" + i));
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
        TextView name;

        public DemoViewHolder(@NonNull final View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_title);
            name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtils.showLong("点我干啥");
                }
            });
        }

        public void bindTo(RvBean rvBean) {
            name.setText(rvBean.title);
        }
    }

}
