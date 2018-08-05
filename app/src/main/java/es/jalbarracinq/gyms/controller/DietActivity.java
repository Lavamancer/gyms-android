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
import android.widget.LinearLayout;
import android.widget.TextView;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.jalbarracinq.gyms.R;
import es.jalbarracinq.gyms.Session;
import es.jalbarracinq.gyms.model.Diet;
import es.jalbarracinq.gyms.model.Meal;
import es.jalbarracinq.gyms.model.Subscriber;
import es.jalbarracinq.gyms.controller.widget.HeaderController;

public class DietActivity extends Activity {

    @BindView(R.id.diet_today_button) Button todayButton;
    @BindView(R.id.diet_previous_linearlayout) LinearLayout previousLinearLayout;
    @BindView(R.id.diet_next_linearlayout) LinearLayout nextLinearLayout;
    @BindView(R.id.diet_previous_textview) TextView previousTextView;
    @BindView(R.id.diet_next_textview) TextView nextTextView;
    @BindView(R.id.diet_day_textview) TextView dayTextView;
    @BindView(R.id.diet_breakfast_textview) TextView breakfastTextView;
    @BindView(R.id.diet_brunch_textview) TextView brunchTextView;
    @BindView(R.id.diet_lunch_textview) TextView lunchTextView;
    @BindView(R.id.diet_snack_textview) TextView snackTextView;
    @BindView(R.id.diet_dinner_textview) TextView dinnerTextView;

    HeaderController headerController;
    HashMap<Integer, HashMap<Meal.Type, List<Meal>>> meals;
    DateTime date;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet);
        ButterKnife.bind(this);
        configureViews();
    }

    private void configureViews() {
        headerController = new HeaderController(this, null);
        configureMeals(((Subscriber) Session.getInstance().user).getDiet());
        configureDay(DateTime.now());
        configureListener();
    }

    private void configureDay(DateTime dateTime) {
        this.date = dateTime;

        previousTextView.setText(date.minusDays(1).dayOfWeek().getAsText());
        dayTextView.setText(date.dayOfWeek().getAsText());
        nextTextView.setText(date.plusDays(1).dayOfWeek().getAsText());

        breakfastTextView.setText(configureMealText(meals.get(date.getDayOfWeek()).get(Meal.Type.BREAKFAST)));
        brunchTextView.setText(configureMealText(meals.get(date.getDayOfWeek()).get(Meal.Type.BRUNCH)));
        lunchTextView.setText(configureMealText(meals.get(date.getDayOfWeek()).get(Meal.Type.LUNCH)));
        snackTextView.setText(configureMealText(meals.get(date.getDayOfWeek()).get(Meal.Type.SNACK)));
        dinnerTextView.setText(configureMealText(meals.get(date.getDayOfWeek()).get(Meal.Type.DINNER)));
    }

    private void configureListener() {
        previousLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                configureDay(date.minusDays(1));
            }
        });
        nextLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                configureDay(date.plusDays(1));
            }
        });
        todayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                configureDay(DateTime.now());
            }
        });
    }

    private void configureMeals(Diet diet) {
        meals = new HashMap<>();
        for (int i = 1; i < 8; i++) {
            HashMap<Meal.Type, List<Meal>> map = new HashMap<>();
            map.put(Meal.Type.BREAKFAST, new ArrayList<Meal>());
            map.put(Meal.Type.BRUNCH, new ArrayList<Meal>());
            map.put(Meal.Type.LUNCH, new ArrayList<Meal>());
            map.put(Meal.Type.SNACK, new ArrayList<Meal>());
            map.put(Meal.Type.DINNER, new ArrayList<Meal>());
            meals.put(i, map);
        }

        if (diet != null) {
            for (Meal meal : diet.getMeals()) {
                meals.get(meal.getDay()).get(meal.getType()).add(meal);
            }
        }
    }

    private String configureMealText(List<Meal> list) {
        String text = "";
        for (Meal meal : list) {
            text += "- " + meal.getName() + "\n";
        }
        return text;
    }

}
