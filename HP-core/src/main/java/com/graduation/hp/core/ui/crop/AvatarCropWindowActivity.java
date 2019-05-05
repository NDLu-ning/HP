package com.graduation.hp.core.ui.crop;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View.OnClickListener;

import com.graduation.hp.core.R;
import com.graduation.hp.core.utils.ImageUtils;
import com.graduation.hp.core.utils.ToastUtils;

import java.io.File;

public class AvatarCropWindowActivity extends BaseCropWindowActivity implements OnClickListener {

    private static final String TAG = AvatarCropWindowActivity.class.getSimpleName();

    private static final int PROFILE_PICTURE_SIZE_LARGE = 140;
    private static final int PROFILE_PICTURE_SIZE_SMALL = 42;

    private File mLargeAvatarFile;
    private File mSmallAvatarFile;

    public static Intent createIntent(Context context, String filePath) {
        final Intent intent = new Intent(context, AvatarCropWindowActivity.class);
        intent.putExtra(FILE_PATH, filePath);
        return intent;
    }

    @Override
    protected boolean fixAspectRatio() {
        return true;
    }

    @Override
    protected void onUseButtonClick(Bitmap croppedImage) {
        final Bitmap bitmap = croppedImage.copy(croppedImage.getConfig(), false);
        croppedImage.recycle();

        mLargeAvatarFile = ImageUtils.scaleTempImageFile(bitmap, PROFILE_PICTURE_SIZE_LARGE,
                PROFILE_PICTURE_SIZE_LARGE, false);
        if (mLargeAvatarFile == null) {
            ToastUtils.show(this, R.string.error_general);
            Log.e(TAG, "Error creating large avatar file.");
            return;
        }
        mSmallAvatarFile = ImageUtils.scaleTempImageFile(bitmap, PROFILE_PICTURE_SIZE_SMALL,
                PROFILE_PICTURE_SIZE_SMALL, true);
        if (mSmallAvatarFile == null) {
            ToastUtils.show(this, R.string.error_general);
            Log.e(TAG, "Error creating small avatar file.");
            return;
        }
        final Intent data = new Intent();
        data.putExtra(AVATAR_LARGE_FILE_PATH, mLargeAvatarFile.getAbsolutePath());
        data.putExtra(AVATAR_SMALL_FILE_PATH, mSmallAvatarFile.getAbsolutePath());
        setResult(RESULT_OK, data);
        finish();
    }
}
