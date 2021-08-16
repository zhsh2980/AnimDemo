package anim.bro.com.animdemo.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import anim.bro.com.animdemo.R;
import anim.bro.com.animdemo.fragment.model.FragmentModel;
import anim.bro.com.animdemo.rv.RvAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhangshan on 2021/8/16 11:30.
 */
public class RvFragment1 extends Fragment {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private FragmentModel fragmentModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_r_v, container, false);
        ButterKnife.bind(this , view);
        //构建ViewModel实例
        fragmentModel = ViewModelProviders.of(getActivity()).get(FragmentModel.class);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity() , DividerItemDecoration.HORIZONTAL));
        RvAdapter rvAdapter = new RvAdapter(25 ,"fragment1", R.color.cor_BF75FF);
        //模拟网络延时
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                recyclerView.setAdapter(rvAdapter);
                Log.i("bro" , "fragemnt_1加载完成");
                Toast.makeText(getActivity() , "fragemnt_1加载完成"  ,Toast.LENGTH_LONG).show();
                fragmentModel.mUserLiveData.setValue(2);
            }
        } , 2000);

    }
}
