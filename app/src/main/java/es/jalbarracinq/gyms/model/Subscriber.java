/*
 * Created by Juan Albarracin on 4/08/18 14:25
 * Copyright (c) 2018. All right reserved.
 *
 * Last modified 4/08/18 14:25
 */

package es.jalbarracinq.gyms.model;

import com.github.javafaker.Faker;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Subscriber extends User {

    private Diet diet;
    private Facility facility;


    public Subscriber() { }

    public Subscriber(Faker faker, int position) {
        super(faker, position);
        diet = new Diet(faker, position);
        facility = new Facility(faker, position);
    }

}
