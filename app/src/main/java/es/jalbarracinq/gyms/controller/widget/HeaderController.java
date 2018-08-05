/*
 * Created by Juan Albarracin on 4/08/18 14:25
 * Copyright (c) 2018. All right reserved.
 *
 * Last modified 4/08/18 14:25
 */

package es.jalbarracinq.gyms.controller.widget;


import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import es.jalbarracinq.gyms.R;

public class HeaderController {

    public HeaderController(final Activity activity, final DrawerLayout drawerLayout) {
        ImageView menuImageView = activity.findViewById(R.id.main_menu_imageview);

        if (drawerLayout == null) {
            menuImageView.setImageResource(R.drawable.ui_header_icon_back);
            menuImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.onBackPressed();
                }
            });
        } else {
            menuImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawerLayout.openDrawer(Gravity.LEFT);
                }
            });
        }

    }

}
