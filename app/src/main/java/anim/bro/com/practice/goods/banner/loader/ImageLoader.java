package anim.bro.com.practice.goods.banner.loader;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * 图片代理实现
 */
public class ImageLoader implements ViewLoaderInterface<ImageView> {

    @Override
    public ImageView createView(Context context) {
        return new ImageView(context);
    }

    @Override
    public void onPrepared(Context context, Object path, ImageView imageView) {
        try {
            if (path instanceof Integer) {
                imageView.setImageResource((int) path);
            } else if (path instanceof Uri) {
                String pStr = path.toString();
//                RequestOptions options = new RequestOptions()
//                        .centerCrop()
//                        .diskCacheStrategy(DiskCacheStrategy.ALL);
//                Glide.with(context).load(pStr).apply(options).into(imageView);
                Glide.with(context).load(pStr).into(imageView);

//                String type = pStr.substring(pStr.lastIndexOf("."));
//                File file = new File(Constants.DEFAULT_DOWNLOAD_DIR + File.separator + MD5Util.md5(path.toString()) + type);
//                if (file.exists()) {
//                    Log.e("lake", "onPrepared: isCache");
//                    imageView.setImageURI(Uri.fromFile(file));
//                } else {
//                    Log.e("lake", "onPrepared: noCache");
//                    imageView.setImageResource(R.mipmap.defaultvideobg);
//                }
            } else {
                imageView.setImageBitmap(BitmapFactory.decodeFile((String) path));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy(ImageView imageView) {
        System.gc();
    }
}
