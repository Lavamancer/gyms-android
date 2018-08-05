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

import com.google.android.flexbox.FlexboxLayout;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.jalbarracinq.gyms.R;
import es.jalbarracinq.gyms.model.Lesson;
import es.jalbarracinq.gyms.model.LessonType;
import es.jalbarracinq.gyms.model.Subscription;
import es.jalbarracinq.gyms.service.LessonService;
import es.jalbarracinq.gyms.service.SubscriptionService;
import es.jalbarracinq.gyms.service.tool.StringTool;

public class SubscriptionAdapter extends CustomBaseAdapter<Subscription, SubscriptionAdapter.SubscriptionHolder> {

    Subscription subscriptionSelected;
    Lesson lesson;
    LinearLayout reservationLinearLayout;
    Button reservationButton;
    ListView listView;
    HashSet<Long> infoSubscriptions = new HashSet<>();


    public SubscriptionAdapter(Activity activity, ArrayList<Subscription> list, Lesson lesson, LinearLayout reservationLinearLayout, Button reservationButton, ListView listView) {
        super(activity, list, R.layout.item_subscription, SubscriptionHolder.class);
        this.lesson = lesson;
        this.reservationLinearLayout = reservationLinearLayout;
        this.reservationButton = reservationButton;
        this.listView = listView;
    }

    public static class SubscriptionHolder {
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
        @BindView(R.id.subscriptionitem_paused_linearlayout) LinearLayout pausedLinearLayout;
        @BindView(R.id.subscriptionitem_paused_textview) TextView pausedTextView;
        @BindView(R.id.subscriptionitem_pauseddate_textview) TextView pausedDateTextView;
        @BindView(R.id.subscriptionitem_infotop_linearlayout) LinearLayout infoTopLinearLayout;
        @BindView(R.id.subscriptionitem_infobottom_linearlayout) LinearLayout infoBottomLinearLayout;
        @BindView(R.id.subscriptionitem_dates_linearlayout) LinearLayout datesLinearLayout;
        @BindView(R.id.subscriptionitem_details_linearlayout) LinearLayout detailsLinearLayout;
        @BindView(R.id.subscriptionitem_infotypetitle_textview) TextView infoTypeTitleTextView;
        @BindView(R.id.subscriptionitem_infotype_textview) TextView infoTypeTextView;
        @BindView(R.id.subscriptionitem_infodaystitle_textview) TextView infoDaysTitleTextView;
        @BindView(R.id.subscriptionitem_infodays_textview) TextView infoDaysTextView;
        @BindView(R.id.subscriptionitem_infodate_textview) TextView infoDateTextView;
        @BindView(R.id.subscriptionitem_infopaymentmethod_textview) TextView infoPaymentMethodTextView;
        @BindView(R.id.subscriptionitem_infocard_textview) TextView infoCardTextView;
        @BindView(R.id.subscriptionitem_infoprice_textview) TextView infoPriceTextView;
        @BindView(R.id.subscriptionitem_infodiscount_textview) TextView infoDiscountTextView;
        @BindView(R.id.subscriptionitem_infofinalprice_textview) TextView infoFinalPriceTextView;

        List<TextView> lessonTypeTextViews = new ArrayList<>();
        List<ImageView> usageImageViews = new ArrayList<>();

        public SubscriptionHolder(View view) {
            ButterKnife.bind(this, view);
            lessontypesFlexboxLayout.removeAllViews();
            usagesFlexboxLayout.removeAllViews();
        }
    }

