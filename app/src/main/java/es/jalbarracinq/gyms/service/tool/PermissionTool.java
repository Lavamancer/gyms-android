/*
 * Created by Juan Albarracin on 4/08/18 14:25
 * Copyright (c) 2018. All right reserved.
 *
 * Last modified 4/08/18 14:25
 */

package es.jalbarracinq.gyms.service.tool;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import java.util.HashMap;

import es.jalbarracinq.gyms.Session;

public class PermissionTool {

    public static final int IMAGE_CODE = 1032;
    public static final int CAMERA_CODE = 1033;
    public static final int CAMERA_WRITE_PERMISSION_CODE = 1034;
    public static final int CAMERA_PERMISSION_CODE = 1035;
    public static final int GALLERY_PERMISSION_CODE = 1036;

    public final static String AUDIO = Manifest.permission.RECORD_AUDIO;
    public final static String WRITE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    public final static String CAMERA = Manifest.permission.CAMERA;
    public final static String GPS = Manifest.permission.ACCESS_FINE_LOCATION;


    static public void requestPermission(Activity activity, Fragment fragment, String permission, int permissionCode) {
        String[] p = {permission};
        if (fragment != null) {
            fragment.requestPermissions(p, permissionCode);
        } else {
            ActivityCompat.requestPermissions(activity, p, permissionCode);
        }
    }

    static public boolean checkPermission(Activity activity, String permission) {
        int result = ContextCompat.checkSelfPermission(activity, permission);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    // Comprobamos si el usuario le ha dado al checkbox y ha denegado el permiso
    static public boolean isPermissionPermaDenied(Activity activity, String permission) {
        if (Session.getInstance().permissions == null) {
            Session.getInstance().permissions = new HashMap<>();
            return false;
        }

        if (Session.getInstance().permissions.containsKey(permission)) {
            return !Session.getInstance().permissions.get(permission);
        } else {
            return false;
        }
    }

    // Este metodo comprueba la situacion del permiso y lo actualiza si lo ha denegado y ha seleccionado el checkbox
    static public boolean strikePermission(Activity activity, String permission) {
        boolean checkbox = ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);
        boolean result = false;
        if (Session.getInstance().permissions == null) {
            Session.getInstance().permissions = new HashMap<>();
        }

        if (Session.getInstance().permissions.containsKey(permission)) {
            if (Session.getInstance().permissions.get(permission)) {
                if (!checkbox) {
                    Session.getInstance().permissions.put(permission, false);
                    result = true;
                }
            } else {
                result = true;
            }
        } else if (checkbox) {
            Session.getInstance().permissions.put(permission, true);
        }

        return result;
    }

    static public void sendSettingsForPermissionManual(Activity activity) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", activity.getPackageName(), null));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

}
