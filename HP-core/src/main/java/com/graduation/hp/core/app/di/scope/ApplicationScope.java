package com.graduation.hp.core.app.di.scope;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Win on 2018/5/2.
 */
@Scope
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface ApplicationScope {
}
