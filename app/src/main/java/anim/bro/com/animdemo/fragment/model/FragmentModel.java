package anim.bro.com.animdemo.fragment.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * Created by zhangshan on 2019-05-16 18:47.
 */
public class FragmentModel extends ViewModel {
    /**
     * 取消按钮点击
     */
    public final MutableLiveData<Integer> mUserLiveData = new MutableLiveData<>();


}
