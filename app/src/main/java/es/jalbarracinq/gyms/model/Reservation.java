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
public class Reservation implements Serializable {

    private Long id;
    private Lesson lesson;
    private DateTime date;
    private Boolean waiting = false;


    public Reservation() { }

    public Reservation(Faker faker, int position) {
        id = (long) position;
        lesson = new Lesson(faker, position);
        date = DateTime.now().minusHours(faker.number().numberBetween(10, 100));
        waiting = Math.random() > 0.95f;
    }

    public static List<Reservation> mocks(Faker faker, int count) {
        List<Reservation> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            list.add(new Reservation(faker, i));
        }
        return list;
    }

}
