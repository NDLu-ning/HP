package com.graduation.hp.core.utils;

import android.app.Activity;
import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.NotificationManagerCompat;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;


import com.graduation.hp.core.HPApplication;

import java.util.Locale;
import java.util.UUID;

public class DeviceUtils {

    private static Class CLASS = DeviceUtils.class;

    /**
     * Hides the virtual keyboard
     *
     * @param context A context
     * @param view    The View subclass that is requesting to hide the keyboard
     */
    public static void hideVirtualKeyboard(Context context, View view) {
        final InputMethodManager inputMethodManager =
                (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static void hideVirtualKeyboard(Activity activity) {
        if (activity != null && activity.getCurrentFocus() != null) {
            final InputMethodManager imm = (InputMethodManager) activity.getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    public static void setVirtualKeyboardAlwaysHidden(Activity activity) {
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public static boolean isDisplayXhdpiOrGreater() {
        final int density =
                HPApplication.getInstance().getResources().getDisplayMetrics().densityDpi;
        return (density >= DisplayMetrics.DENSITY_XHIGH);
    }

    public static boolean isScreenSizeLarge() {
        return equalsScreenSize(Configuration.SCREENLAYOUT_SIZE_LARGE);
    }

    public static boolean isScreenSizeXLarge() {
        return equalsScreenSize(Configuration.SCREENLAYOUT_SIZE_XLARGE);
    }

    /**
     * Determines whether the current device screen size is equal to the input size.
     *
     * @param size the size to check for equality; must be one of
     *             {@link Configuration#SCREENLAYOUT_SIZE_SMALL},
     *             {@link Configuration#SCREENLAYOUT_SIZE_NORMAL},
     *             {@link Configuration#SCREENLAYOUT_SIZE_LARGE},
     *             {@link Configuration#SCREENLAYOUT_SIZE_XLARGE}
     * @return true if the device size equals the input size; false otherwise
     */
    public static boolean equalsScreenSize(int size) {
        final int screenSizeBitmask =
                HPApplication.getInstance().getResources().getConfiguration().screenLayout;
        final int screenSize = screenSizeBitmask & Configuration.SCREENLAYOUT_SIZE_MASK;
        return (screenSize == size);
    }

    public static boolean hasCamera() {
        final PackageManager packageMgr = HPApplication.getInstance().getPackageManager();
        return (packageMgr.hasSystemFeature(PackageManager.FEATURE_CAMERA)
                || packageMgr.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT));
    }

    public static boolean isCurrentApiJellyBeanOrLess() {
        final int currentApiVersion = android.os.Build.VERSION.SDK_INT;
        return currentApiVersion <= Build.VERSION_CODES.JELLY_BEAN;
    }

    public static DisplayMetrics getDisplayMetrics(Activity activity) {
        final DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics;
    }

    public static boolean isOrientationLandscape() {
        return HPApplication.getInstance().getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
    }

    /**
     * Getting the default user-agent String:
     * http://cdrussell.blogspot.com/2012/09/programmatically-get-user-agent-string.html
     * The user-agent String for the Nexus 10, for example, is:
     * "Dalvik/1.6.0 (Linux; U; Android 4.2.2; Nexus 10 Build/JDQ39)"
     *
     * @return the device's user-agent String
     */
    public static String getUserAgent() {
        return System.getProperty("http.agent");
    }

    /**
     * Gets the ANDROID_ID set on the device.
     * <p>
     * Note that there are multiple potential issues with trying to use this value as a
     * unique device identifier. See: http://stackoverflow.com/a/13831099
     *
     * @return the ANDROID_ID value of the device
     */
    public static String getAndroidId() {
        final ContentResolver contentResolver = HPApplication.getInstance().getContentResolver();
        return Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID);
    }

    /**
     * Generates a custom globally unique ID (GUID) specific to the app.
     *
     * @return app instance id String
     */
    public static String generateUniqueUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * Copies the given text to the device clipboard.
     *
     * @param textToCopy the text to copy
     */
    public static void copyToClipboard(CharSequence textToCopy) {

        final Context context = HPApplication.getInstance();
        final int currentApiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentApiVersion >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            final android.content.ClipboardManager clipboard =
                    (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            final ClipData clip = android.content.ClipData.newPlainText("", textToCopy);
            clipboard.setPrimaryClip(clip);
        } else {
            final android.text.ClipboardManager clipboard =
                    (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(textToCopy);
        }
    }

    /**
     * @return True if the current app is running on Amazon device
     */
    public static boolean isAmazonKindle() {
        return android.os.Build.MANUFACTURER.equals("Amazon");
    }

    public static String getIsoCountryCodeFromTelephonyOrLocale() {
        final Context context = HPApplication.getInstance();
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_TELEPHONY)) {
            final TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            final String simCountry = telephonyManager.getSimCountryIso();
            if (simCountry != null && simCountry.length() == 2) {
                return simCountry.toUpperCase();
            } else if (telephonyManager.getPhoneType() != TelephonyManager.PHONE_TYPE_CDMA) {
                final String networkCountry = telephonyManager.getNetworkCountryIso();
                if (networkCountry != null && networkCountry.length() == 2) {
                    return networkCountry.toUpperCase();
                }
            }
        }
        return getDeviceLocale().getCountry();
    }

    public static String getDeviceLanguage() {
        return getDeviceLocale().getLanguage();
    }

    public static boolean areNotificationsEnabledForApp() {
        final Context context = HPApplication.getInstance();
        return NotificationManagerCompat.from(context).areNotificationsEnabled();
    }

    public static long getAppInstallDate(Context context) {
        try {
            return context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0)
                    .firstInstallTime;
        } catch (PackageManager.NameNotFoundException e) {
            return -1;
        }
    }

    private static Locale getDeviceLocale() {
        final Context context = HPApplication.getInstance();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            return context.getResources().getConfiguration().locale;
        } else {
            return context.getResources().getConfiguration().getLocales().get(0);
        }
    }
}
