package anim.bro.com.animdemo.goods.banner.loader;

import android.content.Context;
import android.widget.VideoView;

import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

/**
 * 视频加载代理接口
 */
public interface VideoViewLoaderInterface extends ViewLoaderInterface<StandardGSYVideoPlayer>{

    void startVideo(Context context, StandardGSYVideoPlayer view);

    void resumeVideo(Context context, StandardGSYVideoPlayer view);

    void pauseVideo(StandardGSYVideoPlayer view);

}
