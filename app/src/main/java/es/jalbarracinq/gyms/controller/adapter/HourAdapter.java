/*
 * Created by Juan Albarracin on 4/08/18 14:25
 * Copyright (c) 2018. All right reserved.
 *
 * Last modified 4/08/18 14:25
 */

package es.jalbarracinq.gyms.controller.adapter;


import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.jalbarracinq.gyms.R;
import es.jalbarracinq.gyms.model.Hour;
import es.jalbarracinq.gyms.service.CalendarService;

public class HourAdapter extends CustomBaseAdapter<Hour, HourAdapter.HourHolder> {


    public HourAdapter(Activity activity, ArrayList<Hour> list) {
        super(activity, list, R.layout.item_hour, HourHolder.class);
    }

    public static class HourHolder {
        @BindView(R.id.houritem_relativelayout) RelativeLayout relativeLayout;
        @BindView(R.id.houritem_hour_textview) TextView hourTextView;
        @BindView(R.id.houritem_name_textview) TextView nameTextView;
        @BindView(R.id.houritem_interval_textview) TextView intervalTextView;
        @BindView(R.id.houritem_warning_imageview) ImageView warningImageView;
        @BindView(R.id.houritem_background_relativelayout) RelativeLayout backgroundRelativelayout;

        public HourHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public void onBindViewHolder(HourHolder holder, int position) {
        final Hour hour = list.get(position);

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CalendarService.getLesson(activity, hour.getLesson().getId());
            }
        });

        holder.hourTextView.setText(hour.getStart().toString("HH:mm"));
        holder.nameTextView.setText(hour.getName());
        holder.intervalTextView.setText(hour.getStart().toString("HH:mm") + " - " + hour.getEnd().toString("HH:mm"));
        holder.warningImageView.setVisibility(hour.getHasEvent() ? View.VISIBLE : View.GONE);
        holder.backgroundRelativelayout.getBackground().setColorFilter(Color.parseColor(hour.getColor()), PorterDuff.Mode.SRC_ATOP);
    }

}