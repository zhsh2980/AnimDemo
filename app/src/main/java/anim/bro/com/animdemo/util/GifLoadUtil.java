package anim.bro.com.animdemo.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.gifdecoder.GifDecoder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.Target;

/**
 * Created by zhangshan on 2019/3/8 14:46.
 */
public class GifLoadUtil {

    //handler发送消息成功的状态码
    private static final int MESSAGE_SUCCESS = 4424;
    //handler发送消息所携带的参数（持续时间）
    private int duration;

    private static GifLoadUtil instance = null;

    public static synchronized GifLoadUtil getInstance() {
        // 这个方法比上面有所改进，不用每次都进行生成对象，只是第一次
        // 使用时生成实例，提高了效率！
        if (instance == null)
            instance = new GifLoadUtil();
        return instance;
    }

    /**
     * 加载开门Gif动图(只播放一次)
     *
     * @param view
     */
    public void loadGif(Context context, int resource, ImageView view, final GifListener listener) {
        Glide.with(context)
                .load(resource)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .listener(new RequestListener<Integer, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception arg0, Integer arg1,
                                               Target<GlideDrawable> arg2, boolean arg3) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource,
                                                   Integer model, Target<GlideDrawable> target,
                                                   boolean isFromMemoryCache, boolean isFirstResource) {
                        // 计算动画时长
                        GifDrawable drawable = (GifDrawable) resource;
                        GifDecoder decoder = drawable.getDecoder();
                        for (int i = 0; i < drawable.getFrameCount(); i++) {
                            duration += decoder.getDelay(i);
                        }
                        //发送延时消息，通知动画结束
                        //以下两个参数都是 int 型，记得如上的声明
                        if (listener != null) {
                            listener.gifPlayComplete();
                        }
//                        handler.sendEmptyMessageDelayed(MESSAGE_SUCCESS,
//                                duration);
                        return false;
                    }
                })
                //仅仅加载一次gif动画
                //此处的参数 1 及时指明播放次数
                .into(new GlideDrawableImageViewTarget(view, 1));
    }

    /**
     * Gif播放完毕回调
     */
    public interface GifListener {
        void gifPlayComplete();
    }
}
