package ljc.example.com.nestedscrollview.utils;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.NetworkInterface;
import java.net.URLEncoder;

import androidx.annotation.DimenRes;

/**
 * Created by yelong on 2019-12-18.
 * mail:354734713@qq.com
 */
public class AndroidUtil {

    private static long lastClickTime;

    /**
     * 获取屏幕大小
     *
     * @return 长*宽
     */
    public static String getScreenSize(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.heightPixels + "*" + displayMetrics.widthPixels;
    }

    /**
     * 获取屏幕宽度
     *
     * @return
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    /**
     * 获取屏幕高度
     *
     * @return
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.heightPixels;
    }

    /**
     * 获取屏幕高度，手机底部有虚拟键时，会计算进虚拟键的高度
     *
     * @return
     */
    public static int getRealScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getRealMetrics(dm);
        return dm.heightPixels;
    }

    /**
     * dp -> px 最好不要用, 不要用在字体上, 某些机子如M9会出错的..
     *
     * @param contxt
     * @param dp
     * @return
     */
    public static int dp2px(Context contxt, int dp) {
        final float scale = contxt.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }


    /**
     * sp -> px copy from TypedValue.applyDimension(int unit, float value, DisplayMetrics metrics)
     *
     * @param contxt
     * @param sp
     * @return
     */
    public static int sp2px(Context contxt, int sp) {
        final float scale = contxt.getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp * scale + 0.5f);
    }


    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static float getDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    public static int getDensityDpi(Context context) {
        return context.getResources().getDisplayMetrics().densityDpi;
    }

    /**
     * 获状态栏高度
     *
     * @param context 上下文
     * @return 通知栏高度
     */
    public static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object obj = clazz.newInstance();
            Field field = clazz.getField("status_bar_height");
            int temp = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(temp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusBarHeight;
    }

    /**
     * 获状态栏高度
     *
     * @param context 上下文
     * @return 通知栏高度, 返回dip
     */
    public static int getStatusBarHeightAsDip(Context context) {
        return AndroidUtil.px2dip(context, getStatusBarHeight(context));
    }

    public static boolean hasKitkat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    public static boolean canShowDialog(Context context) {
        if (context == null) {
            return false;
        }

        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            return !activity.isFinishing() && !activity.isDestroyed();
        }
        return true;
    }

    /**
     * 判断pName中是否有目标程序的包名
     *
     * @param context     上下文
     * @param packageName 包名
     * @return
     */
    public static boolean hasInstallPackage(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        return packageManager.getLaunchIntentForPackage(packageName) != null;
    }

    public static boolean hasInstallPackage(Context context, String pkgName, int versionCode) {
        if (!TextUtils.isEmpty(pkgName) && null != context) {
            try {
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo(pkgName, 0);
                return packageInfo.versionCode >= versionCode;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static String getAppName(Context context) {
        PackageManager packageManager = context.getApplicationContext().getPackageManager();
        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(getPackageName(context), PackageManager.GET_META_DATA);
            return (String) packageManager.getApplicationLabel(applicationInfo);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "多点";
    }

    public static String getPackageName(Context context) {
        return context.getApplicationContext().getPackageName();
    }

    public static String getVersionName(Context context) {
        PackageManager packageManager = context.getApplicationContext().getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(context), PackageManager.GET_CONFIGURATIONS);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "4.5.0";
    }

    public static String getSysVersion() {
        return "Android-" + Build.VERSION.RELEASE;
    }

    public static boolean isFastClick(long interval) {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < interval) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    public static void isShowKeyboard(Context context, EditText editText, boolean isShow) {
        if (context == null) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            if (isShow) {
                imm.showSoftInput(editText, 0);
            } else {
                imm.hideSoftInputFromWindow(editText.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }



    /**
     * 复制粘贴到剪贴板
     */
    public static void setTextToClipboard(Context context, String label, String text) {
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboardManager != null) {
            ClipData clip = ClipData.newPlainText(label, text);
            clipboardManager.setPrimaryClip(clip);
        }
    }

    /**
     * 获取剪切板里内容
     */
    public static String getClipeBoardContent(Context context) {
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboardManager != null) {
            ClipData primaryClip = clipboardManager.getPrimaryClip();
            String content = null;
            if (primaryClip != null && primaryClip.getItemCount() > 0) {
                ClipData.Item itemAt = primaryClip.getItemAt(0);
                if (itemAt != null) {
                    content = itemAt.getText().toString();
                }
            }
            return content;
        }
        return "";
    }

    public static String getUserAgent(Context context) {
        String userAgent = System.getProperty("http.agent");
        if (TextUtils.isEmpty(userAgent)) {
            return "dmall/" + getVersionName(context) + " Android";
        } else {
            StringBuilder sb = new StringBuilder();
            for (int i = 0, length = userAgent.length(); i < length; i++) {
                char c = userAgent.charAt(i);
                if (c <= '\u001f' || c >= '\u007f') {
                    sb.append(String.format("\\u%04x", (int) c));
                } else {
                    sb.append(c);
                }
            }
            return "dmall/" + getVersionName(context) + " " + sb.toString();
        }
    }

    public static String getMacAddress() {
        String macAddress;
        StringBuilder buf = new StringBuilder();
        NetworkInterface networkInterface;
        try {
            networkInterface = NetworkInterface.getByName("eth1");
            if (networkInterface == null) {
                networkInterface = NetworkInterface.getByName("wlan0");
            }
            if (networkInterface == null) {
                return "";
            }
            byte[] addr = networkInterface.getHardwareAddress();
            for (byte b : addr) {
                buf.append(String.format("%02X:", b));
            }
            if (buf.length() > 0) {
                buf.deleteCharAt(buf.length() - 1);
            }
            macAddress = buf.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return macAddress;
    }

    public static String getOSType(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        double diagonalPixels = Math.sqrt(Math.pow(dm.widthPixels, 2.0)
                + Math.pow(dm.heightPixels, 2.0));
        double screenSize = diagonalPixels / (160 * dm.density);

        String device;
        if (screenSize > 7.0f) {
            device = "apad";
        } else {
            device = "aphone";
        }
        return device;
    }

    public static String getOSVersion() {
        try {
            return URLEncoder.encode(Build.VERSION.RELEASE, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getManufacturer() {
        try {
            return URLEncoder.encode(Build.MANUFACTURER + "-" + Build.BRAND + "-" + Build.MODEL, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getDeviceInfo() {
        String deviceInfo = Build.BRAND + " " + Build.MODEL + " " + Build.DISPLAY;
        return getValueEncoded(deviceInfo);
    }

    private static String getValueEncoded(String value) {
        try {
            if (value == null) return "null";
            String newValue = value.replace("\n", "");
            for (int i = 0, length = newValue.length(); i < length; i++) {
                char c = newValue.charAt(i);
                if (c <= '\u001f' || c >= '\u007f') {
                    return URLEncoder.encode(newValue, "UTF-8");
                }
            }
            return newValue;
        } catch (Exception e) {

        }
        return null;
    }

    public static String getMemoryInfo() {
        return "最大可分配内存: " + (int) (Runtime.getRuntime().maxMemory() / (1024 * 1024)) + "MB\n" + "当前已分配内存：" + (int) (Runtime.getRuntime().totalMemory() / (1024 * 1024)) + "MB";
    }

    public static String getAndroidID(Context context) {
        return Settings.System.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static String getFirstInstallTime(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return String.valueOf(packageInfo.firstInstallTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getLastInstallTime(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return String.valueOf(packageInfo.lastUpdateTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void exitApp() {
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    public static void gotoSetting(Context context) {
        if (context == null) return;

        try {
            //各个厂家的连接设置页面不一样，统一到一级设置页面比较保险一些
            context.startActivity(new Intent("android.settings.SETTINGS"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void startDialActivity(Context context, String phone) {
        try {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel://" + phone));
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置当前屏幕亮度值 0--1，并使之生效
     */
    public static void setScreenBrightness(final Activity act, final float value) {
        act.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Window mWindow = act.getWindow();
                WindowManager.LayoutParams mParams = mWindow.getAttributes();
                mParams.screenBrightness = value;
                mWindow.setAttributes(mParams);
            }
        });
    }

    /**
     * 如果当前window亮度小于系统最大亮度的90%,调节window亮度到系统亮度90%
     *
     * @param act
     */
    public static void starthHightlight(Activity act) {
        try {
            int screenBrightness = Settings.System.getInt(act.getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS);

            //如果亮度小于系统最大亮度的90%,调节系统亮度到90%*255
            if (screenBrightness < 230) {
                setScreenBrightness(act, 0.9f);
            }

        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 恢复高亮前的系统亮度
     *
     * @param act
     */
    public static void stopHighlight(Activity act) {
        //恢复系统亮度
        setScreenBrightness(act, -1);
    }

    public static int getScreenBright(Activity act) {
        int screenBrightness = 0;
        try {
            screenBrightness = Settings.System.getInt(act.getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return screenBrightness;
    }

    public static int[] getLocationInWindowPosition(View view) {
        int[] location = new int[2];
        view.getLocationInWindow(location);
        return location;
    }

    public static int[] getLocationOnScreen(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return location;
    }

    public static boolean isFullVisibleItem(Context context, View target, int viewHeight) {
        int[] screenLocation = AndroidUtil.getLocationOnScreen(target);
        return getScreenHeight(context) - screenLocation[1] - target.getBottom() >= AndroidUtil.dp2px(context, viewHeight);
    }

    public static int getCharacterWidth(Context context, String text, int spVal) {
        if (TextUtils.isEmpty(text)) {
            return 0;
        }

        float textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, spVal,
                context.getResources().getDisplayMetrics());
        Paint paint = new Paint();
        paint.setTextSize(textSize);
        return (int) paint.measureText(text);
    }

    public static float getDimens(Context context, @DimenRes int id) {
        return context.getResources().getDimension(id);
    }
}
