package com.independ.framework.exception;

/**
 * Created by 李嘉伦.
 * Date: 2020/5/14
 * Time: 下午 2:16
 * 服务器异常
 */
public class ApiException extends Throwable {
    private int code;
    private String displayMessage;

    public ApiException(int code, String displayMessage) {
        this.code = code;
        this.displayMessage = displayMessage;
    }

    public ApiException(int code, String message, String displayMessage) {
        super(message);
        this.code = code;
        this.displayMessage = displayMessage;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDisplayMessage() {
        return displayMessage;
    }

    public void setDisplayMessage(String displayMessage) {
        this.displayMessage = displayMessage;
    }

    @Override
    public String getMessage() {
        return displayMessage;
    }
}
