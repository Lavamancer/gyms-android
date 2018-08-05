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
import es.jalbarracinq.gyms.service.LoginService;
import es.jalbarracinq.gyms.service.tool.AndroidTool;

public class RegisterActivity extends Activity {

    @BindView(R.id.login_username_edittext) EditText usernameEditText;
    @BindView(R.id.login_password_edittext) EditText passwordEditText;
    @BindView(R.id.login_terms_checkbox) CheckBox termsCheckBox;
    @BindView(R.id.login_enter_button) Button enterButton;
    @BindView(R.id.login_register_button) Button registerButton;
    @BindView(R.id.login_recovery_button) Button recoveryButton;
    @BindView(R.id.login_version_textview) TextView versionTextView;
    @BindView(R.id.login_title_textview) TextView titleTextView;
    @BindView(R.id.login_back_button) Button backButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        configureViews();
    }

    private void configureViews() {
        versionTextView.setText("v" + AndroidTool.getVersion(this));
        titleTextView.setText("I WANT TO REGISTER...");
        passwordEditText.setVisibility(View.GONE);
        backButton.setVisibility(View.GONE);

        usernameEditText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ui_input_icon_mail, 0, 0, 0);
        usernameEditText.setHint("Email address");

        enterButton.setText("Register");
        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LoginService.validateLogin(RegisterActivity.this, usernameEditText.getText().toString(), null, termsCheckBox.isChecked())) {
                    LoginService.postRegister(RegisterActivity.this, usernameEditText.getText().toString());
                }
            }
        });

        registerButton.setText("I'm already register");
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        recoveryButton.setVisibility(View.GONE);
        recoveryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, RecoveryActivity.class));
            }
        });
    }
}
