package anim.bro.com.practice.network;

public abstract class GsonBaseCallback<T> {

    public abstract T parseNetworkResponse(String response) throws Exception;

    public abstract void onError();

    public abstract void onResponse(T response);

}