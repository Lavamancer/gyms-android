/*
 * Created by Juan Albarracin on 4/08/18 14:25
 * Copyright (c) 2018. All right reserved.
 *
 * Last modified 4/08/18 14:25
 */

package es.jalbarracinq.gyms.controller.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.jalbarracinq.gyms.R;
import es.jalbarracinq.gyms.Session;
import es.jalbarracinq.gyms.controller.EvolutionActivity;
import es.jalbarracinq.gyms.model.Evolution;
import es.jalbarracinq.gyms.service.EvolutionService;
import es.jalbarracinq.gyms.service.tool.BitmapTool;
import es.jalbarracinq.gyms.service.tool.ImagePickerTool;

public class EvolutionFragment extends Fragment {

    @BindView(R.id.evolution_history_button) Button historyButton;
    @BindView(R.id.evolution_date_textview) TextView dateTextView;
    @BindView(R.id.evolution_goaldate_textview) TextView goaldateTextView;
    @BindView(R.id.evolution_height_edittext) EditText heightEditText;
    @BindView(R.id.evolution_weight_edittext) EditText weightEditText;
    @BindView(R.id.evolution_goalweight_edittext) EditText goalweightEditText;
    @BindView(R.id.evolution_fat_edittext) EditText fatEditText;
    @BindView(R.id.evolution_goalfat_edittext) EditText goalfatEditText;
    @BindView(R.id.evolution_mass_edittext) EditText massEditText;
    @BindView(R.id.evolution_goalmass_edittext) EditText goalmassEditText;
    @BindView(R.id.evolution_waist_edittext) EditText waistEditText;
    @BindView(R.id.evolution_goalwaist_edittext) EditText goalwaistEditText;
    @BindView(R.id.evolution_stomach_edittext) EditText stomachEditText;
    @BindView(R.id.evolution_goalstomach_edittext) EditText goalstomachEditText;
    @BindView(R.id.evolution_chest_edittext) EditText chestEditText;
    @BindView(R.id.evolution_goalchest_edittext) EditText goalchestEditText;
    @BindView(R.id.evolution_arm_edittext) EditText armEditText;
    @BindView(R.id.evolution_goalarm_edittext) EditText goalarmEditText;
    @BindView(R.id.evolution_leg_edittext) EditText legEditText;
    @BindView(R.id.evolution_goalleg_edittext) EditText goallegEditText;
    @BindView(R.id.evolution_frontal_imageview) ImageView frontalImageView;
    @BindView(R.id.evolution_side_imageview) ImageView sideImageView;
    @BindView(R.id.evolution_back_imageview) ImageView backImageView;
    @BindView(R.id.evolution_silhouette_imageview) ImageView silhouetteImageView;

