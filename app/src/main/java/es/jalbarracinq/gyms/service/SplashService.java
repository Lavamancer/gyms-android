/*
 * Created by Juan Albarracin on 4/08/18 16:13
 * Copyright (c) 2018. All right reserved.
 *
 * Last modified 4/08/18 16:13
 */

package es.jalbarracinq.gyms.service;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;

import com.github.javafaker.Faker;

import es.jalbarracinq.gyms.controller.LoginActivity;
import es.jalbarracinq.gyms.service.tool.FakerTool;

public class SplashService {


    public static void loadData(final Activity activity) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                Faker faker = FakerTool.getInstance();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                activity.startActivity(new Intent(activity, LoginActivity.class));
                activity.overridePendingTransition(0, 0);
                activity.finish();
            }
        }.execute();
    }

}
