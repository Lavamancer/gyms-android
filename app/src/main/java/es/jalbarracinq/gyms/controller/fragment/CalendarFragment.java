/*
 * Created by Juan Albarracin on 4/08/18 14:25
 * Copyright (c) 2018. All right reserved.
 *
 * Last modified 4/08/18 14:25
 */

package es.jalbarracinq.gyms.controller.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.jalbarracinq.gyms.R;
import es.jalbarracinq.gyms.Session;
import es.jalbarracinq.gyms.controller.adapter.DayAdapter;
import es.jalbarracinq.gyms.controller.adapter.HourAdapter;
import es.jalbarracinq.gyms.model.Day;
import es.jalbarracinq.gyms.model.Facility;
import es.jalbarracinq.gyms.model.Hour;
import es.jalbarracinq.gyms.model.Lesson;
import es.jalbarracinq.gyms.model.LessonType;
import es.jalbarracinq.gyms.model.Reservation;
import es.jalbarracinq.gyms.model.Subscriber;
import es.jalbarracinq.gyms.service.CalendarService;

public class CalendarFragment extends Fragment {

    @BindView(R.id.calendar_progressbar) View progressBarView;
    @BindView(R.id.calendar_gridview) GridView gridView;
    @BindView(R.id.calendar_listview) ListView listView;
    @BindView(R.id.calendar_facilities_spinner) Spinner facilitiesSpinner;
    @BindView(R.id.calendar_lessontypes_spinner) Spinner lessontypesSpinner;
    @BindView(R.id.calendar_monthyear_textview) TextView monthyearTextView;
    @BindView(R.id.calendar_date_textview) TextView dateTextView;
    @BindView(R.id.calendar_myreservations_checkbox) CheckBox myreservationsCheckBox;
    @BindView(R.id.calendar_lessontypes_linearlayout) LinearLayout lessontypesLinearLayout;
    @BindView(R.id.calendar_lessontypes_textview) TextView lessontypesTextView;
    @BindView(R.id.calendar_gotoday_textview) TextView gotodayTextView;
    @BindView(R.id.calendar_monthleft_icontextview) TextView monthleftTextView;
    @BindView(R.id.calendar_monthright_icontextview) TextView monthrightTextView;
    @BindView(R.id.calendar_facilities_linearlayout) LinearLayout facilitiesLinearLayout;
    @BindView(R.id.calendar_facilities_textview) TextView facilitiesTextView;

    View view;
    DayAdapter dayAdapter;
    HourAdapter hourAdapter;

    ArrayList<Day> days = new ArrayList<>();
    ArrayList<Hour> hours = new ArrayList<>();
    ArrayList<Lesson> lessons = new ArrayList<>();
    ArrayList<Reservation> reservations = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_calendar, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        ButterKnife.bind(this, view);
        configureViews();
        updateCalendar(DateTime.now());
    }

    @Override
    public void onResume() {
        super.onResume();
        CalendarService.getLessons(this, progressBarView, dayAdapter, Session.getInstance().facilities.get(0).getId(), dayAdapter.getDate(), lessons, reservations);
    }

    private void configureViews() {
        dayAdapter = new DayAdapter(getActivity(), this, days);
        gridView.setAdapter(dayAdapter);

        hourAdapter = new HourAdapter(getActivity(), hours);
        listView.setAdapter(hourAdapter);

        myreservationsCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                updateCalendar(dayAdapter.getDate());
            }
        });

        configureFacilities();
        configureLessonTypes();
        configureGoToday();
    }

    private void configureLessonTypes() {
        lessontypesLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lessontypesSpinner.performClick();
            }
        });
        final List<String> lessonTypes = new ArrayList<>();
        lessonTypes.add("All");
        for (LessonType lessonType : Session.getInstance().lessonTypes) {
            lessonTypes.add(lessonType.getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.widget_spinner, lessonTypes);
        lessontypesSpinner.setAdapter(adapter);
        lessontypesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                lessontypesTextView.setText(lessonTypes.get(position));
                updateCalendar(dayAdapter.getDate());
            }
            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });
        LessonType lessonType = (LessonType) getActivity().getIntent().getSerializableExtra("lessonType");
        if (lessonType != null) {
            for (int i = 0; i < Session.getInstance().lessonTypes.size(); i++) {
                LessonType lt = Session.getInstance().lessonTypes.get(i);
                if (lt.getId().longValue() == lessonType.getId().longValue()) {
                    lessontypesSpinner.setSelection(i + 1);
                }
            }
        }
    }

    private void configureGoToday() {
        monthleftTextView.setText("{fa-chevron-left}");
        monthrightTextView.setText("{fa-chevron-right}");

        gotodayTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCalendar(DateTime.now());
            }
        });
        monthleftTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCalendar(dayAdapter.getDate().minusMonths(1));
            }
        });
        monthrightTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCalendar(dayAdapter.getDate().plusMonths(1));
            }
        });
    }

    private void configureFacilities() {
        facilitiesLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                facilitiesSpinner.performClick();
            }
        });
        final List<String> facilities = new ArrayList<>();
        int position = 0;
        for (int i = 0; i < Session.getInstance().facilities.size(); i++) {
            Facility facility = Session.getInstance().facilities.get(i);
            facilities.add(facility.getName());

            if (Session.getInstance().user instanceof Subscriber
                    && ((Subscriber) Session.getInstance().user).getFacility() != null
                    && ((Subscriber) Session.getInstance().user).getFacility().getId().longValue() == facility.getId().longValue()) {
                position = i;
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.widget_spinner, facilities);
        facilitiesSpinner.setAdapter(adapter);
        facilitiesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Session.getInstance().facility = Session.getInstance().facilities.get(position);
                facilitiesTextView.setText(facilities.get(position));
                CalendarService.getLessons(CalendarFragment.this, progressBarView, dayAdapter, Session.getInstance().facilities.get(position).getId(), dayAdapter.getDate(), lessons, reservations);
            }
            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });
        facilitiesSpinner.setSelection(position);

