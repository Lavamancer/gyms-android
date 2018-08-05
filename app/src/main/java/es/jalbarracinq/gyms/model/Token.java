/*
 * Created by Juan Albarracin on 4/08/18 14:25
 * Copyright (c) 2018. All right reserved.
 *
 * Last modified 4/08/18 14:25
 */

package es.jalbarracinq.gyms.model;

import com.github.javafaker.Faker;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Data;

@Data
public class Token implements Serializable {

    @SerializedName("access_token")
    private String accessToken;
    @SerializedName("refresh_token")
    private String refreshToken;


    public Token() { }

    public Token(Faker faker, int position) {
        accessToken = "Bearer " + faker.letterify("????????-????-????-????-????????????");
        refreshToken = faker.letterify("????????-????-????-????-????????????");
    }


}
