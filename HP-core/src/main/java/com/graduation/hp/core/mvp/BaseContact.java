package com.graduation.hp.core.mvp;

import io.reactivex.disposables.Disposable;

public interface BaseContact {

    interface View {

        /**
         * 显示信息
         *
         * @param msg
         */
        void showMessage(String msg);

        /**
         * 取消自定义加载框
         */
        void dismissDialog();

        /**
         * 显示自定义加载框
         */
        void showDialog(String message);

        /**
         * 显示加载页面
         */
        void showLoading();

        /**
         * 显示错误/异常页面
         */
        void showError(String msg);

        /**
         * 显示空页面
         */
        void showEmpty();

        /**
         * 显示主页面
         */
        void showMain();

        /**
         * 跳转登录界面
         */
        void skipToLoginPage();
    }

    interface Presenter<V extends View, M extends Model> {

        /**
         * 绑定View
         *
         * @param mvpView 实现MvpView的页面
         */
        void onAttach(V mvpView);

        /**
         * 解除绑定
         */
        void onDetach();

        /**
         * 统一处理请求异常
         *
         * @param apiException
         */
        void handlerApiError(Throwable apiException);
    }


    interface Model {

        /**
         * 是否可以取消请求
         *
         * @return
         */
        boolean canCancelable();

        /**
         * 统一管理Retrofit请求生命周期
         *
         * @param disposable
         */
        void addSubscribe(Disposable disposable);

        /**
         * 取消订阅
         */
        void cancelSubscribe();

        /**
         *
         */
        void onDestroy();
    }
}
