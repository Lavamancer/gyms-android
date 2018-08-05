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

@Data
public class Product implements Serializable {

    private Long id;
    private String name;
    private String description;
    private String alternative;
    private String reference;
    private Float price;
    private Float taxRate;
    private String barcode;
    private Document image;


    public Product() { }

    public Product(Faker faker, int position) {
        id = (long) position;
        name = faker.twinPeaks().location();
        description = faker.twinPeaks().quote();
        alternative = faker.twinPeaks().quote();
        reference = faker.letterify("????????");
        price = (float) (Math.random() * 50) + 10f;
        taxRate = 21f;
        barcode = faker.letterify("?????????????????????");
        image = new Document(faker, position);
    }

}

