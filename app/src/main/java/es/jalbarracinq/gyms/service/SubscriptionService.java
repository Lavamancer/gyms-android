/*
 * Created by Juan Albarracin on 4/08/18 14:25
 * Copyright (c) 2018. All right reserved.
 *
 * Last modified 4/08/18 14:25
 */

package es.jalbarracinq.gyms.service;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Toast;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import es.jalbarracinq.gyms.Session;
import es.jalbarracinq.gyms.controller.SubscriptionNewActivity;
import es.jalbarracinq.gyms.controller.adapter.SubscriptionTypeAdapter;
import es.jalbarracinq.gyms.model.LessonType;
import es.jalbarracinq.gyms.model.Subscription;
import es.jalbarracinq.gyms.model.SubscriptionType;
import es.jalbarracinq.gyms.service.tool.DialogTool;
import es.jalbarracinq.gyms.service.tool.FakerTool;
import es.jalbarracinq.gyms.service.tool.ProgressBarTool;
import es.jalbarracinq.gyms.service.tool.RetrofitTool;

public class SubscriptionService {


    public static void getSubscriptions(final BaseAdapter adapter, final ListView listView, final List<Subscription> list, final Boolean actives) {
        new AsyncTask<Void, Void, List<Subscription>>() {
            @Override
            protected List<Subscription> doInBackground(Void... voids) {
                if (Session.getInstance().mock) {
                    return Subscription.mocks(FakerTool.getInstance(), 6);
                } else {
                    try {
                        return RetrofitTool.getInstance().apiRepository.getSubscriptions(Session.getInstance().token.getAccessToken(), true, actives).execute().body();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(List<Subscription> result) {
                if (result != null) {
                    list.clear();
                    list.addAll(result);
                    adapter.notifyDataSetChanged();
                    listView.invalidateViews();
                }
            }
        }.execute();
    }

    public static void getSubscriptionTypes(final SubscriptionNewActivity activity, final SubscriptionTypeAdapter adapter, final ArrayList<SubscriptionType> list) {
        final Dialog dialog = ProgressBarTool.create(activity);
        dialog.show();
        new AsyncTask<Void, Void, List<SubscriptionType>>() {
            @Override
            protected List<SubscriptionType> doInBackground(Void... voids) {
                if (Session.getInstance().mock) {
                    return SubscriptionType.mocks(FakerTool.getInstance(), 15);
                } else {
                    try {
                        return RetrofitTool.getInstance().apiRepository.getSubscriptionTypes(Session.getInstance().token.getAccessToken()).execute().body();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(List<SubscriptionType> result) {
                dialog.dismiss();
                if (result != null) {
                    activity.configureForms(result);
                    list.clear();
                    list.addAll(result);
                    adapter.updateFilters();
                }
            }
        }.execute();
    }

    public static void showSubscriptionPauseCalendarPicker(final Activity activity, final Subscription subscription, final ListView listView, final BaseAdapter adapter) {
        if (subscription.getPausedFrom() != null) {
            final DialogTool.DialogWidget dialogWidget = DialogTool.createDialog(activity,
                    "Scheduled pause",
                    "This subscription is already scheduled for day "
                            + subscription.getPausedFrom().toString("dd/MM/yyyy"));
            dialogWidget.cancelButton.setVisibility(View.GONE);
            dialogWidget.acceptButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogWidget.dialog.dismiss();
                }
            });
            dialogWidget.dialog.show();
            return;
        }

        final Calendar calendar = Calendar.getInstance();
        DateTime date = DateTime.now();

        calendar.set(date.getYear(), date.getMonthOfYear() - 1, date.getDayOfMonth());
        final DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                DateTime d = new DateTime(year, monthOfYear + 1, dayOfMonth, 0, 0);
                patchSubscriptionPause(activity, subscription, d, listView, adapter);
            }
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(activity, dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        if (subscription.getStart().plusDays(1).isBeforeNow()) {
            datePickerDialog.getDatePicker().setMinDate(DateTime.now().getMillis());
        } else {
            datePickerDialog.getDatePicker().setMinDate(subscription.getStart().plusDays(1).getMillis());
        }
        datePickerDialog.getDatePicker().setMaxDate(subscription.getEnd().minusDays(1).getMillis());
        datePickerDialog.show();
    }

    private static void patchSubscriptionPause(final Activity activity, final Subscription subscription, final DateTime date, final ListView listView, final BaseAdapter adapter) {
        final Dialog dialog = ProgressBarTool.create(activity);
        dialog.show();
        new AsyncTask<Void, Void, Subscription>() {
            @Override
            protected Subscription doInBackground(Void... voids) {
                if (Session.getInstance().mock) {
                    return new Subscription(FakerTool.getInstance(), 1);
                } else {
                    try {
                        return RetrofitTool.getInstance().apiRepository.patchSubscriptionPause(Session.getInstance().token.getAccessToken(), subscription.getId(), date).execute().body();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Subscription result) {
                dialog.dismiss();
                if (result != null) {
                    // todo implement
                    Toast.makeText(activity, "Hello Subscription Pause", Toast.LENGTH_SHORT).show();
                    subscription.setEnd(result.getEnd());
                    subscription.setPausedFrom(result.getPausedFrom());
                    subscription.setPausedTo(result.getPausedTo());
                    adapter.notifyDataSetChanged();
                    listView.invalidateViews();
                } else {
                    Toast.makeText(activity, "Unable to connect to the server", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }

    public static void configureLessonTypes(ArrayList<String> listString, ArrayList<LessonType> lessonTypes, List<SubscriptionType> list) {
        HashMap<Long, LessonType> map = new HashMap<>();
        for (SubscriptionType subscriptionType : list) {
            for (LessonType lessonType : subscriptionType.getLessonTypes()) {
                map.put(lessonType.getId(), lessonType);
            }
        }

        for (LessonType lessonType : map.values()) {
            listString.add(lessonType.getName());
            lessonTypes.add(lessonType);
        }
    }

    public static void configureMonths(ArrayList<String> monthsString, ArrayList<Integer> months, List<SubscriptionType> list) {
        HashSet<Integer> set = new HashSet<>();
        for (SubscriptionType subscriptionType : list) {
            set.add(subscriptionType.getMonths());
        }
        months.addAll(set);
        Collections.sort(months);
        monthsString.add("All");
        for (Integer m : months) {
            monthsString.add(m > 0 ? m + " months" : m + " month");
        }
    }

    public static void configureDays(ArrayList<String> daysString, ArrayList<Integer> days, List<SubscriptionType> list) {
        HashSet<Integer> set = new HashSet<>();
        for (SubscriptionType subscriptionType : list) {
            if (subscriptionType.getUsages() == null) {
                if (subscriptionType.getDays() == null) {
                    set.add(0);
                } else {
                    set.add(subscriptionType.getDays());
                }
            }
        }
        days.addAll(set);
        Collections.sort(days);
        daysString.add("All");
        for (Integer d : days) {
            if (d == 0) {
                daysString.add("Unlimited");
            } else if (d == 1) {
                daysString.add("1 day");
            } else {
                daysString.add(d + " days");
            }
        }
    }

    public static void configureUsages(ArrayList<String> usagesString, ArrayList<Integer> usages, List<SubscriptionType> list) {
        HashSet<Integer> set = new HashSet<>();
        for (SubscriptionType subscriptionType : list) {
            if (subscriptionType.getUsages() != null) {
                set.add(subscriptionType.getUsages());
            }
        }
        usages.addAll(set);
        Collections.sort(usages);
        usagesString.add("All");
        for (Integer u : usages) {
            usagesString.add(u == 1 ? "1 session" : u + " sessions");
        }
    }


    public static String spinnerToString(ArrayAdapter<String> arrayAdapter, boolean[] selected) {
        StringBuilder spinnerBuilder = new StringBuilder();
        for (int i = 0; i < arrayAdapter.getCount(); i++) {

            if (selected[i]) {
                spinnerBuilder.append(arrayAdapter.getItem(i));
                spinnerBuilder.append(", ");
            }
        }
        String spinnerText;
        spinnerText = spinnerBuilder.toString();
        if (spinnerText.length() > 2) {
            spinnerText = spinnerText.substring(0, spinnerText.length() - 2);
        }
        return spinnerText;
    }

    public static HashMap<Long, LessonType> spinnerToHashMap(ArrayList<LessonType> lessonTypes, boolean[] selected) {
        HashMap<Long, LessonType> map = new HashMap<>();
        for (int i = 0; i < lessonTypes.size(); i++) {
            if (selected[i]) {
                map.put(lessonTypes.get(i).getId(), lessonTypes.get(i));
            }
        }
        return map;
    }

}
