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

import org.joda.time.DateTime;

import java.util.List;

import es.jalbarracinq.gyms.Session;
import es.jalbarracinq.gyms.controller.LessonActivity;
import es.jalbarracinq.gyms.controller.adapter.DayAdapter;
import es.jalbarracinq.gyms.controller.fragment.CalendarFragment;
import es.jalbarracinq.gyms.model.Lesson;
import es.jalbarracinq.gyms.model.Reservation;
import es.jalbarracinq.gyms.model.response.CalendarResponse;
import es.jalbarracinq.gyms.model.response.LessonResponse;
import es.jalbarracinq.gyms.service.tool.FakerTool;
import es.jalbarracinq.gyms.service.tool.ProgressBarTool;
import es.jalbarracinq.gyms.service.tool.RetrofitTool;

public class CalendarService {


    public static void getLessons(final CalendarFragment calendarFragment, final View progressBarView, final DayAdapter dayAdapter, final Long facilityId, DateTime date, final List<Lesson> lessons, final List<Reservation> reservations) {
        progressBarView.setVisibility(View.VISIBLE);
        new AsyncTask<Void, Void, CalendarResponse>() {
            @Override
            protected CalendarResponse doInBackground(Void... voids) {
                if (Session.getInstance().mock) {
                    return new CalendarResponse(FakerTool.getInstance(), 1);
                } else {
                    try {
                        return RetrofitTool.getInstance().apiRepository.getCalendar(Session.getInstance().token.getAccessToken(), facilityId).execute().body();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(CalendarResponse result) {
                progressBarView.setVisibility(View.GONE);
                if (result != null && result.getLessons() != null) {
                    lessons.clear();
                    lessons.addAll(result.getLessons());
                    reservations.clear();
                    reservations.addAll(result.getReservations());
                    calendarFragment.updateCalendar(dayAdapter.getDate());
                }
            }
        }.execute();
    }

    public static void getLesson(final Activity activity, final Long lessonId) {
        final Dialog dialog = ProgressBarTool.create(activity);
        dialog.show();
        new AsyncTask<Void, Void, LessonResponse>() {
            @Override
            protected LessonResponse doInBackground(Void... voids) {
                if (Session.getInstance().mock) {
                    return new LessonResponse(FakerTool.getInstance(), 1);
                } else {
                    try {
                        return RetrofitTool.getInstance().apiRepository.getLesson(Session.getInstance().token.getAccessToken(), lessonId).execute().body();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(LessonResponse result) {
                dialog.dismiss();
                if (result != null) {
                    Intent intent = new Intent(activity, LessonActivity.class);
                    intent.putExtra("lessonResponse", result);
                    activity.startActivity(intent);
                }
            }
        }.execute();
    }

}
