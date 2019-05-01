package com.graduation.hp.core.repository.http.bean;

public enum ResponseCode {
    ERROR(0, "ERROR"),
    NOT_NEED_SHOW_MESSAGE(1000, "无关紧要的消息"),
    SUCCESS(200, "SUCCESS"),
    TIME_OUT(3002, "请求超时"),
    DATA_NULL(404, "数据暂无"),
    ILLEGAL_ARGUMENT(500, "参数无效"),
    TOKEN_ERROR(600, "尚未登录或登录令牌失效"),
    UNKNOW_ERROR(900, "系统错误"),
    UNKNOWN_SERVER_HOST(902, "未知域名"),
    INPUT_USERNAME_ERROR(9001, "用户名输入长度小于6个字符"),
    INPUT_PASSWORD_ERROR(9002, "密码输入长度小于6个字符"),
    INPUT_REPASSWORD_ERROR(9003, "确认密码输入长度小于6个字符"),
    NOT_SAME_ERROR(9004, "两次密码不一致"),
    INPUT_PHONENUMBER_ERROR(9005, "手机号码输入不符合规则"),
    DATA_EMPTY(10001, "数据集为空");
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
