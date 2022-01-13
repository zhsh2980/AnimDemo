package anim.bro.com.practice.network;

/**
 * Created by zhangshan on 2021/12/31 16:02.
 * Description：请添加类注释
 */
public class OKhttpUtil {

    public static OKhttpUtil getInstance() {
        return new OKhttpUtil();
    }

    public void execute(String response, GsonBaseCallback callback) {
        try {
            Object o = callback.parseNetworkResponse(response);
            sendSuccessResultCallback(o, callback);
        } catch (Exception e) {
        }

    }

    public void sendSuccessResultCallback(final Object object, final GsonBaseCallback callback) {
        if (callback == null) return;
        callback.onResponse(object);
    }
}
