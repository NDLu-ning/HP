package com.graduation.hp.core.app.base.lifecycleable;

import android.support.annotation.NonNull;

import io.reactivex.subjects.Subject;

public interface Lifecycleable<E> {
    @NonNull
    Subject<E> provideLifecycleSubject();
}