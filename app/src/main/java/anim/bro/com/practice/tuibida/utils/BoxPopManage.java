package anim.bro.com.practice.tuibida.utils;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import anim.bro.com.practice.R;
import anim.bro.com.practice.util.UIUtils;

/**
 * Created by zhangshan on 2019/3/9 12:20.
 */
public class BoxPopManage {

    private final static BoxPopManage INSTANCE = new BoxPopManage();

    private BoxPopManage() {
    }

    public static BoxPopManage getInstance() {
        return INSTANCE;
    }

    private CustomPopWindow customPopupWindow;

    public void dissmiss() {
        if (customPopupWindow != null && customPopupWindow.isShown()) {
            customPopupWindow.dissmiss();
        }
    }

    public void showPopWindow(View anchor, String tip_text) {
        if (customPopupWindow == null) {
            View contentView = LayoutInflater.from(anchor.getContext()).inflate(R.layout.pop_treasure_box_text, null);
            customPopupWindow = new CustomPopWindow.PopupWindowBuilder(anchor.getContext())
                    .setView(contentView)//显示的布局
                    .size(UIUtils.dip2px(81 * 2), UIUtils.dip2px(32 * 2)) //设置显示的大小，不设置就默认包裹内容
                    .create();//创建PopupWindow
        } else {
            customPopupWindow.dissmiss();
        }

        TextView textView = customPopupWindow.getContentView().findViewById(R.id.tv_pop_trea_box);
        //显示的布局
        if (null != textView && !TextUtils.isEmpty(tip_text)) {
            textView.setText(tip_text);
        }

        customPopupWindow.showAsDropDown(anchor, -customPopupWindow.getWidth() - UIUtils.dip2px(8),
                -customPopupWindow.getHeight() * 3 / 4 - customPopupWindow.getHeight() * 3 / 4);//显示PopupWindow

    }

//    public void showMyPublishPopWindow(View anchor, String tip_text) {
//        if (customPopupWindow == null) {
//            View contentView = LayoutInflater.from(anchor.getContext()).inflate(R.layout.pop_treasure_box_publish_text, null);
//            customPopupWindow = new CustomPopWindow.PopupWindowBuilder(anchor.getContext())
//                    .setView(contentView)//显示的布局
//                    .size(UIUtils.dip2px(127), UIUtils.dip2px(30)) //设置显示的大小，不设置就默认包裹内容
//                    .create();//创建PopupWindow
//        } else {
//            customPopupWindow.dissmiss();
//        }
//
//        TextView textView = customPopupWindow.getContentView().findViewById(R.id.tv_pop_trea_box_publish);
//        //显示的布局
//        if (null != textView && !TextUtils.isEmpty(tip_text)) {
//            textView.setText(tip_text);
//        }
//
//        customPopupWindow.showAsDropDown(anchor, -customPopupWindow.getWidth() - UIUtils.dip2px(8),
//                -customPopupWindow.getHeight() * 3 / 4 - customPopupWindow.getHeight() * 3 / 4);//显示PopupWindow
//
//    }


}
