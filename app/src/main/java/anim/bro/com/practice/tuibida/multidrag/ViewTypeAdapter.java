package anim.bro.com.practice.tuibida.multidrag;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import anim.bro.com.practice.R;


/**
 * Created by zhangshan on 2019/1/4 11:48.
 */
public class ViewTypeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TITLE_ITEM = 1;
    public static final int CONTENT_ITEM = 2;

    private static final int MYLIVE_MODE_CHECK = 0;
    int mEditMode = MYLIVE_MODE_CHECK;

    private Context mContext;
    List<SelectBean> mDatas = new ArrayList<>();

    public ViewTypeAdapter(Context context) {
        mContext = context;
    }

    public void setData(List<SelectBean> datas) {
        if (datas != null && datas.size() > 0) {
            mDatas.clear();
            mDatas.addAll(datas);
            notifyDataSetChanged();
        }
    }

    public List<SelectBean> getDataList() {
        return mDatas;
    }

    public void addData(List<SelectBean> datas) {
        if (datas != null && datas.size() > 0) {
            mDatas.addAll(datas);
            notifyDataSetChanged();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        RecyclerView.ViewHolder holder;
        if (TITLE_ITEM == viewType) {
            View v = mInflater.inflate(R.layout.item_title, parent, false);
            holder = new TitleHolder(v);
        } else {
            View v = mInflater.inflate(R.layout.item_content, parent, false);
            holder = new ContentHolder(v);

        }
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ContentHolder) {
            final ContentHolder contentHolder = (ContentHolder) holder;
            contentHolder.tvContent.setText(mDatas.get(position).getContentBean().getContent());
            if (mEditMode == MYLIVE_MODE_CHECK) {
                contentHolder.mCheckBox.setVisibility(View.INVISIBLE);
            } else {
                contentHolder.mCheckBox.setVisibility(View.VISIBLE);
                if (mDatas.get(position).getContentBean().isSelect()) {
                    contentHolder.mCheckBox.setImageResource(R.drawable.ic_checked);
                } else {
                    contentHolder.mCheckBox.setImageResource(R.drawable.ic_uncheck);
                }
            }
            contentHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClickListener(contentHolder.getAdapterPosition(), mDatas);
                }
            });
        } else if (holder instanceof TitleHolder) {
            TitleHolder titleHolder = (TitleHolder) holder;
            titleHolder.tvTitle.setText(mDatas.get(position).getTitle());
        }
    }

    @Override
    public int getItemViewType(int position) {
//            if(position % 3 == 0){
//                return CONTENT_ITEM;
//            }else{
//                return TITLE_ITEM;
//            }

        if (mDatas == null && mDatas.size() == 0) {
            return 0;
        } else {
            if (mDatas.get(position).getType() == SelectBean.TYPE_TITLE) {
                return TITLE_ITEM;
            } else {
                return CONTENT_ITEM;
            }
        }
//        if (position == 0 || position == 2) {
//
//        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void setEditMode(int editMode) {
        mEditMode = editMode;
        notifyDataSetChanged();
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClickListener(int pos, List<SelectBean> myLiveList);
    }
}
