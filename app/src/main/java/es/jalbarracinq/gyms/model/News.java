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
public class News implements Serializable {

    private Long id;
    private String title;
    private String author;
    private String description;
    private String content;
    private Document image;
    private Document thumbnail;
    private DateTime date;
    private String url;


    public News() { }

    public News(Faker faker, int position) {
        id = (long) position;
        title = faker.book().title();
        author = faker.book().author();
        description = faker.lorem().sentence(10, 100);
        content = faker.lorem().sentence(10, 100);
        image = new Document(faker, position);
        thumbnail = new Document(faker, position);
        date = DateTime.now().minusDays(faker.number().numberBetween(1, 20)).minusHours(faker.number().numberBetween(0, 20));
        url = faker.company().url();
    }

    public static ArrayList<News> mocks(Faker faker, int count) {
        ArrayList<News> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            list.add(new News(faker, i));
        }
        return list;
    }

}
