package anim.bro.com.practice.network;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;


/**
 * Created by JimGong on 2016/6/23.
 */

public abstract class GenericsCallback<T extends GsonBaseModel> extends GsonBaseCallback<T> {
    JsonGenericsSerializator mGenericsSerializator = new JsonGenericsSerializator();

    @Override
    public T parseNetworkResponse(String response) throws IOException {
        String string = response;
        Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        T bean = mGenericsSerializator.transform(string, entityClass);
        return bean;
    }

}
