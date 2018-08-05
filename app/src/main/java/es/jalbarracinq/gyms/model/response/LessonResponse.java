/*
 * Created by Juan Albarracin on 4/08/18 14:25
 * Copyright (c) 2018. All right reserved.
 *
 * Last modified 4/08/18 14:25
 */

package es.jalbarracinq.gyms.model.response;

import com.github.javafaker.Faker;

import java.io.Serializable;
import java.util.ArrayList;

import es.jalbarracinq.gyms.model.Facility;
import es.jalbarracinq.gyms.model.Lesson;
import es.jalbarracinq.gyms.model.Reservation;
import es.jalbarracinq.gyms.model.Subscription;
import lombok.Data;

@Data
public class LessonResponse implements Serializable {

    private Lesson lesson;
    private Integer reservations;
    private Reservation reservation;
    private ArrayList<Facility> facilities;
    private ArrayList<Subscription> subscriptions;


    public LessonResponse() { }

    public LessonResponse(Faker faker, int position) {
        lesson = new Lesson(faker, position);
        reservation = Math.random() > 0.5f ? new Reservation(faker, 1) : null;
        facilities = Facility.mocks(faker, faker.number().numberBetween(1, 4));
        subscriptions = Subscription.mocks(faker, faker.number().numberBetween(0, 3));
        reservations = faker.number().numberBetween(0, lesson.getSeats() - 1);
    }
}
