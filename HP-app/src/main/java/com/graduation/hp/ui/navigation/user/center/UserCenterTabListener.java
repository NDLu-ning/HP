package com.graduation.hp.ui.navigation.user.center;

public interface UserCenterTabListener {
    void onItemClick(boolean post, long id);

    void onLikeClick(boolean post ,long id, boolean liked);
}