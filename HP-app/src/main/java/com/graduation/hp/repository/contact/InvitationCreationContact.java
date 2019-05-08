package com.graduation.hp.repository.contact;

import com.graduation.hp.app.event.UploadProfileEvent;

import java.io.File;

public interface InvitationCreationContact {
    interface Presenter {

        void uploadPicture(int index, File file);
    }

    interface View {
        void dismissPictureLoading(int index);

        void uploadFileError(int index);

        void onUploadFileSuccess(int finalI, UploadProfileEvent result);
    }
}
