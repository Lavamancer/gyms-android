/*
 * Created by Juan Albarracin on 4/08/18 14:25
 * Copyright (c) 2018. All right reserved.
 *
 * Last modified 4/08/18 14:25
 */

package es.jalbarracinq.gyms.controller;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.jalbarracinq.gyms.R;
import es.jalbarracinq.gyms.service.LoginService;
import es.jalbarracinq.gyms.service.tool.AndroidTool;
import es.jalbarracinq.gyms.service.tool.StringTool;

public class RecoveryActivity extends Activity {

    @BindView(R.id.login_username_edittext) EditText usernameEditText;
    @BindView(R.id.login_password_edittext) EditText passwordEditText;
    @BindView(R.id.login_enter_button) Button enterButton;
    @BindView(R.id.login_version_textview) TextView versionTextView;
    @BindView(R.id.login_title_textview) TextView titleTextView;
    @BindView(R.id.login_description_textview) TextView descriptionTextView;
    @BindView(R.id.login_recovery_button) Button recoveryButton;
    @BindView(R.id.login_register_button) Button registerButton;
    @BindView(R.id.login_terms_linearlayout) LinearLayout termsLinearLayout;
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
        titleTextView.setText("RECOVERY PASSWORD");
        descriptionTextView.setText("Enter your email and we will send you an email to recover your password.");
        recoveryButton.setVisibility(View.GONE);
        registerButton.setVisibility(View.GONE);

        usernameEditText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ui_input_icon_mail, 0, 0, 0);
        usernameEditText.setHint("Email address");

        passwordEditText.setVisibility(View.GONE);
//        passwordEditText .setCompoundDrawablesWithIntrinsicBounds(R.drawable.ui_input_icon_phone, 0, 0, 0);
//        passwordEditText.setHint("Teléfono");
//        passwordEditText.setInputType(InputType.TYPE_CLASS_PHONE);
//        passwordEditText.setTypeface(usernameEditText.getTypeface());

        termsLinearLayout.setVisibility(View.GONE);

        enterButton.setText("Recovery password");
        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (StringTool.validateEmail(usernameEditText.getText().toString())) {
                    LoginService.patchRecovery(RecoveryActivity.this, usernameEditText.getText().toString());
                } else {
                    Toast.makeText(RecoveryActivity.this, "Enter a valid email", Toast.LENGTH_SHORT).show();
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
