/*
 * Created by Juan Albarracin on 4/08/18 14:25
 * Copyright (c) 2018. All right reserved.
 *
 * Last modified 4/08/18 14:25
 */

package es.jalbarracinq.gyms.model.response;

import com.github.javafaker.Faker;

import java.io.Serializable;
import java.util.List;

import es.jalbarracinq.gyms.model.Lesson;
import es.jalbarracinq.gyms.model.Reservation;
import lombok.Data;

@Data
public class CalendarResponse implements Serializable {

    private List<Lesson> lessons;
    private List<Reservation> reservations;


    public CalendarResponse() { }

    public CalendarResponse(Faker faker, int position) {
        lessons = Lesson.mocks(faker, 100);
        reservations = Reservation.mocks(faker, 10);
    }

}
