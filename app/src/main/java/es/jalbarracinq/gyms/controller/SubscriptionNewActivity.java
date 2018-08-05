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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.jalbarracinq.gyms.R;
import es.jalbarracinq.gyms.controller.adapter.SubscriptionTypeAdapter;
import es.jalbarracinq.gyms.controller.widget.HeaderController;
import es.jalbarracinq.gyms.controller.widget.PaymentController;
import es.jalbarracinq.gyms.model.LessonType;
import es.jalbarracinq.gyms.model.SubscriptionType;
import es.jalbarracinq.gyms.service.SubscriptionService;
import es.jalbarracinq.gyms.service.tool.DateTimeTool;
import io.apptik.widget.multiselectspinner.BaseMultiSelectSpinner;
import io.apptik.widget.multiselectspinner.MultiSelectSpinner;

public class SubscriptionNewActivity extends Activity implements PaymentController.PaymentDismiss {

    @BindView(R.id.subscriptionnew_relativelayout) RelativeLayout relativeLayout;
    @BindView(R.id.subscriptionnew_header_linearlayout) LinearLayout headerLinearLayout;
    @BindView(R.id.subscriptionnew_cancel_button) Button cancelButton;
    @BindView(R.id.subscriptionnew_monthly_radiobutton) RadioButton monthlyRadioButton;
    @BindView(R.id.subscriptionnew_usages_radiobutton) RadioButton usagesRadioButton;
    @BindView(R.id.subscriptionnew_listview) ListView listView;

    @BindView(R.id.subscriptionnew_icon_imageview) ImageView iconImageView;
    @BindView(R.id.subscriptionnew_icontitle_textview) TextView iconTitleTextView;
    @BindView(R.id.subscriptionnew_lessontypes_textview) TextView lessontypesTextView;
    @BindView(R.id.subscriptionnew_lessontypestitle_textview) TextView lessontypesTitleTextView;
    @BindView(R.id.subscriptionnew_lessontypes_spinner) MultiSelectSpinner lessontypesSpinner;
    @BindView(R.id.subscriptionnew_start_textview) TextView startTextView;

    @BindView(R.id.subscriptionnew_monthly_linearlayout) LinearLayout monthlyLinearLayout;
    @BindView(R.id.subscriptionnew_months_textview) TextView monthsTextView;
    @BindView(R.id.subscriptionnew_months_spinner) Spinner monthsSpinner;
    @BindView(R.id.subscriptionnew_days_textview) TextView daysTextView;
    @BindView(R.id.subscriptionnew_days_spinner) Spinner daysSpinner;
    @BindView(R.id.subscriptionnew_usages_linearlayout) LinearLayout usagesLinearLayout;
    @BindView(R.id.subscriptionnew_usages_textview) TextView usagesTextView;
    @BindView(R.id.subscriptionnew_usages_spinner) Spinner usagesSpinner;

    @BindView(R.id.subscriptionnew_empty_textview) TextView emptyTextView;
    @BindView(R.id.subscriptionnew_contract_linearlayout) LinearLayout contractLinearLayout;
    @BindView(R.id.subscriptionnew_contract_button) Button contractButton;

