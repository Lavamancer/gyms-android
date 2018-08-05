/*
 * Created by Juan Albarracin on 4/08/18 14:25
 * Copyright (c) 2018. All right reserved.
 *
 * Last modified 4/08/18 14:25
 */

package es.jalbarracinq.gyms.controller.widget;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.DateTime;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.jalbarracinq.gyms.R;
import es.jalbarracinq.gyms.Session;
import es.jalbarracinq.gyms.controller.adapter.CardAdapter;
import es.jalbarracinq.gyms.model.Card;
import es.jalbarracinq.gyms.model.Lesson;
import es.jalbarracinq.gyms.model.Product;
import es.jalbarracinq.gyms.model.SubscriptionType;
import es.jalbarracinq.gyms.service.LessonService;
import es.jalbarracinq.gyms.service.PaymentService;
import es.jalbarracinq.gyms.service.tool.StringTool;

public class PaymentController {

    @BindView(R.id.payment_recyclerview) RecyclerView recyclerView;
    @BindView(R.id.payment_price_textview) TextView priceTextView;
    @BindView(R.id.payment_contract_button) Button contractButton;
    @BindView(R.id.payment_cancel_button) Button cancelButton;

    Activity activity;
    CardAdapter cardAdapter;
    ArrayList<Card> list = new ArrayList<>();
    Product product;
    View view;
    SubscriptionType subscriptionType;
    DateTime start;
    Lesson lesson;


    public PaymentController(Activity activity, ViewGroup container, Product product, SubscriptionType subscriptionType, DateTime start) {
        this(activity, container, product);
        this.subscriptionType = subscriptionType;
        this.start = start;
    }

    public PaymentController(Activity activity, ViewGroup container, Product product, Lesson lesson) {
        this(activity, container, product);
        this.lesson = lesson;
    }

    public PaymentController(Activity activity, ViewGroup container, Product product) {
        this.activity = activity;
        this.view = LayoutInflater.from(activity).inflate(R.layout.widget_payment, null);
        this.product = product;
        ButterKnife.bind(this, view);
        container.addView(view);
        configureViews();
    }

    private void configureViews() {
        list.clear();
        list.addAll(Session.getInstance().cards);
        list.add(new Card());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(activity, linearLayoutManager.getOrientation());
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(activity, R.drawable.background_divider_card));
        recyclerView.addItemDecoration(dividerItemDecoration);
        cardAdapter = new CardAdapter(activity, list, recyclerView);
        recyclerView.setAdapter(cardAdapter);

        contractButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cardAdapter.cardSelected == null) {
                    Toast.makeText(activity, "Select a payment method", Toast.LENGTH_SHORT).show();
                } else {
                    if (subscriptionType != null && start != null) {
                        PaymentService.postSubscription(activity, subscriptionType, start, cardAdapter.cardSelected);
                    } else if (lesson != null) {
                        LessonService.postReservation(activity, lesson, cardAdapter.cardSelected);
                    }
                    // todo implement buy a product
                }
            }   
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((PaymentDismiss) activity).dismissPayment();
            }
        });

        priceTextView.setText(StringTool.floatToPrice(product.getPrice()));
    }

    public void update() {
        list.clear();
        list.addAll(Session.getInstance().cards);
        list.add(new Card());
        cardAdapter = new CardAdapter(activity, list, recyclerView);
        recyclerView.setAdapter(cardAdapter);
    }

    public View getView() {
        return view;
    }

    public interface PaymentDismiss {
        void dismissPayment();
    }

}
