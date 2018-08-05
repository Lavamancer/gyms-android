/*
 * Created by Juan Albarracin on 4/08/18 14:25
 * Copyright (c) 2018. All right reserved.
 *
 * Last modified 4/08/18 14:25
 */

package es.jalbarracinq.gyms.controller.adapter;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.flexbox.FlexboxLayout;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.jalbarracinq.gyms.R;
import es.jalbarracinq.gyms.controller.SubscriptionNewActivity;
import es.jalbarracinq.gyms.model.LessonType;
import es.jalbarracinq.gyms.model.SubscriptionType;
import es.jalbarracinq.gyms.service.tool.StringTool;
import lombok.Data;

public class SubscriptionTypeAdapter extends CustomBaseAdapter<SubscriptionType, SubscriptionAdapter.SubscriptionHolder> implements FilteredAdapter {

    List<SubscriptionType> subscriptionTypes;
    Filter filter;
    ListView listView;
    SubscriptionType subscriptionTypeSelected;
    Button contractButton;
    LinearLayout contractLinearLayout;
    TextView emptyTextView;


    @Data
    public static class Filter {
        private Boolean monthly = true;
        private HashMap<Long, LessonType> lessonTypes = new HashMap<>();
        private DateTime start;
        private Integer months;
        private Integer days;
        private Integer usages;
    }

    public SubscriptionTypeAdapter(Activity activity, ArrayList<SubscriptionType> list, Filter filter, ListView listView, Button contractButton, LinearLayout contractLinearLayout, TextView emptyTextView) {
        super(activity, new ArrayList<SubscriptionType>(), R.layout.item_subscription, SubscriptionAdapter.SubscriptionHolder.class);
        this.filter = filter;
        this.listView = listView;
        this.subscriptionTypes = list;
        this.contractButton = contractButton;
        this.contractLinearLayout = contractLinearLayout;
        this.emptyTextView = emptyTextView;
    }

    public static class SubscriptionTypeHolder {
        @BindView(R.id.subscriptionitem_background_linearlayout) LinearLayout backgroundLinearLayout;
        @BindView(R.id.subscriptionitem_icon_imageview) ImageView iconImageView;
        @BindView(R.id.subscriptionitem_name_textview) TextView nameTextView;
        @BindView(R.id.subscriptionitem_short_textview) TextView shortTextView;
        @BindView(R.id.subscriptionitem_pause_imageview) ImageView pauseImageView;
        @BindView(R.id.subscriptionitem_info_imageview) ImageView infoImageView;
        @BindView(R.id.subscriptionitem_from_textview) TextView fromTextView;
        @BindView(R.id.subscriptionitem_start_textview) TextView startTextView;
        @BindView(R.id.subscriptionitem_to_textview) TextView toTextView;
        @BindView(R.id.subscriptionitem_end_textview) TextView endTextView;
        @BindView(R.id.subscriptionitem_lessontypes_flexboxlayout) FlexboxLayout lessontypesFlexboxLayout;
        @BindView(R.id.subscriptionitem_support_textview) TextView supportTextView;
        @BindView(R.id.subscriptionitem_days_linearlayout) LinearLayout daysLinearLayout;
        @BindView(R.id.subscriptionitem_oneday_textview) TextView onedayTextView;
        @BindView(R.id.subscriptionitem_twodays_textview) TextView twodaysTextView;
        @BindView(R.id.subscriptionitem_threedays_textview) TextView threedaysTextView;
        @BindView(R.id.subscriptionitem_fourdays_textview) TextView fourdaysTextView;
        @BindView(R.id.subscriptionitem_nolimit_textview) TextView nolimitTextView;
        @BindView(R.id.subscriptionitem_usages_flexboxlayout) FlexboxLayout usagesFlexboxLayout;

        List<TextView> lessonTypeTextViews = new ArrayList<>();
        List<ImageView> usageImageViews = new ArrayList<>();

        public SubscriptionTypeHolder(View view) {
            ButterKnife.bind(this, view);
            lessontypesFlexboxLayout.removeAllViews();
            usagesFlexboxLayout.removeAllViews();
        }
    }

