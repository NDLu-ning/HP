package com.graduation.hp.ui.auth.reset;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.graduation.hp.app.constant.Key;
import com.graduation.hp.core.app.di.component.AppComponent;
import com.graduation.hp.core.ui.BaseFragment;

public class InputPhoneFragment extends BaseFragment {

    private InputPhoneFragmentListener listener;

    public interface InputPhoneFragmentListener {
        void verifyPhoneNumber(String phoneNumber);
    }

    public static InputPhoneFragment newInstance(String phoneNumber) {
        InputPhoneFragment fragment = new InputPhoneFragment();
        Bundle args = new Bundle();
        args.putString(Key.PHONE_NUMBER, phoneNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        try {
            listener = (InputPhoneFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement InputPhoneFragmentListener");
        }
        super.onAttach(context);
    }

    @Override
    protected void init(Bundle savedInstanceState, View view) {

    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {

    }
}
