package com.graduation.hp.ui.auth.reset;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.view.View;

import com.graduation.hp.R;
import com.graduation.hp.app.constant.Key;
import com.graduation.hp.app.event.AuthEvent;
import com.graduation.hp.core.HPApplication;
import com.graduation.hp.core.app.di.component.AppComponent;
import com.graduation.hp.core.repository.http.bean.ResponseCode;
import com.graduation.hp.core.ui.BaseFragment;
import com.graduation.hp.core.utils.VerifyUtils;
import com.graduation.hp.ui.auth.AuthActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnTextChanged;

public class InputPhoneFragment extends BaseFragment {

    private InputPhoneFragmentListener mListener;

    public interface InputPhoneFragmentListener {
        void verifyCode(String phoneNumber, String code);

        void onTryToSendCode(String phone);
    }

    public static InputPhoneFragment newInstance(String phoneNumber) {
        InputPhoneFragment fragment = new InputPhoneFragment();
        Bundle args = new Bundle();
        args.putString(Key.PHONE_NUMBER, phoneNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.register_phone_til)
    TextInputLayout registerPhoneTil;

    @BindView(R.id.register_phone_et)
    AppCompatEditText registerPhoneEt;

    @BindView(R.id.register_code_til)
    TextInputLayout registerCodeTil;

    @BindView(R.id.register_code_et)
    AppCompatEditText registerCodeEt;

    @BindView(R.id.register_send_btn)
    AppCompatButton registerSendBtn;


    private Timer mTimer;
    private CodeTimerTask mTask;

    @Override
    public void onAttach(Context context) {
        try {
            mListener = (InputPhoneFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement InputPhoneFragmentListener");
        }
        super.onAttach(context);
    }

    @Override
    protected void init(Bundle savedInstanceState, View view) {
        registerSendBtn.setOnClickListener(v -> {
            String phone = registerPhoneEt.getText().toString();
            mListener.onTryToSendCode(phone);
            startTimer();
        });
    }

    @OnTextChanged(callback = OnTextChanged.Callback.TEXT_CHANGED, value = {R.id.register_phone_et})
    public void onPhoneTextChange(CharSequence charSequence, int start, int before, int after) {
        registerSendBtn.setEnabled(VerifyUtils.isPhoneVerified(String.valueOf(charSequence)));
    }

    @OnTextChanged(callback = OnTextChanged.Callback.TEXT_CHANGED, value = {R.id.register_code_et})
    public void onCodeTextChange(CharSequence charSequence, int start, int before, int after) {
        String phoneNumber = registerPhoneEt.getText().toString();
        String code = registerCodeEt.getText().toString();
        if(!TextUtils.isEmpty(code)){
            mListener.verifyCode(phoneNumber, code);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_input_phone;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
    }

    @Override
    public boolean useEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceive(AuthEvent event) {
        if (!isAdded() || event.getFragment() != AuthActivity.FRAGMENT_IS_INPUT_PHONE) return;
        if (event.getCode() == ResponseCode.INPUT_PHONE_NUMBER_ERROR.getStatus()) {
            registerPhoneTil.setError(event.getMsg());
        } else if (event.getCode() == ResponseCode.INPUT_CODE_ERROR.getStatus()) {
            registerCodeTil.setError(event.getMsg());
        } else if (event.getCode() == AuthActivity.SEND_SUCCESS) {
        }
    }

    private static class CodeTimerTask extends TimerTask {

        private WeakReference<InputPhoneFragment> mFragments;
        private int count = 60;

        CodeTimerTask(InputPhoneFragment fragment) {
            this.mFragments = new WeakReference<>(fragment);
        }

        @Override
        public void run() {
            InputPhoneFragment fragment = mFragments.get();
            if (fragment != null && !fragment.isAdded()) {
                fragment.getActivity().runOnUiThread(() -> {
                    if (count-- > 0) {
                        fragment.registerCodeEt.setText(count + "秒");
                    } else {
                        fragment.registerCodeEt.setText(HPApplication.getStringById(R.string.action_register_send_code));
                        fragment.stopTimer();
                    }
                    fragment.registerCodeEt.setEnabled(count <= 0);
                });
            } else {
                cancel();
            }
        }
    }

    @Override
    public void onStop() {
        stopTimer();
        super.onStop();
    }

    private void startTimer() {
        mTimer = new Timer();
        mTask = new CodeTimerTask(this);
        mTimer.schedule(mTask, 0, 1000);
    }

    private void stopTimer() {
        if (mTimer != null) {
            mTimer.cancel();
        }
        mTimer = null;
        if (mTask != null) {
            mTask.cancel();
        }
        mTask = null;
    }
}
