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

import lombok.Data;

@Data
public class Facility implements Serializable {

    private Long id;
    private String name;


    public Facility(Faker faker, int position) {
        id = (long) position;
        name = faker.gameOfThrones().city();
    }

    public static ArrayList<Facility> mocks(Faker faker, int count) {
        ArrayList<Facility> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            list.add(new Facility(faker, i));
        }
        return list;
    }

}
