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

@Data
public class Evolution implements Serializable {

    private Long id;
    private Integer height;
    private Integer weight;
    private Integer fat;
    private Float mass;
    private Integer waist;
    private Integer stomach;
    private Integer chest;
    private Integer arm;
    private Integer leg;
    private DateTime date;
    private Boolean goal = false;
    private Document frontal;
    private Document side;
    private Document back;
    private Monitor monitor;


    public Evolution() { }

    public Evolution(Faker faker, int position) {
        id = (long) position;
        height = faker.number().numberBetween(165, 199);
        weight = faker.number().numberBetween(60, 100);
        fat = faker.number().numberBetween(30, 50);
        mass = (float) (faker.number().numberBetween(50, 70) + Math.random());
        waist = faker.number().numberBetween(30, 50);
        stomach = faker.number().numberBetween(30, 50);
        chest = faker.number().numberBetween(30, 50);
        arm = faker.number().numberBetween(10, 20);
        leg = faker.number().numberBetween(10, 20);
        date = new DateTime(faker.date().between(DateTime.now().minusDays(10).toDate(), DateTime.now().plusDays(10).toDate()));
        goal = Math.random() > 0.8f;
        frontal = new Document(faker, 1);
        side = new Document(faker, 2);
        back = new Document(faker, 3);
        monitor = new Monitor(faker, position);
    }

    public static ArrayList<Evolution> mocks(Faker faker, int count) {
        ArrayList<Evolution> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            list.add(new Evolution(faker, i));
        }
        return list;
    }

}
