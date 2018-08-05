/*
 * Created by Juan Albarracin on 4/08/18 14:25
 * Copyright (c) 2018. All right reserved.
 *
 * Last modified 4/08/18 14:25
 */

package es.jalbarracinq.gyms.controller.adapter;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.jalbarracinq.gyms.R;
import es.jalbarracinq.gyms.Session;
import es.jalbarracinq.gyms.model.Evolution;

public class EvolutionAdapter extends CustomBaseAdapter<Evolution, EvolutionAdapter.EvolutionHolder> {

    private Long evolutionId;


    public EvolutionAdapter(Activity activity, ArrayList<Evolution> list, Long evolutionId) {
        super(activity, list, R.layout.item_history, EvolutionHolder.class);
        this.evolutionId = evolutionId;
    }

    public static class EvolutionHolder {
        @BindView(R.id.historyitem_relativelayout) RelativeLayout relativeLayout;
        @BindView(R.id.historyitem_radiobutton) RadioButton radioButton;
        @BindView(R.id.historyitem_date_textview) TextView dateTextView;
        @BindView(R.id.historyitem_name_textview) TextView monitorTextView;
        @BindView(R.id.historyitem_checkbox) CheckBox checkBox;
        @BindView(R.id.historyitem_detail_textview) TextView textrightTextView;

        public EvolutionHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public void onBindViewHolder(EvolutionHolder holder, int position) {
        final Evolution evolution = list.get(position);

        if (evolution.getGoal()) {
            holder.radioButton.setVisibility(View.INVISIBLE);
            holder.relativeLayout.setOnClickListener(null);
        } else {
            holder.radioButton.setVisibility(View.VISIBLE);
            holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Session.getInstance().evolutionId = evolution.getId();
                    activity.onBackPressed();
                }
            });
        }

        if (position % 2 == 0) {
            holder.relativeLayout.setBackgroundColor(ContextCompat.getColor(activity, R.color.gray_lighter));
        } else {
            holder.relativeLayout.setBackgroundColor(ContextCompat.getColor(activity, R.color.white));
        }

        if (evolution.getId().longValue() == evolutionId.longValue()) {
            holder.radioButton.setChecked(true);
        } else {
            holder.radioButton.setChecked(false);
        }

        holder.dateTextView.setText(evolution.getDate().toString("dd/MM/yyyy"));
        holder.checkBox.setChecked(evolution.getGoal());
        if (evolution.getMonitor() != null) {
            holder.monitorTextView.setText(evolution.getMonitor().getName() + " " + evolution.getMonitor().getLastname());
        } else {
            holder.monitorTextView.setText("-");
        }
        holder.textrightTextView.setVisibility(View.GONE);
    }

}