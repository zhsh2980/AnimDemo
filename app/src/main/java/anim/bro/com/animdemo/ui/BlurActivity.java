package anim.bro.com.animdemo.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import anim.bro.com.animdemo.R;
import anim.bro.com.animdemo.util.UIUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.blurry.Blurry;

public class BlurActivity extends AppCompatActivity {

    @BindView(R.id.iv_blur)
    ImageView ivBlur;
    @BindView(R.id.frame_root)
    FrameLayout frameRoot;
    @BindView(R.id.btn_blur)
    Button btnBlur;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blur);
        ButterKnife.bind(this);
        // 间接调用

//        doBlur();
    }


    @OnClick(R.id.btn_blur)
    public void onViewClicked() {
        Log.d("BlurActivity", "点我了");
        doBlur();
    }

    private void doBlur() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.blur);
//        ivBlur.setImageBitmap(bg2WhiteBitmap(bitmap));
        //for ViewGroup
//        Blurry.with(this)
//                .radius(10)//模糊半径
//                .sampling(8)//缩放大小，先缩小再放大
//                .color(Color.argb(66, 255, 255, 0))//颜色
//                .async()//是否异步
//                .animate(500)//显示动画，目前仅支持淡入淡出，默认时间是300毫秒，仅支持传入控件为ViewGroup
//                .onto(frameRoot);
//        //for view
//        Blurry.with(this)
//                .radius(10)//模糊半径
//                .sampling(8)//缩放大小，先缩小再放大
//                .color(Color.argb(66, 255, 255, 0))//颜色
//                .async()//是否异步
//                .capture(view)//传入View
//                .into(view);//显示View
//        //for bitmap
        Blurry.with(this)
                .radius(8)//模糊半径
                .sampling(8)//缩放大小，先缩小再放大
//                .color(Color.argb(66, 255, 255, 0))//颜色
                .async()//是否异步
                .from(bg2WhiteBitmap(bitmap))//传入bitmap
                .into(ivBlur);//显示View

    }


    /**
     * 设置bitmap四周白边
     *
     * @param bitmap 原图
     * @return
     */
    public Bitmap bg2WhiteBitmap(Bitmap bitmap) {
        int size = bitmap.getWidth() < bitmap.getHeight() ? bitmap.getWidth() : bitmap.getHeight();
        int numWidth = UIUtils.dip2px(9 * 4);
        int numHeight = UIUtils.dip2px(16 * 4);
//        int sizebig = size + num;
        int sizeWidthBig = bitmap.getWidth() + numWidth;
        int sizeHeightBig = bitmap.getHeight() + numHeight;
        // 背图
        Bitmap newBitmap = Bitmap.createBitmap(sizeWidthBig, sizeHeightBig, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        // 生成白色的
        paint.setColor(getResources().getColor(R.color.blur_bg));
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP));

        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawBitmap(bitmap, numWidth / 2, numHeight / 2, paint);
        // 画正方形的
        canvas.drawRect(0, 0, sizeWidthBig, sizeHeightBig, paint);
        return newBitmap;
    }


}
