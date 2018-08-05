/*
 * Created by Juan Albarracin on 4/08/18 14:25
 * Copyright (c) 2018. All right reserved.
 *
 * Last modified 4/08/18 14:25
 */

package es.jalbarracinq.gyms.controller.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.jalbarracinq.gyms.R;
import es.jalbarracinq.gyms.controller.SubscriptionNewActivity;
import es.jalbarracinq.gyms.controller.SubscriptionsActivity;
import es.jalbarracinq.gyms.controller.adapter.SubscriptionAdapter;
import es.jalbarracinq.gyms.model.Subscription;
import es.jalbarracinq.gyms.service.SubscriptionService;

public class SubscriptionFragment extends Fragment {

    @BindView(R.id.subscription_listview) ListView listView;
    @BindView(R.id.subscription_history_button) Button historyButton;
    @BindView(R.id.subscription_new_button) Button newButton;

    SubscriptionAdapter adapter;
    ArrayList<Subscription> list = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_subscription, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        configureViews();
    }

    @Override
    public void onResume() {
        super.onResume();
        SubscriptionService.getSubscriptions(adapter, listView, list, true);
    }

    private void configureViews() {
        adapter = new SubscriptionAdapter(getActivity(), list, null, null, null, listView);
        listView.setAdapter(adapter);

        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SubscriptionsActivity.class));
            }
        });

        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SubscriptionNewActivity.class));
            }
        });
    }

}
