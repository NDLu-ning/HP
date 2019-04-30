package com.graduation.hp.ui.auth.reset;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;

import com.graduation.hp.R;
import com.graduation.hp.app.constant.Key;
import com.graduation.hp.core.app.di.component.AppComponent;
import com.graduation.hp.core.ui.BaseFragment;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
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

    @Override
    protected void init(Bundle savedInstanceState, View view) {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_update_password;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
    }
}
