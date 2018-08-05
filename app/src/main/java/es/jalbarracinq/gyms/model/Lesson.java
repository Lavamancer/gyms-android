/*
 * Created by Juan Albarracin on 4/08/18 14:25
 * Copyright (c) 2018. All right reserved.
 *
 * Last modified 4/08/18 14:25
 */

package es.jalbarracinq.gyms.model;

import com.github.javafaker.Faker;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Lesson implements Serializable {

    private Long id;
    private LessonType lessonType;
    private Integer seats = 0;
    private DateTime start;
    private DateTime end;


    public Lesson() { }

    public Lesson(Faker faker, int position) {
        id = (long) position;
        lessonType = new LessonType(faker, position);
        seats = faker.number().numberBetween(1, 20);
        start = DateTime.now().withMillisOfDay(0).plusHours(7 + (position % 9)).plusDays(position / 9);
        end = start.plusMinutes(45);
    }

    public static List<Lesson> mocks(Faker faker, int count) {
        List<Lesson> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            list.add(new Lesson(faker, i));
        }
        return list;
    }

}