//        CalendarService.getLessons(this, dayAdapter, Session.getInstance().facilities.get(0).getId(), dayAdapter.getDate(), lessons, reservations);
    }

    // CALENDAR
    public void updateCalendar(DateTime date) {
        days.clear();
        days.addAll(createDays(date));
        Pair<HashMap<String, List<Lesson>>, HashSet<Long>> pair = updateEvents(days);
//        Map<String, List<Lesson>> lessons = updateEvents(days);

        dayAdapter.setDate(date);
        dayAdapter.notifyDataSetChanged();
        gridView.invalidateViews();
        dateTextView.setText(date.dayOfMonth().getAsText() + ", " + date.dayOfWeek().getAsText());
        monthyearTextView.setText(date.monthOfYear().getAsText() + " " + date.year().getAsText());

        hours.clear();
        hours.addAll(createHours(date, pair.first, pair.second));
        hourAdapter.notifyDataSetChanged();
        listView.invalidateViews();
    }

    private ArrayList<Day> createDays(DateTime dateTime) {
        DateTime first = dateTime.withDayOfMonth(1);
        DateTime last = dateTime.dayOfMonth().withMaximumValue();
        ArrayList<Day> list = new ArrayList<>();
        addDaysBefore(list, first);
        addDays(list, first, last);
        addDaysAfter(list, last);
        return list;
    }

    private Pair<HashMap<String, List<Lesson>>, HashSet<Long>> updateEvents(List<Day> days) {
        DateTime start = dayAdapter.getDate().dayOfMonth().withMinimumValue();
        DateTime end = dayAdapter.getDate().dayOfMonth().withMaximumValue();
        boolean myReservations = myreservationsCheckBox.isChecked();
        LessonType lessonType = lessontypesSpinner.getSelectedItemPosition() == 0 ? null : Session.getInstance().lessonTypes.get(lessontypesSpinner.getSelectedItemPosition() - 1);
        // 2018-01-23 -> list lessons
        HashMap<String, List<Lesson>> map = new HashMap<>();
        for (Lesson lesson : lessons) {
            String key = lesson.getStart().toString("yyyy-MM-dd");
            if (!map.containsKey(key)) map.put(key, new ArrayList<Lesson>());
            map.get(key).add(lesson);
        }

        // 1 -> Reservation
        HashSet<Long> set = new HashSet<>();
        for (Reservation reservation : reservations) {
            set.add(reservation.getLesson().getId());
        }

        for (Day day : days) {
            String key = day.getDate().toString("yyyy-MM-dd");
            if (map.containsKey(key)) {
                for (Lesson lesson : map.get(key)) {
                    if (lessonType == null ||
                            lesson.getLessonType().getId().longValue() == lessonType.getId().longValue()) {
                        if (!myReservations || set.contains(lesson.getId())) {
                            day.setHasEvent(true);
                        }
                    }
                }
            }
        }
        return Pair.create(map, set);
    }

    private void addDaysBefore(ArrayList<Day> days, DateTime first) {
        ArrayList<Day> list = new ArrayList<>();
        for (int i = first.getDayOfWeek() - 1; i > 0; i--) {
            Day day = new Day();
            day.setDate(first.minusDays(i));
            list.add(day);
        }
        days.addAll(list);
    }

    private void addDays(ArrayList<Day> days, DateTime first, DateTime last) {
        for (int i = 0; i < last.getDayOfMonth(); i++) {
            Day day = new Day();
            day.setDate(first.plusDays(i));
            days.add(day);
        }
    }

    private void addDaysAfter(ArrayList<Day> days, DateTime last) {
        DateTime first = last.plusDays(1);
        ArrayList<Day> list = new ArrayList<>();
        for (int i = 0; i < 42 - days.size(); i++) {
            Day day = new Day();
            day.setDate(first.plusDays(i));
            list.add(day);
        }
        days.addAll(list);
    }

    private ArrayList<Hour> createHours(DateTime date, Map<String, List<Lesson>> lessons, Set<Long> reservations) {
        ArrayList<Hour> list = new ArrayList<>();
        String key = date.toString("yyyy-MM-dd");
        boolean myReservations = myreservationsCheckBox.isChecked();
        if (lessons.containsKey(key)) {
            for (Lesson lesson : lessons.get(key)) {
                int p = lessontypesSpinner.getSelectedItemPosition();
                if (p == 0 || Session.getInstance().lessonTypes.get(p - 1).getId().longValue() == lesson.getLessonType().getId().longValue()) {
                    if (!myReservations || reservations.contains(lesson.getId())) {
                        Hour hour = new Hour();
                        hour.setName(lesson.getLessonType().getName());
                        hour.setColor(lesson.getLessonType().getColor());
                        hour.setHasEvent(false);
                        hour.setStart(lesson.getStart());
                        hour.setEnd(lesson.getEnd());
                        hour.setLesson(lesson);
                        System.out.println(hour.getStart().toString("hh:mm"));
                        list.add(hour);
                    }
                }
            }
        }
        return list;
    }



}