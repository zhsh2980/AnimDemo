package anim.bro.com.practice.base.kotlin.base.java;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

import anim.bro.com.practice.base.kotlin.viewbinding.base.ViewBindingUtil;

public abstract class BindingActivity<VB extends ViewBinding> extends AppCompatActivity {

    private VB binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ViewBindingUtil.INSTANCE.inflateWithGeneric(this, getLayoutInflater());
        setContentView(binding.getRoot());
    }

    public VB getBinding() {
        return binding;
    }
}