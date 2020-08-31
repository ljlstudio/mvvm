package com.independ.framework.scheduler;

import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;

/**
 * @Package: com.independ.framework.scheduler
 * @ClassName: BaseRxScheduler
 * @Description: java类作用描述
 * @Author: 李嘉伦
 * @CreateDate: 2020/5/14 20:27
 * @Email: 86152
 */
public interface BaseRxScheduler {
    @NonNull
    Scheduler computation();

    @NonNull
    Scheduler io();

    @NonNull
    Scheduler ui();

    @NonNull
    <T> ObservableTransformer<T, T> schedulers();
}
