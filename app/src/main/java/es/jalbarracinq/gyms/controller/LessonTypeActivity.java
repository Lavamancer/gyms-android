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
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.jalbarracinq.gyms.R;
import es.jalbarracinq.gyms.Session;
import es.jalbarracinq.gyms.controller.adapter.LessonTypeAdapter;
import es.jalbarracinq.gyms.controller.widget.HeaderController;
import es.jalbarracinq.gyms.model.LessonType;

public class LessonTypeActivity extends Activity {

    @BindView(R.id.lessontype_listview) ListView listView;

    HeaderController headerController;
    LessonTypeAdapter adapter;
    ArrayList<LessonType> list = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessontype);
        ButterKnife.bind(this);
        configureViews();
    }

    private void configureViews() {
        headerController = new HeaderController(this, null);
        adapter = new LessonTypeAdapter(this, list);
        listView.setAdapter(adapter);
        list.addAll(Session.getInstance().lessonTypes);
        adapter.notifyDataSetChanged();
        listView.invalidateViews();
    }
}
