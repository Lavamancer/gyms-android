/*
 * Created by Juan Albarracin on 4/08/18 14:25
 * Copyright (c) 2018. All right reserved.
 *
 * Last modified 4/08/18 14:25
 */

package es.jalbarracinq.gyms.service;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.List;

import es.jalbarracinq.gyms.Session;
import es.jalbarracinq.gyms.controller.LessonActivity;
import es.jalbarracinq.gyms.controller.MainActivity;
import es.jalbarracinq.gyms.model.Card;
import es.jalbarracinq.gyms.model.Facility;
import es.jalbarracinq.gyms.model.Lesson;
import es.jalbarracinq.gyms.model.LessonType;
import es.jalbarracinq.gyms.model.Reservation;
import es.jalbarracinq.gyms.service.tool.DialogTool;
import es.jalbarracinq.gyms.service.tool.FakerTool;
import es.jalbarracinq.gyms.service.tool.ProgressBarTool;
import es.jalbarracinq.gyms.service.tool.RetrofitTool;

public class LessonService {


    public static void getFacilities(final Activity activity, final LessonType lessonType) {
        final Dialog dialog = ProgressBarTool.create(activity);
        dialog.show();
        new AsyncTask<Void, Void, List<Facility>>() {
            @Override
            protected List<Facility> doInBackground(Void... voids) {
                if (Session.getInstance().mock) {
                    return Facility.mocks(FakerTool.getInstance(), 5);
                } else {
                    try {
                        return RetrofitTool.getInstance().apiRepository.getFacilitiesByLessonType(Session.getInstance().token.getAccessToken(), lessonType.getId()).execute().body();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(List<Facility> result) {
                dialog.dismiss();
                if (result != null) {
                    Intent intent = new Intent(activity, LessonActivity.class);
                    intent.putExtra("facilities", (ArrayList<Facility>) result);
                    intent.putExtra("lessonType", lessonType);
                    activity.startActivity(intent);
                } else {
                    Toast.makeText(activity, "Unable to connect to the server", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }

    public static void postReservation(final Activity activity, final Long lessonId, final Long subscriptionId) {
        final Dialog dialog = ProgressBarTool.create(activity);
        dialog.show();
        new AsyncTask<Void, Void, Reservation>() {
            @Override
            protected Reservation doInBackground(Void... voids) {
                if (Session.getInstance().mock) {
                    return new Reservation(FakerTool.getInstance(), 1);
                } else{
                    try {
                        return RetrofitTool.getInstance().apiRepository.postReservations(Session.getInstance().token.getAccessToken(), lessonId, subscriptionId, null).execute().body();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Reservation result) {
                dialog.dismiss();
                if (result != null) {
                    String message = "Day " + result.getLesson().getStart().toString("dd/MM/yyyy") + " at "
                            + result.getLesson().getStart().toString("hh:mm") + ", you have your "
                            + result.getLesson().getLessonType().getName() + " class in " + Session.getInstance().facility.getName();
                    final DialogTool.DialogWidget dialogWidget = DialogTool.createDialog(activity, "Your class have been reserved", message);
                    dialogWidget.cancelButton.setVisibility(View.GONE);
                    dialogWidget.acceptButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialogWidget.dialog.dismiss();
                            Intent intent = new Intent(activity, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            activity.startActivity(intent);
                            activity.finish();
                        }
                    });
                    dialogWidget.dialog.show();
                } else {
                    Toast.makeText(activity, "The reservation could not be completed", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }


    public static void postReservation(final Activity activity, final Lesson lesson, final Card card) {
        final Dialog dialog = ProgressBarTool.create(activity);
        dialog.show();
        new AsyncTask<Void, Void, Reservation>() {
            @Override
            protected Reservation doInBackground(Void... voids) {
                if (Session.getInstance().mock) {
                    return new Reservation(FakerTool.getInstance(), 1);
                } else {
                    try {
                        return RetrofitTool.getInstance().apiRepository.postReservations(Session.getInstance().token.getAccessToken(), lesson.getId(), null, card.getId()).execute().body();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Reservation result) {
                dialog.dismiss();
                if (result != null) {
                    String message = "Day " + result.getLesson().getStart().toString("dd/MM/yyyy") + " at "
                            + result.getLesson().getStart().toString("hh:mm") + ", you have your "
                            + result.getLesson().getLessonType().getName() + " class in " + Session.getInstance().facility.getName();
                    final DialogTool.DialogWidget dialogWidget = DialogTool.createDialog(activity, "Your class have been reserved", message);
                    dialogWidget.cancelButton.setVisibility(View.GONE);
                    dialogWidget.acceptButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialogWidget.dialog.dismiss();
                            activity.onBackPressed();
                        }
                    });
                    dialogWidget.dialog.show();
                } else {
                    Toast.makeText(activity, "The reservation could not be completed", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }

    public static void deleteReservation(final Activity activity, final Long reservationId) {
        final Dialog dialog = ProgressBarTool.create(activity);
        dialog.show();
        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                if (Session.getInstance().mock) {
                    Faker faker = FakerTool.getInstance();
                    return true;
                } else {
                    try {
                        return RetrofitTool.getInstance().apiRepository.deleteReservations(Session.getInstance().token.getAccessToken(), reservationId).execute().isSuccessful();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Boolean result) {
                dialog.dismiss();
                if (result != null && result) {
                    Toast.makeText(activity, "Cancelled reservation", Toast.LENGTH_SHORT).show();
                    activity.onBackPressed();
                } else {
                    Toast.makeText(activity, "The reservation could not be completed", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }

    public static boolean canBeCanceled(Activity activity, Lesson lesson) {
        if (lesson.getStart().minusDays(1).isBeforeNow()) {
            Toast.makeText(activity, "The reservation can not be canceled when there are 24 hours left for the class", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
