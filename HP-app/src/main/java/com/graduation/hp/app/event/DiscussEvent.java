package com.graduation.hp.app.event;

public class  DiscussEvent {

    private boolean added;

    public DiscussEvent(boolean added) {
        this.added = added;
    }

    public boolean isAdded() {
        return added;
    }
}
