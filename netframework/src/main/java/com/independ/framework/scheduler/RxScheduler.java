package com.independ.framework.scheduler;

import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.Nullable;
import io.reactivex.schedulers.Schedulers;

/**
 * @Package: com.independ.framework.scheduler
 * @ClassName: RxScheduler
 * @Description: 线程调度器
 * @Author: 李嘉伦
 * @CreateDate: 2020/5/14 20:25
 * @Email: 86152
 */
public class RxScheduler implements BaseRxScheduler {

    @Nullable
    private static volatile RxScheduler INSTANCE;

    // Prevent direct instantiation.
    private RxScheduler() {
    }

    public static synchronized RxScheduler getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RxScheduler();
        }
        return INSTANCE;
    }

    @Override
    public Scheduler computation() {
        return Schedulers.computation();
    }

    @Override
    public Scheduler io() {
        return Schedulers.io();
    }

    @Override
    public Scheduler ui() {
        return AndroidSchedulers.mainThread();
    }

    @Override
    public <T> ObservableTransformer<T, T> schedulers() {
        return observable -> observable.subscribeOn(io())
                .observeOn(ui());
    }
}
