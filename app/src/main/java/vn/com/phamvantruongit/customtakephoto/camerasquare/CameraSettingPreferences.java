package vn.com.phamvantruongit.customtakephoto.camerasquare;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Camera;
import android.os.Build;

/**
 * Created by desmond on 4/10/15.
 */
public class CameraSettingPreferences {

    private static final String FLASH_MODE = "squarecamera__flash_mode";

    private static SharedPreferences getCameraSettingPreferences( final Context context) {
        return context.getSharedPreferences("com.desmond.squarecamera", Context.MODE_PRIVATE);
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public static void saveCameraFlashMode(final Context context, final String cameraFlashMode) {
        final SharedPreferences preferences = getCameraSettingPreferences(context);

        if (preferences != null) {
            final SharedPreferences.Editor editor = preferences.edit();
            editor.putString(FLASH_MODE, cameraFlashMode);
            editor.apply();
        }
    }

    public static String getCameraFlashMode(final Context context) {
        final SharedPreferences preferences = getCameraSettingPreferences(context);

        if (preferences != null) {
            return preferences.getString(FLASH_MODE, Camera.Parameters.FLASH_MODE_AUTO);
        }

        return Camera.Parameters.FLASH_MODE_AUTO;
    }
}
