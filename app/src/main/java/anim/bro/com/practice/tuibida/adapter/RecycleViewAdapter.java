package anim.bro.com.practice.tuibida.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import anim.bro.com.practice.R;


/**
 * Created by zhangshan on 2018/11/6 11:43.
 */
public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.MyViewHolder> {
    private Context context;
    private List<String> dataList;
    public RecycleViewAdapter(Context context, List<String> dataList) {
        this.context = context;
        this.dataList = dataList;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycleview_item, parent, false);
        return new MyViewHolder(view);
    }
    @Override
    public int getItemCount() {
        return dataList.size();
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv_text.setText(dataList.get(position));
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_text;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv_text=(TextView) itemView.findViewById(R.id.tv_text);
        }
    }
}
