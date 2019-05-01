package com.graduation.hp.repository.preferences;

import com.graduation.hp.repository.http.entity.User;

public interface PreferencesHelper {

    boolean isAppFirstLauncher();

    void updateAppFirstLauncherStatus(boolean isFirst);

    String getCurrentUserToken();

    void saveCurrentUserToken(String token);

    void saveCurrentUserInfo(User user);

    String getCurrentUserRemark();

    int getCurrentUserGender();

    void saveCurrentUserRemark(String remark);

    void saveCurrentUserGender(Integer gender);

    User getCurrentUserInfo();

    void saveCurrentUserUsername(String username);

    String getCurrentUserUsername();

    String getCurrentUserIcon();

    void saveCurrentUserIcon(String icon);

    String getCurrentUserNickname();

    void saveCurrentUserNickname(String nickname);

    long getCurrentUserPhysiquId();

    void saveCurrentUserPhysiquId(long num);

    long getCurrentUserId();

    void saveCurrentUserId(long userId);

    void clearUserInfo();
}
