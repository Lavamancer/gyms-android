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
import es.jalbarracinq.gyms.controller.adapter.EvolutionAdapter;
import es.jalbarracinq.gyms.controller.widget.HeaderController;
import es.jalbarracinq.gyms.model.Evolution;
import es.jalbarracinq.gyms.service.EvolutionService;

public class EvolutionActivity extends Activity {

    @BindView(R.id.evolution_listview) ListView listView;
    @BindView(R.id.evolution_current_button) Button currentButton;

    HeaderController headerController;
    EvolutionAdapter adapter;
    ArrayList<Evolution> list = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evolution);
        ButterKnife.bind(this);
        configureViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        EvolutionService.getEvolutions(this, adapter, listView, list);
    }

    private void configureViews() {
        headerController = new HeaderController(this, null);
        adapter = new EvolutionAdapter(this, list, (Long) getIntent().getSerializableExtra("evolutionId"));
        listView.setAdapter(adapter);
        currentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // todo implement
                onBackPressed();
            }
        });
    }
}
