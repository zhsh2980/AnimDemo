package anim.bro.com.practice.fragment;

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
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import anim.bro.com.practice.R;
import anim.bro.com.practice.databinding.FragmentMydd2022Binding;
import anim.bro.com.practice.fragment.model.FragmentModel;
import anim.bro.com.practice.fragment.nonreflection.java.BaseBindingFragment;
import anim.bro.com.practice.rv.RvAdapter;

/**
 * Created by zhangshan on 2021/8/16 11:30.
 */
public class MyDangDang2022Fragment extends BaseBindingFragment<FragmentMydd2022Binding> {

//    @BindView(R.id.recycler_view)
//    RecyclerView recyclerView;

    private FragmentModel fragmentModel;

    @Override
    protected FragmentMydd2022Binding onCreateViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent) {
        return FragmentMydd2022Binding.inflate(inflater, parent, false);
    }

//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.activity_r_v, container, false);
////        ButterKnife.bind(this , view);
//        //构建ViewModel实例
//        fragmentModel = ViewModelProviders.of(getActivity()).get(FragmentModel.class);
//        return view;
//    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentModel = ViewModelProviders.of(getActivity()).get(FragmentModel.class);
        FragmentMydd2022Binding binding = getBinding();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.HORIZONTAL));
        RvAdapter rvAdapter = new RvAdapter(25, "fragment1", R.color.cor_BF75FF);
        //模拟网络延时
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.recyclerView.setAdapter(rvAdapter);
                Log.i("bro", "fragemnt_1加载完成");
                Toast.makeText(getActivity(), "fragemnt_1加载完成", Toast.LENGTH_LONG).show();
                fragmentModel.mUserLiveData.setValue(2);
            }
        }, 2000);

    }
}
