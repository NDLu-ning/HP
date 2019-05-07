package com.graduation.hp.core.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.graduation.hp.core.HPApplication;
import com.graduation.hp.core.R;
import com.graduation.hp.core.app.constant.Code;
import com.graduation.hp.core.app.listener.PermissionCallback;
import com.graduation.hp.core.repository.entity.BottomSheetOption;
import com.graduation.hp.core.ui.BaseActivity;
import com.graduation.hp.core.ui.bottomsheet.ListSelectionBottomSheetFragment;
import com.graduation.hp.core.ui.crop.AvatarCropWindowActivity;
import com.graduation.hp.core.ui.crop.BaseCropWindowActivity;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class UploadAvatarHelper {

    private static final int SHEET_ID_CHOOSE_FROM_GALLERY = 0;
    private static final int SHEET_ID_USE_CAMERA = 1;
    private static final String CAMERA_RESULT_FILE_PATH = "CAMERA_RESULT_FILE_PATH";

    private WeakReference<BaseActivity> mActivity;
    private String mCameraResultFilePath;

    private UploadAvatarHelperListener mCallback;

    public UploadAvatarHelper(BaseActivity activity,
                              UploadAvatarHelperListener callback) {
        mActivity = new WeakReference<>(activity);
        mCallback = callback;
    }

    public interface UploadAvatarHelperListener {
        void onAvatarUpload(String largeFilePath, String smallFilePath);
    }

    public void changeAvatar(/*boolean shouldVerifyPassword*/) {
        showUpdateAvatarBottomSheet();
    }


    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString(CAMERA_RESULT_FILE_PATH, mCameraResultFilePath);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mCameraResultFilePath = savedInstanceState.getString(CAMERA_RESULT_FILE_PATH);
        }
    }

    public void onBottomSheetOptionSelected(final BottomSheetOption option) {
        final BaseActivity activity = mActivity.get();
        if (activity != null && !activity.isFinishing()) {
            if (PermissionsUtils.hasPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                onStoragePermissionGranted(option);
            } else {
                activity.setPermissionCallback(new PermissionCallback() {
                    @Override
                    public void onPermissionGranted() {
                        onStoragePermissionGranted(option);
                    }
                });
                PermissionsUtils.checkAndRequestPermissions(activity,
                        R.string.read_external_storage_permission_message,
                        Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        }
    }

    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Code.ATTACHMENT_NEW_PHOTO: {
                onCameraResult(resultCode);
                return true;
            }

            case Code.ATTACHMENT_GALLERY_IMAGE: {
                onGalleryResult(resultCode, data);
                return true;
            }
            case Code.ATTACHMENT_CROP_PHOTO: {
                onCropWindowResult(resultCode, data);
                return true;
            }

            default: {
                return false;
            }
        }
    }

    private void startCropWindow(String filePath) {
        final BaseActivity activity = mActivity.get();
        if (activity == null || activity.isFinishing()) {
            return;
        }

        final Intent intent = AvatarCropWindowActivity.createIntent(activity, filePath);
        ActivityUtils.startActivityForResult(activity, intent, Code.ATTACHMENT_CROP_PHOTO);
    }

    private void onStoragePermissionGranted(BottomSheetOption option) {
        switch (option.getId()) {
            case SHEET_ID_CHOOSE_FROM_GALLERY: {
                choosePictureFromGallery();
                break;
            }
            case SHEET_ID_USE_CAMERA: {
                takePicture();
                break;
            }
            default: {
                throw new IllegalArgumentException("Invalid selection position: " + option.getId());
            }
        }
    }


    private void takePicture() {
        final BaseActivity activity = mActivity.get();
        if (activity == null || activity.isFinishing()) {
            return;
        }
        if (DeviceUtils.hasCamera()) {
            final File file = CameraUtils.createExternalImageFile(activity);
            if (file != null) {
                mCameraResultFilePath = file.getAbsolutePath();
                ActivityUtils.startActivityForResult(activity,
                        CameraUtils.createImageCaptureIntent(activity, file),
                        Code.ATTACHMENT_NEW_PHOTO);
            }
        } else {
            AlertDialogFactory.showCameraNotFoundDialog(activity);
        }
    }

    private void onCameraResult(int resultCode) {
        final BaseActivity activity = mActivity.get();
        if (activity == null || activity.isFinishing() || resultCode != Activity.RESULT_OK) {
            return;
        }
        startCropWindow(mCameraResultFilePath);
        mCameraResultFilePath = null;
    }

    private void choosePictureFromGallery() {
        final BaseActivity activity = mActivity.get();
        if (activity == null || activity.isFinishing()) {
            return;
        }
        final Intent photoPickerIntent = GalleryUtils.getPickImageFromGalleryIntent();
        ActivityUtils.startActivityForResult(activity, photoPickerIntent,
                Code.ATTACHMENT_GALLERY_IMAGE);
    }

    private void onGalleryResult(int resultCode, Intent data) {
        final BaseActivity activity = mActivity.get();
        if (activity == null || activity.isFinishing() || resultCode != Activity.RESULT_OK) {
            return;
        }

        final Uri selectedImageUri = data.getData();
        String filePath = FileUtils.getFilePath(activity, selectedImageUri);

        if (TextUtils.isEmpty(filePath) || !(new File(filePath)).exists()) {
            final File tempFile = FileUtils.writeToTempFile(selectedImageUri);
            if (tempFile != null) {
                filePath = tempFile.getAbsolutePath();
            }
        }
        startCropWindow(filePath);
    }


    private void onCropWindowResult(int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        mCallback.onAvatarUpload(data.getStringExtra(BaseCropWindowActivity.AVATAR_LARGE_FILE_PATH),
                data.getStringExtra(BaseCropWindowActivity.AVATAR_SMALL_FILE_PATH));/*data.getStringExtra(Key.AVATAR_URL)*/
    }

    private void showUpdateAvatarBottomSheet() {
        final AppCompatActivity activity = mActivity.get();
        if (activity != null && !activity.isFinishing()) {
            final ArrayList<BottomSheetOption> options = new ArrayList<>(2);
            options.add(new BottomSheetOption(SHEET_ID_CHOOSE_FROM_GALLERY,
                    HPApplication.getStringById(R.string.tips_choose_gallery)));
            options.add(new BottomSheetOption(SHEET_ID_USE_CAMERA,
                    HPApplication.getStringById(R.string.tips_choose_camera)));
            ListSelectionBottomSheetFragment.newInstance(HPApplication.getStringById(R.string.tips_upload_profile),
                    options).show(activity.getSupportFragmentManager());
        }
    }

}
