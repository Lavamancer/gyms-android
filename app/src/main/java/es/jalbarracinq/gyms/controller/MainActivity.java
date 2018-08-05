/*
 * Created by Juan Albarracin on 4/08/18 14:25
 * Copyright (c) 2018. All right reserved.
 *
 * Last modified 4/08/18 14:25
 */

package es.jalbarracinq.gyms.controller;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.jalbarracinq.gyms.R;
import es.jalbarracinq.gyms.controller.adapter.CustomPagerAdapter;
import es.jalbarracinq.gyms.controller.widget.FooterController;
import es.jalbarracinq.gyms.controller.widget.HeaderController;
import es.jalbarracinq.gyms.controller.widget.SideMenuController;
import es.jalbarracinq.gyms.controller.fragment.CalendarFragment;
import es.jalbarracinq.gyms.controller.fragment.EvolutionFragment;
import es.jalbarracinq.gyms.controller.fragment.NewsFragment;
import es.jalbarracinq.gyms.controller.fragment.ProfileFragment;
import es.jalbarracinq.gyms.controller.fragment.SubscriptionFragment;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_drawerlayout) DrawerLayout drawerLayout;
    @BindView(R.id.main_viewpager) ViewPager viewPager;

    HeaderController headerController;
    FooterController footerController;
    SideMenuController sideMenuController;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        configureTabLayoutAndViewPager();
        configureViews();
    }

    private void configureViews() {
        headerController = new HeaderController(this, drawerLayout);
        footerController = new FooterController(this, viewPager);
        sideMenuController = new SideMenuController(this, viewPager, drawerLayout);
    }

    private void configureTabLayoutAndViewPager() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new ProfileFragment());
        fragments.add(new NewsFragment());
        fragments.add(new CalendarFragment());
        fragments.add(new EvolutionFragment());
        fragments.add(new SubscriptionFragment());
        viewPager.setAdapter(new CustomPagerAdapter(getSupportFragmentManager(), fragments));
        viewPager.setCurrentItem(2);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            @Override public void onPageScrollStateChanged(int state) {}
            @Override
            public void onPageSelected(int position) {
                footerController.select(position);
                sideMenuController.select(position);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


}
