package com.graduation.hp.ui.auth.reset;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.view.View;

import com.graduation.hp.R;
import com.graduation.hp.app.constant.Key;
import com.graduation.hp.app.event.AuthEvent;
import com.graduation.hp.core.app.di.component.AppComponent;
import com.graduation.hp.core.repository.http.bean.ResponseCode;
import com.graduation.hp.core.ui.BaseFragment;
import com.graduation.hp.ui.auth.AuthActivity;
import com.graduation.hp.ui.auth.register.RegisterFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import retrofit2.http.Body;

public class UpdatePasswordFragment extends BaseFragment {


    public interface UpdatePasswordFragmentListener {
        void updatePassword(String phoneNumber, String password, String repassword);
    }

    public static UpdatePasswordFragment newInstance(String phoneNumber) {
        UpdatePasswordFragment fragment = new UpdatePasswordFragment();
        Bundle args = new Bundle();
        args.putString(Key.PHONE_NUMBER, phoneNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.update_password_et)
    AppCompatEditText updatePasswordEt;

    @BindView(R.id.update_password_til)
    TextInputLayout updatePasswordTil;

    @BindView(R.id.update_repassword_et)
    AppCompatEditText updateRepasswordEt;

    @BindView(R.id.update_repassword_til)
    TextInputLayout updateRepasswordTil;

    private String phoneNumber;
    private UpdatePasswordFragmentListener mListener;

    @Override
    public void onAttach(Context context) {
        try {
            mListener = (UpdatePasswordFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement InputPhoneFragmentListener");
        }
        super.onAttach(context);
    }

    @Override
    protected void init(Bundle savedInstanceState, View view) {
        phoneNumber = getArguments().getString(Key.PHONE_NUMBER);
        if (TextUtils.isEmpty(phoneNumber)) {
            throw new IllegalArgumentException("PhoneNumber must not be null");
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_update_password;
    }

    @OnClick(R.id.update_password_btn)
    public void updatePassword() {
        if (!isAdded()) return;
        String password = updatePasswordEt.getText().toString();
        String repassword = updateRepasswordEt.getText().toString();
        mListener.updatePassword(phoneNumber, password, repassword);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveRegisterError(AuthEvent event) {
        if (!isAdded() || event.getFragment() != AuthActivity.FRAGMENT_IS_UPDATE_PASSWORD) return;
        if (event.getCode() == ResponseCode.INPUT_PASSWORD_ERROR.getStatus()) {
            updatePasswordTil.setError(event.getMsg());
            updateRepasswordTil.setError("");
        } else if (event.getCode() == ResponseCode.INPUT_REPEAT_PASSWORD_ERROR.getStatus()) {
            updateRepasswordTil.setError(event.getMsg());
            updatePasswordTil.setError("");
        } else if (event.getCode() == ResponseCode.INPUT_NICKNAME_ERROR.getStatus()) {
            updatePasswordTil.setError("");
            updateRepasswordTil.setError(event.getMsg());
        }
        showMessage(event.getMsg());
    }


    @Override
    public boolean useEventBus() {
        return true;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
    }
}
