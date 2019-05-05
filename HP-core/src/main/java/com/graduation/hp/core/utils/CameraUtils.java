package com.graduation.hp.core.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.graduation.hp.core.app.constant.AppSettings;

import java.io.File;

public final class CameraUtils {

    private static final String TAG = CameraUtils.class.getSimpleName();

    public static boolean doesDeviceHaveCamera(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    public static Intent createImageCaptureIntent(Context context, File file) {
        final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        setPermissionsForFileProvider(context, file, intent);
        return intent;
    }

    public static Intent createVideoCaptureIntent(Context context, File file) {
        final Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        setPermissionsForFileProvider(context, file, intent);
        return intent;
    }

    // Create a new .jpg image file in the external public directory Pictures/{PackageName}
    @Nullable
    public static File createExternalImageFile(Context context) {
        return createExternalFile(context, Environment.DIRECTORY_PICTURES, FileUtils.EXTENSION_JPG);
    }

    // Create a new .mp4 video file in the external public directory Movies/{PackageName}
    @Nullable
    public static File createExternalVideoFile(Context context) {
        return createExternalFile(context, Environment.DIRECTORY_MOVIES, FileUtils.EXTENSION_MP4);
    }

    @Nullable
    private static File createExternalFile(Context context, String directory, String fileExtension) {
        if (context == null) {
            return null;
        }

        final File packageDir = new File(
                context.getExternalFilesDir(directory),
                context.getPackageName());
        if (!packageDir.exists() && !packageDir.mkdirs()) {
            Log.e(TAG, "Failed to create external public storage directory.");
            return null;
        }

        return new File(packageDir.getPath() + File.separator + DateUtils.getFileTimestamp()
                + fileExtension);
    }

    /**
     * Sets the proper permissions to be able to read/write to the file through the FileProvider.
     *
     * @param context the context
     * @param file    the file where the image/video will be stored
     * @param intent  the image or video capture intent
     */
    private static void setPermissionsForFileProvider(Context context, File file, Intent intent) {
        final Uri contentUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION
                    | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

            contentUri = FileProvider.getUriForFile(context,
                    AppSettings.getFileProvider(), file);
        } else {
            contentUri = Uri.fromFile(file);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
    }
}
