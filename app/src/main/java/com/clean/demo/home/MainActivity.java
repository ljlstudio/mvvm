package com.clean.demo.home;


import android.os.Bundle;
import android.widget.Toast;

import com.clean.demo.App;
import com.common.framework.basic.BaseActivity;
import com.clean.demo.BR;
import com.clean.demo.R;
import com.clean.demo.databinding.ActivityMainBinding;


public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> {



    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    public int initVariableId() {
        return BR.mainViewModel;
    }

    @Override
    public void initViewObservable() {
        binding.getMainViewModel().fi.observe(this, aBoolean -> {
            Toast.makeText(App.getInstance(), "view+model+viewmodel通信", Toast.LENGTH_LONG);
        });

    }

    @Override
    public void initParam() {
        super.initParam();

    }


}
