package com.graduation.hp.ui.auth.reset;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.view.View;

import com.graduation.hp.R;
import com.graduation.hp.app.constant.Key;
import com.graduation.hp.core.app.di.component.AppComponent;
import com.graduation.hp.core.ui.BaseFragment;
import com.graduation.hp.ui.auth.register.RegisterFragment;

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

    @BindView(R.id.update_repassword_et)
    AppCompatEditText updateRepasswordEt;

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


    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
    }
}
