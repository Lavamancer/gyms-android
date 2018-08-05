/*
 * Created by Juan Albarracin on 4/08/18 14:25
 * Copyright (c) 2018. All right reserved.
 *
 * Last modified 4/08/18 14:25
 */

package es.jalbarracinq.gyms.controller;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.jalbarracinq.gyms.R;
import es.jalbarracinq.gyms.controller.widget.HeaderController;
import es.jalbarracinq.gyms.controller.widget.PaymentController;
import es.jalbarracinq.gyms.model.Facility;
import es.jalbarracinq.gyms.model.Lesson;
import es.jalbarracinq.gyms.model.LessonType;
import es.jalbarracinq.gyms.model.Reservation;
import es.jalbarracinq.gyms.model.Subscription;
import es.jalbarracinq.gyms.model.response.LessonResponse;
import es.jalbarracinq.gyms.service.LessonService;
import es.jalbarracinq.gyms.service.tool.StringTool;

public class LessonActivity extends Activity implements PaymentController.PaymentDismiss {

    HeaderController headerController;

    @BindView(R.id.lesson_relativelayout) RelativeLayout relativeLayout;
    @BindView(R.id.lessonitem_image_imageview) ImageView imageView;
    @BindView(R.id.lessonitem_name_textview) TextView nameTextView;
    @BindView(R.id.lessonitem_alternative_textview) TextView alternativeTextView;
    @BindView(R.id.lessonitem_description_textview) TextView descriptionTextView;
    @BindView(R.id.lessonitem_info_textview) TextView infoTextView;
    @BindView(R.id.lesson_facilities_flexboxlayout) FlexboxLayout facilitiesFlexboxLayout;
    @BindView(R.id.lesson_subscription_linearlayout) LinearLayout subscriptionLinearLayout;
    @BindView(R.id.lesson_subscription_imageview) ImageView subscriptionImageView;
    @BindView(R.id.lesson_subscription_textview) TextView subscriptionTextView;
    @BindView(R.id.lesson_seats_textview) TextView seatsTextView;
    @BindView(R.id.lesson_price_linearlayout) LinearLayout priceLinearLayout;
    @BindView(R.id.lesson_price_textview) TextView priceTextView;
    @BindView(R.id.lesson_reservations_flexboxlayout) FlexboxLayout reservationsFlexboxLayout;
    @BindView(R.id.lesson_reservation_button) Button reservationButton;
    @BindView(R.id.lesson_cancel_button) Button cancelButton;

