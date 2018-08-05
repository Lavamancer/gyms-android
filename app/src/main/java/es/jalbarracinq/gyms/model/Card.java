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
public class Card implements Serializable {

    public enum Type { VISA, MASTER_CARD }

    private Long id;
    private String name; // alias
    private String holder;
    private String number;
    private Integer month;
    private Integer year;
    private String verification;
    private Type type;


    public Card() { }

    public Card(Faker faker, int position) {
        id = (long) position;
        name = faker.ancient().god();
        holder = faker.ancient().hero();
        number = faker.numerify("####");
        month = faker.number().numberBetween(1, 12);
        year = faker.number().numberBetween(DateTime.now().getYear(), DateTime.now().getYear() + 20);
        verification = faker.numerify("###");
        type = faker.bool().bool() ? Type.VISA : Type.MASTER_CARD;
    }

    public static List<Card> mocks(Faker faker, int count) {
        List<Card> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            list.add(new Card(faker, i));
        }
        return list;
    }

}
