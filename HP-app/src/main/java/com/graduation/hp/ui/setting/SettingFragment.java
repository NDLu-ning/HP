package com.graduation.hp.ui.setting;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.View;

import com.graduation.hp.R;
import com.graduation.hp.app.event.TextSizeEvent;
import com.graduation.hp.core.app.di.component.AppComponent;
import com.graduation.hp.core.app.listener.SimpleItemClickListenerAdapter;
import com.graduation.hp.core.ui.BaseFragment;
import com.graduation.hp.core.utils.DialogUtils;
import com.graduation.hp.core.utils.PhoneUtils;
import com.graduation.hp.repository.http.entity.vo.UserVO;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

import static com.graduation.hp.ui.setting.SettingActivity.TEXTSIZE;

public class SettingFragment extends BaseFragment {

    public interface SettingFragmentListener {
        void getCurrentUserInfo();

        int getTextSize();

        void getAppVersion();

        void updateTextSize();

        void operateUserLayout(boolean isCurUserLogin);

        void showAboutPage();
    }

    @BindView(R.id.setting_text_tv)
    AppCompatTextView settingTextTv;

    @BindView(R.id.setting_clear_tv)
    AppCompatTextView settingClearTv;

    @BindView(R.id.setting_update_tv)
    AppCompatTextView settingUpdateTv;

    @BindView(R.id.setting_user_tv)
    AppCompatTextView settingUserTv;

    @BindView(R.id.setting_user_status_tv)
    AppCompatTextView settingUserStatusTv;

    private SettingFragmentListener mCallback;
    private int textSize = -1;
    private UserVO mUser;


    public static SettingFragment newInstance() {
        return new SettingFragment();
    }

    @Override
    public void onAttach(Context context) {
        try {
            mCallback = (SettingFragment.SettingFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement LoginFragmentListener");
        }
        super.onAttach(context);
    }

    @Override
    protected void init(Bundle savedInstanceState, View view) {
        super.init(savedInstanceState, view);
        mCallback.getCurrentUserInfo();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_setting;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
    }

    @Subscribe(threadMode = ThreadMode.POSTING, priority = 3)
    public void onGetUserInfoSuccess(UserVO user) {
        if (!isAdded()) return;
        this.mUser = user;
        getActivity().runOnUiThread(() -> setSettingView());
    }

    @Subscribe(threadMode = ThreadMode.POSTING, priority = 3)
    public void operateLocalTextSizeSuccess(TextSizeEvent textSizeEvent) {
        if (!isAdded()) return;
        EventBus.getDefault().cancelEventDelivery(textSizeEvent);
        this.textSize = textSizeEvent.textSize;
        getActivity().runOnUiThread(() -> settingTextTv.setText(TEXTSIZE[textSize]));
    }

    @OnClick({R.id.setting_clear_cl, R.id.setting_update_cl, R.id.setting_about_cl, R.id.setting_text_cl, R.id.setting_user_cl})
    public void onSettingFunctionClick(View v) {
        switch (v.getId()) {
            case R.id.setting_clear_cl:
                clearCache();
                break;
            case R.id.setting_update_cl:
                mCallback.getAppVersion();
                break;
            case R.id.setting_text_cl:
                mCallback.updateTextSize();
                break;
            case R.id.setting_user_cl:
                mCallback.operateUserLayout(mUser != null);
                break;
            case R.id.setting_about_cl:
                mCallback.showAboutPage();
                break;
        }
    }

    private void setSettingView() {
        try {
            settingUserTv.setText(!TextUtils.isEmpty(mUser.getNickname()) ? mUser.getNickname() : getString(R.string.tips_setting_user));
            settingUserStatusTv.setText(mUser != null ? getString(R.string.tips_setting_logout) : getString(R.string.tips_setting_login));
            settingTextTv.setText(TEXTSIZE[textSize == -1 ? mCallback.getTextSize() : textSize]);
            settingClearTv.setText(PhoneUtils.getCacheSize(getContext().getCacheDir(), getContext().getExternalCacheDir()));
            settingUpdateTv.setText(PhoneUtils.getAppVersion());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearCache() {
        if (!isAdded()) return;
        DialogUtils.showConfirmDialog(getContext(), getString(R.string.tips_dialog_title), getString(R.string.tips_want_to_clear_cache), new SimpleItemClickListenerAdapter() {
            @Override
            public void OnItemClick(View view, int position) {
                if (position == 1) {
                    PhoneUtils.clearAllCache(getContext());
                    setSettingView();
                }
            }
        });
    }

    @Override
    public boolean useEventBus() {
        return true;
    }
}
