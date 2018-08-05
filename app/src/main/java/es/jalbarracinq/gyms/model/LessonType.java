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
import java.util.List;

import es.jalbarracinq.gyms.service.tool.ColorTool;
import lombok.Data;

@Data
public class LessonType implements Serializable {

    private Long id;
    private String name;
    private String alternative;
    private String description;
    private String color;
    private Product product;


    public LessonType(Faker faker, int position) {
        id = (long) position;
        name = faker.gameOfThrones().dragon();
        color = ColorTool.mock(position);
        alternative = faker.gameOfThrones().quote();
        description = faker.witcher().quote();
        product = new Product(faker, position);
    }

    public static List<LessonType> mocks(Faker faker, int count) {
        List<LessonType> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            list.add(new LessonType(faker, i));
        }
        return list;
    }

}
