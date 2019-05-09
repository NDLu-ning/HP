package com.graduation.hp.app.event;

public enum PublishEvent {

    INVITATION_SUCCESS(202);

    private final int code;

    PublishEvent(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
