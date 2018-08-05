/*
 * Created by Juan Albarracin on 4/08/18 14:25
 * Copyright (c) 2018. All right reserved.
 *
 * Last modified 4/08/18 14:25
 */

package es.jalbarracinq.gyms.controller.adapter;


import android.app.Activity;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.joda.time.DateTime;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.jalbarracinq.gyms.R;
import es.jalbarracinq.gyms.controller.fragment.CalendarFragment;
import es.jalbarracinq.gyms.model.Day;

public class DayAdapter extends CustomBaseAdapter<Day, DayAdapter.DayHolder> {

    private CalendarFragment fragment;
    private DateTime date;


    public DayAdapter(Activity activity, CalendarFragment fragment, ArrayList<Day> list) {
        super(activity, list, R.layout.item_day, DayHolder.class);
        this.fragment = fragment;
        this.date = DateTime.now();
    }

    public void setDate(DateTime date) {
        this.date = date;
    }

    public DateTime getDate() {
        return date;
    }

    public static class DayHolder {
        @BindView(R.id.dayitem_relativelayout) RelativeLayout relativeLayout;
        @BindView(R.id.dayitem_day_textview) TextView dayTextView;
        @BindView(R.id.dayitem_event_imageview) ImageView eventImageView;

        public DayHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public void onBindViewHolder(DayHolder holder, int position) {
        final Day day = list.get(position);

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.updateCalendar(day.getDate());
            }
        });

        holder.dayTextView.setText(String.format("%02d", day.getDate().dayOfMonth().get()));

        if (day.getHasEvent()) {
            holder.eventImageView.setVisibility(View.VISIBLE);
        } else {
            holder.eventImageView.setVisibility(View.GONE);
        }

        if (day.getDate().withMillisOfDay(0).isEqual(date.withMillisOfDay(0))) {
            holder.dayTextView.setBackgroundResource(R.drawable.background_calendar_day);
            holder.dayTextView.setTextColor(ContextCompat.getColor(activity, R.color.white));
        } else {
            holder.dayTextView.setBackgroundResource(R.color.transparent);
            holder.dayTextView.setTextColor(ContextCompat.getColor(activity, R.color.gray_dark));
        }

        if (day.getDate().monthOfYear().equals(date.monthOfYear())) {
            holder.dayTextView.setTypeface(holder.dayTextView.getTypeface(), Typeface.BOLD);
        } else {
            holder.dayTextView.setTextColor(ContextCompat.getColor(activity, R.color.gray_light));
            holder.dayTextView.setTypeface(holder.dayTextView.getTypeface(), Typeface.NORMAL);
        }
    }

}