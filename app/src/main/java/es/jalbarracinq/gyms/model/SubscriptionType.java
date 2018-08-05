/*
 * Created by Juan Albarracin on 4/08/18 14:25
 * Copyright (c) 2018. All right reserved.
 *
 * Last modified 4/08/18 14:25
 */

package es.jalbarracinq.gyms.model;

import com.github.javafaker.Faker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class SubscriptionType implements Serializable {

    private Long id;
    private Integer days;               // 1 2 3 4 5 6 7 y si no contabiliza dias de semana seria null
    private Integer usages;             // si es tipo bono 1, 2, 3 y si no es bono es null
    private Integer months;             // numero de meses de duracion
    private Product product;
    private List<LessonType> lessonTypes;


    public SubscriptionType() { }

    public SubscriptionType(Faker faker, int position) {
        id = (long) position;
        days = Math.random() > 0.3f ? null : faker.number().numberBetween(1, 4);
        usages = (days == null && Math.random() > 0.3f) ? faker.number().numberBetween(1, 20) : null;
        months = faker.number().numberBetween(3, 6);
        product = new Product(faker, position);
        lessonTypes = LessonType.mocks(faker, faker.number().numberBetween(1, 4));
    }

    public static ArrayList<SubscriptionType> mocks(Faker faker, int count) {
        ArrayList<SubscriptionType> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            list.add(new SubscriptionType(faker, i));
        }
        return list;
    }

}
