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

import lombok.Data;

@Data
public class User implements Serializable {

    public enum Gender { MALE, FEMALE }

    private Long id;
    private String name;
    private String lastname;
    private String email;
    private String password;
    private String personalId;
    private String description;
    private String nickname;
    private String phone;
    private String address;
    private String state;
    private String zip;
    private String city;
    private DateTime birthdate;
    private Gender gender;
    private Document image;
    private Document thumbnail;


    public User() { }

    public User(Faker faker, int position) {
        id = (long) position;
        name = faker.name().name();
        lastname = faker.name().lastName();
        email = faker.internet().emailAddress();
        personalId = faker.numerify("########") + faker.letterify("?");
        description = faker.witcher().quote();
        nickname = getName();
        phone = faker.phoneNumber().cellPhone();
        address = faker.address().fullAddress();
        state = faker.address().state();
        zip = faker.address().zipCode();
        city = faker.address().city();
        birthdate = new DateTime(faker.date().between(DateTime.now().minusYears(40).toDate(), DateTime.now().minusYears(19).toDate()));
        gender = Math.random() > 0.5f ? Gender.MALE : Gender.FEMALE;
        image = new Document(faker, position);
        thumbnail = new Document(faker, position);
    }

}
