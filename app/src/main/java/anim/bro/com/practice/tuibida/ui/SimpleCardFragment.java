package anim.bro.com.practice.tuibida.ui;

import android.annotation.SuppressLint;
import android.util.Log;
import android.widget.TextView;

import anim.bro.com.practice.R;

@SuppressLint("ValidFragment")
public class SimpleCardFragment extends LazyLoadFragment {
    private String mTitle;

    public static SimpleCardFragment getInstance(String title) {
        SimpleCardFragment sf = new SimpleCardFragment();
        sf.mTitle = title;
        return sf;
    }

    /**
     * 视图是否已经对用户可见，系统的方法
     */
//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        Log.d("SimpleCardFragment", "setUserVisibleHint: " + mTitle);
//    }

    @Override
    protected int setContentView() {
        return R.layout.fr_simple_card;
    }

    @Override
    protected void lazyLoad() {
        TextView card_title_tv = findViewById(R.id.card_title_tv);
        card_title_tv.setText(mTitle);
        Log.d("SimpleCardFragment", "lazyLoad: " + mTitle);
    }

    @Override
    protected void stopLoad() {
        Log.d("SimpleCardFragment", "stopLoad: " + mTitle);
    }

    //    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View v = inflater.inflate(R.layout.fr_simple_card, null);
//        TextView card_title_tv = (TextView) v.findViewById(R.id.card_title_tv);
//        card_title_tv.setText(mTitle);
//        Log.d("SimpleCardFragment", "onCreateView: " + mTitle);
//        return v;
//    }
}