/*
 * Created by Juan Albarracin on 4/08/18 14:25
 * Copyright (c) 2018. All right reserved.
 *
 * Last modified 4/08/18 14:25
 */

package es.jalbarracinq.gyms.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import es.jalbarracinq.gyms.R;
import es.jalbarracinq.gyms.Session;
import es.jalbarracinq.gyms.service.LoginService;
import es.jalbarracinq.gyms.service.SplashService;

public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        configureViews();
    }

    private void configureViews() {
        if (Session.getInstance().autoLogin != null) {
            LoginService.postLogin(this, Session.getInstance().autoLogin.getUsername(), Session.getInstance().autoLogin.getPassword(), true);
        } else {
            SplashService.loadData(this);
        }
    }

}
