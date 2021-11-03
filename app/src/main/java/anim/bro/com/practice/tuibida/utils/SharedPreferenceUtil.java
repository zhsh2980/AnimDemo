package anim.bro.com.practice.tuibida.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * base lib SharedPreference
 * Created by wenlong on 2018/2/23.
 */

public class SharedPreferenceUtil implements SharedPreferences.OnSharedPreferenceChangeListener {

    @SuppressLint("StaticFieldLeak")
    private static SharedPreferenceUtil sInstance;
    private final SharedPreferences mSharedPreferences;

    private Set<SharedPreferences.OnSharedPreferenceChangeListener> mChangeListener =
            Collections.synchronizedSet(new HashSet<SharedPreferences.OnSharedPreferenceChangeListener>());

    private SharedPreferenceUtil(Context context) {
        this.mSharedPreferences = context.getSharedPreferences("base_shared_perf", Context.MODE_PRIVATE);
        this.mSharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    public static synchronized SharedPreferenceUtil getInstance(Context context) {
        if(sInstance == null) {
            sInstance = new SharedPreferenceUtil(context.getApplicationContext());
        }
        return sInstance;
    }

    public SharedPreferences getSharedPreferences(){
        return mSharedPreferences;
    }

    public void addChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
        mChangeListener.add(listener);
    }

    public void removeChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener){
        mChangeListener.remove(listener);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        for (SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener : mChangeListener) {
            onSharedPreferenceChangeListener.onSharedPreferenceChanged(sharedPreferences, key);
        }
    }
}
