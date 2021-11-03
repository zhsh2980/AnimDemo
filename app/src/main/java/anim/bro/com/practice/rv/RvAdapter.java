package anim.bro.com.practice.rv;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import anim.bro.com.practice.R;

/**
 * Created by zhangshan on 4/21/21 7:16 PM.
 */
public class RvAdapter extends RecyclerView.Adapter<RvAdapter.DemoViewHolder> {

    private List<RvBean> list;

    public RvAdapter() {
        list = new ArrayList();
        for (int i = 0; i <= 100; i++) {
            list.add(new RvBean("haha" + i));
        }
    }

    public RvAdapter(int size, String title, int color) {
        list = new ArrayList();
        for (int i = 0; i <= size; i++) {
            list.add(new RvBean(title + "---" + i, color));
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

        }

        public void bindTo(RvBean rvBean) {
            name.setText(rvBean.title);
            if (rvBean.color != -1) {
                name.setTextColor(itemView.getResources().getColor(rvBean.color, null));
            }
            name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(), "点我干啥", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

}