    HeaderController headerController;
    PaymentController paymentController;
    SubscriptionTypeAdapter adapter;
    ArrayList<SubscriptionType> list = new ArrayList<>();
    SubscriptionTypeAdapter.Filter filter = new SubscriptionTypeAdapter.Filter();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription_new);
        ButterKnife.bind(this);
        configureViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (paymentController != null) {
            paymentController.update();
        }
    }

    private void configureViews() {
        adapter = new SubscriptionTypeAdapter(this, list, filter, listView, contractButton, contractLinearLayout, emptyTextView);
        listView.setAdapter(adapter);
        ((ViewGroup) headerLinearLayout.getParent()).removeView(headerLinearLayout);
        listView.addHeaderView(headerLinearLayout);

        headerController = new HeaderController(this, null);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        monthlyRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRadioButtonClicked(view);
            }
        });
        usagesRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRadioButtonClicked(view);
            }
        });

        lessontypesTextView.setText("");
        monthsTextView.setText("All");
        daysTextView.setText("All");
        usagesTextView.setText("All");

        filter.setStart(DateTime.now());
        startTextView.setText(DateTime.now().toString(DateTimeTool.DATE_EDITTEXT_FORMAT));
        DateTimeTool.configureDatePicker(this, startTextView, filter, adapter);

        SubscriptionService.getSubscriptionTypes(this, adapter, list);

    }

    public void configureForms(List<SubscriptionType> subscriptionTypes) {
        // LESSON TYPES SPINNER
        ArrayList<String> lessonTypesString = new ArrayList<>();
        final ArrayList<LessonType> lessonTypes = new ArrayList<>();
        SubscriptionService.configureLessonTypes(lessonTypesString, lessonTypes, subscriptionTypes);
        lessontypesTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lessontypesSpinner.performClick();
            }
        });
        final ArrayAdapter<String> lessontypesArrayAdapter = new ArrayAdapter<>(this, R.layout.widget_spinner_checkbox, lessonTypesString);
        lessontypesSpinner.setListAdapter(lessontypesArrayAdapter).setSelectAll(true).setMinSelectedItems(1);
        lessontypesSpinner.setListener(new BaseMultiSelectSpinner.MultiSpinnerListener() {
            @Override
            public void onItemsSelected(boolean[] selected) {
                lessontypesTextView.setText(SubscriptionService.spinnerToString(lessontypesArrayAdapter, selected));
                filter.setLessonTypes(SubscriptionService.spinnerToHashMap(lessonTypes, selected));
                adapter.updateFilters();

            }
        });
        lessontypesSpinner.setSelectAll(false);
        lessontypesSpinner.setMinSelectedItems(0);

        // MONTHS SPINNER
        monthsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                monthsSpinner.performClick();
            }
        });
        ArrayList<String> monthsString = new ArrayList<>();
        final ArrayList<Integer> months = new ArrayList<>();
        SubscriptionService.configureMonths(monthsString, months, subscriptionTypes);
        final ArrayAdapter<String> monthsArrayAdapter = new ArrayAdapter<>(this, R.layout.widget_spinner, monthsString);
        monthsSpinner.setAdapter(monthsArrayAdapter);
        monthsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onNothingSelected(AdapterView<?> adapterView) {}
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                monthsTextView.setText(monthsSpinner.getSelectedItem().toString());
                filter.setMonths(i == 0 ? null : months.get(i - 1));
                adapter.updateFilters();
            }
        });

        // DAYS SPINNER
        daysTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                daysSpinner.performClick();
            }
        });
        ArrayList<String> daysString = new ArrayList<>();
        final ArrayList<Integer> days = new ArrayList<>();
        SubscriptionService.configureDays(daysString, days, subscriptionTypes);
        final ArrayAdapter<String> daysArrayAdapter = new ArrayAdapter<>(this, R.layout.widget_spinner, daysString);
        daysSpinner.setAdapter(daysArrayAdapter);
        daysSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onNothingSelected(AdapterView<?> adapterView) { }
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                daysTextView.setText(daysSpinner.getSelectedItem().toString());
                filter.setDays(i == 0 ? null : days.get(i - 1));
                adapter.updateFilters();
            }
        });

        // USAGES SPINNER
        usagesTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usagesSpinner.performClick();
            }
        });
        ArrayList<String> usagesString = new ArrayList<>();
        final ArrayList<Integer> usages = new ArrayList<>();
        SubscriptionService.configureUsages(usagesString, usages, subscriptionTypes);
        final ArrayAdapter<String> usagesArrayAdapter = new ArrayAdapter<>(this, R.layout.widget_spinner, usagesString);
        usagesSpinner.setAdapter(usagesArrayAdapter);
        usagesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onNothingSelected(AdapterView<?> adapterView) { }
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                usagesTextView.setText(usagesSpinner.getSelectedItem().toString());
                filter.setUsages(i == 0 ? null : usages.get(i - 1));
                adapter.updateFilters();
            }
        });
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.subscriptionnew_monthly_radiobutton:
                if (checked) {
                    monthlyRadioButton.setBackgroundResource(R.drawable.background_rounded_left_checked);
                    usagesRadioButton.setBackgroundResource(R.drawable.background_rounded_right_unchecked);
                    monthlyRadioButton.setEnabled(false);
                    usagesRadioButton.setEnabled(true);
                    monthlyLinearLayout.setVisibility(View.VISIBLE);
                    usagesLinearLayout.setVisibility(View.GONE);
                    iconImageView.setImageResource(R.drawable.ui_subscription_icon_monthly_red);
                    iconTitleTextView.setText("Subscription details");
                    lessontypesTitleTextView.setText("Classes included");
                    filter.setMonthly(true);
                    adapter.updateFilters();
                }
                break;
            case R.id.subscriptionnew_usages_radiobutton:
                if (checked) {
                    monthlyRadioButton.setBackgroundResource(R.drawable.background_rounded_left_unchecked);
                    usagesRadioButton.setBackgroundResource(R.drawable.background_rounded_right_checked);
                    monthlyRadioButton.setEnabled(true);
                    usagesRadioButton.setEnabled(false);
                    monthlyLinearLayout.setVisibility(View.GONE);
                    usagesLinearLayout.setVisibility(View.VISIBLE);
                    iconImageView.setImageResource(R.drawable.ui_subscription_icon_sessions_red);
                    iconTitleTextView.setText("Subscription details");
                    lessontypesTitleTextView.setText("Subscription type");
                    filter.setMonthly(false);
                    adapter.updateFilters();
                }
                break;
        }
    }

    public void showPayment(SubscriptionType subscriptionType, DateTime start) {
        paymentController = new PaymentController(this, relativeLayout, subscriptionType.getProduct(), subscriptionType, start);
    }

    @Override
    public void dismissPayment() {
        relativeLayout.removeView(paymentController.getView());
        paymentController = null;
    }
}