    LessonType lessonType;
    Lesson lesson;
    ArrayList<Facility> facilities;
    Reservation reservation;
    Integer reservations;
    ArrayList<Subscription> subscriptions;
    PaymentController paymentController;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);
        ButterKnife.bind(this);
        configureExtras();
        configureViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (paymentController != null) {
            paymentController.update();
        }
    }

    private void configureExtras() {
        if (getIntent().getSerializableExtra("lessonType") != null) {
            // came from LessonTypeActivity
            lessonType = (LessonType) getIntent().getSerializableExtra("lessonType");
            facilities = (ArrayList<Facility>) getIntent().getSerializableExtra("facilities");
        } else {
            // came from CalendarFragment -> onClickHour
            LessonResponse lessonResponse = (LessonResponse) getIntent().getSerializableExtra("lessonResponse");
            lessonType = lessonResponse.getLesson().getLessonType();
            facilities = lessonResponse.getFacilities();
            lesson = lessonResponse.getLesson();
            reservations = lessonResponse.getReservations();
            reservation = lessonResponse.getReservation();
            subscriptions = lessonResponse.getSubscriptions();
        }
    }

    private void configureViews() {
        headerController = new HeaderController(this, null);

        Glide.with(this).load("https://picsum.photos/420/320?image=" + 0).into(imageView);
        nameTextView.setText(lessonType.getName());
        alternativeTextView.setText(lessonType.getAlternative());
        descriptionTextView.setText(lessonType.getDescription());

        configureFacilities();
        configureSubscription();
        configureSeats();
        configureButtons();
    }

    private void configureFacilities() {
        if (facilities != null) {
            facilitiesFlexboxLayout.removeAllViews();
            for (Facility facility : facilities) {
                TextView textview = (TextView) LayoutInflater.from(this).inflate(R.layout.widget_tag, null);
                textview.setText(facility.getName());
                facilitiesFlexboxLayout.addView(textview);
            }
        }
    }

    private void configureSubscription() {
        if (lesson == null || subscriptions == null) {
            subscriptionLinearLayout.setVisibility(View.GONE);
            priceLinearLayout.setVisibility(View.GONE);
        } else {
            if (reservation != null) {
                subscriptionLinearLayout.setBackgroundResource(R.color.green);
                subscriptionTextView.setText("You already have reserved this class");
                priceLinearLayout.setVisibility(View.GONE);
            } else if (subscriptions.isEmpty()) {
                subscriptionLinearLayout.setBackgroundResource(R.color.red_dark);
                subscriptionImageView.setImageResource(R.drawable.calendar_class_suscription_icon_not_included);
                subscriptionTextView.setText("This class is not included in your subscription");
                priceLinearLayout.setVisibility(View.VISIBLE);
                priceTextView.setText(StringTool.floatToPrice(lessonType.getProduct().getPrice()));
            } else {
                priceLinearLayout.setVisibility(View.GONE);
            }
        }
    }

    private void configureSeats() {
        if (lesson != null) {
            infoTextView.setText(lesson.getStart().toString("EEE dd/MM/yyyy hh:mm")); // "Vie. 02/03/2018 16:30"
            if (reservations != null) {
                int assignedSeats = reservations > lesson.getSeats() ? lesson.getSeats() : reservations;
                int freeSeats = lesson.getSeats() - assignedSeats;
                seatsTextView.setText(lesson.getSeats() + " seats (" + freeSeats + " available):");
                reservationsFlexboxLayout.removeAllViews();
                for (int i = 0; i < assignedSeats; i++) {
                    ImageView imageView = (ImageView) LayoutInflater.from(this).inflate(R.layout.widget_user, null);
                    if (reservation != null && i == 0) {
                        int[][] states = new int[][] {
                                new int[] { android.R.attr.state_enabled},
                                new int[] {-android.R.attr.state_enabled},
                                new int[] {-android.R.attr.state_checked},
                                new int[] { android.R.attr.state_pressed}
                        };
                        int[] colors = new int[] { ContextCompat.getColor(this, R.color.green), Color.BLACK, Color.BLACK, Color.BLACK };
                        ColorStateList myList = new ColorStateList(states, colors);
                        imageView.setImageTintList(myList);
                    }
                    reservationsFlexboxLayout.addView(imageView);
                }
                for (int i = 0; i < freeSeats; i++) {
                    ImageView imageView = (ImageView) LayoutInflater.from(this).inflate(R.layout.widget_user, null);
                    int[][] states = new int[][] {
                            new int[] { android.R.attr.state_enabled},
                            new int[] {-android.R.attr.state_enabled},
                            new int[] {-android.R.attr.state_checked},
                            new int[] { android.R.attr.state_pressed}
                    };
                    int[] colors = new int[] { ContextCompat.getColor(this, R.color.gray_light), Color.BLACK, Color.BLACK, Color.BLACK };
                    ColorStateList myList = new ColorStateList(states, colors);
                    imageView.setImageTintList(myList);
                    reservationsFlexboxLayout.addView(imageView);
                }
            }
        } else {
            infoTextView.setText("See availability");
            infoTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // todo persist facility to calendar view
                    Intent intent = new Intent(LessonActivity.this, MainActivity.class);
                    intent.putExtra("lessonType", lessonType);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            });
            seatsTextView.setVisibility(View.INVISIBLE);
            reservationsFlexboxLayout.setVisibility(View.INVISIBLE);
        }
    }

    private void configureButtons() {
        if (lesson != null) {
            if (reservation == null) {
                reservationButton.setVisibility(View.VISIBLE);
                cancelButton.setVisibility(View.GONE);
            } else {
                reservationButton.setVisibility(View.GONE);
                cancelButton.setVisibility(View.VISIBLE);
            }
            reservationButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (subscriptions != null && !subscriptions.isEmpty()) {
                        if (subscriptions.size() > 1) {
                            Intent intent = new Intent(LessonActivity.this, ReservationActivity.class);
                            intent.putExtra("subscriptions", subscriptions);
                            intent.putExtra("lesson", lesson);
                            startActivity(intent);
                        } else {
                            LessonService.postReservation(LessonActivity.this, lesson.getId(), subscriptions.get(0).getId());
                        }
                    } else {
                        paymentController = new PaymentController(LessonActivity.this, relativeLayout, lessonType.getProduct(), lesson);
                    }
                }
            });
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (LessonService.canBeCanceled(LessonActivity.this, lesson)) {
                        LessonService.deleteReservation(LessonActivity.this, reservation.getId());
                    }
                }
            });
        } else {
            reservationButton.setVisibility(View.INVISIBLE);
            cancelButton.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void dismissPayment() {
        relativeLayout.removeView(paymentController.getView());
        paymentController = null;
    }

}
