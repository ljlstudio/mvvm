package com.common.framework.commad;


public interface BindingConsumer<T> {
    void call(T t);
}
