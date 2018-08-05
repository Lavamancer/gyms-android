/*
 * Created by Juan Albarracin on 4/08/18 14:25
 * Copyright (c) 2018. All right reserved.
 *
 * Last modified 4/08/18 14:25
 */

package es.jalbarracinq.gyms.service.tool;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.widget.ImageView;

import com.nguyenhoanglam.imagepicker.model.Config;
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker;

import java.io.File;

public class ImagePickerTool {


    public static void start(Activity activity, Fragment fragment) {
        if (!PermissionTool.checkPermission(activity, PermissionTool.WRITE)) {
            if (PermissionTool.isPermissionPermaDenied(activity, PermissionTool.WRITE)) {
                showSettingsAlert(activity);
            } else {
                PermissionTool.requestPermission(activity, fragment, PermissionTool.WRITE, PermissionTool.CAMERA_WRITE_PERMISSION_CODE);
            }
        } else if (!PermissionTool.checkPermission(activity, PermissionTool.CAMERA)) {
            if (PermissionTool.isPermissionPermaDenied(activity, PermissionTool.CAMERA)) {
                showSettingsAlert(activity);
            } else {
                PermissionTool.requestPermission(activity, fragment, PermissionTool.CAMERA, PermissionTool.CAMERA_PERMISSION_CODE);
            }
        } else {
            startImagePicker(fragment);
        }
    }

    public static void onRequestPermissionsResult(Activity activity, Fragment fragment, int requestCode) {
        if (requestCode == PermissionTool.GALLERY_PERMISSION_CODE) {
            if (PermissionTool.checkPermission(activity, PermissionTool.WRITE)) {
                startImagePicker(fragment);
            } else {
                PermissionTool.strikePermission(activity, PermissionTool.WRITE);
            }
        } else if (requestCode == PermissionTool.CAMERA_WRITE_PERMISSION_CODE) {
            if (PermissionTool.checkPermission(activity, PermissionTool.WRITE)) {
                if (PermissionTool.isPermissionPermaDenied(activity, PermissionTool.CAMERA)) {
                    showSettingsAlert(activity);
                } else {
                    PermissionTool.requestPermission(activity, fragment, PermissionTool.CAMERA, PermissionTool.CAMERA_PERMISSION_CODE);
                }
            } else {
                PermissionTool.strikePermission(activity, PermissionTool.WRITE);
            }
        } else if (requestCode == PermissionTool.CAMERA_PERMISSION_CODE) {
            if (PermissionTool.checkPermission(activity, PermissionTool.CAMERA)) {
//                startCamera(activity);
                startImagePicker(fragment);
            } else {
                PermissionTool.strikePermission(activity, PermissionTool.CAMERA);
            }
        }
    }

    public static Bitmap onActivityResult(int requestCode, int resultCode, Intent data, ImageView imageView) {
        if (requestCode == Config.RC_PICK_IMAGES && resultCode == Activity.RESULT_OK && data != null) {
            return BitmapTool.onImageViewResult(data, imageView);
        }
        return null;
    }

    public static void startImagePicker(Fragment fragment) {
        ImagePicker.with(fragment)             //  Initialize ImagePicker with activity or fragment context
                .setToolbarColor("#ffffff")         //  Toolbar color
                .setStatusBarColor("#ff5051")       //  StatusBar color (works with SDK >= 21  )
                .setToolbarTextColor("#ff5051")     //  Toolbar text color (Title and Done button)
                .setToolbarIconColor("#ff5051")     //  Toolbar icon color (Back and Camera button)
                .setProgressBarColor("#ff5051")     //  ProgressBar color
                .setBackgroundColor("#d9d2d3")      //  Background color
                .setCameraOnly(false)               //  Camera mode
                .setMultipleMode(false)             //  Select multiple images or single image
                .setFolderMode(false)                //  Folder mode
                .setShowCamera(true)                //  Show camera button
                .setFolderTitle("Albums")           //  Folder title (works with FolderMode = true)
                .setImageTitle("Galleries")         //  Image title (works with FolderMode = false)
                .setDoneTitle("Ok")               //  Done button title
                .setLimitMessage("You have reached the limit")    // Selection limit message
                .setMaxSize(1)                     //  Max images can be selected
//                        .setSavePath("ImagePicker")         //  Image capture folder name
//                        .setSelectedImages(images)          //  Selected images
//                        .setAlwaysShowDoneButton(true)      //  Set always show done button in multiple mode
                .setKeepScreenOn(true)              //  Keep screen on when selecting images
                .start();
    }

    public static Uri startCamera(Activity activity) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photo = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
        System.out.println("startCamera Image " + photo.getPath());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
        activity.startActivityForResult(intent, PermissionTool.CAMERA_CODE);
        return Uri.fromFile(photo);
    }

    private static void showSettingsAlert(final Activity activity) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(activity, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(activity);
        }
        builder.setTitle("Warning")
                .setMessage("Change the app's permissions in phone settings to access the gallery / camera")
                .setPositiveButton("Ir a ajustes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        PermissionTool.sendSettingsForPermissionManual(activity);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
