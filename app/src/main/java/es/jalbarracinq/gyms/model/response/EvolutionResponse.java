/*
 * Created by Juan Albarracin on 4/08/18 14:25
 * Copyright (c) 2018. All right reserved.
 *
 * Last modified 4/08/18 14:25
 */

package es.jalbarracinq.gyms.model.response;

import com.github.javafaker.Faker;

import java.io.Serializable;

import es.jalbarracinq.gyms.model.Evolution;
import lombok.Data;

@Data
public class EvolutionResponse implements Serializable {

    private Evolution evolution;
    private Evolution goal;


    public EvolutionResponse() { }

    public EvolutionResponse(Faker faker, int position) {
        evolution = new Evolution(faker, position);
        goal = new Evolution(faker, position);
    }
}
