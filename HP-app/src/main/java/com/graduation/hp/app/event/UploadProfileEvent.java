package com.graduation.hp.app.event;

public class UploadProfileEvent {

    private String url;

    public UploadProfileEvent() {
    }

    public UploadProfileEvent(String url){
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
