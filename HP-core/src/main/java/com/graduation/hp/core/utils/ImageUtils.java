package com.graduation.hp.core.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.ParcelFileDescriptor;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.Log;

import com.graduation.hp.core.repository.entity.Dimension;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageUtils {

    private static final String TAG = ImageUtils.class.getSimpleName();


    public interface ImageLoadingListener {
        void onResourceReady();

        void onException();
    }


    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            final BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        final Bitmap bitmap;
        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            // Single color bitmap will be created of 1x1 pixel
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        final Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static Drawable getTintedDrawable(Context context, int drawableResId, int colorResId) {
        final Drawable drawable = DrawableCompat.wrap(
                ContextCompat.getDrawable(context, drawableResId));
        DrawableCompat.setTint(drawable, colorResId);
        return drawable;
    }

    public static void tintDrawable(Context context, Drawable drawable, int tintResId) {
        final Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(wrappedDrawable, ContextCompat.getColor(context, tintResId));
    }

    /**
     * Reads a file into a Bitmap object while down-scaling it such that both dimensions
     * are less than or equal to the size of view.
     *
     * @param filePath    the file path of image file
     * @param maxWidthPx  the maximum width in px
     * @param maxHeightPx the maximum height in px
     * @return the scaled bitmap
     */
    public static Bitmap scaleImageFromFile(String filePath, int maxWidthPx, int maxHeightPx) {
        final Dimension dimensions = getImageDimensions(filePath);
        final int scaleFactor = calculateScaleFactor(dimensions, maxWidthPx, maxHeightPx);
        final Bitmap result = decodeFile(filePath, scaleFactor);
        return result;
    }

    public static Dimension getImageDimensions(String filePath) {
        // The inJustDecodeBounds option indicates that we are calculating the dimensions
        // only and not allocating memory for the image.
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        // Calculate the dimensions.
        BitmapFactory.decodeFile(filePath, options);

        return new Dimension(options.outWidth, options.outHeight);
    }

    public static File scaleImageFromFile(ParcelFileDescriptor fileDescriptor, int maxWidthPx, int maxHeightPx) {
        final File file;
        try {
            file = FileUtils.createTempFile(FileUtils.EXTENSION_JPG);
        } catch (IOException e) {
            Log.e(TAG, "Unable to write image to temp file: " + e.getMessage());
            return null;
        }
        final Dimension dimensions = getImageDimensions(fileDescriptor);
        final int scaleFactor = calculateScaleFactor(dimensions, maxWidthPx, maxHeightPx);
        final Bitmap bitmap = decodeFile(fileDescriptor, scaleFactor);
        writeImageToFile(bitmap, file);
        return file;
    }

    public static Dimension getImageDimensions(ParcelFileDescriptor fileDescriptor) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fileDescriptor.getFileDescriptor(), null, options);
        return new Dimension(options.outWidth, options.outHeight);
    }

    private static int calculateScaleFactor(Dimension dimension, int maxWidthPx, int maxHeightPx) {
        int scaleFactor = 1;
        final double widthRatio = (double) dimension.getWidth() / (double) maxWidthPx;
        final double heightRatio = (double) dimension.getHeight() / (double) maxHeightPx;

        if (widthRatio > 1.0 || heightRatio > 1.0) {
            // Take the larger ratio to ensure that the result is at most as large as
            // either max dimension after scaling down both sides by the same factor.
            final double ratio = (widthRatio > heightRatio) ? widthRatio : heightRatio;

            // Find the scale factor that will result in both sides being less than or
            // equal to the max width and height.
            scaleFactor = (int) Math.ceil(ratio);
        }

        return scaleFactor;
    }

    public static boolean writeImageToFile(Bitmap image, File outputFile) {
        try {
            final FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
            image.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            return true;
        } catch (IOException | NullPointerException e) {
            Log.e(TAG, "Unable to write image to file");
            e.printStackTrace();
            return false;
        }
    }

    public static File scaleTempImageFile(Bitmap bitmap, int width, int height, boolean recycleBitmap) {
        final File file;
        final BitmapFactory.Options options = new BitmapFactory.Options();
        try {
            file = FileUtils.createTempFile(FileUtils.EXTENSION_JPG);
            if (!writeImageToFile(bitmap, file)) {
                return null;
            }
        } catch (IOException e) {
            Log.e(TAG, "Unable to write image to temp file: " + e.getMessage());
            return null;
        }

        // First decode with inJustDecodeBounds=true to check dimensions
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getPath(), options);

        options.inSampleSize = calculateInSampleSize(options, width, height);
        options.inJustDecodeBounds = false;
        writeImageToFile(BitmapFactory.decodeFile(file.getPath(), options), file);

        if (recycleBitmap) {
            bitmap.recycle();
        }
        return file;
    }

    public static Bitmap convertFileToBitmap(String filePath) {
        return convertFileToBitmap(new File(filePath), 140, 140);
    }

    public static Bitmap convertFileToBitmap(File file, int width, int height) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inSampleSize = calculateInSampleSize(options, width, height);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(file.getPath(), options);
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth,
                                             int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    private static Bitmap decodeFile(String filePath, int scaleFactor) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = scaleFactor;
        return BitmapFactory.decodeFile(filePath, options);
    }

    private static Bitmap decodeFile(ParcelFileDescriptor fileDescriptor, int scaleFactor) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = scaleFactor;
        return BitmapFactory.decodeFileDescriptor(fileDescriptor.getFileDescriptor(), null, options);
    }

}
