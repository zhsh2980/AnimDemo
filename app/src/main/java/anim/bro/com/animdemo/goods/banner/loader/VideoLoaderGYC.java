package anim.bro.com.animdemo.goods.banner.loader;

import android.content.Context;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;


/**
 * 视频代理实现
 */
public class VideoLoaderGYC implements VideoViewLoaderInterface {
    private StandardGSYVideoPlayer videoView;

    @Override
    public StandardGSYVideoPlayer createView(Context context) {
        if (videoView == null) {
            videoView = new StandardGSYVideoPlayer(context);
        }
        return videoView;
    }

    @Override
    public void onPrepared(Context context, Object path, StandardGSYVideoPlayer videoView) {
        try {
//            videoView.setBackgroundColor(Color.TRANSPARENT);
//            videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
//                @Override
//                public boolean onError(MediaPlayer mp, int what, int extra) {
//                    //视频读取失败！
//                    return true;
//                }
//            });
            if (path instanceof String) {

                videoView.setUp((String) path, true, "测试视频");


//                videoView.fullscreenButton.setVisibility(View.INVISIBLE);
//                videoView.setVideoImageDisplayType(Jzvd.VIDEO_IMAGE_DISPLAY_TYPE_FILL_SCROP);
////                RequestOptions options = new RequestOptions()
////                        .centerCrop()
////                        .fitCenter()
////                        .diskCacheStrategy(DiskCacheStrategy.NONE);
//                ImageView thumbImageView = videoView.thumbImageView;
//                Glide.with(context).load(R.drawable.anim_box_3).into(thumbImageView);//封面
////                Glide.with(context).load("http://img.zcool.cn/community/01700557a7f42f0000018c1bd6eb23.jpg").into(videoView.thumbImageView);
//
//                videoView.setUp((String) path, "");
//                videoView.setVideoPath((String) path);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startVideo(Context context, StandardGSYVideoPlayer videoView) {
//        videoView.seekTo(0);
        if (videoView != null) {
            videoView.startPlayLogic();
        }
    }

    @Override
    public void resumeVideo(Context context, StandardGSYVideoPlayer view) {
        if (view != null) {
            view.onVideoResume();
        }
    }

    @Override
    public void pauseVideo(StandardGSYVideoPlayer view) {
        if (view != null) {
            view.onVideoPause();
        }
    }

    @Override
    public void onDestroy(StandardGSYVideoPlayer videoView) {
        GSYVideoManager.releaseAllVideos();
    }
}
