package com.graduation.hp.repository.preferences;

import com.graduation.hp.repository.http.entity.User;

public interface PreferencesHelper {

    boolean isAppFirstLauncher();

    void updateAppFirstLauncherStatus(boolean isFirst);

    String getCurrentUserToken();

    void updateCurrentUserToken(String token);

    void saveCurrentUserInfo(User user);

    String getCurrentUserIcon();

    void setCurrentUserIcon(String icon);

    String getCurrentUserNickname();

    void setCurrentUserNickname(String nickname);

    float getCurrentUserBMI();

    void setCurrentUserBMI(float icon);

    int getCurrentUserHealthyNum();

    void setCurrentUserHealthyNum(int num);

    void clearUserInfo();
}
