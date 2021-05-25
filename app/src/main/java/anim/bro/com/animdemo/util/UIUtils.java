package anim.bro.com.animdemo.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

/**
 * <p>
 * Project JuMeiYouPin
 * Created by shilei
 * Data 17/5/4
 * Desc UI 相关工具类
 *      相关方法简介
 *        getWindownManager() 获取windown管理器
 *        getDensity() 获取像素密度
 *        getInflater() 获取布局加载器
 *        find() 根据ID获取控件
 *        bindLayout() 绑定布局文件
 *        inflate() 加载布局
 *        getLayoutParams() 获取控件参数管理器
 *        getHorizontalPadding() 获取水平方向的padding值
 *        getVerticalPadding() 获取垂直方向的padding值
 *        getHorizontalMargin() 获取水平方向的margin值
 *        getVerticalMargin() 获取垂直方向的marging值
 *        dip2px()
 *        px2dip()
 *        px2sp()
 *        sp2px()
 *        getViewWidthAndHeight() 获取view的宽高 通过回调获取参数
 *        getScreenWidthAndHeight() 获取屏幕宽高
 *        getScreenWidth() 获取屏幕宽度
 *        getScreenHeight() 获取屏幕高度
 *        getStatusHeight() 获取状态栏高度
 *        toast() 吐司提示 可对前一个吐司提示进行覆盖
 *        getDimensionPixelSize() 获取dp资源
 *        getString() 获取string资源
 *        getStringArray() 获取string列表资源
 *        getColor() 获取颜色资源
 *        getColorStateList() 获取颜色状态选择器资源
 *        getDrawable() 获取图片资源
 * </p>
 */
@SuppressWarnings({"unchecked", "unused", "UnusedReturnValue"})
public abstract class UIUtils {
    /**
     * 获取Application上下文对象
     */
    public static Context getAppContext(){
        return SingleContainer.getApplicationContext();
    }

