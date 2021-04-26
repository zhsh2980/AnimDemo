//package anim.bro.com.animdemo.goods.banner.loader;
//
//import android.content.Context;
//import android.graphics.Color;
//import android.graphics.PixelFormat;
//import android.media.MediaPlayer;
//import android.net.Uri;
//import android.util.Log;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.VideoView;
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.load.engine.DiskCacheStrategy;
//
//import anim.bro.com.animdemo.R;
//import cn.jzvd.Jzvd;
//
///**
// * 视频代理实现
// */
//public class VideoLoader implements VideoViewLoaderInterface {
//    private MyJzvdStd videoView;
//
//    @Override
//    public MyJzvdStd createView(Context context) {
//        if (videoView == null) {
//            videoView = new MyJzvdStd(context);
//        }
//        return videoView;
//    }
//
//    @Override
//    public void onPrepared(Context context, Object path, MyJzvdStd videoView) {
//        try {
////            videoView.setBackgroundColor(Color.TRANSPARENT);
////            videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
////                @Override
////                public boolean onError(MediaPlayer mp, int what, int extra) {
////                    //视频读取失败！
////                    return true;
////                }
////            });
//            if (path instanceof String) {
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
////                videoView.setVideoPath((String) path);
//            } else if (path instanceof Uri) {
//                Log.e("lake", "onPrepared: noCache");
////                videoView.setVideoURI((Uri) path);
//
////                String pStr = path.toString();
////                String type = pStr.substring(pStr.lastIndexOf("."));
////                File file = new File(Constants.DEFAULT_DOWNLOAD_DIR + File.separator + MD5Util.md5(path.toString()) + type);
////                if (file.exists()) {
////                    Log.e("lake", "onPrepared: isCache");
////                    videoView.setVideoPath(file.getAbsolutePath());
////                } else {
////                    Log.e("lake", "onPrepared: noCache");
////                    videoView.setVideoURI((Uri) path);
////                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void startVideo(Context context, MyJzvdStd videoView) {
////        videoView.seekTo(0);
//        if (videoView != null) {
//            videoView.startVideo();
//        }
//    }
//
//    @Override
//    public void resumeVideo(Context context, MyJzvdStd view) {
//        if (view != null) {
//            view.resumeVideo();
//        }
//    }
//
//    @Override
//    public void pauseVideo(MyJzvdStd view) {
//        if (view != null) {
//            view.pauseVideo();
//        }
//    }
//
//    @Override
//    public void onDestroy(MyJzvdStd videoView) {
//        if (videoView != null) {
//            videoView.destroyVideo();
//        }
//    }
//}
