package com.graduation.hp.repository.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.graduation.hp.HPApplication;
import com.graduation.hp.repository.http.entity.User;

public class PreferencesHelperImpl implements PreferencesHelper {

    private final SharedPreferences mPreferences;

    private PreferencesHelperImpl(Context context) {
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static PreferencesHelperImpl getInstance() {
        return PreferencesHelperHolder.INSTANCE;
    }

    @Override
    public String getCurrentUserToken() {
        return mPreferences.getString(SharedPrefsKey.APP_CURRENT_USER_TOKEN, "");
    }

    @Override
    public void updateCurrentUserToken(String token) {
        mPreferences.edit().putString(SharedPrefsKey.APP_CURRENT_USER_TOKEN, token).apply();
    }

    @Override
    public void saveCurrentUserInfo(User user) {
//        setCurrentUserIcon();
//        setCurrentUserNickname();
//        setCurrentUserBMI();
//        setCurrentUserHealthyNum();
    }

    @Override
    public String getCurrentUserIcon() {
        return mPreferences.getString(SharedPrefsKey.APP_CURRENT_USER_ICON, "");
    }

    @Override
    public void setCurrentUserIcon(String icon) {
        mPreferences.edit().putString(SharedPrefsKey.APP_CURRENT_USER_ICON, icon).apply();
    }

    @Override
    public String getCurrentUserNickname() {
        return mPreferences.getString(SharedPrefsKey.APP_CURRENT_USER_NICKNAME, "");
    }

    @Override
    public void setCurrentUserNickname(String nickname) {
        mPreferences.edit().putString(SharedPrefsKey.APP_CURRENT_USER_NICKNAME, nickname).apply();
    }

    @Override
    public float getCurrentUserBMI() {
        return mPreferences.getFloat(SharedPrefsKey.APP_CURRENT_USER_BMI, 0F);
    }

    @Override
    public void setCurrentUserBMI(float bmi) {
        mPreferences.edit().putFloat(SharedPrefsKey.APP_CURRENT_USER_BMI, bmi).apply();
    }

    @Override
    public int getCurrentUserHealthyNum() {
        return mPreferences.getInt(SharedPrefsKey.APP_CURRENT_USER_HEALTHY_NUM, 1);

    }

    @Override
    public void setCurrentUserHealthyNum(int num) {
        mPreferences.edit().putInt(SharedPrefsKey.APP_CURRENT_USER_HEALTHY_NUM, num).apply();
    }

    @Override
    public void clearUserInfo() {
        mPreferences.edit().clear().apply();
        updateAppFirstLauncherStatus(false);
    }

    @Override
    public boolean isAppFirstLauncher() {
        return mPreferences.getBoolean(SharedPrefsKey.APP_IS_FIRST_LAUNCHER, true);
    }

    @Override
    public void updateAppFirstLauncherStatus(boolean isFirst) {
        mPreferences.edit().putBoolean(SharedPrefsKey.APP_IS_FIRST_LAUNCHER, isFirst).apply();
    }

    private static final class PreferencesHelperHolder {
        private static final PreferencesHelperImpl INSTANCE = new PreferencesHelperImpl(HPApplication.getInstance());
    }

    static class SharedPrefsKey {
        static final String APP_INSTANCE_ID = "app_instance_id";

        static final String APP_IS_FIRST_LAUNCHER = "app_is_first_launcher";

        static final String APP_CURRENT_USER_TOKEN = "app_current_user_token";

        static final String APP_CURRENT_USER_ICON = "app_current_user_icon";
        static final String APP_CURRENT_USER_NICKNAME = "app_current_user_password";
        static final String APP_CURRENT_USER_USERNAME = "app_current_user_username";
        static final String APP_CURRENT_USER_BMI = "app_current_user_bmi";
        static final String APP_CURRENT_USER_HEALTHY_NUM = "app_current_user_healthy_num";

    }
}

