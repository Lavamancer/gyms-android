/*
 * Created by Juan Albarracin on 4/08/18 14:25
 * Copyright (c) 2018. All right reserved.
 *
 * Last modified 4/08/18 14:25
 */

package es.jalbarracinq.gyms.controller.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.jalbarracinq.gyms.R;
import es.jalbarracinq.gyms.Session;
import es.jalbarracinq.gyms.model.Facility;
import es.jalbarracinq.gyms.model.Subscriber;
import es.jalbarracinq.gyms.model.User;
import es.jalbarracinq.gyms.service.LoginService;
import es.jalbarracinq.gyms.service.ProfileService;
import es.jalbarracinq.gyms.service.tool.BitmapTool;
import es.jalbarracinq.gyms.service.tool.DateTimeTool;
import es.jalbarracinq.gyms.service.tool.ImagePickerTool;

public class ProfileFragment extends Fragment {

    @BindView(R.id.profile_name_textview) TextView nameTextView;
    @BindView(R.id.profile_name_edittext) EditText nameEditText;
    @BindView(R.id.profile_lastname_edittext) EditText lastnameEditText;
    @BindView(R.id.profile_nickname_edittext) EditText nicknameEditText;
    @BindView(R.id.profile_personalid_edittext) EditText personalidEditText;
    @BindView(R.id.profile_birthdate_edittext) EditText birthdateEditText;
    @BindView(R.id.profile_phone_edittext) EditText phoneEditText;
    @BindView(R.id.profile_save_button) Button saveButton;
    @BindView(R.id.profile_facility_textview) TextView facilityTextView;
    @BindView(R.id.profile_facility_spinner) Spinner facilitySpinner;
    @BindView(R.id.profile_imageview) ImageView imageView;
    @BindView(R.id.profile_select_imageview) ImageView selectImageView;
    @BindView(R.id.profile_gender_textview) TextView genderTextView;
    @BindView(R.id.profile_gender_spinner) Spinner genderSpinner;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        configureViews();
    }

    private void configureViews() {
        User user = Session.getInstance().user;
        if (user != null) {
            if (user.getImage() != null) {
                Glide.with(getActivity()).load(user.getImage().getUrl()).into(imageView);
            }

            nameTextView.setText((user.getName() != null && user.getLastname() != null) ? user.getName() + " " + user.getLastname() : "");
            nameEditText.setText(user.getName() != null ? user.getName() : "");
            lastnameEditText.setText(user.getLastname() != null ? user.getLastname() : "");
            nicknameEditText.setText(user.getNickname() != null ? user.getNickname() : "");
            personalidEditText.setText(user.getPersonalId() != null ? user.getPersonalId() : "");
            birthdateEditText.setText(user.getBirthdate() != null ? user.getBirthdate().toString(DateTimeTool.DATE_EDITTEXT_FORMAT) : "");
            phoneEditText.setText(user.getPhone() != null ? user.getPhone() : "");

            facilityTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    facilitySpinner.performClick();
                }
            });
            ArrayList<String> facilities = new ArrayList<>();
            int position = 0;
            for (int i = 0; i < Session.getInstance().facilities.size(); i++) {
                Facility facility = Session.getInstance().facilities.get(i);
                facilities.add(facility.getName());
                if (user instanceof Subscriber
                        && ((Subscriber) user).getFacility() != null
                        && ((Subscriber) user).getFacility().getId().longValue() == facility.getId().longValue()) {
                    position = i;
                }
            }
            final ArrayAdapter<String> facilitiesArrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.widget_spinner, facilities);
            facilitySpinner.setAdapter(facilitiesArrayAdapter);
            facilitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override public void onNothingSelected(AdapterView<?> adapterView) {}
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    facilityTextView.setText(facilitySpinner.getSelectedItem().toString());
                }
            });
            facilitySpinner.setSelection(position);

            genderTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    genderSpinner.performClick();
                }
            });

            genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override public void onNothingSelected(AdapterView<?> adapterView) {}
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    genderTextView.setText(genderSpinner.getSelectedItem().toString());
                }
            });
            if (user.getGender() != null) {
                switch (user.getGender()) {
                    case MALE: genderSpinner.setSelection(0); break;
                    case FEMALE: genderSpinner.setSelection(1); break;
                }
            }
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Subscriber subscriber = new Subscriber();
                subscriber.setName(nameEditText.getText().toString());
                subscriber.setLastname(lastnameEditText.getText().toString());
                subscriber.setNickname(nicknameEditText.getText().toString());
                subscriber.setPersonalId(personalidEditText.getText().toString());
                subscriber.setFacility(Session.getInstance().facilities.get(facilitySpinner.getSelectedItemPosition()));
                subscriber.setGender(genderSpinner.getSelectedItemPosition() == 0 ? User.Gender.MALE : User.Gender.FEMALE);

                if (!birthdateEditText.getText().toString().isEmpty()) {
                    subscriber.setBirthdate(DateTime.parse(birthdateEditText.getText().toString(), DateTimeFormat.forPattern(DateTimeTool.DATE_EDITTEXT_FORMAT)));
                }
                subscriber.setPhone(phoneEditText.getText().toString());
                if (LoginService.validateProfile(getActivity(), subscriber)) {
                    ProfileService.save(getActivity(), subscriber);
                }
            }
        });

        DateTimeTool.configureDatePicker(getActivity(), birthdateEditText);

        selectImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePickerTool.start(getActivity(), ProfileFragment.this);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        ImagePickerTool.onRequestPermissionsResult(getActivity(), this, requestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap bitmap = ImagePickerTool.onActivityResult(requestCode, resultCode, data, null);
        if (bitmap != null) {
            ProfileService.updateImage(getActivity(), BitmapTool.bitmapToByteArray(bitmap));
        }
    }

}