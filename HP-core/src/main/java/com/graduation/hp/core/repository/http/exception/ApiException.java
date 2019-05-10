package com.graduation.hp.core.repository.http.exception;


import com.graduation.hp.core.repository.http.bean.ResponseCode;

/**
 * 自定义系统数据访问异常类
 * Created by Ning on 2018/11/20.
 */

public class ApiException extends Exception {

    private final int code;

    public ApiException(ResponseCode apiCode) {
        super(apiCode.getMsg());
        this.code = apiCode.getStatus();
    }

    public ApiException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
