package com.graduation.hp.presenter;

import com.graduation.hp.R;
import com.graduation.hp.core.HPApplication;
import com.graduation.hp.core.mvp.BasePresenter;
import com.graduation.hp.repository.contact.InvitationCreationContact;
import com.graduation.hp.repository.model.impl.InvitationModel;
import com.graduation.hp.repository.model.impl.UploadModel;
import com.graduation.hp.ui.navigation.invitation.create.InvitationCreationActivity;

import java.io.File;

import javax.inject.Inject;

public class InvitationCreationPresenter extends BasePresenter<InvitationCreationActivity, InvitationModel>
        implements InvitationCreationContact.Presenter {

    @Inject
    UploadModel uploadModel;

    @Inject
    public InvitationCreationPresenter(InvitationModel mMvpModel) {
        super(mMvpModel);
    }


    @Override
    public void uploadPicture(int index, File file) {
        if (!mMvpView.isNetworkAvailable()) {
            mMvpView.showError(HPApplication.getStringById(R.string.tips_network_unavailable));
            return;
        }
        mMvpModel.addSubscribe(uploadModel.uploadFile(file)
                .doFinally(() -> mMvpView.dismissPictureLoading(index))
                .subscribe(result -> {
                    mMvpView.onUploadFileSuccess(index, result);
                }, throwable -> {
                    mMvpView.uploadFileError(index);
                }));
    }
}
