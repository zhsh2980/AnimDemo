package anim.bro.com.practice.base.kotlin.base.java;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.viewbinding.ViewBinding;

import anim.bro.com.practice.base.kotlin.viewbinding.base.ViewBindingUtil;

public abstract class BindingDialog<VB extends ViewBinding> extends Dialog {

    private VB binding;

    public BindingDialog(@NonNull Context context) {
        this(context, 0);
    }

    public BindingDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ViewBindingUtil.INSTANCE.inflateWithGeneric(this, getLayoutInflater());
        setContentView(binding.getRoot());
    }

    public VB getBinding() {
        return binding;
    }
}