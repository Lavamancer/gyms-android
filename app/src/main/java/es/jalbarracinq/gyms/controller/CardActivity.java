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
import android.text.InputFilter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.jalbarracinq.gyms.R;
import es.jalbarracinq.gyms.controller.widget.HeaderController;
import es.jalbarracinq.gyms.model.Card;
import es.jalbarracinq.gyms.service.CardService;
import es.jalbarracinq.gyms.service.tool.InputMinMaxFilter;
import es.jalbarracinq.gyms.service.tool.StringTool;

public class CardActivity extends Activity {

    @BindView(R.id.card_types_textview) TextView typesTextView;
    @BindView(R.id.card_types_spinner) Spinner typesSpinner;
    @BindView(R.id.card_number_edittext) EditText numberEditText;
    @BindView(R.id.card_verification_edittext) EditText verificationEditText;
    @BindView(R.id.card_month_edittext) EditText monthEditText;
    @BindView(R.id.card_year_edittext) EditText yearEditText;
    @BindView(R.id.card_add_linearlayout) LinearLayout addLinearLayout;
    @BindView(R.id.card_add_button) Button addButton;

    HeaderController headerController;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        ButterKnife.bind(this);
        configureViews();

        numberEditText.setText("4242424242424242");
        verificationEditText.setText("123");
        monthEditText.setText("9");
        yearEditText.setText("22");
    }

    private void configureViews() {
        headerController = new HeaderController(this, null);
        monthEditText.setFilters(new InputFilter[]{ new InputMinMaxFilter("1", "12")});
        typesTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                typesSpinner.performClick();
            }
        });
        ArrayList<String> typesString = new ArrayList<>();
        final ArrayList<Card.Type> types = new ArrayList<>();
        for (Card.Type type : Card.Type.values()) {
            typesString.add(StringTool.underscoreToCapitalized(type.toString()));
            types.add(type);
        }
        final ArrayAdapter<String> typesArrayAdapter = new ArrayAdapter<>(this, R.layout.widget_spinner, typesString);
        typesSpinner.setAdapter(typesArrayAdapter);
        typesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onNothingSelected(AdapterView<?> adapterView) {}
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                typesTextView.setText(typesSpinner.getSelectedItem().toString());
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Card card = new Card();
                card.setType(types.get(typesSpinner.getSelectedItemPosition()));
                card.setNumber(numberEditText.getText().toString());
                card.setVerification(verificationEditText.getText().toString());
                card.setMonth(monthEditText.getText().toString().isEmpty() ? null : Integer.valueOf(monthEditText.getText().toString()));
                card.setYear(yearEditText.getText().toString().isEmpty() ? null : Integer.valueOf(yearEditText.getText().toString()));

                if (CardService.validate(CardActivity.this, card)) {
                    CardService.postCard(CardActivity.this, card);
                }
            }
        });
    }

}