    /**
     * 获取窗口管理器
     * 建议使用 {@link UIUtils#getWindowManager(Context)}
     * @return
     */
    @Deprecated
    public static WindowManager getWindownManager(){
        return (WindowManager) getAppContext().getSystemService(Context.WINDOW_SERVICE);
    }
    public static WindowManager getWindowManager(Context context){
        if (context == null){
            return getWindownManager();
        }
        return (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
    }

    /**
     * 获取设备像素密度
     * 建议使用 {@link UIUtils#getDensity(Context)}
     * @return
     */
    @Deprecated
    public static float getDensity(){
        return getAppContext().getResources().getDisplayMetrics().density;
    }
    public static float getDensity(Context context){
        if (context == null){
            return getDensity();
        }
        if (context.getResources() == null){
            return getDensity();
        }
        if (context.getResources().getDisplayMetrics() == null){
            return getDensity();
        }
        return context.getResources().getDisplayMetrics().density;
    }

    /**
     * 获取布局填充器
     * 建议使用 {@link UIUtils#getInflater(Context)}
     * @return
     */
    @Deprecated
    public static LayoutInflater getInflater(){
        LayoutInflater localinflater =(LayoutInflater)getAppContext().getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        return localinflater;
    }
    public static LayoutInflater getInflater(Context context){
        if (context != null){
            return (LayoutInflater)context.getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
        }
        return getInflater();
    }

    /**
     * Activity获取控件
     * Activity.findViewById()
     *
     * @param context Activity对象
     * @param id      控件ID
     * @param <T>
     * @return
     */
    public static @Nullable
    <T extends View> T find(Activity context, int id) {
        if (context == null){
            return null;
        }
        return (T) context.findViewById(id);
    }
    /**
     * rootView获取控件
     * rootView.findViewById
     *
     * @param rootView 根容器控件
     * @param id       子控件ID
     * @param <T>
     * @return
     */
    public static @Nullable
    <T extends View> T find(View rootView, int id) {
        if (rootView == null){
            return null;
        }
        return (T) rootView.findViewById(id);
    }
    /**
     * dialog 获取控件
     * @param dialog
     * @param id
     * @param <T>
     * @return
     */
    public static @Nullable
    <T extends View> T find(Dialog dialog, int id) {
        if (dialog == null){
            return null;
        }
        return (T) dialog.findViewById(id);
    }

    public static @Nullable
    View bindLayout(Context context, int layoutId){
        if (context == null){
            return null;
        }
        return getInflater(context).inflate(layoutId, null);
    }
    public static @Nullable
    View bindLayout(ViewGroup viewGroup, int layoutId){
        if (viewGroup != null && viewGroup.getContext() != null){
            return getInflater(viewGroup.getContext()).inflate(layoutId, viewGroup);
        }
        return null;
    }
    public static @Nullable
    View bindLayout(ViewGroup viewGroup, int layoutId, boolean attachToRoot){
        if (viewGroup != null && viewGroup.getContext() != null){
            return getInflater(viewGroup.getContext()).inflate(layoutId,viewGroup,attachToRoot);
        }
        return null;
    }

    /**
     * 获取控件 LayoutParams
     * @param view 控件
     * @param <P> LayoutParams
     * @return LayoutParams
     */
    public static @Nullable
    <P extends ViewGroup.LayoutParams> P getLayoutParams(View view){
        if (view == null){
            return null;
        }
        return (P)view.getLayoutParams();
    }

    public static int getHorizontalPadding(View view){
        if (view == null){
            return 0;
        }
        return view.getPaddingRight()+view.getPaddingLeft();
    }
    public static int getVerticalPadding(View view){
        if (view == null){
            return 0;
        }
        return view.getPaddingTop()+view.getPaddingBottom();
    }

    public static int getHorizontalMargin(View view){
        ViewGroup.LayoutParams layoutParams = getLayoutParams(view);
        if (layoutParams != null && layoutParams instanceof ViewGroup.MarginLayoutParams){
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) layoutParams;
            return params.leftMargin+params.rightMargin;
        }
        return 0;
    }
    public static int getVerticalMargin(View view){
        ViewGroup.LayoutParams layoutParams = getLayoutParams(view);
        if (layoutParams != null && layoutParams instanceof ViewGroup.MarginLayoutParams){
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) layoutParams;
            return params.topMargin+params.bottomMargin;
        }
        return 0;
    }

    /**
     * 加载布局文件
     *
     * @param id      布局文件id
     * @return
     */
    public static View inflate(int id) {
        return inflate(id,null);
    }
    public static View inflate(int id, ViewGroup parent){
        return inflate(id,parent,false);
    }
    public static View inflate(int id, ViewGroup parent, boolean attachToRoot){
        View view = getInflater().inflate(id,parent,attachToRoot);
        return view;
    }
    public static @Nullable
    View inflate(Context context, int layoutId) {
        if (context == null){
            return null;
        }
        return getInflater(context).inflate(layoutId,null);
    }


    //-----------单位转换----------
    /**
     * dp转px
     * 建议使用 {@link UIUtils#dip2px(Context,float)}
     * @param dipValue
     * @return
     */
    public static int dip2px(float dipValue) {
        float scale = getDensity();
        return (int) (dipValue * scale + 0.5f);
    }
    public static int dip2px(Context context, float dipValue){
        float density = getDensity(context);
        return (int) (dipValue * density + 0.5f);
    }

    /**
     * dp转px 并按比例放大
     * 建议使用 {@link UIUtils#dip2px(Context,float,float)}
     * @param dipValue
     * @param toscale
     * @return
     */
    public static int dip2px(float dipValue, float toscale) {
        float scale = getDensity();
        return (int) ((dipValue * scale + 0.5f) * toscale);
    }
    public static int dip2px(Context context, float dipValue, float toscale) {
        float scale = getDensity(context);
        return (int) ((dipValue * scale + 0.5f) * toscale);
    }

    /**
     * px转dp
     * 建议使用 {@link UIUtils#px2dip(Context,float)}
     * @param pxValue
     * @return
     */
    public static int px2dip(float pxValue) {
        float scale = getDensity();
        return (int) (pxValue / scale + 0.5f);
    }
    public static int px2dip(Context context, float pxValue) {
        float scale = getDensity(context);
        return (int) (pxValue / scale + 0.5f);
    }
    /**
     * 将px值转换为sp值，保证文字大小不变
     * 建议使用 {@link UIUtils#px2sp(Context,float)}
     * @param pxValue
     * @return
     */
    public static int px2sp(float pxValue) {
        float scale = getDensity();
        return (int) (pxValue / scale + 0.5f);
    }
    public static int px2sp(Context context, float pxValue) {
        float scale = getDensity(context);
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     * 建议使用 {@link UIUtils#sp2px(Context,float)}
     * @param spValue
     * @return
     */
    public static int sp2px(float spValue) {
        float scale = getDensity();
        return (int) (spValue * scale + 0.5f);
    }
    public static int sp2px(Context context, float spValue) {
        float scale = getDensity(context);
        return (int) (spValue * scale + 0.5f);
    }

    //----------获取宽高部分-----------

    /**
     * 获取指定控件的宽和高
     * @param view
     * @param callBack
     */
    public static void getViewWidthAndHeight(final View view, final WidthAndHeightCallBack callBack) {

        view.post(new Runnable() {
            @Override
            public void run() {
                Rect outRect = new Rect();
                view.getDrawingRect(outRect);
                callBack.widthAndHeight(outRect.width(),outRect.height());
            }
        });

    }
    public interface WidthAndHeightCallBack {
        void widthAndHeight(int width, int height);
    }

    /**
     * 获取屏幕宽度 和高度
     * 建议使用 {@link UIUtils#getScreenWidthAndHeight(Context)}
     */
    @Deprecated
    public static int[] getScreenWidthAndHeight() {
        Display display = getWindownManager().getDefaultDisplay();
        return new int[]{display.getWidth(),display.getHeight()};
    }
    public static int[] getScreenWidthAndHeight(Context context) {
        if (context == null){
            return new int[]{0,0};
        }
        WindowManager windowManager = getWindowManager(context);
        if (windowManager == null){
            return new int[]{0,0};
        }
        DisplayMetrics dm = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(dm);
        return new int[]{dm.widthPixels,dm.heightPixels};
    }

    /**
     * 获取屏幕宽度
     * 建议使用 {@link UIUtils#getScreenWidth(Context)}
     * @return
     */
    public static int getScreenWidth(){
        return getScreenWidthAndHeight()[0];
    }
    public static int getScreenWidth(Context context){
        return getScreenWidthAndHeight(context)[0];
    }

    /**
     * 获取屏幕高度
     * 建议使用 {@link UIUtils#getScreenHeight(Context)}
     * @return
     */
    public static int getScreenHeight(){
        return getScreenWidthAndHeight()[1];
    }
    public static int getScreenHeight(Context context){
        return getScreenWidthAndHeight(context)[1];
    }

    /**
     * 打开 或 关闭 键盘
     *
     * @param openInputKeyBoard true  为打开  ,false 为关闭
     */
    public static void showInputKeyBoard(boolean openInputKeyBoard, View edit) {
        InputMethodManager imm = (InputMethodManager) getAppContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (openInputKeyBoard) {
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        } else {
            imm.hideSoftInputFromWindow(edit.getWindowToken(), 0);
        }
    }

    /**
     * 隐藏 键盘
     * @param activity
     */
    public static void hiddenInput(Activity activity) {
        if(null == activity){
            return;
        }
        try {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            activity.getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        }
    }

    //-------------Toast 工具方法部分---------------

    private static Toast toast;
    /**
     * 短时间显示Toast
     * @param message
     */
    public static void toast(Context context, CharSequence message) {
        if (toast != null){
            toast.cancel();
            toast = null;
        }
        toast = Toast.makeText(context,message, Toast.LENGTH_SHORT);
        toast.show();

    }
    /**
     * 自定义显示Toast时间
     *
     * @param message
     * @param duration
     */
    public static void toast(Context context, CharSequence message, int duration) {
        if (toast != null){
            toast.cancel();
            toast = null;
        }
        toast = Toast.makeText(context, message, duration);
        toast.show();
    }


    //--------------资源相关---------------

    public static float getDimensionPixelSize(Context context, @DimenRes int id){
        if (context == null) {
            return 0;
        }
        return context.getResources().getDimensionPixelSize(id);
    }

    /**
     * 获取字符串
     * @param id
     * @return
     */
    public static String getString(@StringRes int id) {
        return getAppContext().getResources().getString(id);
    }
    public static String getString(Context context, @StringRes int id){
        if (context == null) {
            return "";
        }
        return context.getResources().getString(id);
    }

    /**
     * 获取字符串数组
     * @param id
     * @return
     */
    public static String[] getStringArray(int id) {
        return getAppContext().getResources().getStringArray(id);
    }
    public static String[] getStringArray(Context context, int id){
        if (context == null) {
            return null;
        }
        return context.getResources().getStringArray(id);
    }

    /**
     * 获取颜色
     * @param id
     * @return
     */
    public static @ColorInt
    int getColor(@ColorRes int id) {
        return getAppContext().getResources().getColor(id);
    }
    public static @ColorInt
    int getColor(Context context, @ColorRes int id){
        if (context == null) {
            return 0;
        }
        return context.getResources().getColor(id);
    }

    /**
     * 获取颜色的状态选择器
     * @param id
     * @return
     */
    public static ColorStateList getColorStateList(int id) {
        return getAppContext().getResources().getColorStateList(id);
    }

    /**
     * 获取图片
     * @param id
     * @return drawable对象
     */
    public static Drawable getDrawable(@DrawableRes int id) {
        return getAppContext().getResources().getDrawable(id);
    }
    public static Drawable getDeawable(Context context, @DrawableRes int id){
        if (context == null) {
            return null;
        }
        return context.getResources().getDrawable(id);
    }

    /**
     * 获取 自定义 dialog
     * @return
     */
//    public static Dialog getDialog(Context context, int layoutid) {
//        Dialog dialog = new Dialog(context, R.style.ls_default_user_dialog);
//        dialog.setContentView(layoutid);
//        return dialog;
//    }

    public static RectF getRect(Activity activity, View v) {
        if (activity == null || v == null){
            return null;
        }
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        Rect rectangle= new Rect();
        Window window = activity.getWindow();
        if (window != null) {
            window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
        }
        int left = 0;
        int top = location[1] - rectangle.top;
        int right = rectangle.right;
        int bottom = v.getHeight() + top;
        return new RectF(left , top , right, bottom );
    }

}
