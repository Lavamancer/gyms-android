/*
 * Created by Juan Albarracin on 4/08/18 14:25
 * Copyright (c) 2018. All right reserved.
 *
 * Last modified 4/08/18 14:25
 */

package es.jalbarracinq.gyms.service.tool;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;

import com.nguyenhoanglam.imagepicker.model.Config;
import com.nguyenhoanglam.imagepicker.model.Image;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class BitmapTool {


    public static byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public static Bitmap onImageViewResult(Intent data, ImageView imageView) {
        ArrayList<Image> images = data.getParcelableArrayListExtra(Config.EXTRA_IMAGES);
        String path = images.get(0).getPath();

        Bitmap bitmap = BitmapFactory.decodeFile(path);
        bitmap = BitmapTool.croppedProportionally(710, bitmap);
        bitmap = BitmapTool.rotateProperly(bitmap, path);

        if (imageView != null) {
            imageView.setImageBitmap(bitmap);
            imageView.setVisibility(View.VISIBLE);
            imageView.setPadding(0, 0, 0, 0);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
        return bitmap;
    }

    public static Bitmap croppedProportionally(int max, Bitmap bitmap) {
        if (bitmap == null) return null;
        int h, w;
        if (bitmap.getHeight() >= bitmap.getWidth()) {
            if (bitmap.getHeight() >= max) {
                h = max;
                w = bitmap.getWidth() * max / bitmap.getHeight();
            } else {
                h = bitmap.getHeight();
                w = bitmap.getWidth();
            }
        } else {
            if (bitmap.getWidth() >= max) {
                w = max;
                h = bitmap.getHeight() * max / bitmap.getWidth();
            } else {
                w = bitmap.getWidth();
                h = bitmap.getHeight();
            }
        }
        return Bitmap.createScaledBitmap(bitmap, w, h, true);
    }

    public static Bitmap rotateProperly(Bitmap bitmap, String path) {
        if (bitmap == null || path == null) return null;
        int rotate = 0;
        try {
            ExifInterface exif = new ExifInterface(path);
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Matrix matrix = new Matrix();
        matrix.postRotate(rotate);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static Bitmap square(Bitmap bitmap) {
        if (bitmap == null) return null;
        if (bitmap.getHeight() > bitmap.getWidth()) {
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getWidth());
        } else if (bitmap.getWidth() > bitmap.getHeight()) {
            return Bitmap.createBitmap(bitmap, (bitmap.getWidth() / 2) - (bitmap.getHeight() / 2), 0, bitmap.getHeight(), bitmap.getHeight());
        } else {
            return bitmap;
        }
    }

    public static String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

}
