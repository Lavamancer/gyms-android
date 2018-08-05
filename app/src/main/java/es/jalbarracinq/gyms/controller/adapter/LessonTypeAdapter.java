/*
 * Created by Juan Albarracin on 4/08/18 14:25
 * Copyright (c) 2018. All right reserved.
 *
 * Last modified 4/08/18 14:25
 */

package es.jalbarracinq.gyms.controller.adapter;


import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.jalbarracinq.gyms.R;
import es.jalbarracinq.gyms.model.LessonType;
import es.jalbarracinq.gyms.service.LessonService;

public class LessonTypeAdapter extends CustomBaseAdapter<LessonType, LessonTypeAdapter.LessonTypeHolder> {


    public LessonTypeAdapter(Activity activity, ArrayList<LessonType> list) {
        super(activity, list, R.layout.item_lesson, LessonTypeHolder.class);
    }

    public static class LessonTypeHolder {
        @BindView(R.id.lessonitem_relativelayout) RelativeLayout relativeLayout;
        @BindView(R.id.lessonitem_image_imageview) ImageView imageView;
        @BindView(R.id.lessonitem_name_textview) TextView nameTextView;
        @BindView(R.id.lessonitem_alternative_textview) TextView alternativeTextView;
        @BindView(R.id.lessonitem_description_textview) TextView descriptionTextView;
        @BindView(R.id.lessonitem_info_textview) TextView infoTextView;

        public LessonTypeHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public void onBindViewHolder(LessonTypeHolder holder, int position) {
        final LessonType lessonType = list.get(position);
        Glide.with(activity).load("https://picsum.photos/420/320?image=" + position).into(holder.imageView);

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LessonService.getFacilities(activity, lessonType);
            }
        });
        holder.nameTextView.setText(lessonType.getName());
        holder.alternativeTextView.setText(lessonType.getAlternative());
        holder.descriptionTextView.setText(lessonType.getDescription());
        holder.infoTextView.setVisibility(View.INVISIBLE);
    }


}