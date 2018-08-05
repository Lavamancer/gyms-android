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
import android.widget.ListView;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import es.jalbarracinq.gyms.R;
import es.jalbarracinq.gyms.controller.adapter.SubscriptionAdapter;
import es.jalbarracinq.gyms.controller.widget.HeaderController;
import es.jalbarracinq.gyms.model.Lesson;
import es.jalbarracinq.gyms.model.Subscription;

public class ReservationActivity extends Activity {

    @BindView(R.id.reservation_listview) ListView listView;
    @BindView(R.id.reservation_cancel_button) Button cancelButton;
    @BindView(R.id.reservation_reservation_linearlayout) LinearLayout reservationLinearLayout;
    @BindView(R.id.reservation_reservation_button) Button reservationButton;

    SubscriptionAdapter adapter;
    ArrayList<Subscription> list;
    Lesson lesson;
    HeaderController headerController;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        ButterKnife.bind(this);
        list = (ArrayList<Subscription>) getIntent().getSerializableExtra("subscriptions");
        lesson = (Lesson) getIntent().getSerializableExtra("lesson");
        configureViews();
    }

    private void configureViews() {
        headerController = new HeaderController(this, null);
        adapter = new SubscriptionAdapter(this, list, lesson, reservationLinearLayout, reservationButton, listView);
        listView.setAdapter(adapter);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }
}