    @Override
    public void onBindViewHolder(SubscriptionHolder holder, int position) {
        final Subscription subscription = list.get(position);

        holder.nameTextView.setText(subscription.getSubscriptionType().getProduct().getName());
        holder.pauseImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SubscriptionService.showSubscriptionPauseCalendarPicker(activity, subscription, listView, SubscriptionAdapter.this);
            }
        });
        holder.infoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (infoSubscriptions.contains(subscription.getId())) {
                    infoSubscriptions.remove(subscription.getId());
                } else {
                    infoSubscriptions.add(subscription.getId());
                }
                notifyDataSetChanged();
                listView.invalidateViews();
            }
        });
        holder.startTextView.setText(subscription.getStart().toString("dd/MM/yyyy"));
        holder.endTextView.setText(subscription.getEnd().toString("dd/MM/yyyy"));
        configureLessonTypes(subscription, holder.lessontypesFlexboxLayout, holder.lessonTypeTextViews);

        if (subscription.getRemainingUsages() == null) {
            holder.backgroundLinearLayout.setBackgroundResource(R.drawable.background_subscription_red);
            holder.iconImageView.setImageResource(R.drawable.ui_subscription_icon_monthly);
            holder.shortTextView.setText(subscription.getSubscriptionType().getMonths() + (subscription.getSubscriptionType().getMonths() == 1 ? " month" : " months"));
            holder.fromTextView.setTextColor(ContextCompat.getColor(activity, R.color.red_light));
            holder.toTextView.setTextColor(ContextCompat.getColor(activity, R.color.red_light));
            holder.supportTextView.setText("Weekly assistance:");
            holder.daysLinearLayout.setVisibility(View.VISIBLE);
            holder.usagesFlexboxLayout.setVisibility(View.GONE);
            configureDaysTags(subscription, holder.onedayTextView, holder.twodaysTextView, holder.threedaysTextView, holder.fourdaysTextView, holder.nolimitTextView);
        } else {
            holder.backgroundLinearLayout.setBackgroundResource(R.drawable.background_subscription_gold);
            holder.iconImageView.setImageResource(R.drawable.ui_subscription_icon_sessions);
            holder.shortTextView.setText(subscription.getRemainingUsages() + "/" + subscription.getSubscriptionType().getUsages() + " sessions");
            holder.fromTextView.setTextColor(ContextCompat.getColor(activity, R.color.gold));
            holder.toTextView.setTextColor(ContextCompat.getColor(activity, R.color.gold));
            holder.supportTextView.setText("Usages:");
            holder.daysLinearLayout.setVisibility(View.GONE);
            holder.usagesFlexboxLayout.setVisibility(View.VISIBLE);
            configureUsages(subscription, holder.usagesFlexboxLayout, holder.usageImageViews);
        }

        if (lesson != null) {
            holder.backgroundLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (subscriptionSelected != null && subscriptionSelected.getId().longValue() == subscription.getId().longValue()) {
                        subscriptionSelected = null;
                        reservationLinearLayout.setVisibility(View.GONE);
                        reservationButton.setOnClickListener(null);
                    } else {
                        subscriptionSelected = subscription;
                        reservationLinearLayout.setVisibility(View.VISIBLE);
                        reservationButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                LessonService.postReservation(activity, lesson.getId(), subscription.getId());
                            }
                        });
                    }
                    notifyDataSetChanged();
                    listView.invalidateViews();
                }
            });
        }

        if (lesson == null || (subscriptionSelected != null && subscription.getId().longValue() == subscriptionSelected.getId().longValue())) {
            holder.backgroundLinearLayout.setForeground(null);
        } else {
            holder.backgroundLinearLayout.setForeground(ContextCompat.getDrawable(activity, R.drawable.background_rounded_white50));
        }

        DateTime now = DateTime.now();
        if (subscription.getPausedFrom() != null && subscription.getPausedFrom().isBefore(now)
                && subscription.getPausedTo().isAfter(now)) {
            holder.backgroundLinearLayout.setForeground(ContextCompat.getDrawable(activity, R.drawable.background_rounded_black75));
            holder.pausedLinearLayout.setVisibility(View.VISIBLE);
            holder.pausedTextView.setText("Subscription paused");
            String pausedAt = subscription.getPausedTo().toString("dd/MM/yyyy");
            holder.pausedDateTextView.setText("(To " + pausedAt + ")");
            holder.infoImageView.setOnClickListener(null);
            holder.pauseImageView.setOnClickListener(null);
        } else {
            holder.backgroundLinearLayout.setForeground(null);
            holder.pausedLinearLayout.setVisibility(View.GONE);
        }

        configureInfo(subscription, holder);
    }

    private void configureInfo(Subscription subscription, SubscriptionHolder holder) {
        if (infoSubscriptions.contains(subscription.getId())) {
            holder.backgroundLinearLayout.setBackgroundResource(R.drawable.background_subscription_black);
            holder.infoTopLinearLayout.setVisibility(View.VISIBLE);
            holder.infoBottomLinearLayout.setVisibility(View.VISIBLE);
            holder.datesLinearLayout.setVisibility(View.GONE);
            holder.detailsLinearLayout.setVisibility(View.GONE);

            if (subscription.getRemainingUsages() == null) {
                holder.infoTypeTitleTextView.setText("Subscription type:");
                holder.infoTypeTextView.setText(subscription.getSubscriptionType().getMonths() + (subscription.getSubscriptionType().getMonths() == 1 ? " month" : " months"));
                holder.infoDaysTitleTextView.setText("Weekly assistance:");
                if (subscription.getSubscriptionType().getDays() != null) {
                    holder.infoDaysTextView.setText("Unlimited");
                } else if (subscription.getSubscriptionType().getDays() == 1){
                    holder.infoDaysTextView.setText("1 día");
                } else {
                    holder.infoDaysTextView.setText(subscription.getSubscriptionType().getDays() + " días");
                }
            } else {
                holder.infoTypeTitleTextView.setText("Subscription type:");
                if (subscription.getSubscriptionType().getUsages() == 1) {
                    holder.infoTypeTextView.setText("1 session");
                } else {
                    holder.infoTypeTextView.setText(subscription.getSubscriptionType().getUsages() + " sessions");
                }
                holder.infoDaysTitleTextView.setText("Sessions left:");
                if (subscription.getRemainingUsages() == 0) {
                    holder.infoDaysTextView.setText("Used");
                } else if (subscription.getRemainingUsages() == 1) {
                    holder.infoDaysTextView.setText("1 session");
                } else {
                    holder.infoDaysTextView.setText(subscription.getRemainingUsages() + " sessions");
                }
            }

            holder.infoDateTextView.setText(subscription.getCreatedAt() != null ? subscription.getCreatedAt().toString("dd/MM/yyyy") : "--/--/--");

            if (subscription.getPayment() != null && subscription.getPayment().getCard() != null) {
                holder.infoPaymentMethodTextView.setText("Card");
                holder.infoCardTextView.setText("(" + StringTool.underscoreToCapitalized(subscription.getPayment().getCard().getType().toString()) + ") **** **** **** " + subscription.getPayment().getCard().getNumber());
            } else {
                holder.infoPaymentMethodTextView.setText("-");
                holder.infoCardTextView.setText("-");
            }
            if (subscription.getPayment() != null && subscription.getPayment().getPrice() != null) {
                if (subscription.getPayment().getDiscountRate() != null) {
                    float price = subscription.getPayment().getPrice() / (1 - subscription.getPayment().getDiscountRate());
                    holder.infoPriceTextView.setText(StringTool.floatToPrice(price));
                } else {
                    holder.infoPriceTextView.setText(StringTool.floatToPrice(subscription.getPayment().getPrice()));
                }
                holder.infoFinalPriceTextView.setText(StringTool.floatToPrice(subscription.getPayment().getPrice()));
            } else {
                holder.infoPriceTextView.setText("-");
                holder.infoFinalPriceTextView.setText("-");
            }
            if (subscription.getPayment() != null && subscription.getPayment().getDiscountRate() != null) {
                holder.infoDiscountTextView.setText(StringTool.floatToRate(subscription.getPayment().getDiscountRate()));
            } else {
                holder.infoDiscountTextView.setText("-");
            }
        } else {
            holder.infoTopLinearLayout.setVisibility(View.GONE);
            holder.infoBottomLinearLayout.setVisibility(View.GONE);
            holder.datesLinearLayout.setVisibility(View.VISIBLE);
            holder.detailsLinearLayout.setVisibility(View.VISIBLE);
        }
    }

    private void configureUsages(Subscription subscription, FlexboxLayout flexboxLayout, List<ImageView> imageViews) {
        for (ImageView imageView : imageViews) {
            imageView.setVisibility(View.GONE);
        }
        for (int i = 0; i < subscription.getSubscriptionType().getUsages(); i++) {
            ImageView imageView;
            if (imageViews.size() < i + 1) {
                imageView = (ImageView) LayoutInflater.from(activity).inflate(R.layout.widget_usage, null);
                imageViews.add(imageView);
                flexboxLayout.addView(imageView);
            } else {
                imageView = imageViews.get(i);
            }
            imageView.setVisibility(View.VISIBLE);
            if (subscription.getRemainingUsages() > i) {
                imageView.setImageResource(R.drawable.ui_subscription_session_mark);
            } else {
                imageView.setImageResource(R.drawable.ui_subscription_session_consumed);
            }
        }
    }

    private void configureLessonTypes(Subscription subscription, FlexboxLayout flexboxLayout, List<TextView> textViews) {
        for (TextView textView : textViews) {
            textView.setVisibility(View.GONE);
        }
        for (int i = 0; i < subscription.getSubscriptionType().getLessonTypes().size(); i++) {
            LessonType lessonType = subscription.getSubscriptionType().getLessonTypes().get(i);
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
            if (subscription.getRemainingUsages() == null) {
                textView.setTextColor(ContextCompat.getColor(activity, R.color.red_light));
            } else {
                textView.setTextColor(ContextCompat.getColor(activity, R.color.gold));
            }
        }
    }

    private void configureDaysTags(Subscription subscription, TextView onedayTextView, TextView twodaysTextView, TextView threedaysTextView, TextView fourdaysTextView, TextView nolimitTextView) {
        if (subscription.getSubscriptionType().getDays() != null) {
            switch (subscription.getSubscriptionType().getDays()) {
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
