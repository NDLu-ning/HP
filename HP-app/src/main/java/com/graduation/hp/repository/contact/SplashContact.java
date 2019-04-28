package com.graduation.hp.repository.contact;

import android.content.Intent;

public interface SplashContact {

    interface View {
        void goToNextPage(boolean isCurUserLogin);
    }

    interface Presenter {
        void checkUserLoginStatus();
    }

}
