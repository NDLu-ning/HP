package com.graduation.hp.ui.auth.register;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.graduation.hp.R;
import com.graduation.hp.core.app.di.component.AppComponent;
import com.graduation.hp.core.repository.http.bean.ResponseCode;
import com.graduation.hp.core.repository.http.exception.ApiException;
import com.graduation.hp.core.ui.BaseFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Ning on 2019/4/24.
 */

public class RegisterFragment extends BaseFragment {

    public interface RegisterFragmentListener {
        void onTryToSignup(String username, String password, String repassword, String phoneNumber);
    }


    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @Override
    protected void init(Bundle savedInstanceState, View view) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_register;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public boolean useEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveInputError(ApiException exception) {

    }
}
