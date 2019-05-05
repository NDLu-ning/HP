package com.graduation.hp.core.ui.crop;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.graduation.hp.core.R;
import com.graduation.hp.core.utils.DeviceUtils;
import com.graduation.hp.core.utils.ImageUtils;
import com.theartofdev.edmodo.cropper.CropImageView;


public abstract class BaseCropWindowActivity extends AppCompatActivity implements
        View.OnClickListener {


    public static final String FILE_PATH = "FILE_PATH";
    public static final String AVATAR_LARGE_FILE_PATH = "AVATAR_LARGE_FILE_PATH";
    public static final String AVATAR_SMALL_FILE_PATH = "AVATAR_SMALL_FILE_PATH";

    private static final int LAYOUT_ID = R.layout.profile_crop_window;
    private static final int NINETY_DEGREES = 90;

    private CropImageView mCropImageView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        final String filePath = getIntent().getStringExtra(FILE_PATH);
        final DisplayMetrics displayMetrics = DeviceUtils.getDisplayMetrics(this);
        final Bitmap bitmap = ImageUtils.scaleImageFromFile(filePath, displayMetrics.widthPixels,
                displayMetrics.heightPixels);

        mCropImageView = (CropImageView) findViewById(R.id.cropImageView);
        mCropImageView.setImageBitmap(bitmap);
        mCropImageView.setFixedAspectRatio(fixAspectRatio());

        // Creates the useButton to use a picture.
        final Button useButton = (Button) findViewById(R.id.usePicture);
        useButton.setOnClickListener(this);

        // Creates a rotateButton to rotate the picture.
        final Button rotateButton = (Button) findViewById(R.id.rotatePicture);
        rotateButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.usePicture) {
            onUseButtonClick(mCropImageView.getCroppedImage());
        } else {
            mCropImageView.rotateImage(NINETY_DEGREES);
        }
    }

    protected int getLayoutResource() {
        return LAYOUT_ID;
    }

    protected String getSourceFilePath() {
        return getIntent().getStringExtra(FILE_PATH);
    }

    /**
     * Determines whether the aspect ratio should be fixed.
     *
     * @return true to have a fixed 1:1 ratio; false to have it not be fixed
     */
    protected abstract boolean fixAspectRatio();

    /**
     * Performs whatever action is necessary when the "Use" button is clicked in the
     * BaseCropWindowActivity.
     *
     * @param croppedImage the cropped image as specified by the Cropper
     */
    protected abstract void onUseButtonClick(Bitmap croppedImage);
}
