/*
 * Created by Juan Albarracin on 4/08/18 14:25
 * Copyright (c) 2018. All right reserved.
 *
 * Last modified 4/08/18 14:25
 */

package es.jalbarracinq.gyms.controller.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.jalbarracinq.gyms.R;
import es.jalbarracinq.gyms.controller.adapter.NewsAdapter;
import es.jalbarracinq.gyms.model.News;
import es.jalbarracinq.gyms.service.NewsService;

public class NewsFragment extends Fragment {

    @BindView(R.id.news_listview) ListView listView;

    NewsAdapter adapter;
    ArrayList<News> list = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        configureViews();
    }

    private void configureViews() {
        adapter = new NewsAdapter(getActivity(), list);
        listView.setAdapter(adapter);
        NewsService.getNews(adapter, listView, list);
    }

}
