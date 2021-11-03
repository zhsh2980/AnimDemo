//package anim.bro.com.animdemo.goods.banner.loader;
//
//import android.content.Context;
//import android.graphics.Color;
//import android.graphics.PixelFormat;
//import android.media.MediaPlayer;
//import android.net.Uri;
//import android.util.Log;
//import android.widget.VideoView;
//
///**
// * 视频代理实现
// */
//public class VideoLoaderOld implements VideoViewLoaderInterface {
//    private VideoView videoView;
//
//    @Override
//    public VideoView createView(Context context) {
//        if (videoView == null) {
//            videoView = new VideoView(context);
//        }
//        return videoView;
//    }
//
//    @Override
//    public void onPrepared(Context context, Object path, VideoView videoView) {
//        try {
//            videoView.setBackgroundColor(Color.TRANSPARENT);
//            videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
//                @Override
//                public boolean onError(MediaPlayer mp, int what, int extra) {
//                    //视频读取失败！
//                    return true;
//                }
//            });
//            if (path instanceof String) {
//                videoView.setVideoPath((String) path);
//            } else if (path instanceof Uri) {
//
//                Log.e("lake", "onPrepared: noCache");
//                videoView.setVideoURI((Uri) path);
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
//    public void startVideo(Context context, VideoView videoView) {
////        videoView.seekTo(0);
//        videoView.setZOrderOnTop(true);
//        videoView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
//        videoView.start();
//    }
//
//    @Override
//    public void pauseVideo(VideoView view) {
//        view.pause();
//    }
//
//    @Override
//    public void onDestroy(VideoView videoView) {
//        videoView.stopPlayback();
////        System.gc();
//    }
//}
