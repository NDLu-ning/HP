package com.graduation.hp.app.event;

public class TestResultEvent {

    private final long mPhysiquId;

    public TestResultEvent(long mPhysiquId) {
        this.mPhysiquId = mPhysiquId;
    }

    public long getmPhysiquId() {
        return mPhysiquId;
    }
}
