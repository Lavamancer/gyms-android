/*
 * Created by Juan Albarracin on 4/08/18 14:25
 * Copyright (c) 2018. All right reserved.
 *
 * Last modified 4/08/18 14:25
 */

package es.jalbarracinq.gyms.controller.adapter;


import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.jalbarracinq.gyms.R;
import es.jalbarracinq.gyms.controller.NewsActivity;
import es.jalbarracinq.gyms.model.News;

public class NewsAdapter extends CustomBaseAdapter<News, NewsAdapter.NewsHolder> {

    public NewsAdapter(Activity activity, ArrayList<News> list) {
        super(activity, list, R.layout.item_news, NewsHolder.class);
    }

    public static class NewsHolder {
        @BindView(R.id.newsitem_linearlayout) LinearLayout linearLayout;
        @BindView(R.id.newsitem_image_imageview) ImageView imageView;
        @BindView(R.id.newsitem_title_textview) TextView titleTextView;
        @BindView(R.id.newsitem_author_textview) TextView authorTextView;
        @BindView(R.id.newsitem_date_textview) TextView dateTextView;
        @BindView(R.id.newsitem_description_textview) TextView descriptionTextView;
        @BindView(R.id.newsitem_content_textview) TextView contentTextView;

        public NewsHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public void onBindViewHolder(NewsHolder holder, int position) {
        final News news = list.get(position);
        if (news.getImage() != null) {
            holder.imageView.setVisibility(View.VISIBLE);
            Glide.with(activity).load(news.getImage().getUrl()).into(holder.imageView);
        } else {
            holder.imageView.setVisibility(View.GONE);
        }
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, NewsActivity.class);
                intent.putExtra("news", news);
                activity.startActivity(intent);
            }
        });
        holder.titleTextView.setText(news.getTitle());
        holder.titleTextView.setMaxLines(2);
        holder.authorTextView.setText(news.getAuthor() != null ? news.getAuthor() : "System");
        holder.dateTextView.setText(news.getDate().toString("dd/MM/yyyy"));

        if (news.getDescription() != null) {
            holder.descriptionTextView.setVisibility(View.VISIBLE);
            holder.descriptionTextView.setText(news.getDescription());
            holder.contentTextView.setVisibility(View.GONE);
        } else {
            holder.descriptionTextView.setVisibility(View.GONE);
            holder.contentTextView.setVisibility(View.VISIBLE);
            holder.contentTextView.setText(news.getContent());
            holder.contentTextView.setMaxLines(4);
        }

    }


}