/*
 * Created by Juan Albarracin on 4/08/18 14:25
 * Copyright (c) 2018. All right reserved.
 *
 * Last modified 4/08/18 14:25
 */

package es.jalbarracinq.gyms.service.tool;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.util.Calendar;

import es.jalbarracinq.gyms.controller.adapter.FilteredAdapter;
import es.jalbarracinq.gyms.controller.adapter.SubscriptionTypeAdapter;

public class DateTimeTool {

    public static final String DATE_EDITTEXT_FORMAT = "dd MMMM yyyy";


    public static int firstDayOfMonth(DateTime dateTime) {
        return dateTime.withDayOfMonth(0).getDayOfWeek();
    }

    public static void configureDatePicker(final Activity activity, final TextView textView) {
        configureDatePicker(activity, textView, null, null);
    }

    public static void configureDatePicker(final Activity activity, final TextView textView, final SubscriptionTypeAdapter.Filter filter, final FilteredAdapter adapter) {
        textView.setFocusable(false);
        final Calendar calendar = Calendar.getInstance();
        DateTime date;
        if (textView.getText().toString().isEmpty()) {
            date = DateTime.now().minusYears(18);
        } else {
            date = DateTime.parse(textView.getText().toString(), DateTimeFormat.forPattern(DATE_EDITTEXT_FORMAT));
        }
        calendar.set(date.getYear(), date.getMonthOfYear() - 1, date.getDayOfMonth());
        final DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                DateTime d = new DateTime(year, monthOfYear + 1, dayOfMonth, 0, 0);
                textView.setText(d.toString(DATE_EDITTEXT_FORMAT));
                if (adapter != null) {
                    filter.setStart(d);
                    adapter.updateFilters();
                }
            }
        };
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(activity, dateSetListener,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    public static void showDatePicker(final Activity activity) {
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
                System.out.println("datePicker " + d);
            }
        };

        new DatePickerDialog(activity, dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();

    }

}
