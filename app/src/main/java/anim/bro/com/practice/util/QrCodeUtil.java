package anim.bro.com.practice.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.Hashtable;

/**
 * Created by zhangshan on 2021/12/7 20:56.
 * Description：二维码工具
 * https://blog.csdn.net/xch_yang/article/details/82147461
 */
public class QrCodeUtil {


    /**
     * @param content    字符串内容
     * @param width      二维码宽度
     * @param height     二维码高度
     * @param logoBitmap logo图片
     * @return 调用：
     * createQRCodeBitmap(content, 800, 800, logoBitmap);
     * logo图片需要转为Bitmap格式传入，将项目资源下的图片转为Bitmap可使用如下方法:
     * Resources res = getResources();
     * Bitmap logoBitmap= BitmapFactory.decodeResource(res,R.mipmap.logo);
     */
    public static Bitmap createQRCodeBitmap(String content, int width, int height, Bitmap logoBitmap) {
        return createQRCodeBitmap(content, width, height, "UTF-8", ErrorCorrectionLevel.H,
                1, Color.BLACK, Color.WHITE, logoBitmap, 0.2F, null);
    }

    /**
     * 生成自定义二维码
     *
     * @param content                字符串内容
     * @param width                  二维码宽度
     * @param height                 二维码高度
     * @param character_set          编码方式（一般使用UTF-8）
     * @param error_correction_level 容错率 L：7% M：15% Q：25% H：35%
     * @param margin                 空白边距（二维码与边框的空白区域）
     * @param color_black            黑色色块
     * @param color_white            白色色块
     * @param logoBitmap             logo图片（传null时不添加logo）
     * @param logoPercent            logo所占百分比
     * @param bitmap_black           用来代替黑色色块的图片（传null时不代替）
     * @return
     */
    public static Bitmap createQRCodeBitmap(String content, int width, int height, String character_set, ErrorCorrectionLevel error_correction_level,
                                            int margin, int color_black, int color_white, Bitmap logoBitmap, float logoPercent, Bitmap bitmap_black) {
        // 字符串内容判空
        if (TextUtils.isEmpty(content)) {
            return null;
        }
        // 宽和高>=0
        if (width < 0 || height < 0) {
            return null;
        }
        try {
            /** 1.设置二维码相关配置,生成BitMatrix(位矩阵)对象 */
            Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
            // 字符转码格式设置
            if (!TextUtils.isEmpty(character_set)) {
                hints.put(EncodeHintType.CHARACTER_SET, character_set);
            }
            // 容错率设置
            if (error_correction_level != null) {
                hints.put(EncodeHintType.ERROR_CORRECTION, error_correction_level);
            }
            // 空白边距设置
            if (margin > 0) {
                hints.put(EncodeHintType.MARGIN, margin);
            }
            /** 2.将配置参数传入到QRCodeWriter的encode方法生成BitMatrix(位矩阵)对象 */
            BitMatrix bitMatrix = new QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);

            /** 3.创建像素数组,并根据BitMatrix(位矩阵)对象为数组元素赋颜色值 */
            if (bitmap_black != null) {
                //从当前位图按一定的比例创建一个新的位图
                bitmap_black = Bitmap.createScaledBitmap(bitmap_black, width, height, false);
            }
            int[] pixels = new int[width * height];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    //bitMatrix.get(x,y)方法返回true是黑色色块，false是白色色块
                    if (bitMatrix.get(x, y)) {// 黑色色块像素设置
                        if (bitmap_black != null) {//图片不为null，则将黑色色块换为新位图的像素。
                            pixels[y * width + x] = bitmap_black.getPixel(x, y);
                        } else {
                            pixels[y * width + x] = color_black;
                        }
                    } else {
                        pixels[y * width + x] = color_white;// 白色色块像素设置
                    }
                }
            }

            /** 4.创建Bitmap对象,根据像素数组设置Bitmap每个像素点的颜色值,并返回Bitmap对象 */
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);

            /** 5.为二维码添加logo图标 */
            if (logoBitmap != null) {
                return addLogo(bitmap, logoBitmap, logoPercent);
            }
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 向二维码中间添加logo图片(图片合成)
     *
     * @param srcBitmap   原图片（生成的简单二维码图片）
     * @param logoBitmap  logo图片
     * @param logoPercent 百分比 (用于调整logo图片在原图片中的显示大小, 取值范围[0,1] )
     *                    原图片是二维码时,建议使用0.2F,百分比过大可能导致二维码扫描失败。
     * @return
     */
    @Nullable
    private static Bitmap addLogo(@Nullable Bitmap srcBitmap, @Nullable Bitmap logoBitmap, float logoPercent) {
        if (srcBitmap == null) {
            return null;
        }
        if (logoBitmap == null) {
            return srcBitmap;
        }
        //传值不合法时使用0.2F
        if (logoPercent < 0F || logoPercent > 1F) {
            logoPercent = 0.2F;
        }

        /** 1. 获取原图片和Logo图片各自的宽、高值 */
        int srcWidth = srcBitmap.getWidth();
        int srcHeight = srcBitmap.getHeight();
        int logoWidth = logoBitmap.getWidth();
        int logoHeight = logoBitmap.getHeight();

        /** 2. 计算画布缩放的宽高比 */
        float scaleWidth = srcWidth * logoPercent / logoWidth;
        float scaleHeight = srcHeight * logoPercent / logoHeight;

        /** 3. 使用Canvas绘制,合成图片 */
        Bitmap bitmap = Bitmap.createBitmap(srcWidth, srcHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(srcBitmap, 0, 0, null);
        canvas.scale(scaleWidth, scaleHeight, srcWidth / 2, srcHeight / 2);
        canvas.drawBitmap(logoBitmap, srcWidth / 2 - logoWidth / 2, srcHeight / 2 - logoHeight / 2, null);

        return bitmap;
    }


}