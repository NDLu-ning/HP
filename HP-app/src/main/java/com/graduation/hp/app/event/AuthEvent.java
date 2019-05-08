package com.graduation.hp.app.event;

import com.graduation.hp.ui.auth.AuthActivity;

public class AuthEvent {

    private int code;
    private int fragment;
    private String msg;

    public AuthEvent() {
        this.code = 200;
        this.msg = "";
        this.fragment = AuthActivity.FRAGMENT_IS_SIGN_IN;
    }

    public AuthEvent(int fragment, int code, String msg) {
        this.fragment = fragment;
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public int getFragment() {
        return fragment;
    }

    public String getMsg() {
        return msg;
    }
}
