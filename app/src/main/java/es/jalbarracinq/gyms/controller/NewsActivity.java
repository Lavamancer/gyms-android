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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.jalbarracinq.gyms.R;
import es.jalbarracinq.gyms.controller.widget.HeaderController;
import es.jalbarracinq.gyms.model.News;
import es.jalbarracinq.gyms.service.tool.WebTool;

public class NewsActivity extends Activity {

    @BindView(R.id.newsitem_linearlayout) LinearLayout linearLayout;
    @BindView(R.id.newsitem_image_imageview) ImageView imageView;
    @BindView(R.id.newsitem_title_textview) TextView titleTextView;
    @BindView(R.id.newsitem_author_textview) TextView authorTextView;
    @BindView(R.id.newsitem_date_textview) TextView dateTextView;
    @BindView(R.id.newsitem_description_textview) TextView descriptionTextView;
    @BindView(R.id.newsitem_content_textview) TextView contentTextView;

    HeaderController headerController;
    News news;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);
        configureExtras();
        configureViews();
    }

    private void configureExtras() {
        news = (News) getIntent().getSerializableExtra("news");
    }

    private void configureViews() {
        headerController = new HeaderController(this, null);

        if (news.getImage() != null) {
            imageView.setVisibility(View.VISIBLE);
            Glide.with(this).load(news.getImage().getUrl()).into(imageView);
        } else {
            imageView.setVisibility(View.GONE);
        }

        titleTextView.setText(news.getTitle());
        authorTextView.setText(news.getAuthor() != null ? news.getAuthor() : "System");
        dateTextView.setText(news.getDate().toString("dd/MM/yyyy"));

        if (news.getDescription() != null) {
            descriptionTextView.setVisibility(View.VISIBLE);
            descriptionTextView.setText(news.getDescription());
        } else {
            descriptionTextView.setVisibility(View.GONE);
        }

        if (news.getContent() != null) {
            contentTextView.setVisibility(View.VISIBLE);
            contentTextView.setText(news.getContent());
        } else {
            contentTextView.setVisibility(View.GONE);
        }

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebTool.show(NewsActivity.this, news.getUrl());
            }
        });
    }

}
