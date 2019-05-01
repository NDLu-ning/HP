package com.graduation.hp.repository.preferences;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

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
    public void saveCurrentUserToken(String token) {
        mPreferences.edit().putString(SharedPrefsKey.APP_CURRENT_USER_TOKEN, token).apply();
    }

    @Override
    public void saveCurrentUserInfo(User user) {
        saveCurrentUserId(user.getId());
        saveCurrentUserUsername(user.getUsername());
        saveCurrentUserGender(user.getSex());
        saveCurrentUserRemark(user.getRemark());
        saveCurrentUserIcon(user.getHeadUrl());
        saveCurrentUserNickname(user.getNickname());
        saveCurrentUserPhysiquId(user.getPhysiquId());
        saveCurrentUserToken(user.getToken());
    }

    @Override
    public String getCurrentUserRemark() {
        return mPreferences.getString(SharedPrefsKey.APP_CURRENT_USER_REMARK, "");
    }

    @Override
    public int getCurrentUserGender() {
        return mPreferences.getInt(SharedPrefsKey.APP_CURRENT_USER_GENDER, 1);
    }

    @Override
    public void saveCurrentUserRemark(String remark) {
        mPreferences.edit().putString(SharedPrefsKey.APP_CURRENT_USER_REMARK, remark).apply();
    }

    @Override
    public void saveCurrentUserGender(Integer gender) {
        mPreferences.edit().putInt(SharedPrefsKey.APP_CURRENT_USER_GENDER, gender).apply();
    }

    @Override
    public User getCurrentUserInfo() {
        if (!TextUtils.isEmpty(getCurrentUserToken())) {
            User user = new User();
            user.setId(getCurrentUserId());
            user.setNickname(getCurrentUserNickname());
            user.setRemark(getCurrentUserRemark());
            user.setHeadUrl(getCurrentUserIcon());
            user.setPhysiquId(getCurrentUserPhysiquId());
            user.setSex(getCurrentUserGender());
            return user;
        } else {
            return null;
        }
    }

    @Override
    public void saveCurrentUserUsername(String username) {
        mPreferences.edit().putString(SharedPrefsKey.APP_CURRENT_USER_USERNAME, username).apply();
    }

    @Override
    public String getCurrentUserUsername() {
        return mPreferences.getString(SharedPrefsKey.APP_CURRENT_USER_USERNAME, "");
    }

    @Override
    public String getCurrentUserIcon() {
        return mPreferences.getString(SharedPrefsKey.APP_CURRENT_USER_ICON, "");
    }

    @Override
    public void saveCurrentUserIcon(String icon) {
        mPreferences.edit().putString(SharedPrefsKey.APP_CURRENT_USER_ICON, icon).apply();
    }

    @Override
    public String getCurrentUserNickname() {
        return mPreferences.getString(SharedPrefsKey.APP_CURRENT_USER_NICKNAME, "");
    }

    @Override
    public void saveCurrentUserNickname(String nickname) {
        mPreferences.edit().putString(SharedPrefsKey.APP_CURRENT_USER_NICKNAME, nickname).apply();
    }

    @Override
    public long getCurrentUserPhysiquId() {
        return mPreferences.getLong(SharedPrefsKey.APP_CURRENT_USER_HEALTHY_NUM, 0);

    }

    @Override
    public void saveCurrentUserPhysiquId(long num) {
        mPreferences.edit().putLong(SharedPrefsKey.APP_CURRENT_USER_HEALTHY_NUM, num).apply();
    }

    @Override
    public void clearUserInfo() {
        mPreferences.edit().clear().apply();
        updateAppFirstLauncherStatus(false);
    }

    @Override
    public long getCurrentUserId() {
        return mPreferences.getLong(SharedPrefsKey.APP_CURRENT_USER_ID, -1L);
    }

    @Override
    public void saveCurrentUserId(long userId) {
        mPreferences.edit().putLong(SharedPrefsKey.APP_CURRENT_USER_ID, userId).apply();
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
        static final String APP_CURRENT_USER_ID = "app_current_user_id";
        static final String APP_CURRENT_USER_ICON = "app_current_user_icon";
        static final String APP_CURRENT_USER_NICKNAME = "app_current_user_password";
        static final String APP_CURRENT_USER_USERNAME = "app_current_user_username";
        static final String APP_CURRENT_USER_HEALTHY_NUM = "app_current_user_healthy_num";
        static final String APP_CURRENT_USER_GENDER = "app_current_user_gender";
        static final String APP_CURRENT_USER_REMARK = "app_current_user_remark";

    }
}