    Long evolutionId;
    Integer selectPickerCode; // 0 Frontal 1 Side 2 Back


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_evolution, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        configureViews();
    }

    @Override
    public void onResume() {
        super.onResume();
        EvolutionService.getEvolution(this, Session.getInstance().evolutionId);
    }

    private void configureViews() {

        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EvolutionActivity.class);
                intent.putExtra("evolutionId", evolutionId);
                startActivity(intent);
            }
        });

        updateEvolution(null, null);
    }

    public void updateEvolution(Evolution evolution, Evolution goal) {
        if (evolution != null) {
            evolutionId = evolution.getId();
            dateTextView.setText(evolution.getDate().toString("dd/MM/yyyy"));
            heightEditText.setText(evolution.getHeight() != null ? evolution.getHeight() + " cm" : "");
            weightEditText.setText(evolution.getWeight() != null ? evolution.getWeight() + " kg": "");
            fatEditText.setText(evolution.getFat() != null ? evolution.getFat() + "%" : "");
            massEditText.setText(evolution.getMass() != null ? String.format("%.2f", evolution.getMass()) : "");
            waistEditText.setText(evolution.getWaist() != null ? evolution.getWaist() + " cm" : "");
            stomachEditText.setText(evolution.getStomach() != null ? evolution.getStomach() + " cm" : "");
            chestEditText.setText(evolution.getChest() != null ? evolution.getChest() + " cm" : "");
            armEditText.setText(evolution.getArm() != null ? evolution.getArm() + " cm" : "");
            legEditText.setText(evolution.getLeg() != null ? evolution.getLeg() + " cm" : "");
        } else {
            dateTextView.setText("--/--/----");
            heightEditText.setText("");
            weightEditText.setText("");
            fatEditText.setText("");
            massEditText.setText("");
            waistEditText.setText("");
            stomachEditText.setText("");
            chestEditText.setText("");
            armEditText.setText("");
            legEditText.setText("");
        }
        if (goal != null && evolution != null) {
            goalweightEditText.setVisibility((goal.getWeight() != null && evolution.getWeight() != null) ? View.VISIBLE : View.GONE);
            goalfatEditText.setVisibility((goal.getFat() != null && evolution.getFat() != null) ? View.VISIBLE : View.GONE);
            goalmassEditText.setVisibility((goal.getMass() != null && evolution.getMass() != null) ? View.VISIBLE : View.GONE);
            goalwaistEditText.setVisibility((goal.getWaist() != null && evolution.getWaist() != null) ? View.VISIBLE : View.GONE);
            goalstomachEditText.setVisibility((goal.getStomach() != null && evolution.getStomach() != null) ? View.VISIBLE : View.GONE);
            goalchestEditText.setVisibility((goal.getChest() != null && evolution.getChest() != null) ? View.VISIBLE : View.GONE);
            goalarmEditText.setVisibility((goal.getArm() != null && evolution.getArm() != null) ? View.VISIBLE : View.GONE);
            goallegEditText.setVisibility((goal.getLeg() != null && evolution.getLeg() != null) ? View.VISIBLE : View.GONE);

            goaldateTextView.setText("(Goal: " + goal.getDate().toString("dd/MM/yyyy") + ")");
            goalweightEditText.setText(goal.getWeight() + " kg");
            goalfatEditText.setText(goal.getFat() + "%");
            goalmassEditText.setText(String.format("%.2f", goal.getMass()));
            goalwaistEditText.setText(goal.getWaist() + " cm");
            goalstomachEditText.setText(goal.getStomach() + " cm");
            goalchestEditText.setText(goal.getChest() + " cm");
            goalarmEditText.setText(goal.getArm() + " cm");
            goallegEditText.setText(goal.getLeg() + " cm");

            configureArrow(evolution.getWeight(), goal.getWeight(), goalweightEditText);
            configureArrow(evolution.getFat(), goal.getFat(), goalfatEditText);
            configureArrow(evolution.getMass(), goal.getMass(), goalmassEditText);
            configureArrow(evolution.getWaist(), goal.getWaist(), goalwaistEditText);
            configureArrow(evolution.getStomach(), goal.getStomach(), goalstomachEditText);
            configureArrow(evolution.getChest(), goal.getChest(), goalchestEditText);
            configureArrow(evolution.getArm(), goal.getArm(), goalarmEditText);
            configureArrow(evolution.getLeg(), goal.getLeg(), goallegEditText);
        } else {
            goalweightEditText.setVisibility(View.GONE);
            goalfatEditText.setVisibility(View.GONE);
            goalmassEditText.setVisibility(View.GONE);
            goalwaistEditText.setVisibility(View.GONE);
            goalstomachEditText.setVisibility(View.GONE);
            goalchestEditText.setVisibility(View.GONE);
            goalarmEditText.setVisibility(View.GONE);
            goallegEditText.setVisibility(View.GONE);
            goaldateTextView.setText(("Goal: --/--/----"));
        }

        if (evolution != null) {
            if (evolution.getFrontal() != null) {
                Glide.with(this).load(evolution.getFrontal().getUrl()).into(frontalImageView);
            }
            if (evolution.getSide() != null) {
                Glide.with(this).load(evolution.getSide().getUrl()).into(sideImageView);
            }
            if (evolution.getBack() != null) {
                Glide.with(this).load(evolution.getBack().getUrl()).into(backImageView);
            }

            frontalImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectPickerCode = 0;
                    ImagePickerTool.start(getActivity(), EvolutionFragment.this);
                }
            });
            sideImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectPickerCode = 1;
                    ImagePickerTool.start(getActivity(), EvolutionFragment.this);
                }
            });
            backImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectPickerCode = 2;
                    ImagePickerTool.start(getActivity(), EvolutionFragment.this);
                }
            });
        }

        if (Session.getInstance().user.getGender() != null) {
            switch (Session.getInstance().user.getGender()) {
                case MALE: silhouetteImageView.setImageResource(R.drawable.evolution_figure_icon_man); break;
                case FEMALE: silhouetteImageView.setImageResource(R.drawable.evolution_figure_icon_woman); break;
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        ImagePickerTool.onRequestPermissionsResult(getActivity(), this, requestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap bitmap = ImagePickerTool.onActivityResult(requestCode, resultCode, data, null);
        if (bitmap != null && selectPickerCode != null) {
            switch (selectPickerCode) {
                case 0: EvolutionService.updateImageFrontal(getActivity(), BitmapTool.bitmapToByteArray(bitmap), evolutionId, frontalImageView); break;
                case 1: EvolutionService.updateImageSide(getActivity(), BitmapTool.bitmapToByteArray(bitmap), evolutionId, sideImageView); break;
                case 2: EvolutionService.updateImageBack(getActivity(), BitmapTool.bitmapToByteArray(bitmap), evolutionId, backImageView); break;
            }
            selectPickerCode = null;
        }
    }

    private void configureArrow(Integer evolution, Integer goal, EditText editText) {
        if (evolution != null && goal != null) {
            configureArrow((float) evolution, (float) goal, editText);
        }
    }

    private void configureArrow(Float evolution, Float goal, EditText editText) {
        int drawableRes = 0;
        if (evolution != null && goal != null) {
            float maxValue = evolution > goal ? evolution : goal;
            float difference = evolution - goal;
            float result = difference / maxValue;
//            System.out.println("evo " + evolution + " goal " + goal + " max " + maxValue + " dif " + difference + " res " + result);
            if (Math.abs(result) > 0.05f) {
                if (result > 0) {
                    drawableRes = R.drawable.goal_completed;
                } else {
                    drawableRes = R.drawable.goal_exceeded;
                }
            }
        }
        editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, drawableRes, 0);
    }
}
