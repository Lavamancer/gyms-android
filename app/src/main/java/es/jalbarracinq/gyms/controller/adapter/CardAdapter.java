/*
 * Created by Juan Albarracin on 4/08/18 14:25
 * Copyright (c) 2018. All right reserved.
 *
 * Last modified 4/08/18 14:25
 */

package es.jalbarracinq.gyms.controller.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.jalbarracinq.gyms.R;
import es.jalbarracinq.gyms.controller.CardActivity;
import es.jalbarracinq.gyms.model.Card;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardHolder> {

    Activity activity;
    List<Card> list;
    RecyclerView recyclerView;
    public Card cardSelected;


    public CardAdapter(Activity activity, List<Card> list, RecyclerView recyclerView) {
        this.activity = activity;
        this.list = list;
        this.recyclerView = recyclerView;
    }

    public static class CardHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.carditem_card_linearlayout) LinearLayout cardLinearLayout;
        @BindView(R.id.carditem_imageview) ImageView imageView;
        @BindView(R.id.carditem_number_textview) TextView numberTextView;
        @BindView(R.id.carditem_new_linearlayout) LinearLayout newLinearLayout;

        public CardHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @NonNull
    @Override
    public CardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CardHolder(LayoutInflater.from(activity).inflate(R.layout.item_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CardHolder holder, int position) {
        final Card card = list.get(position);

        if (card.getId() != null) {
            configureItem(card, holder);
        } else {
            configureAddItem(holder);
        }
    }

    private void configureItem(final Card card, CardHolder holder) {
        switch (card.getType()) {
            case VISA: holder.imageView.setImageResource(R.drawable.payment_card_icon_visa); break;
            case MASTER_CARD: holder.imageView.setImageResource(R.drawable.payment_card_icon_mastercard); break;
            default: holder.imageView.setImageResource(R.drawable.payment_card_icon_add);
        }
        holder.numberTextView.setText("**** **** **** " + card.getNumber());
        holder.cardLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cardSelected != null && cardSelected.getId().longValue() == card.getId().longValue()) {
                    cardSelected = null;
                } else {
                    cardSelected = card;
                }
                notifyDataSetChanged();
                recyclerView.invalidate();
            }
        });
        if (cardSelected != null && cardSelected.getId().longValue() == card.getId().longValue()) {
            holder.cardLinearLayout.setBackgroundResource(R.drawable.background_rounded_border_pink);
        } else {
            holder.cardLinearLayout.setBackgroundResource(R.drawable.background_rounded_border_gray);
        }
    }

    private void configureAddItem(CardHolder holder) {
        holder.cardLinearLayout.setVisibility(View.GONE);
        holder.newLinearLayout.setVisibility(View.VISIBLE);
        holder.newLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.startActivity(new Intent(activity, CardActivity.class));
            }
        });
    }
}
