/*
 * Created by Juan Albarracin on 4/08/18 14:25
 * Copyright (c) 2018. All right reserved.
 *
 * Last modified 4/08/18 14:25
 */

package es.jalbarracinq.gyms.model;

import com.github.javafaker.Faker;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Payment extends Auditing implements Serializable {

    public enum Status { SENT, ERROR, DENIED, APPROVED }

    private Long id;
    private Card card;
    private Status status;
    private Float price;
    private Float taxRate;
    private Float discountRate;


    public Payment() { }

    public Payment(Faker faker, int position) {
        id = (long) position;
        card = new Card(faker, position);
        status = Status.APPROVED;
        price = (float) (Math.random() * 50) + 5;
        taxRate = 21f;
        discountRate = 0f;
    }

}
