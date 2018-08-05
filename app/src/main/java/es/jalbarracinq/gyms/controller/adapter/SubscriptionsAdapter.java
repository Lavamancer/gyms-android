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

import org.joda.time.DateTime;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.jalbarracinq.gyms.R;
import es.jalbarracinq.gyms.model.Subscription;
import es.jalbarracinq.gyms.service.tool.StringTool;

public class SubscriptionsAdapter extends CustomBaseAdapter<Subscription, SubscriptionsAdapter.SubscriptionHolder> {


    public SubscriptionsAdapter(Activity activity, ArrayList<Subscription> list) {
        super(activity, list, R.layout.item_history, SubscriptionHolder.class);
    }

    public static class SubscriptionHolder {
        @BindView(R.id.historyitem_relativelayout) RelativeLayout relativeLayout;
        @BindView(R.id.historyitem_radiobutton) RadioButton radioButton;
        @BindView(R.id.historyitem_left_checkbox) CheckBox leftCheckBox;
        @BindView(R.id.historyitem_date_textview) TextView dateTextView;
        @BindView(R.id.historyitem_name_textview) TextView nameTextView;
        @BindView(R.id.historyitem_checkbox) CheckBox checkBox;
        @BindView(R.id.historyitem_detail_textview) TextView priceTextView;

        public SubscriptionHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public void onBindViewHolder(SubscriptionHolder holder, int position) {
        final Subscription subscription = list.get(position);

        holder.radioButton.setVisibility(View.GONE);
        holder.leftCheckBox.setVisibility(View.VISIBLE);

        DateTime now = DateTime.now();
        if (subscription.getStart().isBefore(now) && subscription.getEnd().isAfter(now)) {
            holder.relativeLayout.setForeground(null);
            if (subscription.getPausedFrom() != null && subscription.getPausedFrom().isBefore(now)
                    && subscription.getPausedTo().isAfter(now)) {
                holder.leftCheckBox.setChecked(false);
            } else {
                holder.leftCheckBox.setChecked(true);
            }
        } else {
            holder.relativeLayout.setForeground(ContextCompat.getDrawable(activity, R.drawable.background_square_white75));
        }

        if (position % 2 == 0) {
            holder.relativeLayout.setBackgroundColor(ContextCompat.getColor(activity, R.color.gray_lighter));
        } else {
            holder.relativeLayout.setBackgroundColor(ContextCompat.getColor(activity, R.color.white));
        }

        holder.dateTextView.setText(subscription.getStart().toString("dd/MM/yyyy"));
        holder.checkBox.setVisibility(View.INVISIBLE);
        holder.nameTextView.setText(subscription.getSubscriptionType().getProduct().getName());

        holder.priceTextView.setText(StringTool.floatToPrice(subscription.getSubscriptionType().getProduct().getPrice()));

    }


}
