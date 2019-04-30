package com.graduation.hp.repository.preferences;

import com.graduation.hp.repository.http.entity.User;

public interface PreferencesHelper {

    boolean isAppFirstLauncher();

    void updateAppFirstLauncherStatus(boolean isFirst);

    String getCurrentUserToken();

    void saveCurrentUserToken(String token);

    void saveCurrentUserInfo(User user);

    String getCurrentUserIcon();

    void saveCurrentUserIcon(String icon);

    String getCurrentUserNickname();

    void saveCurrentUserNickname(String nickname);

    float getCurrentUserBMI();

    void saveCurrentUserBMI(float icon);

    long getCurrentUserHealthyNum();

    void saveCurrentUserHealthyNum(long num);

    void clearUserInfo();
}
