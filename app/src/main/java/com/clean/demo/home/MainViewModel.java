package com.clean.demo.home;
import android.app.Application;
import androidx.annotation.NonNull;
import com.common.framework.basic.BaseViewModel;
import com.common.framework.bus.SingleLiveEvent;

/**
 * Created by lijialun on 2020/8/19.
 * Describe ：
 **/
public class MainViewModel extends BaseViewModel {

    public SingleLiveEvent<Boolean> fi = new SingleLiveEvent<>();


    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public void showCircle() {
        fi.call();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
