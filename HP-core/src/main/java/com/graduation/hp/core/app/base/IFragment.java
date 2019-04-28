package com.graduation.hp.core.app.base;

import android.support.annotation.NonNull;

import com.graduation.hp.core.app.di.component.AppComponent;

public interface IFragment {

    /**
     * 提供 AppComponent (提供所有的单例对象) 给实现类, 进行 Component 依赖
     *
     * @param appComponent
     */
    void setupFragmentComponent(@NonNull AppComponent appComponent);

    /**
     * 是否使用EventBus
     *
     * @return
     */
    boolean useEventBus();

}
