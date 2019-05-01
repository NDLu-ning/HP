package com.graduation.hp.core.mvp;

import com.graduation.hp.core.app.event.TokenInvalidEvent;
import com.graduation.hp.core.repository.http.bean.ResponseCode;
import com.graduation.hp.core.repository.http.exception.ApiException;

import org.greenrobot.eventbus.EventBus;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.HttpException;

/**
 * Created by Ning on 2019/4/24.
 */

public abstract class BasePresenter<V extends BaseContact.View
        , M extends BaseContact.Model> implements BaseContact.Presenter<V, M> {

    protected V mMvpView;

    protected M mMvpModel;

    protected State mState = State.STATE_REFRESH;

    boolean debug = true;

    public BasePresenter(M mMvpModel) {
        this.mMvpModel = mMvpModel;
    }

    @Override
    public void onAttach(V mvpView) {
        this.mMvpView = mvpView;
        if (useEventBus()) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDetach() {
        if (useEventBus()) {
            EventBus.getDefault().unregister(this);
        }
        if (mMvpModel != null) {
            mMvpModel.onDestroy();
        }
        mMvpModel = null;
        mMvpView = null;
    }

    @Override
    public void handlerApiError(Throwable throwable) {
        if (debug) {
            throwable.printStackTrace();
        }
        if (throwable instanceof UnknownHostException || throwable instanceof SocketTimeoutException
                || throwable instanceof HttpException) {
            throwable = throwable instanceof UnknownHostException ? new ApiException(ResponseCode.UNKNOWN_SERVER_HOST) :
                    new ApiException(ResponseCode.TIME_OUT);
            handleConnectionException();
        }
        if (throwable instanceof ApiException) {
            ApiException apiException = (ApiException) throwable;
            if (apiException.getCode() == ResponseCode.NOT_NEED_SHOW_MESSAGE.getStatus()) {
                // 可以省略的错误
                return;
            }
            if (apiException.getCode() == ResponseCode.TOKEN_ERROR.getStatus()) {
                EventBus.getDefault().post(TokenInvalidEvent.INSTANCE);
                mMvpView.showError("");
                return;
            }
            if (apiException.getCode() == ResponseCode.DATA_EMPTY.getStatus()) {
                if (isRefresh()) {
                    mMvpView.showEmpty();
                } else {
                    mMvpView.showMessage("已加载全部数据");
                    mState = State.STATE_REFRESH;
                }
                return;
            }
            mMvpView.showError(throwable.getMessage());
            mState = State.STATE_REFRESH;
        }
        mMvpView.dismissDialog();
        mMvpView.showMessage(throwable.getMessage());
    }

    protected void handleConnectionException() {

    }

    protected boolean useEventBus() {
        return false;
    }


    public void setCurState(State curState) {
        this.mState = curState;
    }

    public boolean isRefresh() {
        return this.mState != State.STATE_MORE;
    }
}
