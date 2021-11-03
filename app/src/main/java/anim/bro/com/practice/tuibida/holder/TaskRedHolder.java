package anim.bro.com.practice.tuibida.holder;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import anim.bro.com.practice.R;


/**
 * 红包任务的holder
 */
public class TaskRedHolder {
    //是否已经展示过去登录的提示
    private final static String SP_HAS_SHOWN_TASK_RED = "sp_has_shown_task_red";
    private Activity activity;

    private OnEndListener listener;

    public TaskRedHolder(Activity activity, OnEndListener listener) {
        this.activity = activity;
        this.listener =listener;
//        showFirstTip();
    }

    public interface OnEndListener {
        void onGuideEnd();
    }

    /**
     * 显示红包任务
     *
     * @return true  成功显示  false 不显示
     */
//    public boolean showFirstTip() {
////        if (isShowingTip()) return true;//如果正在显示 则直接返回
////        if (activity.isFinishing()) return false;
////        if (!AppGlobal.video_show_login) return false;//判断是否需要展示登录弹窗
////        if (LoginChecker.isLogin(activity)) return false;//如果已经登录 则不展示
//
//        SharedPreferenceUtil instance = SharedPreferenceUtil.getInstance(activity);
//        SharedPreferences sharedPreferences = instance.getSharedPreferences();
//        boolean hasShow = sharedPreferences.getBoolean(SP_HAS_SHOWN_TASK_RED, false);
//        if (hasShow) return false;
//        sharedPreferences.edit().putBoolean(SP_HAS_SHOWN_TASK_RED, true).commit();
//        View view = activity.findViewById(R.id.frame_task_red_guide);
//        if (view == null) {
//            view = getTaskRedGuide(activity);
//        }
//        view.setVisibility(View.VISIBLE);
//        return true;
//    }
//
//    /**
//     * 生成红包任务的 view
//     *
//     * @param activity
//     * @return
//     */
//    @NonNull
//    private View getTaskRedGuide(Activity activity) {
//        TaskRedGuideView view = new TaskRedGuideView(activity);
//        view.setOnClickListener(new TaskRedGuideView.OnGuideClickListener() {
//            @Override
//            public void onGuideEndListener() {
//                hideTaskRedGuide();
//                if (listener!=null){
//                    listener.onGuideEnd();
//                }
//            }
//        });
//        view.setId(R.id.frame_task_red_guide);
////        View view = LayoutInflater.from(activity).inflate(R.layout.sv_video_tip_to_login, null);
//        FrameLayout contentView = (FrameLayout) activity.findViewById(android.R.id.content);
//        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        contentView.addView(view, layoutParams);
//        return view;
//    }

    //移除掉红包任务引导页
    public void hideTaskRedGuide() {
        if (activity.isFinishing()) return;
        View view = activity.findViewById(R.id.frame_task_red_guide);
        if (view == null) return;
        view.setVisibility(View.GONE);
        ViewGroup parent = (ViewGroup) view.getParent();
        parent.removeView(view);
    }

//    @Override
//    public void onClick(View v) {
//        if (activity.isFinishing()|| listener == null) return;
//        if (v.getId() == R.id.txt_cancel) {
//            listener.onCancel();
//        } else if (v.getId() == R.id.txt_login) {
//            listener.toLogin();
//        }
//    }

//    public interface Listener {
//        void onCancel();
//
//        void toLogin();
//    }
}
