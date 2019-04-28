package com.graduation.hp.core.app.di.qualifier;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by Win on 2018/4/23.
 */

@Qualifier
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface ActivityContext {
}
