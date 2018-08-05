/*
 * Created by Juan Albarracin on 4/08/18 14:25
 * Copyright (c) 2018. All right reserved.
 *
 * Last modified 4/08/18 14:25
 */

package es.jalbarracinq.gyms.model;

import com.github.javafaker.Faker;

import java.util.HashMap;

import lombok.Data;

@Data
public class Configuration {

    public enum Name {
        FACEBOOK_URL, TWITTER_URL, HELP_URL, SHARE_ANDROID_URL
    }

    private Name name;
    private String value;


    public static HashMap<Name, String> mocks(Faker faker) {
        HashMap<Name, String> map = new HashMap<>();
        for (Name name : Name.values()) {
            map.put(name, faker.company().url());
        }
        return map;
    }
}
