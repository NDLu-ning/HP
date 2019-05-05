package com.graduation.hp.core.utils;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.MimeTypeMap;

import com.graduation.hp.core.HPApplication;
import com.graduation.hp.core.R;
import com.graduation.hp.core.app.constant.AppSettings;
import com.graduation.hp.core.app.constant.MediaType;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtils {

    private static final String TAG = FileUtils.class.getSimpleName();

    public static final String EXTENSION_PNG = ".png";
    public static final String EXTENSION_JPG = ".jpg";
    public static final String EXTENSION_MP4 = ".mp4";

    private static final Class CLASS = FileUtils.class;

    private static final String AUTHORITY_EXTERNAL_STORAGE_PROVIDER = "com.android.externalstorage.documents";
    private static final String AUTHORITY_DOWNLOADS_PROVIDER = "com.android.providers.downloads.documents";
    private static final String AUTHORITY_MEDIA_PROVIDER = "com.android.providers.media.documents";
    private static final String AUTHORITY_GOOGLE_PHOTOS = "com.google.android.apps.photos.content";

    private static final String DOCUMENT_ID_TYPE_IMAGE = "image";
    private static final String DOCUMENT_ID_TYPE_VIDEO = "video";
    private static final String DOCUMENT_ID_TYPE_AUDIO = "audio";

    private static final String SCHEME_FILE = "file";

    private static final String[] CONTENT_FIELD_PROJECTION = new String[]{
            OpenableColumns.DISPLAY_NAME,
            OpenableColumns.SIZE
    };
    private static final String[] GALLERY_PROJECTION = {
            MediaStore.Images.ImageColumns.DISPLAY_NAME,
            MediaStore.Images.ImageColumns.SIZE,
            MediaStore.Images.ImageColumns.MIME_TYPE,
            MediaStore.Images.ImageColumns.DATE_TAKEN,
            MediaStore.Images.ImageColumns.LATITUDE,
            MediaStore.Images.ImageColumns.LONGITUDE,
            MediaStore.Images.ImageColumns.ORIENTATION
    };
    private static final String[] GOOGLE_DRIVE_PROJECTION = {
            "_display_name"
    };

    /**
     * @param context
     * @param uri     of the file you want to get the true path of
     * @return the true file path
     */
    public static String getFilePath(Context context, Uri uri) {
        if (context == null || uri == null) {
            Log.e(TAG, "Context or Uri is null.");
            return null;
        }

        final String scheme = uri.getScheme();
        final String authority = uri.getAuthority();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
                && DocumentsContract.isDocumentUri(context, uri)) { // DocumentsProvider
            final String documentId = DocumentsContract.getDocumentId(uri);

            switch (authority) {
                case AUTHORITY_EXTERNAL_STORAGE_PROVIDER: {
                    final String[] split = documentId.split(":");
                    final String type = split[0];
                    if (type.equalsIgnoreCase("primary")) {
                        return Environment.getExternalStorageDirectory() + "/" + split[1];
                    }
                    break;
                }
                case AUTHORITY_DOWNLOADS_PROVIDER: {
                    final Uri contentUri = ContentUris.withAppendedId(
                            Uri.parse("content://downloads/public_downloads"),
                            Long.valueOf(documentId));
                    return queryDataColumn(context, contentUri, null, null);
                }
                case AUTHORITY_MEDIA_PROVIDER: {
                    final String[] split = documentId.split(":");
                    final String type = split[0];

                    Uri contentUri = null;
                    switch (type) {
                        case DOCUMENT_ID_TYPE_IMAGE: {
                            contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                            break;
                        }
                        case DOCUMENT_ID_TYPE_VIDEO: {
                            contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                            break;
                        }
                        case DOCUMENT_ID_TYPE_AUDIO: {
                            contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                            break;
                        }
                        default: {
                            break;
                        }
                    }

                    if (contentUri != null) {
                        final String selection = "_id=?";
                        final String[] selectionArgs = new String[]{split[1]};
                        return queryDataColumn(context, contentUri, selection, selectionArgs);
                    }
                    break;
                }
            }
        } else if (scheme.equalsIgnoreCase("content")) { // MediaStore
            if (authority.startsWith(AUTHORITY_GOOGLE_PHOTOS)) {
                String path = null;
                final java.io.File tempFile = writeToTempFile(uri);
                if (tempFile != null) {
                    path = tempFile.getAbsolutePath();
                }
                return path;
            } else {
                return queryDataColumn(context, uri, null, null);
            }
        } else if (scheme.equalsIgnoreCase("file")) { // File
            return uri.getPath();
        }

        return null;
    }

    public static Intent getSafeIntentWithFileUri(Context context, java.io.File file) {
        final Intent intent = new Intent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION
                    | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }
        intent.setData(getFileUri(context, file));
        return intent;
    }

    public static Uri getFileUri(Context context, java.io.File file) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return FileProvider.getUriForFile(context,
                    AppSettings.getFileProvider(), file);
        } else {
            return Uri.fromFile(file);
        }
    }

    /**
     * Write the contents of the given Uri to a temporary file.
     *
     * @return the file that was created
     */
    public static java.io.File writeToTempFile(Uri uri) {
        java.io.File tempFile = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            // Open an input stream on the Uri.
            inputStream = HPApplication.getInstance().getContentResolver().openInputStream(uri);

            // Create a temporary file and a corresponding OutputStream to write to.
            final String filename = getFileName(uri);
            tempFile = createTempFile(filename);
            if (tempFile == null) {
                Log.e(TAG, "Could not create temporary file.");
                return null;
            }
            outputStream = new FileOutputStream(tempFile);

            // Copy the Uri InputStream to the File OutputStream.
            copyStreams(inputStream, outputStream);

        } catch (FileNotFoundException e) {
            Log.e(TAG, "Could not open input stream to uri : " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "Error writing to the file : " + e.getMessage());
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                Log.e(TAG, "Unable to close stream : " + e.getMessage());
            }
        }

        return tempFile;
    }

    // Sometimes files retrieved from the device don't have the correct file:// scheme in the uri path
    public static Uri addFileScheme(final String uriPath) {
        if (TextUtils.isEmpty(uriPath)) {
            return null;
        }

        String newUriPath = uriPath;
        if (!newUriPath.startsWith("file://")) {
            newUriPath = "file://" + newUriPath;
        }
        return Uri.parse(newUriPath);
    }

    public static boolean isLocalFile(Uri uri) {
        return uri.getScheme().equalsIgnoreCase(SCHEME_FILE);
    }

    public static long getFileSize(Context context, Uri uri) {
        long size = new java.io.File(uri.getPath()).length();

        if (size == 0 && context != null) {
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver().query(uri, null, null, null, null);
                final int sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE);
                cursor.moveToFirst();
                size = cursor.getLong(sizeIndex);
            } catch (NullPointerException e) {

                e.printStackTrace();
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }

        return size;
    }

    public static String getFileExtension(String path) {
        final int mediaType = getMediaTypeFromFileExtension(path);
        switch (mediaType) {
            case MediaType.WILDCARD: {
                return "";
            }
            default: {
                final int lastIndex = path.lastIndexOf(".");
                if (lastIndex < path.length() - 1) {
                    return path.substring(lastIndex + 1);
                } else {
                    return "";
                }
            }
        }
    }

    /**
     * @param filename a filename, or url that contains the extension.
     */
    public static int getMediaTypeFromFileExtension(String filename) {
        if (TextUtils.isEmpty(filename)) {
            return MediaType.WILDCARD;
        }

        final int lastIndexOfPeriod = filename.lastIndexOf(".");

        // Extension passed in as filename
        if ((filename.length() == 3 || filename.length() == 4) && lastIndexOfPeriod == -1) {
            return getMediaType(filename);
        }

        // Does not contain an extension
        if (lastIndexOfPeriod == -1) {
            return MediaType.WILDCARD;
        }

        final String extension = filename.substring(lastIndexOfPeriod + 1);
        return getMediaType(extension);
    }

    private static int getMediaType(String extension) {
        switch (extension.toLowerCase()) {
            case "txt": {
                return MediaType.TEXT;
            }
            case "htm":
            case "html": {
                return MediaType.HTML;
            }
            case "pdf": {
                return MediaType.PDF;
            }
            case "ppt":
            case "pptx": {
                return MediaType.PPT;
            }
            case "doc":
            case "docx": {
                return MediaType.DOC;
            }
            case "xls":
            case "xlsx": {
                return MediaType.XLS;
            }
            case "jpg":
            case "jpeg":
            case "png":
            case "tif":
            case "tiff":
            case "gif":
            case "bmp":
            case "bmpf": {
                return MediaType.IMAGE;
            }
            case "mp3": {
                return MediaType.AUDIO;
            }
            case "mp4":
            case "mpeg":
            case "mpg":
            case "mov":
            case "m4v": {
                return MediaType.VIDEO;
            }
            case "zip": {
                return MediaType.ZIP;
            }
            default: {
                return MediaType.WILDCARD;
            }
        }
    }

    public static String getMimeType(String name) {
        if (name.contains(".")) {
            final String extension = name.substring(name.lastIndexOf(".") + 1).toLowerCase();
            final MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
            final String mimeType = mimeTypeMap.getMimeTypeFromExtension(extension);
            if (!TextUtils.isEmpty(mimeType)) {
                return mimeType;
            } else {
                return "application/octet-stream";
            }
        } else {
            return "application/octet-stream";
        }
    }

    public static String getMimeType(Uri uri) {
        final String scheme = uri.getScheme();
        if (scheme == null || scheme.equals(ContentResolver.SCHEME_FILE)) {
            final String name = uri.toString();
            return getMimeType(name);
        } else {
            return HPApplication.getInstance().getContentResolver().getType(uri);
        }
    }

    public static boolean isFileViewable(String fileName) {
        switch (getMediaTypeFromFileExtension(fileName)) {
            case MediaType.IMAGE:
            case MediaType.HTML:
            case MediaType.TEXT: {
                return true;
            }
            default: {
                return false;
            }
        }
    }

    public static boolean isOfficeDocumentType(String filename) {
        final int mediaType = FileUtils.getMediaTypeFromFileExtension(filename);
        switch (mediaType) {
            case MediaType.DOC:
            case MediaType.XLS:
            case MediaType.PPT: {
                return true;
            }
            default: {
                return false;
            }
        }
    }

    public static String getFileName(Uri uri) {
        final Context context = HPApplication.getInstance();
        final String auth = uri.getAuthority();

        if (auth == null) {
            return getDefaultFileName(uri);

        } else if (auth.equals("com.google.android.gallery3d.provider")) {
            return getGalleryFilename(context, uri);

        } else if (auth.startsWith("com.google.android.apps.docs")) {
            return getGoogleDriveFilename(context, uri);

        } else {
            if (ContentResolver.SCHEME_CONTENT.equals(uri.getScheme())) {
                final Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
                try {
                    if (cursor != null && cursor.moveToFirst()) {
                        return cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    }
                } finally {
                    if (cursor != null && !cursor.isClosed()) {
                        cursor.close();
                    }
                }
            } else {
                return getDefaultFileName(uri);
            }
        }
        return context.getString(R.string.unknown_file_name);
    }

    /**
     * Creates a temporary file in the application-specific cache directory. This cache
     * may be cleaned up by the system at any time if space gets low.
     *
     * @throws IOException if unable to create the temp file
     * @suffix the file extension
     */
    public static java.io.File createTempFile(String fileExtension) throws IOException {
        final String timestamp = String.valueOf(System.nanoTime());
        return createTempFile(timestamp, fileExtension);
    }

    public static java.io.File createTempFile(String fileName, String fileExtension) throws IOException {
        final java.io.File cacheDirectory = HPApplication.getInstance().getCacheDir();
        final java.io.File tempFile = java.io.File.createTempFile(fileName, fileExtension,
                cacheDirectory);
        tempFile.createNewFile();
        tempFile.deleteOnExit();
        return tempFile;
    }

    private static int copyStreams(InputStream is, OutputStream os) throws IOException {
        final byte[] buffer = new byte[1024];
        int totalBytes = 0;
        int bytesRead = 0;

        while (-1 != (bytesRead = is.read(buffer))) {
            os.write(buffer, 0, bytesRead);
            totalBytes += bytesRead;
        }
        os.flush();

        return totalBytes;
    }

    /**
     * @param context
     * @param uri           to query
     * @param selection     (Optional) filter used by query
     * @param selectionArgs (Optional) selection args used by query
     * @return the value of the DATA column, usually a file path
     */
    private static String queryDataColumn(Context context, Uri uri, String selection,
                                          String[] selectionArgs) {
        final String column = MediaStore.Files.FileColumns.DATA;
        final String[] projection = {column};
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                return cursor.getString(cursor.getColumnIndexOrThrow(column));
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    private static String getDefaultFileName(Uri uri) {
        return uri.getLastPathSegment();
    }

    /**
     * Gets the name of the file selected from the Gallery app.
     *
     * @param context the Context
     * @param uri     the Uri to query
     * @return the filename
     */
    private static String getGalleryFilename(Context context, Uri uri) {
        final Cursor cursor = context.getContentResolver().query(uri, GALLERY_PROJECTION, null,
                null, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            final String fileName = cursor.getString(0);
            return fileName;
        } else {
            return getDefaultFileName(uri);
        }
    }

    /**
     * Gets the name of the file selected from the user's Google Drive account.
     *
     * @param context the Context
     * @param uri     the Uri to query
     * @return the filename
     */
    private static String getGoogleDriveFilename(Context context, Uri uri) {
        // Query the filename from the Uri.
        final Cursor cursor = context.getContentResolver().query(uri, GOOGLE_DRIVE_PROJECTION, null,
                null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            final String fileName = cursor.getString(0);
            return fileName;
        } else {
            return getDefaultFileName(uri);
        }
    }
}
