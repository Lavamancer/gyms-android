/*
 * Created by Juan Albarracin on 4/08/18 14:25
 * Copyright (c) 2018. All right reserved.
 *
 * Last modified 4/08/18 14:25
 */

package es.jalbarracinq.gyms.model.response;

import com.github.javafaker.Faker;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import es.jalbarracinq.gyms.model.Card;
import es.jalbarracinq.gyms.model.Configuration;
import es.jalbarracinq.gyms.model.Facility;
import es.jalbarracinq.gyms.model.LessonType;
import es.jalbarracinq.gyms.model.Subscriber;
import es.jalbarracinq.gyms.model.Token;
import lombok.Data;

@Data
public class LoginResponse implements Serializable {

    @SerializedName("oauth2AccessToken")
    private Token token;
    private List<Facility> facilities;
    private List<LessonType> lessonTypes;
    private Subscriber subscriber;
    private List<Card> cards;
    private HashMap<Configuration.Name, String> configurations;


    public LoginResponse() { }

    public LoginResponse(Faker faker, int position) {
        token = new Token(faker, position);
        facilities = Facility.mocks(faker, 6);
        lessonTypes = LessonType.mocks(faker, 10);
        subscriber = new Subscriber(faker, position);
        cards = Card.mocks(faker, 3);
        configurations = Configuration.mocks(faker);
    }
}
