package com.graduation.hp.core.app.base.lifecycleable;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by Ning on 2018/11/21.
 */

public interface AppLifecycle {
    void attachBaseContext(@NonNull Context base);

    void onCreate(@NonNull Application application);

    void onTerminate(@NonNull Application application);
}
