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
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.jalbarracinq.gyms.R;
import es.jalbarracinq.gyms.controller.adapter.SubscriptionsAdapter;
import es.jalbarracinq.gyms.controller.widget.HeaderController;
import es.jalbarracinq.gyms.model.Subscription;
import es.jalbarracinq.gyms.service.SubscriptionService;

public class SubscriptionsActivity extends Activity {

    @BindView(R.id.subscriptions_listview) ListView listView;
    @BindView(R.id.subscriptions_actives_button) Button activesButton;

    HeaderController headerController;
    SubscriptionsAdapter adapter;
    ArrayList<Subscription> list = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscriptions);
        ButterKnife.bind(this);
        configureViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SubscriptionService.getSubscriptions(adapter, listView, list, null);
    }

    private void configureViews() {
        headerController = new HeaderController(this, null);
        adapter = new SubscriptionsAdapter(this, list);
        listView.setAdapter(adapter);

        activesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // todo implement
                onBackPressed();
            }
        });
    }
}
