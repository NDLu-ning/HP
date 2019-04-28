package com.graduation.hp.core.repository.http.bean;

public enum ResponseCode {
    ERROR(0, "ERROR"),
    NOT_NEED_SHOW_MESSAGE(1000, "无关紧要的消息"),
    SUCCESS(200, "SUCCESS"),
    TIME_OUT(3002, "请求超时"),
    TOKEN_ERROR(302, "尚未登录或登录令牌失效"),
    DATA_NULL(404, "数据暂无"),
    ILLEGAL_ARGUMENT(500, "参数无效"),
    UNKNOW_ERROR(900, "系统错误"),
    UNKNOWN_SERVER_HOST(902, "未知域名");


    private final int status;
    private final String msg;

    ResponseCode(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }
}
