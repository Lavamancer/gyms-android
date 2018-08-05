/*
 * Created by Juan Albarracin on 4/08/18 14:25
 * Copyright (c) 2018. All right reserved.
 *
 * Last modified 4/08/18 14:25
 */

package es.jalbarracinq.gyms.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.jalbarracinq.gyms.R;
import es.jalbarracinq.gyms.Session;
import es.jalbarracinq.gyms.service.LoginService;
import es.jalbarracinq.gyms.service.tool.AndroidTool;

public class LoginActivity extends Activity {

    @BindView(R.id.login_username_edittext) EditText usernameEditText;
    @BindView(R.id.login_password_edittext) EditText passwordEditText;
    @BindView(R.id.login_terms_checkbox) CheckBox termsCheckBox;
    @BindView(R.id.login_enter_button) Button enterButton;
    @BindView(R.id.login_register_button) Button registerButton;
    @BindView(R.id.login_recovery_button) Button recoveryButton;
    @BindView(R.id.login_description_textview) TextView descriptionTextView;
    @BindView(R.id.login_back_button) Button backButton;
    @BindView(R.id.login_version_textview) TextView versionTextView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        configureViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Session.getInstance().login != null) {
            usernameEditText.setText(Session.getInstance().login.getUsername());
            passwordEditText.setText(Session.getInstance().login.getPassword());
        }
    }

    private void configureViews() {
        versionTextView.setText("v" + AndroidTool.getVersion(this));
        descriptionTextView.setVisibility(View.GONE);
        backButton.setVisibility(View.GONE);

        usernameEditText.setText("jane@gyms.com");
        passwordEditText.setText("1234");

        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LoginService.validateLogin(LoginActivity.this, usernameEditText.getText().toString(), passwordEditText.getText().toString(), termsCheckBox.isChecked())) {
                    LoginService.postLogin(LoginActivity.this, usernameEditText.getText().toString(), passwordEditText.getText().toString(), false);
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        recoveryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RecoveryActivity.class));
            }
        });
    }
}
