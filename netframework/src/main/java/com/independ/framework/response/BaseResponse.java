package com.independ.framework.response;

import java.io.Serializable;

/**
 * Created by 李嘉伦.
 * Date: 2020/5/14
 * Time: 上午 11:09
 */
public class BaseResponse<T> implements Serializable {

    private boolean success;
    private String errMsg;
    private int errCode;
    private T data;

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErroCode(int erroCode) {
        this.errCode = erroCode;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "success=" + success +
                ", errMsg='" + errMsg + '\'' +
                ", data=" + data +
                '}';
    }
}
