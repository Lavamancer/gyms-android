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

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Subscription extends Auditing implements Serializable {

    private Long id;
    private SubscriptionType subscriptionType;
    private DateTime start;
    private DateTime end;
    private DateTime paidAt;
    private DateTime pausedFrom;
    private DateTime pausedTo;
    private Integer remainingUsages;    // usos que le quedan del bono
    private Payment payment;


    public Subscription() { }

    public Subscription(Faker faker, int position) {
        id = (long) position;
        subscriptionType = new SubscriptionType(faker, position);
        start = DateTime.now().minusDays(faker.number().numberBetween(1, 5));
        end = start.plusMonths(3);
        paidAt = new DateTime(start);
        pausedFrom = Math.random() > 0.9f ? start.plusDays(10) : null;
        pausedTo = pausedFrom != null ? pausedFrom.plus(15) : null;
        remainingUsages = subscriptionType.getUsages() != null ? faker.number().numberBetween(0, subscriptionType.getUsages()) : null;
        payment = new Payment(faker, position);
    }

    public static ArrayList<Subscription> mocks(Faker faker, int count) {
        ArrayList<Subscription> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            list.add(new Subscription(faker, i));
        }
        return list;
    }

}