    @Override
    public void onBindViewHolder(SubscriptionAdapter.SubscriptionHolder holder, int position) {
        final SubscriptionType subscriptionType = list.get(position);

        holder.nameTextView.setText(subscriptionType.getProduct().getName());
        holder.pauseImageView.setVisibility(View.GONE);
        holder.pauseImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(activity, "onPauseClick", Toast.LENGTH_SHORT).show();
            }
        });
        holder.infoImageView.setVisibility(View.GONE);
        holder.infoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(activity, "onInfoClick", Toast.LENGTH_SHORT).show();
            }
        });
        if (filter.getStart() != null) {
            holder.startTextView.setText(filter.getStart().toString("dd/MM/yyyy"));
            holder.endTextView.setText(filter.getStart().plusMonths(subscriptionType.getMonths()).toString("dd/MM/yyyy"));
        } else {
            holder.startTextView.setText("-");
            holder.endTextView.setText("-");
        }
        configureLessonTypes(subscriptionType, holder.lessontypesFlexboxLayout, holder.lessonTypeTextViews);

        if (subscriptionType.getUsages() == null) {
            holder.backgroundLinearLayout.setBackgroundResource(R.drawable.background_subscription_red);
            holder.iconImageView.setImageResource(R.drawable.ui_subscription_icon_monthly);
            holder.shortTextView.setText(subscriptionType.getMonths() + (subscriptionType.getMonths() == 1 ? " month" : " months"));
            holder.fromTextView.setTextColor(ContextCompat.getColor(activity, R.color.red_light));
            holder.toTextView.setTextColor(ContextCompat.getColor(activity, R.color.red_light));
            holder.supportTextView.setText("Weekly assistance:");
            holder.daysLinearLayout.setVisibility(View.VISIBLE);
            holder.usagesFlexboxLayout.setVisibility(View.GONE);
            configureDaysTags(subscriptionType, holder.onedayTextView, holder.twodaysTextView, holder.threedaysTextView, holder.fourdaysTextView, holder.nolimitTextView);
        } else {
            holder.backgroundLinearLayout.setBackgroundResource(R.drawable.background_subscription_gold);
            holder.iconImageView.setImageResource(R.drawable.ui_subscription_icon_sessions);
            holder.shortTextView.setText(subscriptionType.getUsages() + "/" + subscriptionType.getUsages() + " sessions");
            holder.fromTextView.setTextColor(ContextCompat.getColor(activity, R.color.gold));
            holder.toTextView.setTextColor(ContextCompat.getColor(activity, R.color.gold));
            holder.supportTextView.setText("Usages:");
            holder.daysLinearLayout.setVisibility(View.GONE);
            holder.usagesFlexboxLayout.setVisibility(View.VISIBLE);
            configureUsages(subscriptionType, holder.usagesFlexboxLayout, holder.usageImageViews);
        }

        holder.backgroundLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (subscriptionTypeSelected != null && subscriptionTypeSelected.getId().longValue() == subscriptionType.getId().longValue()) {
                    subscriptionTypeSelected = null;
                    contractLinearLayout.setVisibility(View.GONE);
                    contractButton.setOnClickListener(null);
                } else {
                    subscriptionTypeSelected = subscriptionType;
                    contractLinearLayout.setVisibility(View.VISIBLE);
                    contractButton.setText("Purchase: " + StringTool.floatToPrice(subscriptionTypeSelected.getProduct().getPrice()));
                    contractButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ((SubscriptionNewActivity) activity).showPayment(subscriptionType, filter.getStart());
                        }
                    });
                }
                notifyDataSetChanged();
                listView.invalidateViews();
            }
        });

        if (subscriptionTypeSelected != null && subscriptionType.getId().longValue() == subscriptionTypeSelected.getId().longValue()) {
            holder.backgroundLinearLayout.setForeground(null);
        } else {
            holder.backgroundLinearLayout.setForeground(ContextCompat.getDrawable(activity, R.drawable.background_rounded_white50));
        }
    }

    @Override
    public void updateFilters() {
        contractLinearLayout.setVisibility(View.GONE);
        subscriptionTypeSelected = null;
        list.clear();
        list.addAll(subscriptionTypes);
        ArrayList<SubscriptionType> aux = new ArrayList<>();

        // MONTHLY FILTER
        aux.clear();
        for (SubscriptionType subscriptionType : list) {
            if ((subscriptionType.getUsages() == null && filter.getMonthly()) || subscriptionType.getUsages() != null && !filter.getMonthly()) {
                aux.add(subscriptionType);
            }
        }
        list.clear();
        list.addAll(aux);

        // LESSON TYPES FILTER
        aux.clear();
        for (SubscriptionType subscriptionType : list) {
            boolean contains = true;
            for (LessonType lt : filter.getLessonTypes().values()) {
                boolean flag = false;
                for (LessonType lessonType : subscriptionType.getLessonTypes()) {
                    if (lessonType.getId().longValue() == lt.getId().longValue()) {
                        flag = true;
                    }
                }
                if (!flag) contains = false;
            }
            if (contains || filter.getLessonTypes().isEmpty()) {
                aux.add(subscriptionType);
            }
        }
        list.clear();
        list.addAll(aux);

        if (filter.getMonthly()) {
            // MONTHS FILTER
            aux.clear();
            for (SubscriptionType subscriptionType : list) {
                if (filter.getMonths() == null || subscriptionType.getMonths().intValue() == filter.getMonths().intValue()) {
                    aux.add(subscriptionType);
                }
            }
            list.clear();
            list.addAll(aux);

            // DAYS FILTER
            aux.clear();
            for (SubscriptionType subscriptionType : list) {
                if (filter.getDays() == null
                        || (filter.getDays() == 0 && subscriptionType.getDays() == null)
                        || (subscriptionType.getDays() != null && subscriptionType.getDays().intValue() == filter.getDays().intValue())) {
                    aux.add(subscriptionType);
                }
            }
            list.clear();
            list.addAll(aux);
        } else {
            // USAGES FILTER
            aux.clear();
            for (SubscriptionType subscriptionType : list) {
                if (filter.getUsages() == null || filter.getUsages().intValue() == subscriptionType.getUsages().intValue()) {
                    aux.add(subscriptionType);
                }
            }
            list.clear();
            list.addAll(aux);
        }

        if (list.isEmpty()) {
            emptyTextView.setVisibility(View.VISIBLE);
            emptyTextView.setText("No subscription found for applied filters");
        } else {
            emptyTextView.setVisibility(View.GONE);
        }
        notifyDataSetChanged();
        listView.invalidateViews();
        System.out.println("Hello Update " + filter.getStart());

    }

    private void configureUsages(SubscriptionType subscriptionType, FlexboxLayout flexboxLayout, List<ImageView> imageViews) {
        for (ImageView imageView : imageViews) {
            imageView.setVisibility(View.GONE);
        }
        for (int i = 0; i < subscriptionType.getUsages(); i++) {
            ImageView imageView;
            if (imageViews.size() < i + 1) {
                imageView = (ImageView) LayoutInflater.from(activity).inflate(R.layout.widget_usage, null);
                imageViews.add(imageView);
                flexboxLayout.addView(imageView);
            } else {
                imageView = imageViews.get(i);
            }
            imageView.setVisibility(View.VISIBLE);
            if (subscriptionType.getUsages() > i) {
                imageView.setImageResource(R.drawable.ui_subscription_session_mark);
            } else {
                imageView.setImageResource(R.drawable.ui_subscription_session_consumed);
            }
        }
    }

    private void configureLessonTypes(SubscriptionType subscriptionType, FlexboxLayout flexboxLayout, List<TextView> textViews) {
        for (TextView textView : textViews) {
            textView.setVisibility(View.GONE);
        }
        for (int i = 0; i < subscriptionType.getLessonTypes().size(); i++) {
            LessonType lessonType = subscriptionType.getLessonTypes().get(i);
            TextView textView;
            if (textViews.size() < i + 1) {
                textView = (TextView) LayoutInflater.from(activity).inflate(R.layout.widget_lessontype, null);
                textViews.add(textView);
                flexboxLayout.addView(textView);
            } else {
                textView = textViews.get(i);
            }
            textView.setVisibility(View.VISIBLE);
            textView.setText(lessonType.getName());

            if (subscriptionType.getUsages() == null) {
                textView.setTextColor(ContextCompat.getColor(activity, R.color.red_light));
            } else {
                textView.setTextColor(ContextCompat.getColor(activity, R.color.gold));
            }
        }
    }

    private void configureDaysTags(SubscriptionType subscriptionType, TextView onedayTextView, TextView twodaysTextView, TextView threedaysTextView, TextView fourdaysTextView, TextView nolimitTextView) {
        if (subscriptionType.getDays() != null) {
            switch (subscriptionType.getDays()) {
                case 0:
                    configureDayTag(onedayTextView, false);
                    configureDayTag(twodaysTextView, false);
                    configureDayTag(threedaysTextView, false);
                    configureDayTag(fourdaysTextView, false);
                    configureDayTag(nolimitTextView, true);
                    break;
                case 1:
                    configureDayTag(onedayTextView, true);
                    configureDayTag(twodaysTextView, false);
                    configureDayTag(threedaysTextView, false);
                    configureDayTag(fourdaysTextView, false);
                    configureDayTag(nolimitTextView, false);
                    break;
                case 2:
                    configureDayTag(onedayTextView, false);
                    configureDayTag(twodaysTextView, true);
                    configureDayTag(threedaysTextView, false);
                    configureDayTag(fourdaysTextView, false);
                    configureDayTag(nolimitTextView, false);
                    break;
                case 3:
                    configureDayTag(onedayTextView, false);
                    configureDayTag(twodaysTextView, false);
                    configureDayTag(threedaysTextView, true);
                    configureDayTag(fourdaysTextView, false);
                    configureDayTag(nolimitTextView, false);
                    break;
                case 4:
                    configureDayTag(onedayTextView, false);
                    configureDayTag(twodaysTextView, false);
                    configureDayTag(threedaysTextView, true);
                    configureDayTag(fourdaysTextView, false);
                    configureDayTag(nolimitTextView, false);
                    break;
            }
        } else {
            configureDayTag(onedayTextView, false);
            configureDayTag(twodaysTextView, false);
            configureDayTag(threedaysTextView, false);
            configureDayTag(fourdaysTextView, false);
            configureDayTag(nolimitTextView, true);
        }
    }

    private void configureDayTag(TextView textView, boolean active) {
        final float scale = activity.getResources().getDisplayMetrics().density;
        int padding = (int) (4 * scale + 0.5f);
        if (active) {
            textView.setBackgroundResource(R.drawable.ui_subscription_sessions_active);
            textView.setPadding((int) (15 * scale + 0.5f), padding, padding, padding);
            textView.setTextColor(ContextCompat.getColor(activity, R.color.white));
        } else {
            textView.setBackgroundResource(R.drawable.ui_subscription_sessions_inactive);
            textView.setPadding(padding, padding, padding, padding);
            textView.setTextColor(ContextCompat.getColor(activity, R.color.white_25));
        }
    }

}