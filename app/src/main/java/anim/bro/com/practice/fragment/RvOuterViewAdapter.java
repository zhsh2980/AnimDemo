package anim.bro.com.practice.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import anim.bro.com.practice.R;

/**
 * Created by zhangshan on 2021/8/16 10:49.
 */
public class RvOuterViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final String TAG = RvOuterViewAdapter.class.getSimpleName();

    public static final int ITEM_FRAGMENT_1 = 1;
    public static final int ITEM_FRAGMENT_2 = 2;
    public static final int ITEM_FRAGMENT_3 = 3;

    private Context mContext;
    private List<Integer> typeList = new ArrayList<>();

    RvOuterViewAdapter(Context context) {
        mContext = context;
        initMockList();
    }

    private void initMockList() {
        typeList.clear();
        typeList.add(ITEM_FRAGMENT_1);
        typeList.add(ITEM_FRAGMENT_2);
        typeList.add(ITEM_FRAGMENT_3);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_element_ledger, parent, false);
//        return new ViewHolder(view);
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        RecyclerView.ViewHolder holder;
        if (ITEM_FRAGMENT_1 == viewType) {
            View v = mInflater.inflate(R.layout.rv_fragment_1, parent, false);
            holder = new Fragment1Holder(v);
        } else if (ITEM_FRAGMENT_2 == viewType) {
            View v = mInflater.inflate(R.layout.rv_fragment_2, parent, false);
            holder = new Fragment2Holder(v);
        } else {
            View v = mInflater.inflate(R.layout.rv_fragment_3, parent, false);
            holder = new Fragment3Holder(v);
        }
        return holder;

    }

    @Override
    public int getItemViewType(int position) {
        return typeList.get(position);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof Fragment1Holder) {
            final Fragment1Holder fragment1Holder = (Fragment1Holder) holder;
            FragmentManager fragmentManager = ((FragmentActivity) mContext).getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(fragment1Holder.frameDetailsContainer.getId(), new RvFragment1()).commitAllowingStateLoss();
        } else if (holder instanceof Fragment2Holder) {
            final Fragment2Holder fragment2Holder = (Fragment2Holder) holder;
            FragmentManager fragmentManager = ((FragmentActivity) mContext).getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(fragment2Holder.frameDetailsContainer.getId(), new RvFragment2()).commitAllowingStateLoss();
        } else if (holder instanceof Fragment3Holder) {
            final Fragment3Holder fragment3Holder = (Fragment3Holder) holder;
            FragmentManager fragmentManager = ((FragmentActivity) mContext).getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(fragment3Holder.frameDetailsContainer.getId(), new RvFragment3()).commitAllowingStateLoss();
        }
    }

    @Override
    public int getItemCount() {
        return typeList.size();
    }

    public class Fragment1Holder extends RecyclerView.ViewHolder {
        private View iView;
        public FrameLayout frameDetailsContainer;

        public Fragment1Holder(View itemView) {
            super(itemView);
            iView = itemView;
            frameDetailsContainer = iView.findViewById(R.id.frame_fragment_1);
        }
    }

    public class Fragment2Holder extends RecyclerView.ViewHolder {
        private View iView;
        public FrameLayout frameDetailsContainer;

        public Fragment2Holder(View itemView) {
            super(itemView);
            iView = itemView;
            frameDetailsContainer = iView.findViewById(R.id.frame_fragment_2);
        }
    }

    public class Fragment3Holder extends RecyclerView.ViewHolder {
        private View iView;
        public FrameLayout frameDetailsContainer;

        public Fragment3Holder(View itemView) {
            super(itemView);
            iView = itemView;
            frameDetailsContainer = iView.findViewById(R.id.frame_fragment_3);
        }
    }
}