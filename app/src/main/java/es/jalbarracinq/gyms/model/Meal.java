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
public class Meal implements Serializable {

    public enum Type {
        BREAKFAST, BRUNCH, LUNCH, SNACK, DINNER
    }

    private Long id;
    private Type type;
    private String name;
    private String description;
    private Integer day;


    public Meal() { }

    public Meal(Faker faker, int position) {
        id = (long) position;
        type = Type.values()[position % Type.values().length];
        name = faker.food().ingredient();
        description = faker.food().measurement();
        day = (int) (position / 15f) + 1;
    }

    public static List<Meal> mocks(Faker faker, int count) {
        List<Meal> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            list.add(new Meal(faker, i));
        }
        return list;
    }

}
