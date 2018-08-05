/*
 * Created by Juan Albarracin on 4/08/18 14:25
 * Copyright (c) 2018. All right reserved.
 *
 * Last modified 4/08/18 14:25
 */

package es.jalbarracinq.gyms.controller.widget;


import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import es.jalbarracinq.gyms.R;

public class FooterController {

    Activity activity;
    ViewPager viewPager;

    LinearLayout profileLinearLayout;
    LinearLayout newsLinearLayout;
    RelativeLayout calendarRelativeLayout;
    LinearLayout evolutionLinearLayout;
    LinearLayout subscriptionsLinearLayout;

    ImageView profileImageView;
    ImageView newsImageView;
    ImageView calendarImageView;
    ImageView evolutionImageView;
    ImageView subscriptionsImageView;

    TextView profileTextView;
    TextView newsTextView;
    TextView calendarTextView;
    TextView evolutionTextView;
    TextView subscriptionsTextView;

    public FooterController(Activity activity, final ViewPager viewPager) {
        this.activity = activity;
        this.viewPager = viewPager;

        profileLinearLayout = activity.findViewById(R.id.main_profile_linearlayout);
        newsLinearLayout = activity.findViewById(R.id.main_news_linearlayout);
        calendarRelativeLayout = activity.findViewById(R.id.main_calendar_relativelayout);
        evolutionLinearLayout = activity.findViewById(R.id.main_evolution_linearlayout);
        subscriptionsLinearLayout = activity.findViewById(R.id.main_subscriptions_linearlayout);

        profileImageView = activity.findViewById(R.id.main_profile_imageview);
        newsImageView = activity.findViewById(R.id.main_news_imageview);
        calendarImageView = activity.findViewById(R.id.main_calendar_imageview);
        evolutionImageView = activity.findViewById(R.id.main_evolution_imageview);
        subscriptionsImageView = activity.findViewById(R.id.main_subscriptions_imageview);

        profileTextView = activity.findViewById(R.id.main_profile_textview);
        newsTextView = activity.findViewById(R.id.main_news_textview);
        calendarTextView = activity.findViewById(R.id.main_calendar_textview);
        evolutionTextView = activity.findViewById(R.id.main_evolution_textview);
        subscriptionsTextView = activity.findViewById(R.id.main_subscriptions_textview);

        configureListener(profileLinearLayout, 0);
        configureListener(newsLinearLayout, 1);
        configureListener(calendarRelativeLayout, 2);
        configureListener(evolutionLinearLayout, 3);
        configureListener(subscriptionsLinearLayout, 4);
    }

    private void configureListener(ViewGroup viewGroup, final int position) {
        viewGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(position);
            }
        });
    }

    private void unselectAll() {
        profileImageView.setImageResource(R.drawable.ui_footer_icon_profile_inactive);
        profileTextView.setTextColor(ContextCompat.getColor(activity, R.color.white_50));

        newsImageView.setImageResource(R.drawable.ui_footer_icon_news_inactive);
        newsTextView.setTextColor(ContextCompat.getColor(activity, R.color.white_50));

        calendarImageView.setImageResource(R.drawable.ui_footer_icon_calendar_inactive);
        calendarTextView.setTextColor(ContextCompat.getColor(activity, R.color.white_50));

        evolutionImageView.setImageResource(R.drawable.ui_footer_icon_evolution_inactive);
        evolutionTextView.setTextColor(ContextCompat.getColor(activity, R.color.white_50));

        subscriptionsImageView.setImageResource(R.drawable.ui_footer_icon_subscriptions_inactive);
        subscriptionsTextView.setTextColor(ContextCompat.getColor(activity, R.color.white_50));
    }

    public void select(int position) {
        unselectAll();
        switch (position) {
            case 0:
                profileImageView.setImageResource(R.drawable.ui_footer_icon_profile_active);
                profileTextView.setTextColor(ContextCompat.getColor(activity, R.color.white));
                break;
            case 1:
                newsImageView.setImageResource(R.drawable.ui_footer_icon_news_active);
                newsTextView.setTextColor(ContextCompat.getColor(activity, R.color.white));
                break;
            case 2:
                calendarImageView.setImageResource(R.drawable.ui_footer_icon_calendar_active);
                calendarTextView.setTextColor(ContextCompat.getColor(activity, R.color.white));
                break;
            case 3:
                evolutionImageView.setImageResource(R.drawable.ui_footer_icon_evolution_active);
                evolutionTextView.setTextColor(ContextCompat.getColor(activity, R.color.white));
                break;
            case 4:
                subscriptionsImageView.setImageResource(R.drawable.ui_footer_icon_subscriptions_active);
                subscriptionsTextView.setTextColor(ContextCompat.getColor(activity, R.color.white));
                break;
        }
    }

}
