package com.graduation.hp.core.repository.http.bean;

public enum ResponseCode {
    ERROR(0, "ERROR"),
    SUCCESS(200, "SUCCESS"),
    FOCUS_ON(200, "关注成功"),
    ALREADY_FOCUS_ON(201, "已关注该作者"),
    HAVE_NOT_FOCUS_ON(200, "未关注该作者"),
    CANCEL_FOCUS_ON(205, "取消关注成功"),
    TIME_OUT(302, "请求超时"),
    DATA_NULL(404, "数据暂无"),
    TOKEN_ERROR(600, "尚未登录或登录令牌失效"),
    UNKNOWN_ERROR(900, "系统错误"),
    UNKNOWN_SERVER_HOST(902, "未知域名"),

    NOT_NEED_SHOW_MESSAGE(1000, "无关紧要的消息"),
    ILLEGAL_ARGUMENT(5000, "参数无效"),

    INPUT_PHONE_NUMBER_ERROR(9001, "手机号码输入不符合规则"),
    INPUT_PASSWORD_ERROR(9003, "密码输入长度应大于6个字符，小于16个字符"),
    NOT_SAME_ERROR(9004, "两次密码不一致"),
    INPUT_REPEAT_PASSWORD_ERROR(9004, "确认密码输入长度应大于6个字符，小于16个字符"),
    INPUT_NICKNAME_ERROR(9005, "昵称输入长度应大于6个字符，小于10个字符"),
    INPUT_CODE_ERROR(9006,"验证码输入错误"),
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
