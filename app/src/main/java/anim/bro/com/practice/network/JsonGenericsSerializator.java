package anim.bro.com.practice.network;

import com.google.gson.Gson;
import com.hjq.gson.factory.GsonFactory;

/**
 * Created by JimGong on 2016/6/23.
 */
public class JsonGenericsSerializator implements IGenericsSerializator {
    //    Gson mGson = new Gson();
    Gson mGson = GsonFactory.getSingletonGson();

    @Override
    public <T> T transform(String response, Class<T> classOfT) {
        return mGson.fromJson(response, classOfT);
    }
}
