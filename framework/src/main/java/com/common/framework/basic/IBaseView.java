package com.common.framework.basic;

import android.content.Intent;

public interface IBaseView {
    /**
     * 初始化界面传递参数
     */
    void initParam();

    /**
     * 初始化数据
     */
    void initView();

    /**
     * 初始化界面观察者的监听
     */
    void initViewObservable();

}
