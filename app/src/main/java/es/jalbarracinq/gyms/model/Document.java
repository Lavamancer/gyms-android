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
public class Document implements Serializable {

    private Long id;
    private String name;
    private String baseName;
    private String extension;
    private String description;
    private String type;
    private String tag;
    private String url;


    public Document() { }

    public Document(Faker faker, int position) {
        id = (long) position;
        baseName = faker.chuckNorris().fact();
        extension = faker.file().extension();
        name = getBaseName() + "." + getExtension();
        description = faker.witcher().quote();
        type = "";
        tag = "";
        url = "https://picsum.photos/420/320?image=" + position;
    }

}
