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
public class Diet implements Serializable {

    public enum Type {
        BALANCE, VEGETARIAN, VEGAN, HYPOCALORIC, DISSOCIATED, MACROBIOTIC, MEDITERRANEAN
    }

    private Long id;
    private Diet template;
    private Type type;
    private List<Meal> meals = new ArrayList<>();


    public Diet() { }

    public Diet(Faker faker, int position) {
        id = (long) position;
        template = null;
        type = Type.values()[faker.number().numberBetween(0, Type.values().length - 1)];
        meals = Meal.mocks(faker, 15 * 7);
    }

}
