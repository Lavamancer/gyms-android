/*
 * Created by Juan Albarracin on 4/08/18 14:25
 * Copyright (c) 2018. All right reserved.
 *
 * Last modified 4/08/18 14:25
 */

package es.jalbarracinq.gyms.controller.widget;


import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.jalbarracinq.gyms.R;
import es.jalbarracinq.gyms.Session;
import es.jalbarracinq.gyms.controller.DietActivity;
import es.jalbarracinq.gyms.controller.LessonTypeActivity;
import es.jalbarracinq.gyms.model.Configuration;
import es.jalbarracinq.gyms.service.LoginService;
import es.jalbarracinq.gyms.service.tool.DialogTool;
import es.jalbarracinq.gyms.service.tool.WebTool;

public class SideMenuController {

    Activity activity;
    ViewPager viewPager;
    DrawerLayout drawerLayout;

    int leftMargin;
    int topMargin;

    @BindView(R.id.sidemenu_profile_imageview) ImageView profileImageView;
    @BindView(R.id.sidemenu_name_textview) TextView nameTextView;
    @BindView(R.id.sidemenu_logout_imageview) ImageView logoutImageView;
    @BindView(R.id.sidemenu_calendar_linearlayout) LinearLayout calendarLinearLayout;
    @BindView(R.id.sidemenu_profile_linearlayout) LinearLayout profileLinearLayout;
    @BindView(R.id.sidemenu_news_linearlayout) LinearLayout newsLinearLayout;
    @BindView(R.id.sidemenu_evolution_linearlayout) LinearLayout evolutionLinearLayout;
    @BindView(R.id.sidemenu_diet_linearlayout) LinearLayout dietLinearLayout;
    @BindView(R.id.sidemenu_lessons_linearlayout) LinearLayout lessonsLinearLayout;
    @BindView(R.id.sidemenu_subscriptions_linearlayout) LinearLayout subscriptionsLinearLayout;
    @BindView(R.id.sidemenu_help_linearlayout) LinearLayout helpLinearLayout;
    @BindView(R.id.sidemenu_facebook_imageview) ImageView facebookImageView;
    @BindView(R.id.sidemenu_twitter_imageview) ImageView twitterImageView;
    @BindView(R.id.sidemenu_share_imageview) ImageView shareImageView;


    public SideMenuController(final Activity activity, final ViewPager viewPager, final DrawerLayout drawerLayout) {
        ButterKnife.bind(this, activity);
        this.activity = activity;
        this.viewPager = viewPager;
        this.drawerLayout = drawerLayout;

        if (Session.getInstance().user.getImage() != null) {
            Glide.with(activity).load(Session.getInstance().user.getImage().getUrl()).into(profileImageView);
        }

        leftMargin = ((ViewGroup.MarginLayoutParams) calendarLinearLayout.getLayoutParams()).leftMargin;
        topMargin = ((ViewGroup.MarginLayoutParams) calendarLinearLayout.getLayoutParams()).topMargin;

        if (Session.getInstance().user != null && Session.getInstance().user.getName() != null && Session.getInstance().user.getLastname() != null) {
            nameTextView.setText(Session.getInstance().user.getName() + " " + Session.getInstance().user.getLastname());
        } else {
            nameTextView.setText("");
        }

        logoutImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogTool.DialogWidget dialogWidget = DialogTool.createDialog(activity, "Logout", "Do you want to close your current session?");
                dialogWidget.acceptButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LoginService.postLogout(activity);
                    }
                });
                dialogWidget.dialog.show();
            }
        });

        configureListener(calendarLinearLayout, 2);
        configureListener(profileLinearLayout, 0);
        configureListener(newsLinearLayout, 1);
        configureListener(evolutionLinearLayout, 3);
        configureListener(subscriptionsLinearLayout, 4);

        lessonsLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawer(Gravity.LEFT);
                activity.startActivity(new Intent(activity, LessonTypeActivity.class));
            }
        });

        dietLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawer(Gravity.LEFT);
                activity.startActivity(new Intent(activity, DietActivity.class));
            }
        });

        helpLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawer(Gravity.LEFT);
                WebTool.show(activity, Session.getInstance().configurations.get(Configuration.Name.HELP_URL));
            }
        });

        facebookImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawer(Gravity.LEFT);
                WebTool.show(activity, Session.getInstance().configurations.get(Configuration.Name.FACEBOOK_URL));
            }
        });

        twitterImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawer(Gravity.LEFT);
                WebTool.show(activity, Session.getInstance().configurations.get(Configuration.Name.TWITTER_URL));
            }
        });

        shareImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawer(Gravity.LEFT);
                WebTool.share(activity, "Gyms", "I share a link", Session.getInstance().configurations.get(Configuration.Name.SHARE_ANDROID_URL));
            }
        });
    }

    private void configureListener(ViewGroup viewGroup, final int position) {
        viewGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(Gravity.LEFT);
                viewPager.setCurrentItem(position);
            }
        });
    }

    private void unselectAll() {
        setMargins(calendarLinearLayout, 0, topMargin, 0, 0);
        setMargins(profileLinearLayout, 0, topMargin, 0, 0);
        setMargins(newsLinearLayout, 0, topMargin, 0, 0);
        setMargins(evolutionLinearLayout, 0, topMargin, 0, 0);
        setMargins(dietLinearLayout, 0, topMargin, 0, 0);
        setMargins(lessonsLinearLayout, 0, topMargin, 0, 0);
        setMargins(subscriptionsLinearLayout, 0, topMargin, 0, 0);
        setMargins(helpLinearLayout, 0, topMargin, 0, 0);
    }

    public void select(int position) {
        unselectAll();
        switch (position) {
            case 0: setMargins(profileLinearLayout, leftMargin, topMargin, 0, 0); break;
            case 1: setMargins(newsLinearLayout, leftMargin, topMargin, 0, 0); break;
            case 2: setMargins(calendarLinearLayout, leftMargin, topMargin, 0, 0); break;
            case 3: setMargins(evolutionLinearLayout, leftMargin, topMargin, 0, 0); break;
            case 4: setMargins(subscriptionsLinearLayout, leftMargin, topMargin, 0, 0); break;
        }
    }

    private static void setMargins (View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }
}
