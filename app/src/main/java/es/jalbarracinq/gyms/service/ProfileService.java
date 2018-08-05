/*
 * Created by Juan Albarracin on 4/08/18 14:25
 * Copyright (c) 2018. All right reserved.
 *
 * Last modified 4/08/18 14:25
 */

package es.jalbarracinq.gyms.service;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import es.jalbarracinq.gyms.Session;
import es.jalbarracinq.gyms.controller.MainActivity;
import es.jalbarracinq.gyms.controller.ProfileActivity;
import es.jalbarracinq.gyms.model.Subscriber;
import es.jalbarracinq.gyms.service.tool.FakerTool;
import es.jalbarracinq.gyms.service.tool.ProgressBarTool;
import es.jalbarracinq.gyms.service.tool.RetrofitTool;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ProfileService {


    public static void save(final Activity activity, final Subscriber subscriber) {
        final Dialog dialog = ProgressBarTool.create(activity);
        dialog.show();
        new AsyncTask<Void, Void, Subscriber>() {
            @Override
            protected Subscriber doInBackground(Void... voids) {
                if (Session.getInstance().mock) {
                    return new Subscriber(FakerTool.getInstance(), 1);
                } else {
                    try {
                        return RetrofitTool.getInstance().apiRepository.patchSubscribers(Session.getInstance().token.getAccessToken(), subscriber).execute().body();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Subscriber result) {
                dialog.dismiss();
                if (result != null) {
                    Session.getInstance().user = result;
                    if (activity instanceof ProfileActivity) {
                        activity.startActivity(new Intent(activity, MainActivity.class));
                        activity.overridePendingTransition(0, 0);
                        activity.finish();
                    } else {
                        activity.recreate(); // para que se modifique el nombre en el viewpager
                    }
                } else {
                    Toast.makeText(activity, "Unable to connect to the server", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }

    public static void updateImage(final Activity activity, byte[] image) {
        final Dialog dialog = ProgressBarTool.create(activity);
        dialog.show();

        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), image);
        final MultipartBody.Part body = MultipartBody.Part.createFormData("image", "image.jpg", requestBody);
        final RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "image");

        new AsyncTask<Void, Void, Subscriber>() {
            @Override
            protected Subscriber doInBackground(Void... voids) {
                if (Session.getInstance().mock) {
                    return new Subscriber(FakerTool.getInstance(), 1);
                } else {
                    try {
                        return RetrofitTool.getInstance().apiRepository.postSubscriberImage(Session.getInstance().token.getAccessToken(), body, name).execute().body();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Subscriber result) {
                dialog.dismiss();
                if (result != null) {
                    Session.getInstance().user = result;
                    activity.recreate();
                } else {
                    Toast.makeText(activity, "Unable to connect to the server", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }
}
