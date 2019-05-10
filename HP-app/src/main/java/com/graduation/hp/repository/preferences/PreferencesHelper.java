package com.graduation.hp.repository.preferences;

import com.graduation.hp.repository.http.entity.vo.UserVO;

public interface PreferencesHelper {

    boolean isAppFirstLauncher();

    void updateAppFirstLauncherStatus(boolean isFirst);

    void updateTextSize(int textSize);

    int getTextSize();

    String getCurrentUserToken();

    void saveCurrentUserToken(String token);

    void saveCurrentUserInfo(UserVO user);

    String getCurrentUserRemark();

    int getCurrentUserGender();

    void saveCurrentUserRemark(String remark);

    void saveCurrentUserGender(Integer gender);

    UserVO getCurrentUserInfo();

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

    String getSearchKeywordsJson();

    void saveSearchKeywordsJson(String json);

    void saveTestResult(String objectToJson);

    String getTestResult();
}
